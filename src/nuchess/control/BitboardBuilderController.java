package nuchess.control;

import java.io.File;
import javax.swing.JPanel;

import nuchess.view.bitboardviewer.BitboardBuilderView;

public class BitboardBuilderController
{
	private BitboardBuilderView view;
	
	public BitboardBuilderController(BitboardBuilderView view)
	{
		this.view = view;
	}
	
	public void init()
	{
		view.controller = this;
	}
	
	public void saveRenderedBoardView()
	{
		File out = FileSaving.chooseImageFile(view.getPanel(), view.getFileName(view.getDisplaying()));
		FileSaving.saveRenderedImage(view.getRenderedImage(), out);
	}
	
	public JPanel getViewPanel()
	{
		return view.getPanel();
	}
}
