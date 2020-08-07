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
	
	private Chessboard board;
	private BoardEvaluator boardEvaluator;
	private MoveEvaluator moveEvaluator;
	protected int depth;
	
	public Algorithm(String username, String userid, Chessboard board, BoardEvaluator boardEvaluator, MoveEvaluator moveEvaluator, int depth)
	{
		super(username, userid);
		this.board = board;
		this.boardEvaluator = boardEvaluator;
		this.moveEvaluator = moveEvaluator;
		this.depth = depth;
	}
	
	public abstract CMove computeMove();
	
	public CMove computeMove(String FEN)
	{
		board.loadFEN(FEN);
		return computeMove();
	}
	
	public void orderMoves(CMove[] moves, int n)
	{
		
	}
	
	public int evaluate(Chessboard board)
	{
		return boardEvaluator.evaluate(board);
	}
	
	public int evaluate(CMove move)
	{
		return moveEvaluator.evaluate(board, move);
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
