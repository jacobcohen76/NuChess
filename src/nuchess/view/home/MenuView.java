package nuchess.view.home;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.view.Tab;
import nuchess.view.View;

public class MenuView implements View
{
	private JPanel panel, displaying;
	private JMenuBar leftMenuBar, rightMenuBar;
	private SpringLayout layout;
	
	public MenuView()
	{
		initComponents();
		putConstraints();
		addComponents();
	}
	
	private void initComponents()
	{
		panel = new JPanel();
		leftMenuBar = new JMenuBar();
		rightMenuBar = new JMenuBar();
		layout = new SpringLayout();
		displaying = null;
	}
	
	public void addLeft(JMenuItem menuItem)
	{
		leftMenuBar.add(menuItem);
	}
	
	public void addRight(JMenuItem menuItem)
	{
		rightMenuBar.add(menuItem);
	}
	
	private void putConstraints()
	{
		layout.putConstraint(SpringLayout.NORTH, leftMenuBar, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, leftMenuBar, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, rightMenuBar, 0, SpringLayout.NORTH, leftMenuBar);
		layout.putConstraint(SpringLayout.EAST, rightMenuBar, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, rightMenuBar, 0, SpringLayout.SOUTH, leftMenuBar);
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(leftMenuBar);
		panel.add(rightMenuBar);
	}
	
	public void display(View view)
	{
		display(view.getPanel());
	}
	
	public void display(JPanel content)
	{
		if(displaying != null)
		{
			layout.removeLayoutComponent(displaying);
			panel.remove(displaying);
		}
		displaying = content;
		layout.putConstraint(SpringLayout.NORTH, displaying, 0, SpringLayout.SOUTH, leftMenuBar);
		layout.putConstraint(SpringLayout.EAST, displaying, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, displaying, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, displaying, 0, SpringLayout.WEST, panel);
		panel.add(displaying);
		panel.revalidate();
		panel.repaint();
	}
	
	public void close()
	{
		
	}
	
	public void saveGraphicsAs()
	{
		
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public String getTitle()
	{
		return "Menu";
	}
	
	public Tab getTab()
	{
		return null;
	}
}
