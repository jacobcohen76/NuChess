package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;

public class BoardEvaluator
{
	public static final int MATED_VALUE = +16384;
	
	private BoardFeature[] features;
	private int[] weights;
	private int n;
	
	public BoardEvaluator(BoardFeature[] features, int[] weights, int n)
	{
		this.features = features;
		this.weights = weights;
		this.n = n;
	}
	
	public int evaluate(Chessboard board)
	{
		int score = 0;
		for(int i = 0; i < n; i++)
		{
			score += features[i].getFeature(board) * weights[i];
		}
		return score;
	}
}
