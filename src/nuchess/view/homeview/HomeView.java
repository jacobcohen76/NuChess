package nuchess.view.homeview;

import javax.swing.JPanel;

import nuchess.view.View;

public class HomeView implements View
{
	private JPanel panel;
	
	public HomeView()
	{
		panel = new JPanel();
	}
	
	public void close()
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
