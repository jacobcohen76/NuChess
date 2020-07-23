package nuchess.view.tabs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.view.View;

public class TabbedView implements View
{
	private JPanel rootPanel, tabsPanel;
	private SpringLayout layout;
	private ArrayList<TabButton> tabButtons;
	
	protected TabButton displaying;
	
	public TabbedView()
	{
		rootPanel = new JPanel();
		tabsPanel = new JPanel();
		layout = new SpringLayout();
		tabButtons = new ArrayList<TabButton>();
		
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
		tabsPanel.setBackground(Color.LIGHT_GRAY);
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
	
	private TabButton getNewHomeTabButton(View view)
	{
		TabButton button = new TabButton(view, this);
		return button;
	}
	
	private TabButton getNewTabButton(View view)
	{
		TabButton button = new TabButton(view, this);
		button.addCloseButton();
		return button;
	}
	
	public void addHomeTab(View view)
	{
		TabButton tabButton = getNewHomeTabButton(view);
		tabButtons.add(tabButton);
		tabsPanel.add(tabButton);
		tabsPanel.revalidate();
	}
	
	public void addNewTab(View view)
	{
		TabButton tabButton = getNewTabButton(view);
		tabButtons.add(tabButton);
		tabsPanel.add(tabButton);
		tabsPanel.revalidate();
	}
	
	public void removeTab(TabButton tabButton)
	{
		int index = tabButtons.indexOf(tabButton);
		tabsPanel.remove(tabButton);
		requestDisplay(index + (index == tabButtons.size() - 1 ? -1 : +1));
		tabButtons.remove(index);
		tabButton.view.close();
	}
	
	public void requestClose(TabButton tabButton)
	{
		removeTab(tabButton);
	}
	
	public void requestDisplay(int tabIndex)
	{
		requestDisplay(tabButtons.get(tabIndex));
	}
	
	public int getNumTabs()
	{
		return tabButtons.size();
	}
	
	protected void requestDisplay(TabButton tab)
	{
		TabButton prev = displaying;
		displaying = tab;
		if(prev != null)
		{
			removeContent(prev.view);
			prev.repaint();
		}
		tab.repaint();
		displayContent(tab.view);
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
