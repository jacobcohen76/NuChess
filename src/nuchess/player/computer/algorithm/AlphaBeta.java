package nuchess.player.computer.algorithm;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.moveorder.MoveOrderer;

public class AlphaBeta extends Algorithm
{
	private static final long serialVersionUID = 6545473395096568748L;

	public AlphaBeta(String username, Chessboard board, BoardEvaluator boardEvaluator, MoveOrderer moveEvaluator, int depth)
	{
		super(username, board, boardEvaluator, moveEvaluator, depth);
	}
	
	@Override
	protected CMove computeMove()
	{
		int max = Integer.MIN_VALUE, score;
		CMove move = null;
		MoveList moves = board.generateMoves();
//		orderMoves(moves);
		for(int i = 0; i < moves.n; i++)
		{
			if(board.canMake(moves.array[i]))
			{
				board.make(moves.array[i]);
				score = abMin(Integer.MIN_VALUE, Integer.MAX_VALUE, depth - 1);
				board.unmake(moves.array[i]);
				if(score > max)
				{
					max = score;
					move = moves.array[i];
				}
			}
		}
		return move;
	}
	
	private int abMax(int a, int b, int depth)
	{
		if(depth <= 0)
		{
			return +be.evaluate(board);
		}
		else
		{
			int score, numLegalMoves = 0;
			MoveList moves = board.generateMoves();
//			orderMoves(moves);
			for(int i = 0; i < moves.n; i++)
			{
				if(board.canMake(moves.array[i]))
				{
					board.make(moves.array[i]);
					score = abMin(a, b, depth - 1);
					board.unmake(moves.array[i]);
					if(score >= b)
					{
						return b;
					}
					if(score > a)
					{
						a = score;
					}
					numLegalMoves++;
				}
			}
			return numLegalMoves == 0 ? +be.evaluate(board) : a;
		}
	}
	
	private int abMin(int a, int b, int depth)
	{
		if(depth <= 0)
		{
			return -be.evaluate(board);
		}
		else
		{
			int score, numLegalMoves = 0;
			MoveList moves = board.generateMoves();
//			orderMoves(moves);
			for(int i = 0; i < moves.n; i++)
			{
				if(board.canMake(moves.array[i]))
				{
					board.make(moves.array[i]);
					score = abMax(a, b, depth - 1);
					board.unmake(moves.array[i]);
					if(score <= a)
					{
						return a;
					}
					if(score < b)
					{
						b = score;
					}
					numLegalMoves++;
				}
			}
			return numLegalMoves == 0 ? -be.evaluate(board) : b;
		}
	}
}
