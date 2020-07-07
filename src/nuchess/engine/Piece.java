package nuchess.engine;

/**
 * Stores read only final values for encoding different PieceTypes
 * and PieceCodes. A PieceType is an integer id ranging from 0-6 for
 * for the 6 different types of pieces, where 0 represents a NULL piece.
 * A PieceCode is an integer id where the bit 0 is 0 for Color.WHITE or 1
 * for Color.BLACK, and bit 3-1 is reserved for the PieceType. This encoding
 * scheme for the PieceCode ensures that all even PieceCodes are Color.WHITE and
 * all odd PieceCodes are Color.BLACK. To extract the PieceType from a PieceCode,
 * simply divide the PieceType by 2, or shift right 1.
 * 
 * @author Jacob Cohen
 *
 */
public final class Piece
{
	public static final int NULL				= 0x0;
	public static final int PAWN				= 0x1;
	public static final int KNIGHT				= 0x2;
	public static final int BISHOP				= 0x3;
	public static final int ROOK				= 0x4;
	public static final int QUEEN				= 0x5;
	public static final int KING				= 0x6;
	
	public static final int WHITE_PAWN			= (PAWN   << 1) + Color.WHITE;
	public static final int WHITE_KNIGHT		= (KNIGHT << 1) + Color.WHITE;
	public static final int WHITE_BISHOP		= (BISHOP << 1) + Color.WHITE;
	public static final int WHITE_ROOK			= (ROOK   << 1) + Color.WHITE;
	public static final int WHITE_QUEEN			= (QUEEN  << 1) + Color.WHITE;
	public static final int WHITE_KING			= (KING   << 1) + Color.WHITE;
	
	public static final int BLACK_PAWN			= (PAWN   << 1) + Color.BLACK;
	public static final int BLACK_KNIGHT		= (KNIGHT << 1) + Color.BLACK;
	public static final int BLACK_BISHOP		= (BISHOP << 1) + Color.BLACK;
	public static final int BLACK_ROOK			= (ROOK   << 1) + Color.BLACK;
	public static final int BLACK_QUEEN			= (QUEEN  << 1) + Color.BLACK;
	public static final int BLACK_KING			= (KING   << 1) + Color.BLACK;
	
	public static final int color(int pieceCode)
	{
		return (pieceCode & 1);
	}
	
	public static final int pieceType(int pieceCode)
	{
		return (pieceCode >> 1);
	}
	
	public static final int pieceCode(int pieceType, int color)
	{
		return (pieceType << 1) + color;
	}
	
	public static final boolean isSlidingPieceType(int pieceType)
	{
		return (pieceType == BISHOP) || (pieceType == ROOK) || (pieceType == QUEEN);
	}
	
	public static final boolean isSliding(int pieceCode)
	{
		return isSlidingPieceType(pieceType(pieceCode));
	}
}
