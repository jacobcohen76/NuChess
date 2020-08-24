package nuchess.player.computer.algorithm;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.engine.TableEntry;
import nuchess.engine.TranspositionTable;
import nuchess.player.Player;
import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.moveorder.MoveOrderer;

public class Computer extends Player
{
	private static final long serialVersionUID = -6392176534603773707L;
	
	private Chessboard board;
	private BoardEvaluator be;
	private MoveOrderer mo;
	private TranspositionTable tt;
	private int recurseDepth;
	
	public Computer(String username, Chessboard board, BoardEvaluator be, MoveOrderer mo, TranspositionTable tt, int recurseDepth)
	{
		super(username);
		this.board = board;
		this.be = be;
		this.mo = mo;
		this.tt = tt;
		this.recurseDepth = recurseDepth;
	}
	
	public CMove computeMove(String FEN)
	{
		board.loadFEN(FEN);
		MoveList moves = orderMoves(board.generateMoves());
		CMove move = null;
		int score, max = Integer.MIN_VALUE;
		for(int i = 0; i < moves.n; i++)
		{
			if(board.canMake(moves.array[i]))
			{
				board.make(moves.array[i]);
				score = abMin(Integer.MIN_VALUE, Integer.MAX_VALUE, recurseDepth - 1);
				board.unmake(moves.array[i]);
				if(score >= max)
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
			TableEntry entry = tt.get(board.zobristKey);
			if(entry.key == board.zobristKey && entry.getType() != TableEntry.ALL && entry.getAge() == board.ply)
			{
				return entry.getScore();
			}
			
			int score, numLegalMoves = 0;
			MoveList moves = orderMoves(board.generateMoves());
			for(int i = 0; i < moves.n; i++)
			{
				if(board.canMake(moves.array[i]))
				{
					board.make(moves.array[i]);
					score = abMin(a, b, depth - 1);
					board.unmake(moves.array[i]);
					if(score >= b)
					{
						tt.cache(board.zobristKey, score, board.ply, recurseDepth - depth, TableEntry.CUT, moves.array[i].move);
						return b;
					}
					if(score > a)
					{
						a = score;
					}
					numLegalMoves++;
				}
			}
			if(numLegalMoves == 0)
			{
				return board.inCheck() ? -(BoardEvaluator.MATED_VALUE - board.ply) : 0;
			}
			else
			{
				tt.cache(board.zobristKey, a, board.ply, recurseDepth - depth, TableEntry.PV, CMove.NULL);
				return a;
			}
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
			TableEntry entry = tt.get(board.zobristKey);
			if(entry.key == board.zobristKey && entry.getType() != TableEntry.CUT && entry.getAge() == board.ply)
			{
				return entry.getScore();
			}
			
			int score, numLegalMoves = 0;
			MoveList moves = orderMoves(board.generateMoves());
			for(int i = 0; i < moves.n; i++)
			{
				if(board.canMake(moves.array[i]))
				{
					board.make(moves.array[i]);
					score = abMax(a, b, depth - 1);
					board.unmake(moves.array[i]);
					if(score <= a)
					{
						tt.cache(board.zobristKey, score, board.ply, recurseDepth - depth, TableEntry.ALL, moves.array[i].move);
						return a;
					}
					if(score < b)
					{
						b = score;
					}
					numLegalMoves++;
				}
			}
			if(numLegalMoves == 0)
			{
				return board.inCheck() ? +(BoardEvaluator.MATED_VALUE - board.ply) : 0;
			}
			else
			{
				tt.cache(board.zobristKey, b, board.ply, recurseDepth - depth, TableEntry.PV, CMove.NULL);
				return b;
			}
		}
	}
	
	private MoveList orderMoves(MoveList moves)
	{
		return moves;
	}
}
