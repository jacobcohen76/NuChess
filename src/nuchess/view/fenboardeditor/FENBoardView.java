package nuchess.view.fenboardeditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nuchess.view.graphics.ChessboardGraphics;
import nuchess.view.graphics.LayeredGraphics;
import nuchess.view.graphics.LayeredGraphicsPanel;
import nuchess.view.graphics.ResourceManager;

class FENBoardView
{
	private LayeredGraphicsPanel lgp;
	private ChessboardGraphics cbg;
	
	protected FENBoardEditorView parent;
	
	public FENBoardView(boolean flipped)
	{
		LayeredGraphics lg = new LayeredGraphics(ResourceManager.getSquareSize() * 8, ResourceManager.getSquareSize() * 8);
		lgp = new LayeredGraphicsPanel(lg);
		cbg = new ChessboardGraphics(flipped, lg);
		parent = null;
		
		initListeners();
		initGraphics();
		resizeViewPanel();
	}
	
	private void initListeners()
	{
		lgp.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{ pressed(e);  }
			public void mouseReleased(MouseEvent e)	{ released(e); }
			public void mouseEntered(MouseEvent e)	{}
			public void mouseExited(MouseEvent e)	{}
		});
		lgp.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)	{ dragged(e);  }
			public void mouseMoved(MouseEvent e)	{}
		});
	}
	
	private void initGraphics()
	{
		cbg.paintBackground();
	}
	
	private void resizeViewPanel()
	{
		lgp.setSize(cbg.getDimensions());
	}
	
	private void pressed(MouseEvent e)
	{
		parent.pressed(e.getButton(), cbg.getSquare(e.getX(), e.getY()));
	}
	
	private void released(MouseEvent e)
	{
		parent.released(e.getButton(), cbg.getSquare(e.getX(), e.getY()));
	}
	
	private void dragged(MouseEvent e)
	{
		if(lgp.contains(e.getPoint()))
		{
			parent.dragged(cbg.getSquare(e.getX(), e.getY()));
		}
	}
	
	public void repaint()
	{
		lgp.repaint();
	}
	
	public void paintFEN(String FEN)
	{
		cbg.paintFEN(FEN);
	}
	
	public void clearPieceLayer(long occ)
	{
		cbg.clear(occ, ChessboardGraphics.PIECE_LAYER);
	}
	
	public JPanel getPanel()
	{
		return lgp;
	}
	
	public BufferedImage getRenderedImage()
	{
		return cbg.getRenderedImage();
	}
}
