package nuchess.view.graphics;

import nuchess.engine.Piece;

public class TextureIDs
{
	public static final int pieceID(char pieceChar)
	{
		switch(pieceChar)
		{
			case 'P':	return WHITE_PAWN;
			case 'N':	return WHITE_KNIGHT;
			case 'B':	return WHITE_BISHOP;
			case 'R':	return WHITE_ROOK;
			case 'Q':	return WHITE_QUEEN;
			case 'K':	return WHITE_KING;
			case 'p':	return BLACK_PAWN;
			case 'n':	return BLACK_KNIGHT;
			case 'b':	return BLACK_BISHOP;
			case 'r':	return BLACK_ROOK;
			case 'q':	return BLACK_QUEEN;
			case 'k':	return BLACK_KING;
			default:	return NULL;
		}
	}
	
	public static final int pieceID(int pieceCode)
	{
		switch(pieceCode)
		{
			case Piece.WHITE_PAWN:		return WHITE_PAWN;
			case Piece.WHITE_KNIGHT:	return WHITE_KNIGHT;
			case Piece.WHITE_BISHOP:	return WHITE_BISHOP;
			case Piece.WHITE_ROOK:		return WHITE_ROOK;
			case Piece.WHITE_QUEEN:		return WHITE_QUEEN;
			case Piece.WHITE_KING:		return WHITE_KING;
			case Piece.BLACK_PAWN:		return BLACK_PAWN;
			case Piece.BLACK_KNIGHT:	return BLACK_KNIGHT;
			case Piece.BLACK_BISHOP:	return BLACK_BISHOP;
			case Piece.BLACK_ROOK:		return BLACK_ROOK;
			case Piece.BLACK_QUEEN:		return BLACK_QUEEN;
			case Piece.BLACK_KING:		return BLACK_KING;
			default:					return NULL;
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
