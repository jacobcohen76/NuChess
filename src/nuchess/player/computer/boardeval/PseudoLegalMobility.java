package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;

public class PseudoLegalMobility implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.generateMoves().n;
	}
	
	public String toString()
	{
		return "Pseudo Legal Mobility";
	}
}
