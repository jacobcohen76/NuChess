package nuchess.view.settings;

import javax.swing.JPanel;

public abstract class Setting extends JPanel
{
	private static final long serialVersionUID = 781225452106299513L;
	
	protected abstract void initComponents();
	protected abstract void putConstraints();
	protected abstract void initListeners();
	protected abstract void addComponents();
}