package nuchess.engine;

public class FENFormatter
{
	public static final int LONGEST_FEN_LAYOUT    = 70;
	public static final int LONGEST_FEN_STRING    = 87;
	public static final int ASCII_BOARD_LENGTH    = 129;
	
	private static final int KING_CASTLE_WHITE    = 0b1000;
	private static final int QUEEN_CASTLE_WHITE   = 0b0100;
	private static final int KING_CASTLE_BLACK    = 0b0010;
	private static final int QUEEN_CASTLE_BLACK   = 0b0001;
	
	public static final char bitboardCharacter(int bitboard)
	{
		switch(bitboard)
		{
			case Color.WHITE:				return 'W';
			case Color.BLACK:				return 'B';
			case Piece.WHITE_PAWN:			return 'P';
			case Piece.BLACK_PAWN:			return 'p'; 
			case Piece.WHITE_KNIGHT:		return 'N';
			case Piece.BLACK_KNIGHT:		return 'n';
			case Piece.WHITE_BISHOP:		return 'B';
			case Piece.BLACK_BISHOP:		return 'b';
			case Piece.WHITE_ROOK:			return 'R'; 
			case Piece.BLACK_ROOK:			return 'r'; 
			case Piece.WHITE_QUEEN:			return 'Q';
			case Piece.BLACK_QUEEN:			return 'q';
			case Piece.WHITE_KING:			return 'K';
			case Piece.BLACK_KING:			return 'k';
			case Chessboard.OCC:			return 'x';
			default:						return '?';
		}
	}
	
	public static final char pieceCharacter(int piece)
	{
		switch(piece)
		{
			case Piece.WHITE_PAWN:			return 'P';
			case Piece.BLACK_PAWN:			return 'p'; 
			case Piece.WHITE_KNIGHT:		return 'N';
			case Piece.BLACK_KNIGHT:		return 'n';
			case Piece.WHITE_BISHOP:		return 'B';
			case Piece.BLACK_BISHOP:		return 'b';
			case Piece.WHITE_ROOK:			return 'R'; 
			case Piece.BLACK_ROOK:			return 'r'; 
			case Piece.WHITE_QUEEN:			return 'Q';
			case Piece.BLACK_QUEEN:			return 'q';
			case Piece.WHITE_KING:			return 'K';
			case Piece.BLACK_KING:			return 'k';
			default:						return '?';
		}
	}
	
	public static final String formatFENcastlingRights(int castlingRights)
	{
		return	((castlingRights & KING_CASTLE_WHITE)  != 0 ? "K" : "") +
				((castlingRights & QUEEN_CASTLE_WHITE) != 0 ? "Q" : "") +
				((castlingRights & KING_CASTLE_BLACK)  != 0 ? "k" : "") +
				((castlingRights & QUEEN_CASTLE_BLACK) != 0 ? "q" : "") +
				(castlingRights == 0 ? "-" : "");
	}
	
	public static final String formatFENtoMove(int toMove)
	{
		return toMove == Color.WHITE ? "w" : "b";
	}
	
	public static final String formatFENepTarget(int epTarget)
	{
		return epTarget == Square.NULL ? "-" : Square.coord(epTarget);
	}
	
	public static final String formatFENhalfmoveClock(int halfmoveClock)
	{
		return "" + halfmoveClock;
	}
	
	public static final String formatFENfullmoveNumber(int ply)
	{
		return "" + ((ply >> 1) + 1);
	}
}
