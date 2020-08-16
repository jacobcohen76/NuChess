package nuchess.player.computer.feature;

import nuchess.engine.Chessboard;

public class Check implements Feature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.inCheck() ? 1 : 0;
	}
	
	public String toString()
	{
		return "In Check";
	}
}
