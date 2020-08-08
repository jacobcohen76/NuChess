package nuchess.player.computer.evaluator;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;

public class SimpleMoveEvaluator implements MoveEvaluator
{
	private static final int[] VALUE = new int[]
	{
		0,
		0,
		+1,
		+1,
		+3,
		+3,
		+3,
		+3,
		+5,
		+5,
		+9,
		+9,		
	};
	
	public int evaluate(Chessboard board, CMove move)
	{
		switch(move.flags())
		{
			case CMove.QUIET:					return 0;
			case CMove.DOUBLE_PAWN_PUSH:		return 1;
			case CMove.KING_CASTLE:				return 2;
			case CMove.QUEEN_CASTLE:			return 3;
			case CMove.CAPTURE:					return VALUE[board.pieceAt(move.to())];
			case CMove.EP_CAPTURE:				return 3;
			case CMove.KNIGHT_PROMO:			return 0;
			case CMove.BISHOP_PROMO:			return 0;
			case CMove.ROOK_PROMO:				return 0;
			case CMove.QUEEN_PROMO:				return 0;
			case CMove.KNIGHT_PROMO_CAP:		return 0;
			case CMove.BISHOP_PROMO_CAP:		return 0;
			case CMove.ROOK_PROMO_CAP:			return 0;
			case CMove.QUEEN_PROMO_CAP:			return 0;
			default:							
		}
		return 0;
	}
}
