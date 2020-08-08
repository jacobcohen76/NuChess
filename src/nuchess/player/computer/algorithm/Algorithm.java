package nuchess.player.computer.algorithm;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.player.computer.Computer;
import nuchess.player.computer.evaluator.BoardEvaluator;
import nuchess.player.computer.evaluator.MoveEvaluator;

public abstract class Algorithm extends Computer
{
	private static final long serialVersionUID = -4720820152801149073L;
	
	protected Chessboard board;
	private BoardEvaluator boardEvaluator;
	private MoveEvaluator moveEvaluator;
	private int[] moveEvaluations;
	private int[] stack;
	protected int depth;
	
	public Algorithm(String username, String userid, Chessboard board, BoardEvaluator boardEvaluator, MoveEvaluator moveEvaluator, int depth)
	{
		super(username, userid);
		this.board = board;
		this.boardEvaluator = boardEvaluator;
		this.moveEvaluator = moveEvaluator;
		this.moveEvaluations = new int[256];
		this.stack = new int[256];
		this.depth = depth;
	}
	
	public abstract CMove computeMove();
	
	public CMove computeMove(String FEN)
	{
		board.loadFEN(FEN);
		return computeMove();
	}
	
	public void orderMoves(MoveList moves)
	{
		for(int i = 0; i < moves.n; i++)
		{
			moveEvaluations[i] = moveEvaluator.evaluate(board, moves.array[i]);
		}
		quicksort(moveEvaluations, moves.array, 0, moves.n - 1);
	}
	
	private int partition(int[] evaluations, CMove[] moves, int low, int high)
	{
		int pivot = evaluations[high];
		int i = low - 1;
		
		int tempEval;
		CMove tempCMove;
		
		for(int j = low; j <= high - 1; j++)
		{
			if(evaluations[j] <= pivot)
			{
				i++;
				
				tempEval = evaluations[i];
				evaluations[i] = evaluations[j];
				evaluations[j] = tempEval;
				
				tempCMove = moves[i];
				moves[i] = moves[j];
				moves[j] = tempCMove;
			}
		}
		
		tempEval = evaluations[i + 1];
		evaluations[i + 1] = evaluations[high];
		evaluations[high] = tempEval;
		
		tempCMove = moves[i + 1];
		moves[i + 1] = moves[high];
		moves[high] = tempCMove;
		
		return i + 1;
	}
	
	private void quicksort(int[] evaluations, CMove[] moves, int l, int h)
	{
		int top = -1, p;
		stack[++top] = l;
		stack[++top] = h;
		while(top >= 0)
		{
			h = stack[top--];
			l = stack[top--];
			p = partition(evaluations, moves, l, h);
			if(p - 1 > l)
			{
				stack[++top] = l;
				stack[++top] = p - 1;
			}
			if(p + 1 < h)
			{
				stack[++top] = p + 1;
				stack[++top] = h;
			}
		}
	}
	
	public int getBoardEvaluation()
	{
		return boardEvaluator.evaluate(board);
	}
	
	public MoveList generateMoves()
	{
		return board.generateMoves();
	}
	
	public boolean canMake(CMove move)
	{
		return board.canMake(move);
	}
	
	public void make(CMove move)
	{
		board.make(move);
	}
	
	public void unmake(CMove move)
	{
		board.unmake(move);
	}
	
	public void setBoard(Chessboard board)
	{
		this.board = board;
	}
	
	public void setBoardEvaluator(BoardEvaluator boardEvaluator)
	{
		this.boardEvaluator = boardEvaluator;
	}
	
	public void setMoveEvaluator(MoveEvaluator moveEvaluator)
	{
		this.moveEvaluator = moveEvaluator;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
}
