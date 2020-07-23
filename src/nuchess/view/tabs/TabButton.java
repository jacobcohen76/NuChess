package nuchess.view.tabs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nuchess.view.View;

class TabButton extends JPanel
{
	private static final long serialVersionUID = 2230075789276130331L;
	
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private JLabel titleLabel;
	
	protected TabbedView parent;
	protected View view;
	
	public TabButton(View view, TabbedView parent)
	{
		this.parent = parent;
		this.view = view;
		
		initComponents();
		putConstraints();
		addComponents();
		initListeners();
	}
	
	public void addCloseButton()
	{
		add(new CloseButton(10, 10));
	}
	
	private void initComponents()
	{
		titleLabel = new JLabel();
		titleLabel.setText(view.getTitle());
	}
	
	private void putConstraints()
	{
		
	}
	
	private void initListeners()
	{
		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{ clicked(e);  }
			public void mousePressed(MouseEvent e)	{ pressed(e);  }
			public void mouseReleased(MouseEvent e)	{ released(e); }
			public void mouseEntered(MouseEvent e)	{ entered(e);  }
			public void mouseExited(MouseEvent e)	{ exited(e);   }
		});
	}
	
	private void addComponents()
	{
		add(titleLabel);
	}
	
	private void clicked(MouseEvent e)
	{
		
	}
	
	private void pressed(MouseEvent e)
	{
		if(parent.displaying != this)
		{
			setCursor(DEFAULT_CURSOR);
			parent.requestDisplay(this);
		}
	}
	
	private void released(MouseEvent e)
	{
		
	}
	
	private void entered(MouseEvent e)
	{
		if(parent.displaying != this)
		{
			setCursor(HAND_CURSOR);
		}
	}
	
	private void exited(MouseEvent e)
	{
		setCursor(DEFAULT_CURSOR);
	}
	
	public void setSize(Dimension d)
	{
		super.setSize(d);
		super.setPreferredSize(d);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());
	}
	
	public void saveGraphicsAs()
	{
		view.saveGraphicsAs();
	}
	
	private void requestClose()
	{
		parent.requestClose(this);
	}
	
	private class CloseButton extends JPanel
	{
		private static final long serialVersionUID = 7854005908263578661L;
		
		public CloseButton(int width, int height)
		{
			setSize(new Dimension(width, height));
			addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent e)	{}
				public void mousePressed(MouseEvent e)	{}
				public void mouseReleased(MouseEvent e)	{ requestClose(); }
				public void mouseEntered(MouseEvent e)	{}
				public void mouseExited(MouseEvent e)	{}
			});
		}
		
		public void setSize(Dimension d)
		{
			super.setSize(d);
			super.setPreferredSize(d);
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			g.setColor(Color.RED);
			g.drawLine(0, 0, getWidth(), getHeight());
			g.drawLine(0, getHeight(), getWidth(), 0);
		}
	}
}
