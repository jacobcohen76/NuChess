package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class RelativePawnMaterial implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.relativeMaterial(Piece.WHITE_PAWN);
	}
	
	public String toString()
	{
		return "Relative Pawn Material";
	}
}
