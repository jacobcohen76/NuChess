package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Color;

public class RelativeMaterial implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.relativeMaterial(Color.WHITE);
	}
	
	public String toString()
	{
		return "Relative Material";
	}
}
