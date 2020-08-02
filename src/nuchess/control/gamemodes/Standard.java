package nuchess.control.gamemodes;

public class Standard implements GameMode
{
	@Override
	public String getStartingFEN()
	{
		return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	}
	
	@Override
	public String toString()
	{
		return "Standard";
	}
}
