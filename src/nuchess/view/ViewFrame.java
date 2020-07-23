package nuchess.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.control.Controller;

public class ViewFrame
{
	private JFrame frame;
	private JPanel root, content;
	private SpringLayout layout;
	
	private ComponentListener componentListener;
	private FocusListener focusListener;
	private WindowListener windowListener;
	
	public ViewFrame()
	{
		frame = new JFrame();
		root = new JPanel();
		content = null;
		
		componentListener = null;
		focusListener = null;
		windowListener = null;
		
		layout = new SpringLayout();
		layout.putConstraint(SpringLayout.EAST, root, 0, SpringLayout.EAST, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, root, 0, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, root, 0, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, root, 0, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().setLayout(layout);
		frame.getContentPane().add(root);
		
		layout = new SpringLayout();
		root.setLayout(layout);
	}
	
	public void setSize(Dimension d)
	{
		frame.setSize(d);
	}
	
	public void setSize(int w, int h)
	{
		frame.setSize(w, h);
	}
	
	public void setVisible(boolean b)
	{
		frame.setVisible(true);
	}
	
	public void setEnabled(boolean b)
	{
		frame.setEnabled(b);
	}
	
	public void setTitle(String title)
	{
		frame.setTitle(title);
	}
	
	public void setLocation(java.awt.Point p)
	{
		frame.setLocation(p);
	}
	
	public void setLocationRelativeTo(Component c)
	{
		frame.setLocationRelativeTo(c);
	}
	
	public void setCursor(Cursor cursor)
	{
		frame.setCursor(cursor);
	}
	
	public void repaint()
	{
		frame.repaint();
		root.repaint();
	}
	
	public void display(Controller controller)
	{
		display(controller.getView());
	}
	
	public void display(View view)
	{
		display(view.getPanel());
	}
	
	public void display(JPanel panel)
	{
		if(content != null)
		{
			root.remove(content);
			layout.removeLayoutComponent(content);
		}
		content = panel;
		layout.putConstraint(SpringLayout.EAST, content, 0, SpringLayout.EAST, root);
		layout.putConstraint(SpringLayout.WEST, content, 0, SpringLayout.WEST, root);
		layout.putConstraint(SpringLayout.NORTH, content, 0, SpringLayout.NORTH, root);
		layout.putConstraint(SpringLayout.SOUTH, content, 0, SpringLayout.SOUTH, root);
		root.add(content);
	}
	
	public void setComponentListener(ComponentListener cl)
	{
		frame.removeComponentListener(componentListener);
		frame.addComponentListener(cl);
		componentListener = cl;
	}
	
	public void setWindowListener(WindowListener wl)
	{
		frame.removeWindowListener(windowListener);
		frame.addWindowListener(wl);
		windowListener = wl;
	}
	
	public void setFocusListener(FocusListener fl)
	{
		frame.removeFocusListener(focusListener);
		frame.addFocusListener(fl);
		focusListener = fl;
	}
}
