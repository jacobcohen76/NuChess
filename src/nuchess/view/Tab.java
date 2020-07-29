package nuchess.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nuchess.view.graphics.ColorIDs;
import nuchess.view.graphics.IconIDs;
import nuchess.view.graphics.ResourceManager;

public class Tab extends JPanel
{
	private static final long serialVersionUID = 2230075789276130331L;
	
	private JLabel titleLabel;
	protected TabbedView parent;
	protected View view;
	protected int color;
	
	public Tab(View view)
	{
		this(view, null);
	}
	
	public Tab(View view, Image image)
	{
		this.view = view;
		setBackground(ColorIDs.TAB);
		
		initComponents(view.getTitle());
		putConstraints();
		addComponents();
		initListeners();
		
		if(image != null)
		{
			titleLabel.setIcon(new ImageIcon(image));
		}
	}
	
	public void addCloseButton()
	{
		Image icon = ResourceManager.getIcon(IconIDs.X_ICON);
		CloseButton closeButton = new CloseButton(icon.getWidth(null), icon.getHeight(null));
		add(closeButton);
	}
	
	private void initComponents(String text)
	{
		titleLabel = new JLabel();
		titleLabel.setText(text);
		titleLabel.setForeground(ResourceManager.getColor(ColorIDs.TAB_FONT));
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
			setBackground(ColorIDs.SELECTED_TAB);
			parent.requestDisplay(view);
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
		parent.closeTab(view);
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
