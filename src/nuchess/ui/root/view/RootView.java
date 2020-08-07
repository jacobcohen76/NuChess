package nuchess.ui.root.view;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.ui.Control;
import nuchess.ui.Tab;
import nuchess.ui.TabbedView;
import nuchess.ui.View;

public class RootView implements View
{
	private JPanel panel;
	private TabbedView tabbedView;
	private JMenuBar mainMenuBar;
	private SpringLayout layout;
	
	public RootView()
	{
		initComponents();
		putConstraints();
		addComponents();
	}
	
	private void initComponents()
	{
		panel = new JPanel();
		tabbedView = new TabbedView();
		mainMenuBar = new JMenuBar();
		layout = new SpringLayout();
	}
	
	public void add(JMenuItem menuItem)
	{
		mainMenuBar.add(menuItem);
	}
	
	public void openTab(View view)
	{
		tabbedView.openTab(view);
	}
	
	public void openTab(Control control)
	{
		tabbedView.openTab(control.getView());
	}
	
	private void putConstraints()
	{
		layout.putConstraint(SpringLayout.NORTH, mainMenuBar, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, mainMenuBar, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, mainMenuBar, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, tabbedView.getPanel(), 0, SpringLayout.SOUTH, mainMenuBar);
		layout.putConstraint(SpringLayout.EAST, tabbedView.getPanel(), 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, tabbedView.getPanel(), 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, tabbedView.getPanel(), 0, SpringLayout.WEST, panel);
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(mainMenuBar);
		panel.add(tabbedView.getPanel());
	}
	
	public void close()
	{
		
	}
	
	public void saveGraphicsAs()
	{
		tabbedView.saveGraphicsAs();
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public String getTitle()
	{
		return "Root";
	}
	
	public Tab getTab()
	{
		return null;
	}
}
