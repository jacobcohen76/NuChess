package nuchess.control;

import nuchess.view.View;

public interface Controller
{
	public void init();
	public void saveGraphicsAs();
	public void saveAs();
	public void close();
	public View getView();
}
