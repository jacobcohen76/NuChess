package nuchess.control.copy;

import java.io.File;

import nuchess.view.View;
import nuchess.view.bitboardeditor.BitboardEditorView;

public class BitboardEditorController implements Controller
{
	private BitboardEditorView view;
	
	public BitboardEditorController(BitboardEditorView view)
	{
		this.view = view;
	}
	
	public void init()
	{
		view.controller = this;
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
