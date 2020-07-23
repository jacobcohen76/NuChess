package nuchess.control;

import java.io.File;

import nuchess.engine.CBoard;
import nuchess.engine.Color;
import nuchess.engine.FENParser;
import nuchess.engine.Piece;
import nuchess.engine.Square;
import nuchess.view.View;
import nuchess.view.fenboardeditor.FENBoardEditorView;

public class FENBoardEditorController implements Controller
{
	private CBoard board;
	private FENBoardEditorView view;
	private int toMove, castlingRights, epSquare, halfmoveClock, plyCount;
	
	public FENBoardEditorController(CBoard board, FENBoardEditorView view)
	{
		this.board = board;
		this.view = view;
		
		toMove = Color.WHITE;
		castlingRights = 0;
		epSquare = Square.NULL;
		halfmoveClock = 0;
		plyCount = 0;
		
		linkObjects();
		
		board.clearBitboards();
	}
	
	public FENBoardEditorController()
	{
		this(null, null);
	}
	
	public void init()
	{
		updateView();
	}
	
	public void saveGraphicsAs()
	{
		File out = FileSaving.chooseImageFile(view.getPanel(), getFEN().replace('/', '.'));
		FileSaving.saveRenderedImage(view.getRenderedImage(), out);
	}
	
	public void saveAs()
	{
		
	}
	
	public void close()
	{
		
	}
	
	public View getView()
	{
		return view;
	}
	
	private void linkObjects()
	{
		view.controller = this;
	}
	
	private void updateView()
	{
		updateView(board.occ(), getFEN());
	}
	
	private void updateView(String FEN)
	{
		updateView(board.occ(), FEN);
	}
	
	private void updateView(long occ, String FEN)
	{
		view.display(FEN);
		view.setOccupancy(occ);
		view.repaint();
	}
	
	public void put(int piece, int square)
	{
		if(Piece.WHITE_PAWN <= piece && piece <= Piece.BLACK_KING)
		{
			if(!board.isEmpty(square))
			{
				int prev = board.pieceAt(square);
				if(Piece.WHITE_PAWN <= prev && prev <= Piece.BLACK_KING)
				{
					board.capture(prev, square);
				}
			}
			board.put(piece, square);
		}
		updateView();
	}
	
	public void capture(int piece, int square)
	{
		if(Piece.WHITE_PAWN <= piece && piece <= Piece.BLACK_KING)
		{
			board.capture(piece, square);
		}
		updateView();
	}
	
	public void capture(int square)
	{
		capture(board.pieceAt(square), square);
	}
	
	public void setToMove(int toMove)
	{
		this.toMove = toMove;
	}
	
	public void setCastlignRights(int castlingRights)
	{
		this.castlingRights = castlingRights;
	}
	
	public void setEpSquare(int epSquare)
	{
		this.epSquare = epSquare;
	}
	
	public void setHalfmoveClock(int halfmoveClock)
	{
		this.halfmoveClock = halfmoveClock;
	}
	
	public void setPlyCount(int plyCount)
	{
		this.plyCount = plyCount;
	}
	
	public String getFEN()
	{
		return	FENParser.formatBoardPosition(board) + " " +
				FENParser.formatToMove(toMove) + " " +
				FENParser.formatCastlingRights(castlingRights) + " " +
				FENParser.formatEnPassantSquare(epSquare) + " " +
				FENParser.formatHalfmoveClock(halfmoveClock) + " " +
				FENParser.formatFullmoveNum(plyCount);
	}
	
	public void setFEN(String FEN)
	{
		String[] elements = FEN.trim().split("\\s+");
		FENParser.loadBoardPostion(board, elements[0]);
		plyCount = FENParser.parseToMove(elements[1]) + FENParser.parseFullmoveNum(elements[5]);
		halfmoveClock = FENParser.parseHalfmoveClock(elements[4]);
		castlingRights = FENParser.parseCastlingRights(elements[2]);
		epSquare = FENParser.parseEnPassantSquare(elements[3]);
		updateView(FEN);
	}
}
