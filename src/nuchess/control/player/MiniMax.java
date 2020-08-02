package nuchess.control.player;

import java.util.List;

import nuchess.engine.CMove;
import nuchess.engine.ChessEngine;

public class MiniMax extends Algorithm
{
	private static final long serialVersionUID = 3774171787443479132L;
	
	public MiniMax(String username, String userid, ChessEngine engine, Evaluator evaluator, int depth)
	{
		super(username, userid, engine, evaluator, depth);
	}
	
	@Override
	public CMove computeMove()
	{
		int max = Integer.MIN_VALUE, evaluation;
		CMove best = null;
		List<CMove> moves = engine.generateMoves();
		for(CMove move : moves)
		{
			if(engine.canMake(move))
			{
				engine.make(move);
				evaluation = maxi(depth - 1);
				if(evaluation > max)
				{
					max = evaluation;
					best = move;
				}
				engine.unmake(move);
			}
		}
		return best;
	}
	
	public int maxi(int depth)
	{
		if(depth <= 0)
		{
			return evaluator.evaluate(engine);
		}
		else
		{
			int max = Integer.MIN_VALUE, evaluation;
			List<CMove> moves = engine.generateMoves();
			for(CMove move : moves)
			{
				if(engine.canMake(move))
				{
					engine.make(move);
					evaluation = mini(depth - 1);
					if(evaluation > max)
					{
						max = evaluation;
					}
					engine.unmake(move);
				}
			}
			return max;
		}
	}
	
	public int mini(int depth)
	{
		if(depth <= 0)
		{
			return evaluator.evaluate(engine);
		}
		else
		{
			int min = Integer.MAX_VALUE, evaluation;
			List<CMove> moves = engine.generateMoves();
			for(CMove move : moves)
			{
				if(engine.canMake(move))
				{
					engine.make(move);
					evaluation = maxi(depth - 1);
					if(evaluation < min)
					{
						min = evaluation;
					}
					engine.unmake(move);
				}
			}
			return min;
		}
	}
}
