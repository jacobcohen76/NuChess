package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;

public class Check implements BoardFeature
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
