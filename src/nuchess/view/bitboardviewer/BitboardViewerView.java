package nuchess.view.bitboardviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import nuchess.control.BitboardViewerController;

public class BitboardViewerView
{
	private JPanel panel;
	private BitboardBoardView bbbv;
	
	private JTextField bitboardInputField;
	private JComboBox<String> baseSelector;
	private JButton testingButton;
	
	public BitboardViewerController controller;
	
	public BitboardViewerView(int squareSize, boolean flipped)
	{
		initComponents(squareSize, flipped);
		putConstraints();
		addComponents();
		
		controller = null;
		
		display(0xFF00L);
	}
	
	private void initComponents(int squareSize, boolean flipped)
	{
		panel = new JPanel();
		bbbv = new BitboardBoardView(squareSize, flipped);
		
		bitboardInputField = new JTextField();
		baseSelector = new JComboBox<String>();
		testingButton = new JButton();
		
		bitboardInputField.setColumns(64);
		
		baseSelector.addItem("Binary");
		baseSelector.addItem("Octal");
		baseSelector.addItem("Decimal");
		baseSelector.addItem("Hexadecimal");
		
		testingButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				controller.saveRenderedBoardView();
			}
		});
	}
	
	private void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, bbbv.getPanel(), 0, SpringLayout.HORIZONTAL_CENTER, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, bbbv.getPanel(), 0, SpringLayout.VERTICAL_CENTER, panel);
		
		layout.putConstraint(SpringLayout.NORTH, bitboardInputField, 0, SpringLayout.NORTH, baseSelector);
		layout.putConstraint(SpringLayout.SOUTH, bitboardInputField, 0, SpringLayout.SOUTH, baseSelector);
		layout.putConstraint(SpringLayout.WEST, bitboardInputField, 0, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, baseSelector, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, baseSelector, 0, SpringLayout.EAST, bitboardInputField);
		
		layout.putConstraint(SpringLayout.NORTH, testingButton, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.EAST, testingButton, 0, SpringLayout.EAST, panel);
		
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(bbbv.getPanel());
		panel.add(bitboardInputField);
		panel.add(baseSelector);
		panel.add(testingButton);
	}
	
	public void display(long bitboard)
	{
		bbbv.display(bitboard);
	}
	
	public BufferedImage getRenderedImage()
	{
		return bbbv.getRenderedImage();
	}
	
	public long getDisplaying()
	{
		return bbbv.getDisplaying();
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
}
