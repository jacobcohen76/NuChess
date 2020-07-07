package nuchess.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 
 * @author Jacob Cohen
 *
 */
public class ChessEngine implements Engine<CMove>
{
	public CBoard board;
	public Stack<Integer> unmakeInfo;
	public int ply;
	public int posInfo;
	
	public ChessEngine(CBoard board, Stack<Integer> unmakeInfo, int ply, int posInfo)
	{
		this.board = board;
		this.unmakeInfo = unmakeInfo;
		this.ply = ply;
		this.posInfo = posInfo;
	}
	
	public ChessEngine()
	{
		this(new CBoard(), new Stack<Integer>(), 0, DEFAULT_POSINFO);
	}
	
	public ChessEngine(String FEN)
	{
		this();
		loadFEN(FEN);
	}
	
	public final boolean isGameOver()
	{
		return	isDraw() ||
				false;
	}
	
	public final boolean isDraw()
	{
		return 100 <= halfmoveClock();
	}
	
	public final List<CMove> generateMoves()
	{
		return MoveGenerator.generateMoves(board, toMove(), castlingRights(), epSquare(), inCheck());
	}
	
	public final List<CMove> generateLegalMoves()
	{
		List<CMove> moves = generateMoves();
		Iterator<CMove> itr = moves.iterator();
		while(itr.hasNext())
		{
			if(canMake(itr.next()) == false)
			{
				itr.remove();
			}
		}
		return moves;
	}
	
	public final boolean canMake(CMove move)
	{
		return	(board.contains(Piece.WHITE_KING + toMove(), move.from()) ?
					(!board.isAttacked(move.to(), toMove() ^ 1, board.occAfter(move))) :
					(board.attacksTo(board.kingSquare(toMove()), toMove() ^ 1, board.occAfter(move)) & ~move.captureBitboard()) == 0);
	}
	
	public final boolean canUnmake(CMove move)
	{
		return unmakeInfo.isEmpty() == false;
	}
	
	public final boolean inCheck()
	{
		return board.isAttacked(board.kingSquare(toMove()), toMove() ^ 1);
	}
	
	public final boolean isCheckMove(CMove move)
	{
		return board.attacksTo(board.kingSquare(toMove() ^ 1), toMove(), board.occAfter(move)) != 0;
	}
	
	public final void make(CMove move)
	{
		unmakeInfo.push(posInfo);
		
		int moving = board.pieceAt(move.from());
		int mcolor = board.colorAt(move.from());
		int capturedSquare = move.isCapture() ? move.captureTarget() : Square.NULL;
		int capturedPiece = board.pieceAt(capturedSquare);
		
		if(move.isCapture())
		{
			board.capture(capturedPiece, capturedSquare);
			posInfo &= MoveMaker.castlingRights(move.captureTarget());
		}
		
		board.move(moving, move.from(), move.to());
		MoveMaker.makeCastle(board, move, mcolor);
		MoveMaker.makePromotion(board, move, mcolor);
		
		posInfo = posInfo 
				& 0x000F0000
				& MoveMaker.castlingRights(move.from())
				| MoveMaker.updateHalfmoveClock(move, moving, halfmoveClock())
				| MoveMaker.updateCapturedPieceInfo(capturedPiece, capturedSquare)
				| MoveMaker.updateEPtarget(move, mcolor);
		ply++;
	}
	
	public final void unmake(CMove move)
	{
		int mcolor = board.colorAt(move.to());
		MoveMaker.unmakePromotion(board, move, mcolor);
		MoveMaker.unmakeCastle(board, move, mcolor);
		int moving = board.pieceAt(move.to());
		
		board.move(moving, move.to(), move.from());
		
		if(move.isCapture())
		{
			board.put(capturedPiece(), capturedSquare());
		}
		
		posInfo = unmakeInfo.pop();
		ply--;
	}
	
	public final boolean inCheck(int side)
	{
		return board.isAttacked(board.kingSquare(side), side ^ 1);
	}
	
	public final int halfmoveClock()
	{
		return posInfo & 0xFF;
	}
	
	public final int epSquare()
	{
		return (posInfo >> 8) & 0xFF;
	}
	
	public final int castlingRights()
	{
		return (posInfo >> 16) & 0xF;
	}
	
	public final int capturedSquare()
	{
		return (posInfo >> 20) & 0xFF;
	}
	
	public final int capturedPiece()
	{
		return (posInfo >> 28) & 0xF;
	}
	
	public final int toMove()
	{
		return ply & 1;
	}
	
	/**
	 * Gets the FEN based off the current state of this engine.
	 * 
	 * @return the FEN of this engine's current state
	 * @see <a href="https://en.wikipedia.org/wiki/Forsyth-Edwards_Notation">https://en.wikipedia.org/wiki/Forsyth-Edwards_Notation</a>
	 */
	public final String getFEN()
	{
		return	FENParser.formatBoardPosition(board) + " " +
				FENParser.formatToMove(toMove()) + " " +
				FENParser.formatCastlingRights(castlingRights()) + " " +
				FENParser.formatEnPassantSquare(epSquare()) + " " +
				FENParser.formatHalfmoveClock(halfmoveClock()) + " " +
				FENParser.formatFullmoveNum(ply);
	}
	
	/**
	 * This function assumes the input FEN position is both free of syntax errors
	 * and not an illegal position.  Before inputing a FEN position into this function
	 * you might want to check to make sure it is free of syntax errors and not
	 * representing an illegal position by passing it through both a syntax checker and
	 * a position checker.
	 * <p>
	 * The input FEN position is trimmed, and then split into an array of elements
	 * delimited by the regex \s+. The resulting array of elements should be comprised
	 * of 6 parts that will be interpreted in the following order.
	 * 
	 * <li>elements[0] = FEN board position</li>
	 * <li>elements[1] = side to move ("w" or "b")</li>
	 * <li>elements[2] = castling rights</li>
	 * <li>elements[3] = en passant square</li>
	 * <li>elements[4] = halfmove clock</li>
	 * <li>elements[5] = fullmove number</li>
	 * 
	 * @param FEN the FEN position to load into this engine
	 * @see <a href="https://en.wikipedia.org/wiki/Forsyth-Edwards_Notation">https://en.wikipedia.org/wiki/Forsyth-Edwards_Notation</a>
	 */
	public final void loadFEN(String FEN)
	{
		String[] elements = FEN.trim().split("\\s+");
		FENParser.loadBoardPostion(board, elements[0]);
		ply = FENParser.parseToMove(elements[1]) + FENParser.parseFullmoveNum(elements[5]);
		posInfo = FENParser.parseHalfmoveClock(elements[4]);
		posInfo |= FENParser.parseCastlingRights(elements[2]) << 16;
		posInfo |= FENParser.parseEnPassantSquare(elements[3]) << 8;
		posInfo |= (Piece.NULL << 28) | (Square.NULL << 20);
	}
	
	public String getSAN(CMove move)
	{
		StringBuilder SAN = new StringBuilder();
		int moving = board.pieceAt(move.from());
		if(Piece.pieceType(moving) == Piece.PAWN)
		{
			if(move.isCapture())
			{
				SAN.append(Square.fileCharacter(Square.file(move.from())));
			}
		}
		else
		{
			SAN.append(FENParser.pieceChar(moving));
			long alt = Attacks.attacks(moving, move.to(), board.occ()) & board.bitboard(moving) & ~(1L << move.from());
			boolean disambiguateFile = false, disambiguateRank = false;
			int numAlts = 0;
			while(alt != 0)
			{
				int square = Bits.bitscanForward(alt);
				disambiguateFile |= Square.file(square) != Square.rank(move.from());
				disambiguateRank |= Square.rank(square) != Square.rank(move.from());
				alt &= alt ^ -alt;
				numAlts++;
			}
			if(disambiguateFile && disambiguateRank && 2 <= numAlts)
			{
				SAN.append(Square.fileCharacter(Square.file(move.from())));
				SAN.append(Square.rank(move.from()) + 1);
			}
			else if(disambiguateFile)
			{
				SAN.append(Square.fileCharacter(Square.file(move.from())));
			}
			else if(disambiguateRank)
			{
				SAN.append(Square.rank(move.from()) + 1);
			}
		}
		if(move.isCapture())
		{
			SAN.append('x');
		}
		SAN.append(Square.coord(move.to()));
		if(move.flags() == CMove.EP_CAPTURE)
		{
			SAN.append("e.p.");
		}
		else if(move.isPromo())
		{
			SAN.append(FENParser.pieceChar(Piece.pieceCode(move.promotedTo(), toMove())));
		}
		if(isCheckMove(move))
		{
			SAN.append('+');
		}
		return SAN.toString();
	}
	
	public static final int DEFAULT_POSINFO = (Piece.NULL << 28) | (Square.NULL << 20) | (0xF << 16) | (Square.NULL << 8);
}
