package nuchess.view.settings;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

public class Chooser
{
	private static final JFileChooser FILE_CHOOSER;
	private static final File DEFAULT_DIRECTORY;
	private static final boolean COLOR_TRANSPARY_SELECTION_ENABLED;
	
	static
	{
		FILE_CHOOSER = new JFileChooser();
		DEFAULT_DIRECTORY = new File(System.getProperty("user.home"));
		COLOR_TRANSPARY_SELECTION_ENABLED = true;
	}
	
	public static Color showDialog(Component component, String title, Color initialColor)
	{
		return JColorChooser.showDialog(component, title, initialColor, COLOR_TRANSPARY_SELECTION_ENABLED);
	}
	
	public static File showDialog(Component parent, String dialogTitle, String approveButtonText, File initialFile)
	{
		FILE_CHOOSER.setDialogTitle(dialogTitle);
		FILE_CHOOSER.setCurrentDirectory(initialFile.getParentFile() != null ? initialFile.getParentFile() : DEFAULT_DIRECTORY);
		FILE_CHOOSER.setSelectedFile(initialFile);
		return FILE_CHOOSER.showDialog(parent, approveButtonText) == JFileChooser.APPROVE_OPTION ? FILE_CHOOSER.getSelectedFile() : null;
	}
}
