package nuchess.view.home;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class GameConstructorPanel extends JPanel
{
	private static final long serialVersionUID = 2761425672726153759L;
	
	private JComboBox<String> gameModeSelector;
	
	public GameConstructorPanel()
	{
		initComponents();
		putConstraints();
		addComponents();
	}
	
	private void initComponents()
	{
		gameModeSelector = new JComboBox<String>();
	}
	
	private void putConstraints()
	{
		
	}
	
	private void addComponents()
	{
		add(gameModeSelector);
	}
	
	public void addGameMode(String name)
	{
		gameModeSelector.addItem(name);
	}
}
