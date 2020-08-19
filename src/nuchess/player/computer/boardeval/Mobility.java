package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;

public class Mobility implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		MoveList moves = board.generateMoves();
		int numLegalMoves = 0;
		for(int i = 0; i < moves.n; i++)
		{
			if(board.canMake(moves.array[i]))
			{
				numLegalMoves++;
			}
		}
		return numLegalMoves;
	}
	
	public String toString()
	{
		return "Mobility";
	}
}
