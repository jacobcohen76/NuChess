package nuchess.ui.game.control;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;
import nuchess.engine.Piece;
import nuchess.player.Player;
import nuchess.player.computer.Computer;
import nuchess.ui.Control;
import nuchess.ui.FileSaving;
import nuchess.ui.View;
import nuchess.ui.game.view.GameView;

public class GameControl implements Control
{
	private GameView view;
	private Chessboard engine;
	private LinkedList<CMove> moveHistory;
	private Player white, black;
	private boolean gameover, closed;
	
	public GameControl(GameView view, Player white, Player black, String FEN)
	{
		this.view = view;
		this.white = white;
		this.black = black;
		engine = new Chessboard(FEN);
		moveHistory = new LinkedList<CMove>();
		view.control = this;
		gameover = false;
		closed = false;
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
		
		if(getNextPlayer() instanceof Computer && !gameover && !closed)
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
		long checkBB = engine.inCheck() ? engine.bitboards[Piece.WHITE_KING + engine.toMove] : 0L;
		long occBB = engine.bitboards[Chessboard.OCC];
		CMove move = moveHistory.getLast();
		List<CMove> legalMoves = generateLegalMoves();
		String FEN = engine.getFEN();
		view.addNewState(checkBB, occBB, move, FEN, SAN);
		view.setMoveableSquares(engine.bitboards[engine.toMove]);
		view.setSelectableMoves(legalMoves);
		gameover = legalMoves.isEmpty() || engine.halfmoveClock >= 100;
	}
	
	public List<CMove> generateLegalMoves()
	{
		MoveList moveList = engine.generateMoves();
		ArrayList<CMove> moves = new ArrayList<CMove>();
		for(int i = 0; i < moveList.n; i++)
		{
			if(moveList.array[i] != null)
			{
				moves.add(moveList.array[i]);
			}
		}
		return moves;
	}
	
	public void unmake()
	{
		CMove move = moveHistory.pollLast();
		engine.unmake(move);
	}
	
	@Override
	public void init()
	{
		long checkBB = engine.inCheck() ? engine.bitboards[Piece.WHITE_KING + engine.toMove] : 0L;
		long occBB = engine.bitboards[Chessboard.OCC];
		String FEN = engine.getFEN();
		
		view.setInitialState(checkBB, occBB, FEN);
		view.setMoveableSquares(engine.bitboards[engine.toMove]);
		view.setSelectableMoves(generateLegalMoves());
		
		view.setWhiteUsername(white.getUsername());
		view.setBlackUsername(black.getUsername());
		
		if(getNextPlayer() instanceof Computer && !gameover && !closed)
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
		closed = true;
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
			setPriority(Thread.MAX_PRIORITY);
		}
		
		public void run()
		{
			CMove move = computer.computeMove(engine.getFEN());
//			try
//			{
//				Thread.sleep(1000L);
//			}
//			catch (InterruptedException iex)
//			{
//				iex.printStackTrace();
//			}
			make(move);
		}
	}
}
