package nuchess.control;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import nuchess.engine.Bits;
import nuchess.view.bitboardviewer.BitboardBuilderView;

public class BitboardBuilderController
{
	private static String getFormatName(File file)
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
	
	private BitboardBuilderView view;
	private JFileChooser chooser;
	
	public BitboardBuilderController(BitboardBuilderView view)
	{
		this.view = view;
		chooser = new JFileChooser();
		chooser.setDialogTitle("Select a file to save your rendered bitboard to");
	}
	
	public void init()
	{
		view.controller = this;
	}
	
	public void saveRenderedBoardView()
	{
		chooser.setSelectedFile(getDefaultSelection(chooser.getCurrentDirectory(), view.getDisplaying(), "png"));
		if(chooser.showSaveDialog(view.getPanel()) == JFileChooser.APPROVE_OPTION)
		{
			File selected = chooser.getSelectedFile();
			if(selected != null)
			{
				try
				{
					ImageIO.write(view.getRenderedImage(), getFormatName(selected), selected);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private File getDefaultSelection(File directory, long bitboard, String extension)
	{
		return new File(directory.toString() + File.separatorChar  + Bits.toHexString(bitboard) + '.' + extension);
	}
	
	public JPanel getViewPanel()
	{
		return view.getPanel();
	}
}
