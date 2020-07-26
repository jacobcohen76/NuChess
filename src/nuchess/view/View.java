package nuchess.view;

import javax.swing.JPanel;

public interface View
{
	public void close();
	public void saveGraphicsAs();
	public JPanel getPanel();
	public String getTitle();
}