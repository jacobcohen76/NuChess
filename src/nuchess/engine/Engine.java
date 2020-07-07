package nuchess.engine;

import java.util.List;

public interface Engine<Move>
{
	public boolean isGameOver();
	public boolean isDraw();
	public void make(Move move);
	public void unmake(Move move);
	public boolean canMake(Move move);
	public List<Move> generateMoves();
	public List<Move> generateLegalMoves();
}
