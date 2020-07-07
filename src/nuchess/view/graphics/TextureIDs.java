package nuchess.view.graphics;

public class TextureIDs
{
	public static final int pieceID(char pieceChar)
	{
		switch(pieceChar)
		{
			case 'P':	return 0x1;
			case 'N':	return 0x2;
			case 'B':	return 0x3;
			case 'R':	return 0x4;
			case 'Q':	return 0x5;
			case 'K':	return 0x6;
			case 'p':	return 0x7;
			case 'n':	return 0x8;
			case 'b':	return 0x9;
			case 'r':	return 0xA;
			case 'q':	return 0xB;
			case 'k':	return 0xC;
			default:	return 0x0;
		}
	}
	
	public static final int NULL				=  0;
	public static final int WHITE_PAWN			=  1;
	public static final int WHITE_KNIGHT		=  2;
	public static final int WHITE_BISHOP		=  3;
	public static final int WHITE_ROOK			=  4;
	public static final int WHITE_QUEEN			=  5;
	public static final int WHITE_KING			=  6;
	public static final int BLACK_PAWN			=  7;
	public static final int BLACK_KNIGHT		=  8;
	public static final int BLACK_BISHOP		=  9;
	public static final int BLACK_ROOK			= 10;
	public static final int BLACK_QUEEN			= 11;
	public static final int BLACK_KING			= 12;
	public static final int DOT					= 13;
	public static final int BORDER				= 14;
	public static final int HIGHLIGHT			= 15;
	public static final int MASK				= 16;
	public static final int DARK_SQUARE			= 17;
	public static final int LIGHT_SQUARE		= 18;
	
}
