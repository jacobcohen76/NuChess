package nuchess.engine;

import nuchess.util.StringUtil;

public final class FENParser
{
	private static final int KING_CASTLE_WHITE    = 0b1000;
	private static final int QUEEN_CASTLE_WHITE   = 0b0100;
	private static final int KING_CASTLE_BLACK    = 0b0010;
	private static final int QUEEN_CASTLE_BLACK   = 0b0001;
	
	public static final int LAYOUT                = 0;
	public static final int TO_MOVE               = 1;
	public static final int CASTLING_RIGHTS       = 2;
	public static final int EP_TARGET             = 3;
	public static final int HALFMOVE_CLOCK        = 4;
	public static final int FULLMOVE_NUMBER       = 5;
	
	public static final boolean isDigit(char ch)
	{
		return '0' <= ch && ch <= '9';
	}
	
	public static final boolean isFile(char ch)
	{
		return 'a' <= ch && ch <= 'h';
	}
	
	public static final boolean isRank(char ch)
	{
		return '1' <= ch && ch <= '8';
	}
	
	public static final boolean isColor(char ch)
	{
		return ch == 'w' || ch == 'b';
	}
	
	public static final boolean isPiece(char ch)
	{
		switch(ch)
		{
			case 'p':
			case 'r':
			case 'n':
			case 'b':
			case 'q':
			case 'k':
			case 'P':
			case 'R':
			case 'N':
			case 'B':
			case 'Q':
			case 'K':
				return true;
			default:
				return false;
		}
	}
	
	public static final int piece(char pieceCharacter)
	{
		switch(pieceCharacter)
		{
			case 'K':	return Piece.WHITE_KING;
			case 'Q':	return Piece.WHITE_QUEEN;
			case 'R':	return Piece.WHITE_ROOK;
			case 'B':	return Piece.WHITE_BISHOP;
			case 'N':	return Piece.WHITE_KNIGHT;
			case 'P':	return Piece.WHITE_PAWN;
			case 'k':	return Piece.BLACK_KING;
			case 'q':	return Piece.BLACK_QUEEN;
			case 'r':	return Piece.BLACK_ROOK;
			case 'b':	return Piece.BLACK_BISHOP;
			case 'n':	return Piece.BLACK_KNIGHT;
			case 'p':	return Piece.BLACK_PAWN;
			default:	return Piece.NULL;
		}
	}
	
	public static final int castlingRightBit(char castlingRightCharacter)
	{
		switch(castlingRightCharacter)
		{
			case 'K':	return KING_CASTLE_WHITE;
			case 'Q':	return QUEEN_CASTLE_WHITE;
			case 'k':	return KING_CASTLE_BLACK;
			case 'q':	return QUEEN_CASTLE_BLACK;
			default:	return 0;
		}
	}
	
	public static final int parseFENcastlingRights(String FENcastlingRights)
	{
		int castlingRights = 0;
		for(int i = 0; i < FENcastlingRights.length(); i++)
		{
			castlingRights |= castlingRightBit(FENcastlingRights.charAt(i));
		}
		return castlingRights;
	}
	
	public static final int parseFENtoMove(String FENtoMove)
	{
		return FENtoMove.equals("w") ? Color.WHITE : Color.BLACK;
	}
	
	public static final int parseFENepTarget(String FENepTarget)
	{
		return FENepTarget.equals("-") ? Square.NULL : Square.makeSquare(FENepTarget);
	}
	
	public static final int parseFENhalfmoveClock(String FENhalfmoveClock)
	{
		return Integer.valueOf(FENhalfmoveClock);
	}
	
	public static final int parseFENfullmoveNumber(String FENfullmoveNumber)
	{
		return Integer.valueOf(FENfullmoveNumber);
	}
	
	//legacy code below this point.
	
	public static final void loadBoardPostion(CBoard board, String FEN)
	{
		board.clearBitboards();
		int i = 0, rank = 7, file = 0;
		char ch;
		while(i < FEN.length() && (ch = FEN.charAt(i++)) != ' ')
		{
			if(ch == '/')
			{
				rank--;
				file = 0;
			}
			else if(isDigit(ch))
			{
				file += ch - '0';
			}
			else
			{
				board.put(piece(ch), Square.makeSquare(rank, file));
				file++;
			}
		}
	}
	
	public static final int parseToMove(String FENtm)
	{
		return FENtm.equals("w") ? 0 : 1;
	}
	
	public static final int parseCastlingRights(String FENcr)
	{
		int castlingRights = 0, i = 0;
		while(i < FENcr.length())
		{
			castlingRights |= castlingRightBit(FENcr.charAt(i));
			i++;
		}
		return castlingRights;
	}
	
	public static final int parseEnPassantSquare(String FENeps)
	{
		return FENeps.equals("-") ? Square.NULL : Square.makeSquare(FENeps);
	}
	
	public static final int parseFullmoveNum(String FENfmn)
	{
		return (Integer.valueOf(FENfmn) - 1) << 1;
	}
	
	public static final int parseHalfmoveClock(String FENhmc)
	{
		return Integer.valueOf(FENhmc);
	}
	
	public static final String formatBoardPosition(CBoard board)
	{
		int emptyCount = 0;
		StringBuilder sb = new StringBuilder();
		for(int rank = 7; 0 <= rank; rank--)
		{
			for(int file = 0; file < 8; file++)
			{
				if(board.isEmpty(Square.makeSquare(rank, file)))
				{
					emptyCount++;
				}
				else
				{
					sb.append(emptyCount == 0 ? "" : emptyCount);
					sb.append(FENFormatter.pieceCharacter(board.pieceAt(Square.makeSquare(rank, file))));
					emptyCount = 0;
				}
			}
			sb.append(emptyCount == 0 ? "" : emptyCount);
			sb.append(rank == 0 ? "" : "/");
			emptyCount = 0;
		}
		return sb.toString();
	}
	
	public static final String formatCastlingRights(int castlingRights)
	{
		return	((castlingRights & KING_CASTLE_WHITE)   != 0 ? "K" : "") +
				((castlingRights & QUEEN_CASTLE_WHITE)  != 0 ? "Q" : "") +
				((castlingRights & KING_CASTLE_BLACK)   != 0 ? "k" : "") +
				((castlingRights & QUEEN_CASTLE_BLACK)  != 0 ? "q" : "") +
				(castlingRights == 0 ? "-" : "");
	}
	
	public static final String formatToMove(int toMove)
	{
		return toMove == Color.WHITE ? "w" : "b";
	}
	
	public static final String formatFullmoveNum(int ply)
	{
		return String.valueOf((ply >> 1) + 1);
	}
	
	public static final String formatEnPassantSquare(int square)
	{
		return square == Square.NULL ? "-" : Square.coord(square);
	}
	
	public static final String formatHalfmoveClock(int halfmoveClock)
	{
		return String.valueOf(halfmoveClock);
	}
	
	public static final boolean isBase10(String numberString)
	{
		for(int i = 0; i < numberString.length(); i++)
		{
			if(!isDigit(numberString.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isCoord(String coordString)
	{
		return coordString.length() == 2 && isFile(coordString.charAt(0)) && isRank(coordString.charAt(1));
	}
	
	private static final int castlingRightPriority(char ch)
	{
		switch(ch)
		{
			case 'K':	return +3;
			case 'Q':	return +2;
			case 'k':	return +1;
			case 'q':	return  0;
			case '-':	return Integer.MIN_VALUE;
			default:	return Integer.MAX_VALUE;
		}
	}
	
	private static final boolean isValidCastlingRightsOrder(String crString)
	{
		int i = 0, prevState = Integer.MAX_VALUE, currState = prevState - 1;
		while(i < crString.length() && currState < prevState)
		{
			prevState = currState;
			currState = castlingRightPriority(crString.charAt(i++));
		}
		return currState < prevState;
	}
	
	public static final boolean isCastlingRights(String crString)
	{
		return	crString.length() != 0 &&
				StringUtil.contains(crString, '-') ?
					crString.length() == 1 :
					isValidCastlingRightsOrder(crString);
	}
	
	public static final boolean isToMove(String toMoveString)
	{
		return toMoveString.length() == 1 && isColor(toMoveString.charAt(0));
	}
}
