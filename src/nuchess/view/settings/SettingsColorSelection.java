package nuchess.view.settings;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import nuchess.view.graphics.ResourceCreator;
import nuchess.view.graphics.ResourceManager;

public class SettingsColorSelection extends Setting
{
	private static final long serialVersionUID = 2162094966803093264L;
	
	private XButton xbutton;
	private JLabel iconLabel;
	private JButton chooseColorButton;
	
	private Color color;
	private String colorName;
	private int id;
	
	public SettingsColorSelection(String colorName, int id)
	{
		this.color = ResourceManager.getColor(id);
		this.colorName = colorName;
		this.id = id;
		
		initComponents();
		putConstraints();
		initListeners();
		addComponents();
	}
	
	@Override
	protected void initComponents()
	{
		xbutton = new XButton();
		iconLabel = new JLabel();
		chooseColorButton = new JButton();
		
		xbutton.setIconVisible(false);
		iconLabel.setIcon(new ImageIcon(ResourceCreator.createSolidSquare(30, color)));
		chooseColorButton.setText("Choose Color");
	}
	
	@Override
	protected void putConstraints()
	{
		
	}
	
	@Override
	protected void initListeners()
	{
		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e)	{ entered(e); }
			public void mouseExited(MouseEvent e)	{ exited(e);  }
		});
		chooseColorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) { chooseColorButtonPressed(e); }
		});
		xbutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) { xbuttonPressed(e); }
		});
	}
	
	@Override
	protected void addComponents()
	{
		add(xbutton);
		add(iconLabel);
		add(chooseColorButton);
	}
	
	@Override
	protected void flagAsHovering()
	{
		xbutton.setIconVisible(true);
		repaint();
	}
	
	@Override
	protected void unflagAsHovering()
	{
		xbutton.setIconVisible(false);
		repaint();
	}
	
	private void entered(MouseEvent e)
	{
		parent.setHoveringSetting(this);
	}
	
	private void exited(MouseEvent e)
	{
		if(!contains(e.getPoint()))
		{
			unflagAsHovering();
		}
	}
	
	private void chooseColorButtonPressed(ActionEvent e)
	{
		Color selected = Chooser.showDialog(parent.getPanel(), "Select a new color for " + colorName, color);
		if(selected != null)
		{
			color = selected;
			iconLabel.setIcon(new ImageIcon(ResourceCreator.createSolidSquare(30, color)));
			ResourceManager.setColor(id, color);
		}
	}
	
	private void xbuttonPressed(ActionEvent e)
	{
		color = ResourceManager.getDefaultColor(id);
		iconLabel.setIcon(new ImageIcon(ResourceCreator.createSolidSquare(30, color)));
		ResourceManager.setColor(id, color);
		
		System.out.println("X Button Pressed");
	}
}
