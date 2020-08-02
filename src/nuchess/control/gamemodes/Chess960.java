package nuchess.control.gamemodes;

import java.util.Random;

public class Chess960 implements GameMode
{
	private static final Random RANDOMIZER = new Random(System.currentTimeMillis());
	
	private static final String[] FEN_POSITIONS = new String[]
	{
			
	};
	
	private static int randomIndex()
	{
		return RANDOMIZER.nextInt(960);
	}

	@Override
	public String getStartingFEN()
	{
		return FEN_POSITIONS[randomIndex()];
	}
	
	@Override
	public String toString()
	{
		return "Chess960";
	}
}
