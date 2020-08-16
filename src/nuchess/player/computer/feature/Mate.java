package nuchess.player.computer.feature;

import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;

public class Mate implements Feature
{
	@Override
	public int getFeature(Chessboard board)
	{
		MoveList moves = board.generateMoves();
		for(int i = 0; i < moves.n; i++)
		{
			if(board.canMake(moves.array[i]))
			{
				return 0;
			}
		}
		return 1;
	}
	
	public String toString()
	{
		return "Mate";
	}
}
