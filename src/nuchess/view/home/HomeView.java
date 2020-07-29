package nuchess.view.home;

import javax.swing.JPanel;

import nuchess.view.Tab;
import nuchess.view.View;
import nuchess.view.graphics.IconIDs;
import nuchess.view.graphics.ResourceManager;

public class HomeView implements View
{
	private JPanel panel;
	private Tab tab;
	
	public HomeView()
	{
		initComponents();
		putConstraints();
		addComponents();
		tab = new Tab(this, ResourceManager.getIcon(IconIDs.HOME));
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
	
	public Tab getTab()
	{
		return tab;
	}
}
