package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;

public class NumChecks implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.getNumChecks();
	}
	
	public String toString()
	{
		return "Num Checks";
	}
}
