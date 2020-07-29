package nuchess.view.settings;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import nuchess.view.TabbedView;

public class SettingsDialog extends JDialog
{
	private static final long serialVersionUID = 7544739407332158881L;
	
	private TabbedView tabbedSettingsView;
	
	public SettingsDialog(JFrame owner, SettingsView... settingsViewTabs)
	{
		super(owner);
		tabbedSettingsView = new TabbedView();
		for(SettingsView settingsView : settingsViewTabs)
		{
			tabbedSettingsView.openTab(settingsView);
		}
		if(settingsViewTabs.length > 0)
		{
			tabbedSettingsView.requestDisplay(0);
		}
		setSize(new Dimension(1000, 1000));
		add(tabbedSettingsView.getPanel());
	}
}
