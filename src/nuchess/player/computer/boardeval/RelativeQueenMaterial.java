package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class RelativeQueenMaterial implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.relativeMaterial(Piece.WHITE_QUEEN);
	}
	
	public String toString()
	{
		return "Relative Queen Material";
	}
}
