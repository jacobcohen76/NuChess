package nuchess.control;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import nuchess.control.player.Computer;
import nuchess.control.player.Player;
import nuchess.engine.CMove;
import nuchess.engine.ChessEngine;
import nuchess.engine.Piece;
import nuchess.view.View;
import nuchess.view.chessgame.ChessGameView;

public class ChessGameController implements Controller
{
	private ChessGameView view;
	private ChessEngine engine;
	private LinkedList<CMove> moveHistory;
	private Player white, black;
	private boolean gameover;
	
	public ChessGameController(ChessGameView view, Player white, Player black, String FEN)
	{
		this.view = view;
		this.white = white;
		this.black = black;
		engine = new ChessEngine(FEN);
		moveHistory = new LinkedList<CMove>();
		view.controller = this;
		gameover = false;
	}
	
	public void replay()
	{
		
	}
	
	public void make(CMove move)
	{
		String SAN = engine.getSAN(move);
		engine.make(move);
		moveHistory.add(move);
		updateView(SAN);
		
		if(getNextPlayer() instanceof Computer && !gameover)
		{
			view.setSelectionEnabled(false);
			dispatchThread((Computer) getNextPlayer());
		}
		else
		{
			view.setSelectionEnabled(!gameover);
		}
	}
	
	public void updateView(String SAN)
	{
		long checkBB = engine.inCheck(engine.toMove()) ? engine.board.bitboard(Piece.WHITE_KING + engine.toMove()) : 0L;
		long occBB = engine.board.occ();
		CMove move = moveHistory.getLast();
		List<CMove> legalMoves = engine.generateLegalMoves();
		String FEN = engine.getFEN();
		view.addNewState(checkBB, occBB, move, FEN, SAN);
		view.setMoveableSquares(engine.board.bitboard(engine.toMove()));
		view.setSelectableMoves(legalMoves);
		
		if(legalMoves.isEmpty())
		{
			gameover = true;
		}
	}
	
	public void unmake()
	{
		CMove move = moveHistory.pollLast();
		engine.unmake(move);
	}
	
	@Override
	public void init()
	{
		long checkBB = engine.inCheck(engine.toMove()) ? engine.board.bitboard(Piece.WHITE_KING + engine.toMove()) : 0L;
		long occBB = engine.board.occ();
		String FEN = engine.getFEN();
		view.setInitialState(checkBB, occBB, FEN);
		view.setMoveableSquares(engine.board.bitboard(engine.toMove()));
		view.setSelectableMoves(engine.generateLegalMoves());
		
		if(getNextPlayer() instanceof Computer && !gameover)
		{
			view.setSelectionEnabled(false);
			dispatchThread((Computer) getNextPlayer());
		}
		else
		{
			view.setSelectionEnabled(!gameover);
		}
	}
	
	@Override
	public void saveGraphicsAs()
	{
		File out = FileSaving.chooseImageFile(view.getPanel(), engine.getFEN().replace('/', '.'));
		FileSaving.saveRenderedImage(view.getRenderedImage(), out);
	}
	
	@Override
	public void saveAs()
	{
		
	}
	
	@Override
	public void close()
	{
		
	}
	
	@Override
	public View getView()
	{
		return view;
	}
	
	public Player getNextPlayer()
	{
		return (moveHistory.size() & 1) == 0 ? white : black;
	}
	
	private void dispatchThread(Computer computer)
	{
		new SelectMoveThread(computer).start();
	}
	
	private class SelectMoveThread extends Thread
	{
		private Computer computer;
		
		public SelectMoveThread(Computer computer)
		{
			this.computer = computer;
		}
		
		public void run()
		{
			CMove move = computer.computeMove(engine.getFEN());
			try
			{
				Thread.sleep(1000L);
			}
			catch (InterruptedException iex)
			{
				iex.printStackTrace();
			}
			make(move);
		}
	}
}
