package nuchess.ui.root.view;

import javax.swing.JPanel;

import nuchess.graphics.IconIDs;
import nuchess.graphics.ResourceManager;
import nuchess.ui.Tab;
import nuchess.ui.View;

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
