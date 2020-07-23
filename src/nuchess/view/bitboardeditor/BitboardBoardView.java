package nuchess.view.bitboardeditor;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nuchess.view.graphics.ChessboardGraphics;
import nuchess.view.graphics.LayeredGraphics;
import nuchess.view.graphics.LayeredGraphicsPanel;

public class BitboardBoardView
{
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	private LayeredGraphicsPanel lgp;
	private ChessboardGraphics cbg;
	private long displaying;
	private int button;
	
	protected BitboardEditorView parent;
	protected boolean shiftheld;
	
	public BitboardBoardView(boolean flipped)
	{
		LayeredGraphics lg = new LayeredGraphics(ChessboardGraphics.getSquareSize() * 8, ChessboardGraphics.getSquareSize() * 8);
		cbg = new ChessboardGraphics(flipped, lg);
		lgp = new LayeredGraphicsPanel(lg);
		displaying = 0L;
		
		parent = null;
		shiftheld = false;
		
		initGraphics();
		initListeners();
		resizeViewPanel();
		lgp.setCursor(HAND_CURSOR);
	}
	
	private void initGraphics()
	{
		cbg.paintBackground();
	}
	
	private void initListeners()
	{
		lgp.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)
			{
				if(button == MouseEvent.NOBUTTON)
				{
					button = e.getButton();
				}
				if(lgp.contains(e.getPoint()))
				{
					pressed(e);
				}
			}
			public void mouseReleased(MouseEvent e)
			{
				if(button == e.getButton())
				{
					button = MouseEvent.NOBUTTON;
				}
			}
			public void mouseEntered(MouseEvent e)	{}
			public void mouseExited(MouseEvent e)	{}		
		});
		lgp.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)
			{
				if(shiftheld && lgp.contains(e.getPoint()))
				{
					pressed(e);
				}
			}
			public void mouseMoved(MouseEvent e)	{}
		});
	}
	
	private void resizeViewPanel()
	{
		lgp.setSize(cbg.getDimensions());
	}
	
	private void pressed(MouseEvent e)
	{
		int square = cbg.getSquare(e.getX(), e.getY());
		if(button == MouseEvent.BUTTON1)
		{
			if(((displaying >> square) & 1) == 0)
			{
				displaying |= 1L << square;
				cbg.paintDot(square);
				parent.setInputBitboard(displaying);
				repaint();
			}
		}
		else if(button == MouseEvent.BUTTON3)
		{
			if(((displaying >> square) & 1) == 1)
			{
				displaying &= ~(1L << square);
				cbg.clear(square, ChessboardGraphics.DOT_LAYER);
				parent.setInputBitboard(displaying);
				repaint();
			}
		}
	}
	
	public void display(long bitboard)
	{
		cbg.clear(displaying, ChessboardGraphics.DOT_LAYER);
		cbg.paintDots(bitboard);
		displaying = bitboard;
	}
	
	public void repaint()
	{
		lgp.repaint();
	}
	
	public BufferedImage getRenderedImage()
	{
		return cbg.getRenderedImage();
	}
	
	public long getDisplaying()
	{
		return displaying;
	}
	
	public JPanel getPanel()
	{
		return lgp;
	}
}
