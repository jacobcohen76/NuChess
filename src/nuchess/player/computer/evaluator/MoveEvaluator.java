package nuchess.player.computer.evaluator;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;

public interface MoveEvaluator
{
	public int evaluate(Chessboard board, CMove move);
}
