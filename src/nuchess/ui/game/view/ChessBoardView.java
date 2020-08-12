package nuchess.ui.game.view;

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
import nuchess.graphics.ChessboardGraphics;
import nuchess.graphics.LayeredGraphics;
import nuchess.graphics.LayeredGraphicsPanel;
import nuchess.graphics.ResourceManager;

class ChessBoardView
{
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private LayeredGraphicsPanel lgp;
	private MoveSelector selector;
	private ChessboardGraphics cbg;
	private MouseMotionListener enabledMML, promoMML, disabledMML;
	private MouseListener enabledML, promoML, disabledML;
	private ArrayList<BoardViewState> states;
	private BoardViewState currentState;
	private long occ, dots, corners, promo;
	private int cursorSquare, promoFrom, promoTo;
	private boolean externalSelectionEnabled, internalSelectionEnabled, promoState, promoIsCapture;
	
	protected GameView parent;
	
	public ChessBoardView(boolean flipped)
	{
		LayeredGraphics lg = new LayeredGraphics(ResourceManager.getSquareSize() * 8, ResourceManager.getSquareSize() * 8);
		
		lgp = new LayeredGraphicsPanel(lg);
		selector = new MoveSelector();
		cbg = new ChessboardGraphics(flipped, lg);
		
		states = new ArrayList<BoardViewState>();
		currentState = null;
		cursorSquare = Square.NULL;
		occ = dots = corners = promo = 0L;
		
		externalSelectionEnabled = true;
		internalSelectionEnabled = true;
		promoState = false;
		
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
		promoMML = new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)	{ promoMoved(e); }
			public void mouseMoved(MouseEvent e)	{ promoMoved(e); }
		};
		promoML = new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{ promoPressed(e); }
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
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			clear(dots, corners, selector.getFrom());
			selector.select(cbg.getSquare(e.getX(), e.getY()));
			dots = selector.getDestinations() & ~occ;
			corners = (selector.getDestinations() & occ);
			paint(dots, corners, selector.getFrom());
			lgp.repaint();
		}
	}
	
	private void moved(MouseEvent e)
	{
		int square = cbg.getSquare(e.getX(), e.getY());
		if(square != cursorSquare)
		{
			updateCursor(square);
		}
	}
	
	private void promoPressed(MouseEvent e)
	{
		int square = cbg.getSquare(e.getX(), e.getY());
		if(((promo >> square) & 1) == 1)
		{
			switch(Square.rank(square))
			{
				case Square.rank_1:
				case Square.rank_8:
					parent.requestMove(new CMove(promoFrom, promoTo, promoIsCapture ? CMove.QUEEN_PROMO_CAP : CMove.QUEEN_PROMO));
					break;
					
				case Square.rank_2:
				case Square.rank_7:
					parent.requestMove(new CMove(promoFrom, promoTo, promoIsCapture ? CMove.KNIGHT_PROMO_CAP : CMove.KNIGHT_PROMO));
					break;
					
				case Square.rank_3:
				case Square.rank_6:
					parent.requestMove(new CMove(promoFrom, promoTo, promoIsCapture ? CMove.ROOK_PROMO_CAP : CMove.ROOK_PROMO));
					break;
					
				case Square.rank_4:
				case Square.rank_5:
					parent.requestMove(new CMove(promoFrom, promoTo, promoIsCapture ? CMove.BISHOP_PROMO_CAP : CMove.BISHOP_PROMO));
					break;
			}
		}
		setPromoState(false, square);
	}
	
	private void promoMoved(MouseEvent e)
	{
		int square = cbg.getSquare(e.getX(), e.getY());
		if(square != cursorSquare)
		{
			lgp.setCursor(((promo >> square) & 1) == 1 ? HAND_CURSOR : DEFAULT_CURSOR);
			cursorSquare = square;
		}
	}
	
	private void updateCursor(int square)
	{
		lgp.setCursor(selector.isClickable(square) ? HAND_CURSOR : DEFAULT_CURSOR);
		cursorSquare = square;
	}
	
	protected void requestMove(CMove move)
	{
		lgp.setCursor(DEFAULT_CURSOR);
		parent.requestMove(move);
	}
	
	protected void requestPromoMove(boolean isCapture, int from, int to)
	{
		promoIsCapture = isCapture;
		promoFrom = from;
		promoTo = to;
		setPromoState(true, to);
	}
	
	public void setSelectionEnabled(boolean b)
	{
		externalSelectionEnabled = b;
		if(externalSelectionEnabled && internalSelectionEnabled)
		{
			if(promoState)
			{
				lgp.setMouseListener(promoML);
				lgp.setMouseMotionListener(promoMML);
				lgp.setCursor(((promo >> cursorSquare) & 1) == 1 ? HAND_CURSOR : DEFAULT_CURSOR);
			}
			else
			{
				lgp.setMouseListener(enabledML);
				lgp.setMouseMotionListener(enabledMML);
				updateCursor(cursorSquare);
			}
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
			if(promoState)
			{
				lgp.setMouseListener(promoML);
				lgp.setMouseMotionListener(promoMML);
				lgp.setCursor(((promo >> cursorSquare) & 1) == 1 ? HAND_CURSOR : DEFAULT_CURSOR);
			}
			else
			{
				lgp.setMouseListener(enabledML);
				lgp.setMouseMotionListener(enabledMML);
				updateCursor(cursorSquare);
			}
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
	
	public void setPromoState(boolean b, int square)
	{
		if(promoState != b)
		{
			if(b)
			{
				lgp.setMouseListener(promoML);
				lgp.setMouseMotionListener(promoMML);
				if(Square.rank(square) == Square.rank_1)
				{
					promo = 0x0000000001010101L;
					promo <<= Square.file(square);
				}
				else if(Square.rank(square) == Square.rank_8)
				{
					promo = 0x0101010100000000L;
					promo <<= Square.file(square);
				}
				cbg.paintPromoState(square, promo);
				lgp.repaint();
			}
			else
			{
				promo = 0L;
				updateCursor(cursorSquare);
				lgp.setMouseListener(enabledML);
				lgp.setMouseMotionListener(enabledMML);
				cbg.clear(ChessboardGraphics.PROMO_LAYER);
				lgp.repaint();
			}
			promoState = b;
		}
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
	
	public int getX(int square)
	{
		return cbg.getX(square);
	}
	
	public int getY(int square)
	{
		return cbg.getY(square);
	}
}
