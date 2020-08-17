package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;

public class Mate implements BoardFeature
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
