package nuchess.player.computer.algorithm;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.player.computer.evaluator.BoardEvaluator;

public class MiniMax extends Algorithm
{
	private static final long serialVersionUID = 394129760291512170L;

	public MiniMax(String username, Chessboard board, BoardEvaluator boardEvaluator, int depth)
	{
		super(username, board, boardEvaluator, null, depth);
	}
	
	@Override
	public CMove computeMove()
	{
		int max = Integer.MIN_VALUE, score;
		CMove move = null;
		MoveList moves = generateMoves();
		for(int i = 0; i < moves.n; i++)
		{
			if(canMake(moves.array[i]))
			{
				make(moves.array[i]);
				score = mini(depth - 1);
				unmake(moves.array[i]);
				if(score > max)
				{
					max = score;
					move = moves.array[i];
				}
			}
		}
		return move;
	}
	
	public int maxi(int depth)
	{
		if(depth <= 0)
		{
			return +getBoardEvaluation();
		}
		else
		{
			int max = Integer.MIN_VALUE, score;
			MoveList moves = generateMoves();
			for(int i = 0; i < moves.n; i++)
			{
				if(canMake(moves.array[i]))
				{
					make(moves.array[i]);
					score = mini(depth - 1);
					unmake(moves.array[i]);
					if(score > max)
					{
						max = score;
					}
				}
			}
			return max;
		}
	}
	
	public int mini(int depth)
	{
		if(depth <= 0)
		{
			return -getBoardEvaluation();
		}
		else
		{
			int min = Integer.MAX_VALUE, score;
			MoveList moves = generateMoves();
			for(int i = 0; i < moves.n; i++)
			{
				if(canMake(moves.array[i]))
				{
					make(moves.array[i]);
					score = maxi(depth - 1);
					unmake(moves.array[i]);
					if(score < min)
					{
						min = score;
					}
				}
			}
			return min;
		}
	}
}
