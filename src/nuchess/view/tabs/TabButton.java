package nuchess.view.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nuchess.view.View;
import nuchess.view.graphics.ColorIDs;
import nuchess.view.graphics.IconIDs;
import nuchess.view.graphics.ResourceManager;

class TabButton extends JPanel
{
	private static final long serialVersionUID = 2230075789276130331L;
	
	private JLabel titleLabel;
	protected TabbedView parent;
	protected View view;
	protected int color;
	
	public TabButton(View view, TabbedView parent)
	{
		this.parent = parent;
		this.view = view;
		setBackground(ColorIDs.TAB);
		
		initComponents();
		putConstraints();
		addComponents();
		initListeners();
	}
	
	public void addCloseButton()
	{
		Image icon = ResourceManager.getIcon(IconIDs.X_ICON);
		add(new CloseButton(icon.getWidth(null), icon.getHeight(null)));
	}
	
	private void initComponents()
	{
		titleLabel = new JLabel();
		titleLabel.setText(view.getTitle());
		titleLabel.setForeground(Color.WHITE);
	}
	
	private void putConstraints()
	{
		FlowLayout layout = new FlowLayout();
		layout.setHgap(5);
		layout.setVgap(5);
		layout.setAlignment(FlowLayout.LEFT);
		setLayout(layout);
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
			setBackground(ColorIDs.SELECTED_TAB);
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
			setBackground(ColorIDs.HOVERING_TAB);
		}
	}
	
	private void exited(MouseEvent e)
	{
		if(parent.displaying != this && !contains(e.getPoint()))
		{
			setBackground(ColorIDs.TAB);
		}
	}
	
	public void setSize(Dimension d)
	{
		super.setSize(d);
		super.setPreferredSize(d);
	}
	
	public void saveGraphicsAs()
	{
		view.saveGraphicsAs();
	}
	
	private void requestClose()
	{
		parent.requestClose(this);
	}
	
	public void setBackground(int id)
	{
		color = id;
		setBackground(ResourceManager.getColor(color));
	}
	
	private class CloseButton extends JPanel
	{
		private static final long serialVersionUID = 7854005908263578661L;
		
		private int icon;
		
		public CloseButton(int width, int height)
		{
			icon = IconIDs.X_ICON;
			color = ColorIDs.TAB;
			setSize(new Dimension(width, height));
			initListeners();
		}
		
		private void initListeners()
		{
			addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent e)	{}
				public void mousePressed(MouseEvent e)	{}
				public void mouseReleased(MouseEvent e) { released(e); }
				public void mouseEntered(MouseEvent e)	{ entered(e);  }
				public void mouseExited(MouseEvent e)	{ exited(e);   }
			});
		}
		
		private void released(MouseEvent e)
		{
			if(icon == IconIDs.X_HOVERING_ICON)
			{
				requestClose();
			}
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
		
		public void setSize(Dimension d)
		{
			super.setSize(d);
			super.setPreferredSize(d);
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			g.setColor(ResourceManager.getColor(color));
			g.fillRect(0, 0, getWidth(), getHeight());
			if(color == ColorIDs.SELECTED_TAB || color == ColorIDs.HOVERING_TAB)
			{
				g.drawImage(ResourceManager.getIcon(icon), 0, 0, getWidth(), getHeight(), this);
			}
		}
	}
}
