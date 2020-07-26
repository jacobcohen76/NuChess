package nuchess.view.chessgame;

import nuchess.engine.CMove;

class BoardViewState
{
	public long checkBB;
	public long occBB;
	public CMove move;
	public String FEN;
	
	public BoardViewState(long checkBB, long occBB, CMove move, String FEN)
	{
		this.checkBB = checkBB;
		this.occBB = occBB;
		this.move = move;
		this.FEN = FEN;
	}
}
