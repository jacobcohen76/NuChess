package nuchess.control;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import nuchess.control.player.Computer;
import nuchess.control.player.Human;
import nuchess.control.player.Player;
import nuchess.engine.CMove;
import nuchess.engine.ChessEngine;
import nuchess.engine.Piece;
import nuchess.view.gameview.ChessGameView;

public class ChessGameController
{
	public static final Player<CMove> DEFAULT_WHITEPLAYER, DEFAULT_BLACKPLAYER;
	
	static
	{
		DEFAULT_WHITEPLAYER = new Human<CMove>("Default White Player", "0");
		DEFAULT_BLACKPLAYER = new Human<CMove>("Default Black Player", "1");
	}
	
	private ChessEngine engine;
	private ChessGameView gameView;
	private LinkedList<CMove> moveHistory;
	private Player<CMove> player1, player2;
	
	public ChessGameController(ChessEngine engine, ChessGameView gameView)
	{
		this.engine = engine;
		this.gameView = gameView;
		moveHistory = new LinkedList<CMove>();
		player1 = getDefaultPlayer1();
		player2 = getDefaultPlayer2();
		linkObjects();
	}
	
	private void linkObjects()
	{
		gameView.controller = this;
	}
	
	public void updateView(String SAN)
	{
		long checkBB = engine.inCheck(engine.toMove()) ? engine.board.bitboard(Piece.WHITE_KING + engine.toMove()) : 0L;
		long occBB = engine.board.occ();
		CMove move = moveHistory.getLast();
		String FEN = engine.getFEN();
		
		gameView.addNewState(checkBB, occBB, move, FEN, SAN);
		gameView.setMoveableSquares(engine.board.bitboard(engine.toMove()));
		gameView.setSelectableMoves(engine.generateLegalMoves());
	}
	
	private void initView()
	{
		long checkBB = engine.inCheck(engine.toMove()) ? engine.board.bitboard(Piece.WHITE_KING + engine.toMove()) : 0L;
		long occBB = engine.board.occ();
		String FEN = engine.getFEN();
		
		gameView.setInitialState(checkBB, occBB, FEN);
		gameView.setMoveableSquares(engine.board.bitboard(engine.toMove()));
		gameView.setSelectableMoves(engine.generateLegalMoves());
	}
	
	public void make(CMove move)
	{
		String SAN = engine.getSAN(move);
		engine.make(move);
		moveHistory.add(move);
		updateView(SAN);
		takeTurn(getNextPlayer());
	}

	public void unmake()
	{
		CMove move = moveHistory.pollLast();
		engine.unmake(move);
	}
	
	public void setPlayer1(Player<CMove> player1)
	{
		this.player1 = player1;
	}

	
	public void setPlayer2(Player<CMove> player2)
	{
		this.player2 = player2;		
	}

	
	public Player<CMove> getDefaultPlayer1()
	{
		return DEFAULT_WHITEPLAYER;
	}

	
	public Player<CMove> getDefaultPlayer2()
	{
		return DEFAULT_BLACKPLAYER;
	}

	
	public Player<CMove> getPlayer1()
	{
		return player1;
	}

	
	public Player<CMove> getPlayer2()
	{
		return player2;
	}

	
	public Player<CMove> getNextPlayer()
	{
		return (moveHistory.size() & 1) == 0 ? getPlayer1() : getPlayer2();
	}

	
	public Player<CMove> getWinner()
	{
		return null;
	}

	
	public List<CMove> getMoveHistory()
	{
		return moveHistory;
	}
	
	public void init()
	{
		initView();
		takeTurn(getNextPlayer());
	}
	
	
	public void end()
	{
		
		updateStatistics();
	}
	
	
	public void savePGN(File f)
	{
	}
	
	
	public void updateStatistics()
	{
		
	}

	
	public void takeTurn(Player<CMove> player)
	{
		while(player instanceof Computer<?> && engine.isGameOver() == false)
		{
			make(player.selectMove());
			player = getNextPlayer();
			System.out.println(player);
		}
		if(engine.isGameOver())
		{
			end();
		}
	}
}
