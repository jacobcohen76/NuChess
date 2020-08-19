package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class RelativeBishopMaterial implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.relativeMaterial(Piece.WHITE_BISHOP);
	}
	
	public String toString()
	{
		return "Relative Bishop Material";
	}
}
