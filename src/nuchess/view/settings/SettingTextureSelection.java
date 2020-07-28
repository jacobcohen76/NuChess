package nuchess.view.settings;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import nuchess.view.graphics.ResourceManager;

public class SettingTextureSelection extends Setting
{
	private static final long serialVersionUID = -9113248285849915390L;
	
	private XButton xbutton;
	private JButton chooseFileButton;
	private JLabel label;
	private File textureFile;
	private int id;
	
	public SettingTextureSelection(int id)
	{
		this.id = id;
		textureFile = ResourceManager.getTextureFile(id);
		initComponents();
		putConstraints();
		initListeners();
		addComponents();
	}
	
	@Override
	protected void initComponents()
	{
		xbutton = new XButton();
		chooseFileButton = new JButton();
		label = new JLabel();
		
		xbutton.setIconVisible(false);
		label.setIcon(new ImageIcon(ResourceManager.getUnscaledTexture(id).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		chooseFileButton.setText("Choose File");
		chooseFileButton.setFocusable(false);
		setOpaque(false);
	}
	
	@Override
	protected void putConstraints()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
	}
	
	@Override
	protected void initListeners()
	{
		this.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)	{}
			public void mousePressed(MouseEvent e)	{}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e)	{ entered(e); }
			public void mouseExited(MouseEvent e)	{ exited(e);  }
		});
		chooseFileButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) { chooseFileButtonPressed(e); }
		});
		xbutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) { xbuttonPressed(e); }
		});
	}
	
	@Override
	protected void addComponents()
	{
		add(xbutton);
		add(label);
		add(chooseFileButton);
	}
	
	@Override
	protected void flagAsHovering()
	{
		xbutton.setIconVisible(true);
		repaint();
	}
	
	@Override
	protected void unflagAsHovering()
	{
		xbutton.setIconVisible(false);
		repaint();
	}
	
	private void entered(MouseEvent e)
	{
		parent.setHoveringSetting(this);
	}
	
	private void exited(MouseEvent e)
	{
		if(!contains(e.getPoint()))
		{
			unflagAsHovering();
		}
	}
	
	public void setCloseIconVisible(boolean b)
	{
		xbutton.setIconVisible(b);
	}
	
	private void chooseFileButtonPressed(ActionEvent e)
	{
		File selected = Chooser.showDialog(parent.getPanel(), "Choose Texture", "Select", textureFile);
		if(selected != null)
		{
			textureFile = selected;
			ResourceManager.loadTextureFile(id, textureFile);
			label.setIcon(new ImageIcon(ResourceManager.getUnscaledTexture(id).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		}
	}
	
	private void xbuttonPressed(ActionEvent e)
	{
		textureFile = ResourceManager.getDefaultTextureFile(id);
		ResourceManager.loadTextureFile(id, textureFile);
		label.setIcon(new ImageIcon(ResourceManager.getUnscaledTexture(id).getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
	}
}
