package nuchess.control.player;

import nuchess.engine.ChessEngine;

public interface Evaluator
{
	public int evaluate(ChessEngine engine);
}
