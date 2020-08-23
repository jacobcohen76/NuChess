package nuchess.player.computer.algorithm;

import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.engine.TableEntry;
import nuchess.engine.TranspositionTable;
import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.moveorder.MoveOrderer;

public class Computer
{
	private Chessboard board;
	private BoardEvaluator be;
	private MoveOrderer mo;
	private TranspositionTable tt;
	private int depth;
	
	public Computer(String username, Chessboard board, BoardEvaluator be, MoveOrderer mo, TranspositionTable tt, int depth)
	{
		this.board = board;
		this.be = be;
		this.mo = mo;
		this.tt = tt;
		this.depth = depth;
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
			for(int i = 0; i < moves.n; i++)
			{
				if(board.canMake(moves.array[i]))
				{
					board.make(moves.array[i]);
					score = abMin(a, b, depth - 1);
					board.unmake(moves.array[i]);
					if(score >= b)
					{
						tt.put(board.zobristKey, score, board.ply, this.depth - depth, TableEntry.PV, moves.array[i].hashCode());
						return b;
					}
					if(score > a)
					{
						a = score;
					}
					numLegalMoves++;
				}
			}
			return numLegalMoves == 0 ? (board.inCheck() ? -(BoardEvaluator.MATED_VALUE - board.ply) : 0) : a;
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
			return numLegalMoves == 0 ? (board.inCheck() ? +(BoardEvaluator.MATED_VALUE - board.ply) : 0) : b;
		}
	}
}
