package nuchess.view.fenbuilder;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import nuchess.engine.Piece;
import nuchess.view.graphics.LayeredGraphics;
import nuchess.view.graphics.LayeredGraphicsPanel;
import nuchess.view.graphics.TextureIDs;

class PieceSelectorPanel
{
	private static final int ICON_LAYER = 0;
	private static final int SELECTION_LAYER = 1;
	
	private LayeredGraphicsPanel lgp;
	private LayeredGraphics lg;
	private int selected, squareSize;

	protected FENBuilderView parent;
	
	public PieceSelectorPanel(int squareSize, int selected)
	{
		lg = new LayeredGraphics(squareSize * 2, squareSize * 6);
		lgp = new LayeredGraphicsPanel(lg);
		this.squareSize = squareSize;
		this.selected = selected;
		parent = null;
		lg.addNewLayer();
		lg.addNewLayer();
		resizePanel();
		initListeners();
	}
	
	private void resizePanel()
	{
		lgp.setSize(lg.getNewDimensions());
	}
	
	private int getIndex(MouseEvent e)
	{
		return (e.getX() / squareSize) + 2 * (e.getY() / squareSize) + 2;
	}
	
	private void pressed(MouseEvent e)
	{
		int index = getIndex(e);
		if(selected != index)
		{
			clearSelection(selected);
			paintSelection(parent.getScaledResources(), index);
			parent.setPiece(index);
			selected = index;
			lgp.repaint();
		}
	}
	
	private void initListeners()
	{
		lgp.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{ pressed(e); }
			public void mouseReleased(MouseEvent e)	{}
			public void mouseEntered(MouseEvent e)	{}
			public void mouseExited(MouseEvent e)	{}
		});
	}
	
	private int getX(int selection)
	{
		return (selection & 1) * squareSize;
	}
	
	private int getY(int selection)
	{
		return (selection / 2) * squareSize - squareSize;
	}
	
	public void initGraphics()
	{
		paintIcons(parent.getScaledResources());
		paintSelection(parent.getScaledResources(), selected);
	}
	
	private void paintIcons(Image[] icons)
	{
		Graphics2D g = lg.getGraphics(ICON_LAYER);
		for(int piece = Piece.WHITE_PAWN; piece <= Piece.BLACK_KING; piece++)
		{
			g.drawImage(icons[TextureIDs.pieceID(piece)], getX(piece), getY(piece), squareSize, squareSize, null);
		}
	}
	
	private void paintSelection(Image[] icons, int selection)
	{
		lg.getGraphics(SELECTION_LAYER).drawImage(icons[TextureIDs.BORDER], getX(selection), getY(selection), squareSize, squareSize, null);
	}
	
	private void clearSelection(int selection)
	{
		lg.clear(SELECTION_LAYER, getX(selection), getY(selection), squareSize, squareSize);
	}
	
	public void repaint()
	{
		lgp.repaint();
	}
	
	public JPanel getPanel()
	{
		return lgp;
	}
}
