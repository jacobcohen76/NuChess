package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class RelativeKnightMaterial implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.relativeMaterial(Piece.WHITE_KNIGHT);
	}
	
	public String toString()
	{
		return "Relative Knight Material";
	}
}
