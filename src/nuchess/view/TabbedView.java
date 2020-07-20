package nuchess.view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

public class TabbedView
{
	private JPanel panel;
	private JTabbedPane tabbedPane;
	
	public TabbedView()
	{
		panel = new JPanel();
		tabbedPane = new JTabbedPane();
		
		putConstraints();
		addComponents();
	}
	
	private void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, panel);

		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(tabbedPane);
	}
	
	public void addTab(String title, JPanel panel)
	{
		tabbedPane.addTab(title, panel);
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
}
