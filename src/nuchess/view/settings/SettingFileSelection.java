package nuchess.view.settings;

import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;

public class SettingFileSelection extends Setting
{
	private static final long serialVersionUID = 4527431295342722142L;
	
	private JButton chooseButton;
	private File defaultSelection;
	
	public SettingFileSelection(File defaultSelection)
	{
		this.defaultSelection = defaultSelection;
		
		initComponents();
		putConstraints();
		initListeners();
		addComponents();
	}
	
	public SettingFileSelection()
	{
		this(null);
	}
	
	protected void initComponents()
	{
		chooseButton = new JButton();
		chooseButton.setText("Choose File");
	}
	
	protected void putConstraints()
	{
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		setLayout(layout);
	}
	
	protected void initListeners()
	{
		
	}
	
	protected void addComponents()
	{
		add(chooseButton);
	}
}
