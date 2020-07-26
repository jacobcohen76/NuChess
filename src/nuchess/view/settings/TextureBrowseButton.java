package nuchess.view.settings;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nuchess.view.graphics.ResourceManager;

public class TextureBrowseButton extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private XButton xbutton;
	private JLabel label;
	private int id;
	
	public TextureBrowseButton(int id, String text)
	{
		this.id = id;
		xbutton = new XButton();
		label = new JLabel();
		
		xbutton.setIconVisible(false);
		label.setIcon(new ImageIcon(ResourceManager.getUnscaledTexture(id).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		label.setText(text);
		setOpaque(false);
		
		putConstraints();
		initListeners();
		addComponents();
	}
	
	protected void putConstraints()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	}
	
	protected void initListeners()
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
		
	}
	
	private void setFile(File file)
	{
		ResourceManager.loadTextureFile(id, file);
		label.setIcon(new ImageIcon(ResourceManager.getUnscaledTexture(id).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
	}
	
	private void entered(MouseEvent e)
	{
		xbutton.setIconVisible(true);
		repaint();
	}
	
	private void exited(MouseEvent e)
	{
		if(!contains(e.getPoint()))
		{
			xbutton.setIconVisible(false);
			repaint();
		}
	}
	
	protected void addComponents()
	{
		add(xbutton);
		add(label);
	}
}
