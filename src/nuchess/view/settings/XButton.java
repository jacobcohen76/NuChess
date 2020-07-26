package nuchess.view.settings;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import nuchess.view.graphics.IconIDs;
import nuchess.view.graphics.ResourceManager;

public class XButton extends JPanel
{
	private static final long serialVersionUID = -6927566984109380117L;
	
	private int icon;
	private boolean iconVisible;
	private ActionListener listener;
	
	public XButton()
	{
		initListeners();
		icon = IconIDs.X_ICON;
		iconVisible = true;
		listener = null;
		setSize(new Dimension(10, 10));
		setOpaque(false);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(iconVisible)
		{
			g.drawImage(ResourceManager.getIcon(icon), 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	private void initListeners()
	{
		addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{ pressed(e);  }
			public void mouseReleased(MouseEvent e) { released(e); }
			public void mouseEntered(MouseEvent e)	{ entered(e);  }
			public void mouseExited(MouseEvent e)	{ exited(e);   }
		});
	}
	
	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
	}
	
	private void pressed(MouseEvent e)
	{
		if(listener != null)
		{
			listener.actionPerformed(null);
		}
	}
	
	private void released(MouseEvent e)
	{
		
	}
	
	private void entered(MouseEvent e)
	{
		icon = IconIDs.X_HOVERING_ICON;
		repaint();
	}
	
	private void exited(MouseEvent e)
	{
		icon = IconIDs.X_ICON;
		repaint();
	}
	
	public void setIconVisible(boolean b)
	{
		iconVisible = b;
	}
	
	public void setSize(Dimension d)
	{
		super.setSize(d);
		super.setPreferredSize(d);
	}
}
