package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;

public class InCheck implements BoardFeature
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
