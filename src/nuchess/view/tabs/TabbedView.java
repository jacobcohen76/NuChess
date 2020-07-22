package nuchess.view.tabs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.view.View;

public class TabbedView
{
	private JPanel rootPanel, tabsPanel;
	private SpringLayout layout;
	private ArrayList<View> tabbedViews;
	
	protected TabButton displaying;
	
	public TabbedView()
	{
		rootPanel = new JPanel();
		tabsPanel = new JPanel();
		layout = new SpringLayout();
		tabbedViews = new ArrayList<View>();
		
		displaying = null;
		
		initComponents();
		putConstraints();
		addComponents();
	}
	
	private void initComponents()
	{
		FlowLayout tabsLayout = new FlowLayout();
		tabsLayout.setAlignment(FlowLayout.LEFT);
		tabsLayout.setHgap(0);
		tabsLayout.setVgap(0);
		tabsPanel.setLayout(tabsLayout);
		tabsPanel.setBackground(Color.GREEN);
		rootPanel.setBackground(Color.BLUE);
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
	
	private TabButton getNewTabButton(View view)
	{
		TabButton button = new TabButton(view, this);
		return button;
	}
	
	public void addTab(View view)
	{
		tabbedViews.add(view);
		tabsPanel.add(getNewTabButton(view));
	}
	
	public void removeTab(TabButton tabButton)
	{
//		removeTab(tabbedPane.indexOfTabComponent(tabButton));
	}
	
	public void removeTab(int index)
	{
		
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
	
	public JPanel getPanel()
	{
		return rootPanel;
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
