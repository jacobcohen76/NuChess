package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class RelativeRookMaterial implements BoardFeature
{
	@Override
	public int getFeature(Chessboard board)
	{
		return board.relativeMaterial(Piece.WHITE_ROOK);
	}
	
	public String toString()
	{
		return "Relative Rook Material";
	}
}
