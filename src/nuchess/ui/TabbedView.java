package nuchess.ui;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.graphics.ColorIDs;
import nuchess.graphics.ResourceManager;

public class TabbedView implements View
{
	private JPanel rootPanel, tabsPanel;
	private SpringLayout layout;
	private ArrayList<View> views;
	
	protected Tab displaying;
	
	public TabbedView()
	{
		rootPanel = new JPanel();
		tabsPanel = new JPanel();
		layout = new SpringLayout();
		views = new ArrayList<View>();
				
		displaying = null;
		
		initComponents();
		putConstraints();
		addComponents();
	}
	
	public void close()
	{
		
	}
	
	public void saveGraphicsAs()
	{
		getDisplayingView().saveGraphicsAs();
	}
	
	public JPanel getPanel()
	{
		return rootPanel;
	}
	
	public String getTitle()
	{
		return "Tabbed View";
	}
	
	public Tab getTab()
	{
		return null;
	}
	
	public View getDisplayingView()
	{
		return displaying.view;
	}
	
	private void initComponents()
	{
		FlowLayout tabsLayout = new FlowLayout();
		tabsLayout.setAlignment(FlowLayout.LEFT);
		tabsLayout.setHgap(0);
		tabsLayout.setVgap(0);
		tabsPanel.setLayout(tabsLayout);
		tabsPanel.setBackground(ResourceManager.getColor(ColorIDs.TAB));
		rootPanel.setBackground(ResourceManager.getColor(ColorIDs.ROOT_BACKGROUND));
	}
	
	private void putConstraints()
	{
		layout.putConstraint(SpringLayout.NORTH, tabsPanel, 0, SpringLayout.NORTH, rootPanel);
		layout.putConstraint(SpringLayout.EAST, tabsPanel, 0, SpringLayout.EAST, rootPanel);
		layout.putConstraint(SpringLayout.WEST, tabsPanel, 0, SpringLayout.WEST, rootPanel);
		rootPanel.setLayout(layout);
	}
	
	private void addComponents()
	{
		rootPanel.add(tabsPanel);
	}
	
	public void openTab(View view)
	{
		view.getTab().parent = this;
		views.add(view);
		tabsPanel.add(view.getTab());
		requestDisplay(views.size() - 1);
		tabsPanel.revalidate();
	}
	
	public void closeTab(View view)
	{
		view.getTab().parent = null;
		
		int index = views.indexOf(view);
		tabsPanel.remove(view.getTab());
		requestDisplay(index + (index == views.size() - 1 ? -1 : +1));
		views.remove(index);
		view.close();
	}
	
	public void requestDisplay(int index)
	{
		requestDisplay(views.get(index));
	}
	
	public int getNumTabs()
	{
		return views.size();
	}
	
	protected void requestDisplay(View view)
	{
		Tab prev = displaying;
		displaying = view.getTab();
		if(prev != null)
		{
			removeContent(prev.view);
			prev.setBackground(ColorIDs.TAB);
			prev.repaint();
		}
		displaying.setBackground(ColorIDs.SELECTED_TAB);
		displaying.repaint();
		displayContent(view);
	}
	
	private void displayContent(View view)
	{
		layout.putConstraint(SpringLayout.NORTH, view.getPanel(), 0, SpringLayout.SOUTH, tabsPanel);
		layout.putConstraint(SpringLayout.EAST, view.getPanel(), 0, SpringLayout.EAST, rootPanel);
		layout.putConstraint(SpringLayout.WEST, view.getPanel(), 0, SpringLayout.WEST, rootPanel);
		layout.putConstraint(SpringLayout.SOUTH, view.getPanel(), 0, SpringLayout.SOUTH, rootPanel);
		rootPanel.add(view.getPanel());
		rootPanel.revalidate();
		rootPanel.repaint();
	}
	
	private void removeContent(View view)
	{
		layout.removeLayoutComponent(view.getPanel());
		rootPanel.remove(view.getPanel());
	}
}
