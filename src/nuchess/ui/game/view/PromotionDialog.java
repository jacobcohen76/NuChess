package nuchess.ui.game.view;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;

import nuchess.engine.Piece;
import nuchess.graphics.LayeredGraphics;
import nuchess.graphics.LayeredGraphicsPanel;
import nuchess.graphics.ResourceManager;

public class PromotionDialog extends JDialog
{
	private static final long serialVersionUID = -5223507510979692600L;
	
	private static final int ICON_LAYER = 0;
	private static final int SELECTION_LAYER = 1;
	
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	private LayeredGraphicsPanel lgp;
	private LayeredGraphics lg;
	private int selected, color;
	
	public PromotionDialog()
	{
		lg = new LayeredGraphics(ResourceManager.getSquareSize() * 4, ResourceManager.getSquareSize() * 2);
		lgp = new LayeredGraphicsPanel(lg);
		
		lg.addNewLayer();
		lg.addNewLayer();
		
		initListeners();
		add(lgp);
		lgp.setCursor(HAND_CURSOR);
		setResizable(false);
		
		setSize(ResourceManager.getSquareSize() * 4, ResourceManager.getSquareSize() * 2);
	}
	
	public void setColor(int toMove)
	{
		color = toMove;
		lg.clear(ICON_LAYER);
		paintIcons();
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
			
			selected = index;
			lgp.repaint();
		}
	}
	
	private void paintIcons()
	{
		Graphics2D g = lg.getGraphics(ICON_LAYER);
		g.drawImage(ResourceManager.getTexture(Piece.WHITE_QUEEN  + color), ResourceManager.getSquareSize() * 0, 0, ResourceManager.getSquareSize(), ResourceManager.getSquareSize(), null);
		g.drawImage(ResourceManager.getTexture(Piece.WHITE_KNIGHT + color), ResourceManager.getSquareSize() * 1, 0, ResourceManager.getSquareSize(), ResourceManager.getSquareSize(), null);
		g.drawImage(ResourceManager.getTexture(Piece.WHITE_BISHOP + color), ResourceManager.getSquareSize() * 2, 0, ResourceManager.getSquareSize(), ResourceManager.getSquareSize(), null);
		g.drawImage(ResourceManager.getTexture(Piece.WHITE_ROOK   + color), ResourceManager.getSquareSize() * 3, 0, ResourceManager.getSquareSize(), ResourceManager.getSquareSize(), null);
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
}
