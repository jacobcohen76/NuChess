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
	protected int depth;
	
	public Algorithm(String username, Chessboard board, BoardEvaluator boardEvaluator, MoveEvaluator moveEvaluator, int depth)
	{
		super(username);
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
	
	public void orderMoves(MoveList moves)
	{
		
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
