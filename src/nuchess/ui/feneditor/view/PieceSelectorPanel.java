package nuchess.ui.feneditor.view;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import nuchess.engine.Piece;
import nuchess.graphics.LayeredGraphics;
import nuchess.graphics.LayeredGraphicsPanel;
import nuchess.graphics.ResourceManager;
import nuchess.graphics.TextureIDs;

class PieceSelectorPanel
{
	private static final int ICON_LAYER = 0;
	private static final int SELECTION_LAYER = 1;
	
	private LayeredGraphicsPanel lgp;
	private LayeredGraphics lg;
	private int selected;
	
	protected FENEditorView parent;
	
	public PieceSelectorPanel(int selected)
	{
		lg = new LayeredGraphics(ResourceManager.getSquareSize() * 2, ResourceManager.getSquareSize() * 6);
		lgp = new LayeredGraphicsPanel(lg);
		this.selected = selected;
		parent = null;
		lg.addNewLayer();
		lg.addNewLayer();
		lgp.setOpaque(false);
		resizePanel();
		initListeners();
	}
	
	private void resizePanel()
	{
		lgp.setSize(lg.getNewDimensions());
	}
	
	private int getIndex(MouseEvent e)
	{
		return (e.getX() / ResourceManager.getSquareSize()) + 2 * (e.getY() / ResourceManager.getSquareSize()) + 2;
	}
	
	private void pressed(MouseEvent e)
	{
		int index = getIndex(e);
		if(selected != index)
		{
			clearSelection(selected);
			paintSelection(index);
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
		return (selection & 1) * ResourceManager.getSquareSize();
	}
	
	private int getY(int selection)
	{
		return (selection / 2) * ResourceManager.getSquareSize() - ResourceManager.getSquareSize();
	}
	
	public void initGraphics()
	{
		paintIcons();
		paintSelection(selected);
	}
	
	private void paintIcons()
	{
		Graphics2D g = lg.getGraphics(ICON_LAYER);
		for(int piece = Piece.WHITE_PAWN; piece <= Piece.BLACK_KING; piece++)
		{
			g.drawImage(ResourceManager.getTexture(piece), getX(piece), getY(piece), ResourceManager.getSquareSize(), ResourceManager.getSquareSize(), null);
		}
	}
	
	private void paintSelection(int selection)
	{
		lg.getGraphics(SELECTION_LAYER).drawImage(ResourceManager.getTexture(TextureIDs.BORDER), getX(selection), getY(selection), ResourceManager.getSquareSize(), ResourceManager.getSquareSize(), null);
	}
	
	private void clearSelection(int selection)
	{
		lg.clear(SELECTION_LAYER, getX(selection), getY(selection), ResourceManager.getSquareSize(), ResourceManager.getSquareSize());
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
