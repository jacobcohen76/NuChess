package nuchess.view.home;

import javax.swing.JPanel;

import nuchess.view.View;

public class HomeView implements View
{
	private JPanel panel;
	
	public HomeView()
	{
		initComponents();
		putConstraints();
		addComponents();
	}
	
	private void initComponents()
	{
		panel = new JPanel();
		panel.setOpaque(false);
	}
	
	private void putConstraints()
	{
		
	}
	
	private void addComponents()
	{
		
	}
	
	@Override
	public void close()
	{
		
	}
	
	@Override
	public void saveGraphicsAs()
	{
		
	}

	@Override
	public JPanel getPanel()
	{
		return panel;
	}
	
	@Override
	public String getTitle()
	{
		return "Home";
	}
}
