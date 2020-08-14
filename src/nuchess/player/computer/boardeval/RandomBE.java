package nuchess.player.computer.boardeval;

import java.util.Random;

import nuchess.engine.Chessboard;

public class RandomBE implements BoardEvaluator
{
	private Random rand;
	
	public RandomBE()
	{
		rand = new Random(System.currentTimeMillis());
	}
	
	@Override
	public int evaluate(Chessboard board)
	{
		return rand.nextInt(10000);
	}
}
