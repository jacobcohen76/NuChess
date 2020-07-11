package nuchess.engine;

import util.StringUtil;

public final class FENParser
{	
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
	
	public static final int pieceCode(char pieceChar)
	{
		switch(pieceChar)
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
	
	public static final char pieceChar(int pieceCode)
	{
		switch(pieceCode)
		{
			case Piece.WHITE_KING:			return 'K';
			case Piece.WHITE_QUEEN:			return 'Q';
			case Piece.WHITE_ROOK:			return 'R'; 
			case Piece.WHITE_BISHOP:		return 'B';
			case Piece.WHITE_KNIGHT:		return 'N';
			case Piece.WHITE_PAWN:			return 'P'; 
			case Piece.BLACK_KING:			return 'k';
			case Piece.BLACK_QUEEN:			return 'q';
			case Piece.BLACK_ROOK:			return 'r'; 
			case Piece.BLACK_BISHOP:		return 'b';
			case Piece.BLACK_KNIGHT:		return 'n';
			case Piece.BLACK_PAWN:			return 'p'; 
			default:						return '?';
		}
	}
	
	public static final int castlingRightsBit(char c)
	{
		switch(c)
		{
			case 'K':	return 0x8;
			case 'Q':	return 0x4;
			case 'k':	return 0x2;
			case 'q':	return 0x1;
			default:	return 0x0;
		}
	}
	
	public static final void loadBoardPostion(CBoard board, String FENpos)
	{
		board.clearBitboards();
		
		char c;
		int i = 0, rank = 7, file = 0;
		while(i < FENpos.length() && (c = FENpos.charAt(i++)) != ' ')
		{
			if(c == '/')
			{
				rank--;
				file = 0;
			}
			else if(isDigit(c))
			{
				file += c - '0';
			}
			else
			{
				board.put(pieceCode(c), Square.makeSquare(rank, file));
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
			castlingRights |= castlingRightsBit(FENcr.charAt(i));
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
		StringBuilder FEN = new StringBuilder();
		int emptyCount = 0;
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
					FEN.append(emptyCount == 0 ? "" : emptyCount);
					FEN.append(pieceChar(board.pieceAt(Square.makeSquare(rank, file))));
					emptyCount = 0;
				}
			}
			FEN.append(emptyCount == 0 ? "" : emptyCount);
			FEN.append(rank == 0 ? "" : "/");
			emptyCount = 0;
		}
		return FEN.toString();
	}
	
	public static final String formatCastlingRights(int castlingRights)
	{
		return	(((castlingRights >> 3) & 1) == 1 ? "K" : "") +
				(((castlingRights >> 2) & 1) == 1 ? "Q" : "") +
				(((castlingRights >> 1) & 1) == 1 ? "k" : "") +
				(((castlingRights >> 0) & 1) == 1 ? "q" : "") +
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
