package nuchess.view.bitboardviewer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

//import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import nuchess.control.BitboardBuilderController;
import nuchess.engine.Bits;

public class BitboardBuilderView
{
	private static final int BINARY			= 0;
	private static final int OCTAL			= 1;
	private static final int DECIMAL		= 2;
	private static final int HEXADECIMAL	= 3;
	
	private JPanel panel;
	private BitboardBoardView bbbv;
	
	private JTextField bitboardInputField;
	private JLabel inputFieldMessageLabel;
	private JComboBox<String> baseSelector;
//	private JButton testingButton;
	
	public BitboardBuilderController controller;
	
	public BitboardBuilderView(int squareSize, boolean flipped)
	{
		initComponents(squareSize, flipped);
		initListeners();
		putConstraints();
		addComponents();
		
		controller = null;
		
		linkObjects();
	}
	
	private void initComponents(int squareSize, boolean flipped)
	{
		panel = new JPanel();
		bbbv = new BitboardBoardView(squareSize, flipped);
		
		bitboardInputField = new JTextField();
		inputFieldMessageLabel = new JLabel();
		baseSelector = new JComboBox<String>();
//		testingButton = new JButton();
		
		bitboardInputField.setColumns(64);
		
		baseSelector.insertItemAt("Binary", BINARY);
		baseSelector.insertItemAt("Octal", OCTAL);
		baseSelector.insertItemAt("Decimal", DECIMAL);
		baseSelector.insertItemAt("Hexadecimal", HEXADECIMAL);
		baseSelector.setSelectedIndex(HEXADECIMAL);
	}
	
	private void initListeners()
	{
//		testingButton.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent event)
//			{
//				controller.saveRenderedBoardView();
//			}
//		});
		
		bitboardInputField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_ENTER)
				{
					inputBitboard(bitboardInputField.getText(), baseSelector.getSelectedIndex());
				}
				else if(event.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					bbbv.shiftheld = true;
				}
			}

			public void keyReleased(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					bbbv.shiftheld = false;
				}
			}
			
			public void keyTyped(KeyEvent event)	{}
		});
		
		baseSelector.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.out.println(event);
			}
		});
		baseSelector.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					bbbv.shiftheld = true;
				}
			}
			
			public void keyReleased(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					bbbv.shiftheld = false;
				}
			}
			
			public void keyTyped(KeyEvent event)	{}
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
		
		layout.putConstraint(SpringLayout.NORTH, inputFieldMessageLabel, 0, SpringLayout.SOUTH, bitboardInputField);
		layout.putConstraint(SpringLayout.EAST, inputFieldMessageLabel, 0, SpringLayout.EAST, bitboardInputField);

		layout.putConstraint(SpringLayout.NORTH, baseSelector, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, baseSelector, 0, SpringLayout.EAST, bitboardInputField);
		
//		layout.putConstraint(SpringLayout.NORTH, testingButton, 0, SpringLayout.NORTH, panel);
//		layout.putConstraint(SpringLayout.EAST, testingButton, 0, SpringLayout.EAST, panel);
		
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(bbbv.getPanel());
		panel.add(bitboardInputField);
		panel.add(inputFieldMessageLabel);
		panel.add(baseSelector);
//		panel.add(testingButton);
	}
	
	private void linkObjects()
	{
		bbbv.parent = this;
	}
	
	public void setInputBitboard(long bitboard)
	{
		bitboardInputField.setText(getBitboardString(bitboard, baseSelector.getSelectedIndex()));
	}
	
	public void inputBitboard(String bitboardString, int selectedBase)
	{
		try
		{
			display(getBitboard(bitboardString, selectedBase));
			bbbv.repaint();
		}
		catch (NumberFormatException nfexception)
		{
			displayInputFieldMessage(nfexception.getLocalizedMessage(), Color.RED);
		}
		catch (Exception exception)
		{
			displayInputFieldMessage(exception.getLocalizedMessage(), Color.RED);
		}
		catch (Error error)
		{
			displayInputFieldMessage(error.getLocalizedMessage(), Color.RED);
		}
	}
	
	private long getBitboard(String bitboardString, int base)
	{
		switch(base)
		{
			case BINARY:		return Long.parseUnsignedLong(bitboardString, 2);
			case OCTAL:			return Long.parseUnsignedLong(bitboardString, 8);
			case DECIMAL:		return Long.parseUnsignedLong(bitboardString, 10);
			case HEXADECIMAL:	return Long.parseUnsignedLong(bitboardString, 16);
			default:			throw new Error("INVALID BASE");
		}
	}
	
	private String getBitboardString(long bitboard, int base)
	{
		switch(base)
		{
			case BINARY:		return Bits.toBinaryString(bitboard);
			case OCTAL:			return Long.toUnsignedString(bitboard, 8);
			case DECIMAL:		return Long.toUnsignedString(bitboard, 10);
			case HEXADECIMAL:	return Bits.toHexString(bitboard);
			default:			throw new Error("INVALID BASE");
		}
	}
	
	private void displayInputFieldMessage(String messageText, Color messageTextColor)
	{
		inputFieldMessageLabel.setForeground(messageTextColor);
		inputFieldMessageLabel.setText(messageText);
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
