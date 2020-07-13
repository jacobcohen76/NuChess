package nuchess.control;

import javax.swing.JPanel;

import nuchess.view.bitboardviewer.BitboardViewerView;

public class BitboardViewerController
{
	private BitboardViewerView view;
	
	public BitboardViewerController(BitboardViewerView view)
	{
		this.view = view;
	}
	
	public void init()
	{
		
	}
	
	public JPanel getViewPanel()
	{
		return view.getPanel();
	}
}
