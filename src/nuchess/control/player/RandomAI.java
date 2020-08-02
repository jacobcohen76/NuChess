package nuchess.control.player;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import nuchess.engine.CMove;
import nuchess.engine.ChessEngine;

public class RandomAI extends Computer
{
	private static final long serialVersionUID = -3229901391278834867L;
	
	private Random rand;
	
	public RandomAI(String username, String userid, ChessEngine engine)
	{
		super(username, userid, engine);
		rand = new Random(System.currentTimeMillis());
	}
	
	public RandomAI(String username, String userid)
	{
		this(username, userid, new ChessEngine());
	}
	
	@Override
	public CMove computeMove()
	{
		List<CMove> moves = engine.generateLegalMoves();
		if(moves.isEmpty())
		{
			return null;
		}
		else
		{
			Iterator<CMove> itr = moves.iterator();
			int num = rand.nextInt(moves.size());
			for(int i = 0; i < num - 1; i++)
			{
				itr.next();
			}
			return itr.next();
		}
	}
}
