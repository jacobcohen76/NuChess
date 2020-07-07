package nuchess.engine;

/**
 * Class containing thread-safe static functions so that the make function
 * in the ChessEngine class is more readable and easier to debug because
 * all of the special cases are handled by the static functions in this class.
 * 
 * @author Jacob Cohen
 * 
 */
public final class MoveMaker
{
	public static final int WHITE_KING_ROOK		= (Piece.WHITE_ROOK << 8) + Square.a1;
	public static final int BLACK_KING_ROOK		= (Piece.BLACK_ROOK << 8) + Square.a8;
	public static final int WHITE_QUEEN_ROOK	= (Piece.WHITE_ROOK << 8) + Square.h1;
	public static final int BLACK_QUEEN_ROOK	= (Piece.BLACK_ROOK << 8) + Square.h8;
	public static final int WHITE_KING_ORIGIN	= (Piece.WHITE_KING << 8) + Square.e1;
	public static final int BLACK_KING_ORIGIN	= (Piece.BLACK_KING << 8) + Square.e8;
	
	public static final int WHITE_KING_CASTLE	= (Color.WHITE << 4) + CMove.KING_CASTLE;
	public static final int BLACK_KING_CASTLE	= (Color.BLACK << 4) + CMove.KING_CASTLE;
	public static final int WHITE_QUEEN_CASTLE	= (Color.WHITE << 4) + CMove.QUEEN_CASTLE;
	public static final int BLACK_QUEEN_CASTLE	= (Color.BLACK << 4) + CMove.QUEEN_CASTLE;
	
	public static final void makeCastle(CBoard board, CMove move, int mcolor)
	{
		switch((mcolor << 4) + move.flags())
		{
		case WHITE_KING_CASTLE:
			board.move(Piece.WHITE_ROOK, Square.h1, Square.f1);
			break;
		case BLACK_KING_CASTLE:
			board.move(Piece.BLACK_ROOK, Square.h8, Square.f8);
			break;
		case WHITE_QUEEN_CASTLE:
			board.move(Piece.WHITE_ROOK, Square.a1, Square.d1);
			break;
		case BLACK_QUEEN_CASTLE:
			board.move(Piece.BLACK_ROOK, Square.a8, Square.d8);
			break;
		}
	}
	
	public static final void makePromotion(CBoard board, CMove move, int mcolor)
	{
		if(move.isPromo())
		{
			board.promote(
							Piece.pieceCode(Piece.PAWN, mcolor),
							Piece.pieceCode(move.promotedTo(), mcolor),
							move.to()
						);
		}
	}
	
	public static final void unmakeCastle(CBoard board, CMove move, int mcolor)
	{
		switch((mcolor << 4) + move.flags())
		{
		case WHITE_KING_CASTLE:
			board.move(Piece.WHITE_ROOK, Square.f1, Square.h1);
			break;
		case BLACK_KING_CASTLE:
			board.move(Piece.BLACK_ROOK, Square.f8, Square.h8);
			break;
		case WHITE_QUEEN_CASTLE:
			board.move(Piece.WHITE_ROOK, Square.d1, Square.a1);
			break;
		case BLACK_QUEEN_CASTLE:
			board.move(Piece.BLACK_ROOK, Square.d8, Square.a8);
			break;
		}
	}
	
	public static final void unmakePromotion(CBoard board, CMove move, int mcolor)
	{
		if(move.isPromo())
		{			
			board.promote(
							Piece.pieceCode(move.promotedTo(), mcolor),
							Piece.pieceCode(Piece.PAWN, mcolor),
							move.to()
						);
		}
	}
	
	/**
	 * When a rook or king is moved from their origin square or a rook
	 * is captured on its origin square, castling rights need to be
	 * updated. This function returns an integer value that will remove
	 * the castling right from a position by using a bit-wise & operation.
	 * @param pieceCode the moved or captured
	 * @param square the squared moved from or captured on
	 * @return
	 */
	public static final int castlingRights(int square)
	{		
		switch(square)
		{
			case Square.a1:		return 0xFFFBFFFF;
			case Square.e1:		return 0xFFF3FFFF;
			case Square.h1:		return 0xFFF7FFFF;
			case Square.a8:		return 0xFFFEFFFF;
			case Square.e8:		return 0xFFFCFFFF;
			case Square.h8:		return 0xFFFDFFFF;
			default:			return 0xFFFFFFFF;
		}
	}
	
	public static final int updateHalfmoveClock(CMove move, int moving, int halfmoveClock)
	{
		return	((move.isCapture() || Piece.pieceType(moving) == Piece.PAWN) ?
					0 : (halfmoveClock + 1));
	}
	
	public static final int updateCapturedPieceInfo(int pieceCode, int square)
	{
		return (pieceCode << 28) | (square << 20);
	}
	
	public static final int updateEPtarget(CMove move, int mcolor)
	{
		return	(move.flags() == CMove.DOUBLE_PAWN_PUSH ?
					Square.makeSquare(mcolor == Color.WHITE ? 2 : 5, Square.file(move.from())) :
					Square.NULL) << 8;
	}
}
