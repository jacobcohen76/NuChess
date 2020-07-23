package nuchess.view.homeview;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.view.View;

public class MenuView implements View
{
	private JPanel panel, displaying;
	private JMenuBar mainMenuBar;
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
		mainMenuBar = new JMenuBar();
		layout = new SpringLayout();
		displaying = null;
	}
	
	public void add(JMenuItem menuItem)
	{
		mainMenuBar.add(menuItem);
	}
	
	private void putConstraints()
	{
		layout.putConstraint(SpringLayout.NORTH, mainMenuBar, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, mainMenuBar, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, mainMenuBar, 0, SpringLayout.WEST, panel);
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(mainMenuBar);
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
		layout.putConstraint(SpringLayout.NORTH, displaying, 0, SpringLayout.SOUTH, mainMenuBar);
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
}
