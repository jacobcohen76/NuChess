package nuchess.player.computer;

import nuchess.engine.CMove;
import nuchess.player.Player;

public abstract class Computer extends Player
{
	private static final long serialVersionUID = -5199764752116960751L;
	
	public Computer(String username)
	{
		super(username);
	}
	
	public abstract CMove computeMove(String FEN);
}
