package nuchess.control.player;

import nuchess.engine.CMove;
import nuchess.engine.ChessEngine;

public abstract class Computer extends Player
{
	private static final long serialVersionUID = -5199764752116960751L;
	
	protected ChessEngine engine;
	
	public Computer(String username, String userid, ChessEngine engine)
	{
		super(username, userid);
		this.engine = engine;
	}
	
	public void setEngine(ChessEngine engine)
	{
		this.engine = engine;
	}
	
	public CMove computeMove(String FEN)
	{
		engine.loadFEN(FEN);
		return computeMove();
	}
	
	public abstract CMove computeMove();
}
