package nuchess.ui.bitboardeditor.control;

import java.io.File;

import nuchess.ui.Control;
import nuchess.ui.FileSaving;
import nuchess.ui.View;
import nuchess.ui.bitboardeditor.view.BitboardEditorView;

public class BitboardEditorControl implements Control
{
	private BitboardEditorView view;
	
	public BitboardEditorControl(BitboardEditorView view)
	{
		this.view = view;
	}
	
	public void init()
	{
		view.controller = this;
		view.setInputBitboard(0L);
	}
	
	public void saveGraphicsAs()
	{
		File out = FileSaving.chooseImageFile(view.getPanel(), view.getFileName(view.getDisplaying()));
		FileSaving.saveRenderedImage(view.getRenderedImage(), out);
	}
	
	public void saveAs()
	{
		
	}
	
	public void close()
	{
		
	}
	
	public View getView()
	{
		return view;
	}
}
