package nuchess.player.computer.evaluator;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class SimpleBoardEvaluator implements BoardEvaluator
{
	private static final int[] VALUE = new int[]
	{
		0,
		0,
		+1,
		-1,
		+3,
		-3,
		+3,
		-3,
		+5,
		-5,
		+9,
		-9,
	};
	
	public int evaluate(Chessboard board)
	{
		int score = 0;
		for(int piece = Piece.WHITE_PAWN; piece <= Piece.BLACK_QUEEN; piece++)
		{
			score += VALUE[piece] * board.material[piece];
		}
		return score;
	}
}
