package nuchess.view;

import javax.swing.JPanel;

public interface View
{
	public void close();
	public JPanel getPanel();
	public String getTitle();
}
