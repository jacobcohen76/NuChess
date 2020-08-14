package nuchess.player.computer.moveorder;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;

public interface MoveOrderer
{
	public int evaluate(Chessboard board, CMove move);
	public void orderMoves(Chessboard board, MoveList moves);
}
