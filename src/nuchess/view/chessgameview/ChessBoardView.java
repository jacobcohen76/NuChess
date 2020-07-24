package nuchess.view.chessgameview;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import nuchess.engine.CMove;
import nuchess.engine.Square;
import nuchess.view.graphics.ChessboardGraphics;
import nuchess.view.graphics.LayeredGraphics;
import nuchess.view.graphics.LayeredGraphicsPanel;
import nuchess.view.graphics.ResourceManager;

class ChessBoardView
{
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private LayeredGraphicsPanel lgp;
	private MoveSelector selector;
	private ChessboardGraphics cbg;
	private MouseMotionListener enabledMML, disabledMML;
	private MouseListener enabledML, disabledML;
	private ArrayList<BoardViewState> states;
	private BoardViewState currentState;
	private long occ, dots, corners;
	private int cursorSquare;
	private boolean externalSelectionEnabled, internalSelectionEnabled;
	
	protected ChessGameView parent;
	
	public ChessBoardView(boolean flipped)
	{
		LayeredGraphics lg = new LayeredGraphics(ResourceManager.getSquareSize() * 8, ResourceManager.getSquareSize() * 8);
		
		lgp = new LayeredGraphicsPanel(lg);
		selector = new MoveSelector();
		cbg = new ChessboardGraphics(flipped, lg);
		
		states = new ArrayList<BoardViewState>();
		currentState = null;
		cursorSquare = Square.NULL;
		occ = dots = corners = 0L;
		
		externalSelectionEnabled = true;
		internalSelectionEnabled = true;
		
		initListeners();
		linkObjects();
		setSelectionEnabled(true);
		
		parent = null;
		
		resizeViewPanel();
	}
	
	private void initListeners()
	{
		enabledMML = new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)	{ moved(e); }
			public void mouseMoved(MouseEvent e)	{ moved(e); }
		};
		enabledML = new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{ pressed(e); }
			public void mouseReleased(MouseEvent e)	{}
			public void mouseEntered(MouseEvent e)	{}
			public void mouseExited(MouseEvent e)	{}
		};
		disabledMML = new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)	{}
			public void mouseMoved(MouseEvent e)	{}
		};
		disabledML = new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{}
			public void mouseReleased(MouseEvent e)	{}
			public void mouseEntered(MouseEvent e)	{}
			public void mouseExited(MouseEvent e)	{}
		};
	}
	
	private void resizeViewPanel()
	{
		lgp.setSize(cbg.getDimensions());
	}
	
	private void linkObjects()
	{
		selector.parent = this;
	}
	
	private void pressed(MouseEvent e)
	{
		clear(dots, corners, selector.getFrom());
		selector.select(cbg.getSquare(e.getX(), e.getY()));
		dots = selector.getDestinations() & ~occ;
		corners = (selector.getDestinations() & occ);
		paint(dots, corners, selector.getFrom());
		lgp.repaint();
	}
	
	private void moved(MouseEvent e)
	{
		int square = cbg.getSquare(e.getX(), e.getY());
		if(square != cursorSquare)
			updateCursor(square);
	}
	
	private void updateCursor(int square)
	{
		if(selector.isClickable(square))
			lgp.setCursor(HAND_CURSOR);
		else
			lgp.setCursor(DEFAULT_CURSOR);
		cursorSquare = square;
	}
	
	protected void requestMove(CMove move)
	{
		parent.requestMove(move);
		lgp.setCursor(DEFAULT_CURSOR);
	}
	
	public void setSelectionEnabled(boolean b)
	{
		externalSelectionEnabled = b;
		if(externalSelectionEnabled && internalSelectionEnabled)
		{
			lgp.setMouseListener(enabledML);
			lgp.setMouseMotionListener(enabledMML);
			updateCursor(cursorSquare);
		}
		else
		{
			lgp.setMouseListener(disabledML);
			lgp.setMouseMotionListener(disabledMML);
			lgp.setCursor(DEFAULT_CURSOR);
		}
	}
	
	/**
	 * When going back and loading a previous board state, the user should not be able to select a
	 * move because the moves to select will not be synchronized with the board position that is
	 * being displayed. To fix this an additional internalSelectionEnabled flag has been added.
	 * To select a move both the internal and external selection flags must be set to true.
	 * <p>
	 * Will enable selection if both the internal selection flag (@param b) and external
	 * selection flag are set to true.
	 * 
	 * @param b the value to set the internal selection flag to
	 */
	private void setInternalSelectionEnabled(boolean b)
	{
		internalSelectionEnabled = b;
		if(externalSelectionEnabled && internalSelectionEnabled)
		{
			lgp.setMouseListener(enabledML);
			lgp.setMouseMotionListener(enabledMML);
			updateCursor(cursorSquare);
		}
		else
		{
			lgp.setMouseListener(disabledML);
			lgp.setMouseMotionListener(disabledMML);
			lgp.setCursor(DEFAULT_CURSOR);
		}
	}
	
	public void setFlipped(boolean b)
	{
		clear(dots, corners, selector.getFrom());
		clear(currentState);
		cbg.setFlipped(b);
		paint(dots, corners, selector.getFrom());
		paint(currentState);
		lgp.repaint();
	}
	
	public void setSelectableMoves(List<CMove> selectableMoves)
	{
		selector.setSelectableMoves(selectableMoves);
	}
	
	public void setMoveableSquares(long moveableSquares)
	{
		selector.setMoveableSquares(moveableSquares);
	}
	
	public void displayState(int ply)
	{
		clear(currentState);
		currentState = states.get(ply);
		paint(currentState);
		
		if(ply != mostRecentState())
		{
			setInternalSelectionEnabled(false);
			clear(dots, corners, selector.getFrom());
			selector.deselect();
		}
		else
		{
			setInternalSelectionEnabled(true);
		}
		
		lgp.repaint();
	}
	
	private void clear(BoardViewState bvs)
	{
		cbg.clear(bvs.occBB, ChessboardGraphics.PIECE_LAYER);
		cbg.clear(bvs.move.from(), ChessboardGraphics.MASK_LAYER);
		cbg.clear(bvs.move.to(), ChessboardGraphics.MASK_LAYER);
		cbg.clear(bvs.checkBB, ChessboardGraphics.HIGHLIGHT_LAYER);
	}
	
	private void clear(long dots, long corners, int from)
	{
		cbg.clear(dots, ChessboardGraphics.DOT_LAYER);
		cbg.clear(corners, ChessboardGraphics.CORNER_LAYER);
		cbg.clear(from, ChessboardGraphics.CORNER_LAYER);
	}
	
	private void paint(BoardViewState bvs)
	{
		cbg.paintFEN(bvs.FEN);
		cbg.paintCheckHighlight(bvs.checkBB);
		cbg.paintMovedMask(bvs.move);
	}
	
	private void paint(long dots, long corners, int from)
	{
		cbg.paintDots(dots);
		cbg.paintCorners(corners);
		cbg.paintCorners(from);
	}
	
	public void setInitialState(BoardViewState initialState)
	{
		currentState = initialState;
		states.add(initialState);
		cbg.paintBackground();
		paint(initialState);
		lgp.repaint();
	}
	
	public void addNewState(BoardViewState newState)
	{
		states.add(newState);
	}
	
	public int mostRecentState()
	{
		return states.size() - 1;
	}
	
	public JPanel getPanel()
	{
		return lgp;
	}
	
	public void setOccupancy(long occ)
	{
		this.occ = occ;
	}
	
	public BufferedImage getRenderedImage()
	{
		return cbg.getRenderedImage();
	}
}
