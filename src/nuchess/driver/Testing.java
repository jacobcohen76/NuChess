package nuchess.driver;

import nuchess.engine.Chessboard;
import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.boardeval.MaterialBE;

public class Testing
{
	public static void main(String args[])
	{
		String FEN = "rnbqkbnr/pppppppp/8/8/5n2/8/PPPPPPPP/RNBQKBNR w - - 0 1";
		BoardEvaluator boardEvaluator = new MaterialBE();
		Chessboard board = new Chessboard(FEN);
		System.out.println(boardEvaluator.evaluate(board));
	}
}
