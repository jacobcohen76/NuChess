package nuchess.player.computer.algorithm;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.player.computer.Computer;
import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.moveorder.MoveOrderer;

public abstract class Algorithm extends Computer
{
	private static final long serialVersionUID = -4720820152801149073L;
	
	protected Chessboard board;
	protected BoardEvaluator be;
	protected MoveOrderer mo;
	protected int depth;
	
	public Algorithm(String username, Chessboard board, BoardEvaluator be, MoveOrderer mo, int depth)
	{
		super(username);
		this.board = board;
		this.be = be;
		this.mo = mo;
		this.depth = depth;
	}
	
	protected abstract CMove computeMove();
	
	public CMove computeMove(String FEN)
	{
		board.loadFEN(FEN);
		return computeMove();
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public void setBoard(Chessboard board)
	{
		this.board = board;
	}
	
	public void setBoardEvaluator(BoardEvaluator be)
	{
		this.be = be;
	}
	
	public void setMoveEvaluator(MoveOrderer mo)
	{
		this.mo = mo;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
}
