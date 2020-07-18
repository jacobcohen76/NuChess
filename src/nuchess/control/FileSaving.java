package nuchess.control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileSaving
{
	private static JFileChooser chooser;
	private static String previousImageFileExtension;
	
	static
	{
		chooser = new JFileChooser();
		previousImageFileExtension = "png";
	}
	
	public static String getFormatName(File file)
	{
		String formatName = file.toString();
		if(formatName.contains("."))
		{
			switch(formatName.substring(formatName.lastIndexOf('.') + 1).toLowerCase())
			{
				case "png":		return "png";
				case "jpg":		return "jpg";
				case "jpeg":	return "jpeg";
			}
		}
		return "png";
	}
		
	public static File chooseImageFile(JPanel panel, String fileName, String dialogTitle)
	{
		chooser.setDialogTitle(dialogTitle);
		chooser.setSelectedFile(getDefaultSelection(chooser.getCurrentDirectory(), fileName, previousImageFileExtension));
		if(chooser.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION)
		{
			previousImageFileExtension = getFormatName(chooser.getSelectedFile());
			return chooser.getSelectedFile();
		}
		else
		{
			return null;
		}
	}
	
	public static File chooseImageFile(JPanel panel, String fileName)
	{
		return chooseImageFile(panel, fileName, "Select a file to save your rendered bitboard to");
	}
	
	public static void saveRenderedImage(BufferedImage im, File out)
	{
		if(out != null)
		{
			try
			{
				ImageIO.write(im, getFormatName(out), out);
			}
			catch (IOException ioex)
			{
				ioex.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public static File getDefaultSelection(File parent, String fileName, String extension)
	{
		return new File(parent.toString() + File.separatorChar + fileName + '.' + extension);
	}
}
