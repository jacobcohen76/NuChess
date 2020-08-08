package nuchess.driver;

import nuchess.engine.Chessboard;
import nuchess.player.computer.evaluator.BoardEvaluator;
import nuchess.player.computer.evaluator.SimpleBoardEvaluator;

public class Testing
{
	public static void main(String args[])
	{
		String FEN = "rnbqkbnr/pppppppp/8/8/5n2/8/PPPPPPPP/RNBQKBNR b - - 0 1";
		BoardEvaluator boardEvaluator = new SimpleBoardEvaluator();
		Chessboard board = new Chessboard(FEN);
		System.out.println(boardEvaluator.evaluate(board));
	}
}
