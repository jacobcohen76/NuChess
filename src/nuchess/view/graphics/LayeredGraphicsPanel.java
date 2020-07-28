package nuchess.view.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class LayeredGraphicsPanel extends JPanel
{
	private static final long serialVersionUID = -5556734795360627315L;
	
	private LayeredGraphics lg;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	
	public LayeredGraphicsPanel(LayeredGraphics lg)
	{
		this.lg = lg;
		mouseListener = null;
		mouseMotionListener = null;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		lg.render(g, this);
	}
	
	public void setSize(Dimension d)
	{
		super.setSize(d);
		super.setPreferredSize(d);
		lg.setSize(d.width, d.height);
	}
	
	public void setMouseListener(MouseListener ml)
	{
		if(mouseListener != null)
		{
			removeMouseListener(mouseListener);
		}
		addMouseListener(ml);
		mouseListener = ml;
	}
	
	public void setMouseMotionListener(MouseMotionListener mml)
	{
		if(mouseMotionListener != null)
		{
			removeMouseMotionListener(mouseMotionListener);
		}
		addMouseMotionListener(mml);
		mouseMotionListener = mml;
	}
	
	public LayeredGraphics getLayeredGraphics()
	{
		return lg;
	}
}
