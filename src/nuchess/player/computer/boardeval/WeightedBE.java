package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.player.computer.feature.Feature;

public class WeightedBE implements BoardEvaluator
{
	private Feature[] features;
	private int[] weights;
	private int n;
	
	public WeightedBE(Feature[] features, int[] weights, int n)
	{
		this.features = features;
		this.weights = weights;
		this.n = n;
	}

	@Override
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
