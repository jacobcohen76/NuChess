package nuchess.view.chessgame;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.Spring;
import javax.swing.SpringLayout;

class MoveHistoryPanel
{
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private JPanel panel, cellSpring;
	private JScrollPane scrollPane;
	private ArrayList<JLabel> labels;
	private ArrayList<JToggleButton> buttons;
	private SpringLayout layout;
	private Color selectedColor, hoverColor;
	private int selected;
	private Font font;
	
	protected ActionPanel parent;
	
	public MoveHistoryPanel(int cellWidth, int cellHeight, Color selectedColor, Color hoverColor, Font font)
	{
		panel = new JPanel();
		cellSpring = new JPanel();
		scrollPane = new JScrollPane(panel);
		labels = new ArrayList<JLabel>();
		buttons = new ArrayList<JToggleButton>();
		layout = new SpringLayout()
		{
			public Dimension preferredLayoutSize(Container parent)
			{
		        return new Dimension(cellWidth * 5, labels.size() * cellHeight);
		    }
		};
		selected = 0;
		
		parent = null;
		
		this.selectedColor = selectedColor;
		this.hoverColor = hoverColor;
		this.font = font;
		
		Dimension d = new Dimension(cellWidth, cellHeight);
		cellSpring.setPreferredSize(d);
		cellSpring.setSize(d);
		
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);
		scrollPane.setBorder(null);
		panel.setLayout(layout);
		
		JToggleButton initialPositionButton = getNewJToggleButton("", 0);
		buttons.add(initialPositionButton);
	}
	
	public void setFont(Font font)
	{
		this.font = font;
		for(JToggleButton button : buttons)
		{
			button.setFont(font);
		}
		for(JLabel label : labels)
		{
			label.setFont(font);
		}
	}
	
	public JScrollPane getScrollPane()
	{
		return scrollPane;
	}
	
	public void jumpTo(int fullMoveNumber)
	{
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(labels.get(fullMoveNumber).getY());
	}
	
	public int getMaxPly()
	{
		return buttons.size() - 1;
	}
	
	public int getCurrentSelection()
	{
		return selected;
	}
	
	public void addMove(String formattedMove)
	{
		if((buttons.size() & 1) == 1)
		{
			JLabel newLabel = getNewJLabel("" + ((buttons.size() + 1) >> 1));
			
			if(labels.isEmpty())
			{
				layout.putConstraint(SpringLayout.NORTH, newLabel, 0, SpringLayout.NORTH, panel);
				layout.putConstraint(SpringLayout.EAST, newLabel, Spring.width(cellSpring), SpringLayout.WEST, newLabel);
				layout.putConstraint(SpringLayout.SOUTH, newLabel, Spring.height(cellSpring), SpringLayout.NORTH, newLabel);
			}
			else
			{
				layout.putConstraint(SpringLayout.NORTH, newLabel, 0, SpringLayout.SOUTH, labels.get(labels.size() - 1));
				layout.putConstraint(SpringLayout.EAST, newLabel, 0, SpringLayout.EAST, labels.get(labels.size() - 1));
				layout.putConstraint(SpringLayout.SOUTH, newLabel, Spring.height(cellSpring), SpringLayout.NORTH, newLabel);
			}
			layout.putConstraint(SpringLayout.WEST, newLabel, 0, SpringLayout.WEST, panel);
			
			labels.add(newLabel);
			panel.add(newLabel);
		}
		
		JToggleButton toggleButton = getNewJToggleButton(formattedMove, buttons.size());
		
		if((buttons.size() & 1) == 1)
		{
			layout.putConstraint(SpringLayout.WEST, toggleButton, 0, SpringLayout.EAST, labels.get(labels.size() - 1));
			layout.putConstraint(SpringLayout.EAST, toggleButton, Spring.sum(Spring.width(cellSpring), Spring.width(cellSpring)), SpringLayout.WEST, toggleButton);
		}
		else
		{
			layout.putConstraint(SpringLayout.WEST, toggleButton, 0, SpringLayout.EAST, buttons.get(buttons.size() - 1));
			layout.putConstraint(SpringLayout.EAST, toggleButton, 0, SpringLayout.EAST, panel);
		}
		layout.putConstraint(SpringLayout.NORTH, toggleButton, 0, SpringLayout.NORTH, labels.get(labels.size() - 1));
		layout.putConstraint(SpringLayout.SOUTH, toggleButton, 0, SpringLayout.SOUTH, labels.get(labels.size() - 1));

		
		buttons.add(toggleButton);
		panel.add(toggleButton);
		
		if(labels.size() > 1)
		{
			panel.setPreferredSize(new Dimension(panel.getWidth(), labels.get(0).getHeight() * labels.size()));
		}
		
		panel.revalidate();
	}
	
	public void selectMove(int ply)
	{
		if(selected != ply)
		{
			deselect(selected);
			select(ply);
			selected = ply;
		}
	}
	
	private void requestStateLoad(int ply)
	{
		parent.requestStateLoad(ply);
	}
	
	private void select(int ply)
	{
		JToggleButton toggleButton = buttons.get(ply);
		toggleButton.setContentAreaFilled(true);
		toggleButton.setBackground(selectedColor);
		panel.repaint();
	}
	
	private void deselect(int ply)
	{
		JToggleButton toggleButton = buttons.get(ply);
		toggleButton.setContentAreaFilled(false);
		toggleButton.setBackground(hoverColor);
		panel.repaint();
	}
	
	private void addHoverEffect(int ply)
	{
		buttons.get(ply).setContentAreaFilled(true);
		panel.repaint();
	}
	
	private void removeHoverEffect(int ply)
	{
		if(selected != ply)
		{
			buttons.get(ply).setContentAreaFilled(false);
			panel.repaint();
		}
	}
	
	private JToggleButton getNewJToggleButton(String formattedMove, int ply)
	{
		JToggleButton toggleButton = new JToggleButton();
		toggleButton.setText(formattedMove);
		toggleButton.setHorizontalTextPosition(JToggleButton.LEFT);
		toggleButton.setHorizontalAlignment(JToggleButton.LEFT);
		toggleButton.setFocusable(false);
		toggleButton.setBorderPainted(false);
		toggleButton.setContentAreaFilled(false);
		toggleButton.setBackground(hoverColor);
		toggleButton.setFont(font);
		for(MouseListener ml : toggleButton.getMouseListeners())
		{
			toggleButton.removeMouseListener(ml);
		}
		toggleButton.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent event)
			{
				
			}

			@Override
			public void mouseEntered(MouseEvent event)
			{
				panel.setCursor(HAND_CURSOR);
				addHoverEffect(ply);
			}

			@Override
			public void mouseExited(MouseEvent event)
			{
				panel.setCursor(DEFAULT_CURSOR);
				removeHoverEffect(ply);
			}
			
			@Override
			public void mousePressed(MouseEvent event)
			{
				if(selected != ply)
				{
					selectMove(ply);
					requestStateLoad(ply);
				}
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				
			}
		});
		return toggleButton;
	}
	
	private JLabel getNewJLabel(String text)
	{
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(font);
		return label;
	}
}
