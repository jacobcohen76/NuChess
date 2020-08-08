package nuchess.player.computer.evaluator;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class SimpleBoardEvaluator implements BoardEvaluator
{
	private static final int[] VALUE = new int[]
	{
		+0,
		+1,
		+3,
		+3,
		+5,
		+9,
		+0,
	};
	
	public int evaluate(Chessboard board)
	{
		int score = 0;
		for(int piece = Piece.WHITE_PAWN; piece <= Piece.BLACK_KING; piece += 2)
		{
			score += VALUE[Piece.pieceType(piece)] * (board.material[piece + board.toMove] - board.material[piece + board.toMove ^ 1]);
		}
		return score;
	}
}
