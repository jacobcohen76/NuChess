package nuchess.control.player;

import nuchess.engine.ChessEngine;

public abstract class Algorithm extends Computer
{
	private static final long serialVersionUID = -4720820152801149073L;
	
	protected Evaluator evaluator;
	protected int depth;
	
	public Algorithm(String username, String userid, ChessEngine engine, Evaluator evaluator, int depth)
	{
		super(username, userid, engine);
		this.evaluator = evaluator;
		this.depth = depth;
	}
	
	public void setEvaluator(Evaluator evaluator)
	{
		this.evaluator = evaluator;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
}
