package nuchess.player.computer.algorithm;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.player.computer.evaluator.BoardEvaluator;
import nuchess.player.computer.evaluator.MoveEvaluator;

public class AlphaBeta extends Algorithm
{
	private static final long serialVersionUID = 6545473395096568748L;

	public AlphaBeta(String username, Chessboard board, BoardEvaluator boardEvaluator, MoveEvaluator moveEvaluator, int depth)
	{
		super(username, board, boardEvaluator, moveEvaluator, depth);
	}
	
	@Override
	public CMove computeMove()
	{
		int max = Integer.MIN_VALUE, score;
		CMove move = null;
		MoveList moves = generateMoves();
//		orderMoves(moves);
		for(int i = 0; i < moves.n; i++)
		{
			if(canMake(moves.array[i]))
			{
				make(moves.array[i]);
				score = abMin(Integer.MIN_VALUE, Integer.MAX_VALUE, depth - 1);
				if(score > max)
				{
					max = score;
					move = moves.array[i];
				}
				unmake(moves.array[i]);
			}
		}
		return move;
	}
	
	private int abMax(int a, int b, int depth)
	{
		if(depth <= 0)
		{
			return +getBoardEvaluation();
		}
		else
		{
			int score;
			MoveList moves = generateMoves();
//			orderMoves(moves);
			for(int i = 0; i < moves.n; i++)
			{
				if(canMake(moves.array[i]))
				{
					make(moves.array[i]);
					score = abMin(a, b, depth - 1);
					unmake(moves.array[i]);
					if(score >= b)
					{
						return b;
					}
					if(score > a)
					{
						a = score;
					}
				}
			}
			return a;
		}
	}
	
	private int abMin(int a, int b, int depth)
	{
		if(depth <= 0)
		{
			return -getBoardEvaluation();
		}
		else
		{
			int score;
			MoveList moves = generateMoves();
//			orderMoves(moves);
			for(int i = 0; i < moves.n; i++)
			{
				if(canMake(moves.array[i]))
				{
					make(moves.array[i]);
					score = abMax(a, b, depth - 1);
					unmake(moves.array[i]);
					if(score <= a)
					{
						return a;
					}
					if(score < b)
					{
						b = score;
					}
				}
			}
			return b;
		}
	}
}
