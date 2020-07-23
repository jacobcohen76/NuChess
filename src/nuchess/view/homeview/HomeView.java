package nuchess.view.homeview;

import javax.swing.JPanel;

import nuchess.view.View;

public class HomeView implements View
{
	private JPanel panel;
	
	public HomeView()
	{
		panel = new JPanel();
		initComponents();
		putConstraints();
		addComponents();
	}
	
	private void initComponents()
	{
		
	}
	
	private void putConstraints()
	{
		
	}
	
	private void addComponents()
	{
		
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
		return "Home";
	}
}
