package nuchess.player.computer.moveorder;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;

public class LazyMO implements MoveOrderer
{

	@Override
	public int evaluate(Chessboard board, CMove move)
	{
		switch(move.flags())
		{
			case CMove.QUIET:					
			case CMove.DOUBLE_PAWN_PUSH:		
			case CMove.KING_CASTLE:			
			case CMove.QUEEN_CASTLE:			
			case CMove.CAPTURE:				
			case CMove.EP_CAPTURE:			
			case CMove.KNIGHT_PROMO:			
			case CMove.BISHOP_PROMO:			
			case CMove.ROOK_PROMO:			
			case CMove.QUEEN_PROMO:			
			case CMove.KNIGHT_PROMO_CAP:		
			case CMove.BISHOP_PROMO_CAP:		
			case CMove.ROOK_PROMO_CAP:		
			case CMove.QUEEN_PROMO_CAP:		
			default:					
		}
		return 0;
	}

	@Override
	public void orderMoves(Chessboard board, MoveList moves)
	{
		int i = 0;
		while(i < moves.n)
		{
			switch(moves.array[i].flags())
			{
				case CMove.QUIET:					
				case CMove.DOUBLE_PAWN_PUSH:		
				case CMove.KING_CASTLE:			
				case CMove.QUEEN_CASTLE:			
				case CMove.CAPTURE:				
				case CMove.EP_CAPTURE:
				case CMove.KNIGHT_PROMO:
				case CMove.QUEEN_PROMO:
				case CMove.KNIGHT_PROMO_CAP:
				case CMove.QUEEN_PROMO_CAP:
					
					break;
				
				//a queen promo is always better than a bishop or a rook promo,
				//so they get pruned
				case CMove.BISHOP_PROMO:			
				case CMove.ROOK_PROMO:
				case CMove.BISHOP_PROMO_CAP:		
				case CMove.ROOK_PROMO_CAP:
					moves.array[i] = null;
					break;
			}
			i++;
		}
	}
}
