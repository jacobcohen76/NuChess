package nuchess.ui.bitboardeditor.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import nuchess.engine.Bits;
import nuchess.ui.Tab;
import nuchess.ui.View;
import nuchess.ui.bitboardeditor.control.BitboardEditorControl;

public class BitboardEditorView implements View
{
	private static final int BINARY			= 0;
	private static final int OCTAL			= 1;
	private static final int DECIMAL		= 2;
	private static final int HEXADECIMAL	= 3;
	private static final boolean DEFAULT_FLIPPED = false;
	
	private JPanel panel;
	private BitboardBoardView bbbv;
	private Tab tab;
	
	private JTextField bitboardInputField;
	private JLabel inputFieldMessageLabel;
	private JComboBox<String> baseSelector;
	
	public BitboardEditorControl controller;
	
	public BitboardEditorView(boolean flipped)
	{
		initComponents(flipped);
		initListeners();
		putConstraints();
		addComponents();
		
		controller = null;
		
		linkObjects();
	}
	
	public BitboardEditorView()
	{
		this(DEFAULT_FLIPPED);
	}
	
	private void initComponents(boolean flipped)
	{
		panel = new JPanel();
		bbbv = new BitboardBoardView(flipped);
		tab = new Tab(this);
		tab.addCloseButton();
		
		bitboardInputField = new JTextField();
		inputFieldMessageLabel = new JLabel();
		baseSelector = new JComboBox<String>();
		
		panel.setOpaque(false);
		bitboardInputField.setColumns(64);
		baseSelector.insertItemAt("Binary", BINARY);
		baseSelector.insertItemAt("Octal", OCTAL);
		baseSelector.insertItemAt("Decimal", DECIMAL);
		baseSelector.insertItemAt("Hexadecimal", HEXADECIMAL);
		baseSelector.setSelectedIndex(HEXADECIMAL);
	}
	
	private void initListeners()
	{
		bitboardInputField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_ENTER)
				{
					inputBitboard(bitboardInputField.getText(), baseSelector.getSelectedIndex());
				}
			}

			public void keyReleased(KeyEvent event)
			{
				
			}
			
			public void keyTyped(KeyEvent event)	{}
		});
		
		baseSelector.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				setInputBitboard(bbbv.getDisplaying());
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
		
		layout.putConstraint(SpringLayout.NORTH, inputFieldMessageLabel, 0, SpringLayout.SOUTH, bitboardInputField);
		layout.putConstraint(SpringLayout.EAST, inputFieldMessageLabel, 0, SpringLayout.EAST, bitboardInputField);

		layout.putConstraint(SpringLayout.NORTH, baseSelector, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, baseSelector, 0, SpringLayout.EAST, bitboardInputField);
		
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(bbbv.getPanel());
		panel.add(bitboardInputField);
		panel.add(inputFieldMessageLabel);
		panel.add(baseSelector);
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
			bbbv.display(getBitboard(bitboardString, selectedBase));
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
	
	public long getBitboard(String bitboardString, int base)
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
	
	public String getBitboardString(long bitboard, int base)
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
	
	public String getFileName(long bitboard)
	{
		switch(baseSelector.getSelectedIndex())
		{
			case BINARY:		return "0b" + Bits.toBinaryString(bitboard);
			case OCTAL:			return "0o" + Long.toUnsignedString(bitboard, 8);
			case DECIMAL:		return Long.toUnsignedString(bitboard, 10);
			case HEXADECIMAL:	return "0x" + Bits.toHexString(bitboard);
			default:			throw new Error("INVALID BASE");
		}
	}
	
	private void displayInputFieldMessage(String messageText, Color messageTextColor)
	{
		inputFieldMessageLabel.setForeground(messageTextColor);
		inputFieldMessageLabel.setText(messageText);
	}
	
	public BufferedImage getRenderedImage()
	{
		return bbbv.getRenderedImage();
	}
	
	public long getDisplaying()
	{
		return bbbv.getDisplaying();
	}
	
	public void saveGraphicsAs()
	{
		controller.saveGraphicsAs();
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public void close()
	{
		controller.close();
	}
	
	public String getTitle()
	{
		return "Bitboard Viewer";
	}
	
	public Tab getTab()
	{
		return tab;
	}
}
