package nuchess.ui.game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Spring;
import javax.swing.SpringLayout;

class ActionPanel
{
	private JPanel panel, cellSpring;
	private MoveHistoryPanel mhp;
	private JLabel topLabel, bottomLabel;
	private JToggleButton flipToggleButton, drawButton, requestTakebackButton, surrenderButton;
	private JButton tailButton, prevButton, nextButton, headButton;
	
	protected GameView parent;
	
	public ActionPanel(String whitePlayerLabel, String blackPlayerLabel, int cellWidth, int cellHeight, Color selectedColor, Color hoverColor, Font font)
	{
		panel = new JPanel();
		cellSpring = new JPanel();
		mhp = new MoveHistoryPanel(cellWidth, cellHeight, selectedColor, hoverColor, font);
		topLabel = new JLabel(blackPlayerLabel);
		bottomLabel = new JLabel(whitePlayerLabel);
		flipToggleButton = new JToggleButton();
		drawButton = new JToggleButton();
		requestTakebackButton = new JToggleButton();
		surrenderButton = new JToggleButton();
		
		tailButton = new JButton();
		prevButton = new JButton();
		nextButton = new JButton();
		headButton = new JButton();
		
		panel.setOpaque(false);
		prevButton.setEnabled(false);
		tailButton.setEnabled(false);
		nextButton.setEnabled(false);
		headButton.setEnabled(false);
		
		mhp.parent = this;
		parent = null;
		
		Dimension d = new Dimension(cellWidth, cellHeight);
		cellSpring.setPreferredSize(d);
		cellSpring.setSize(d);
		
		putConstraints();
		addComponents();
		addListeners();
		setFont(font);
		
		panel.setPreferredSize(new Dimension(cellWidth * 5, cellHeight * 8));
	}
	
	public ActionPanel()
	{
		this("WHITE", "BLACK", 64, 30, Color.GREEN, Color.CYAN, new Font("Consolas", Font.BOLD, 14));
	}
	
	public void setFont(Font font)
	{
		topLabel.setFont(font);
		bottomLabel.setFont(font);
	}
	
	public void addMove(String formattedMove)
	{
		mhp.addMove(formattedMove);
		mhp.selectMove(mhp.getMaxPly());
		nextButton.setEnabled(false);
		headButton.setEnabled(false);
		prevButton.setEnabled(true);
		tailButton.setEnabled(true);
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	protected void requestStateLoad(int ply)
	{
		prevButton.setEnabled(ply > 0);
		tailButton.setEnabled(ply > 0);
		nextButton.setEnabled(ply < mhp.getMaxPly());
		headButton.setEnabled(ply < mhp.getMaxPly());
		parent.displayState(ply);
	}
	
	public int getMaxPly()
	{
		return mhp.getMaxPly();
	}
	
	protected void toggleFlipped()
	{
		swapPlayerLabels();
		parent.setFlipped(flipToggleButton.isSelected());
	}
	
	protected void drawButtonToggled()
	{
		System.out.println("draw button toggled");
	}
	
	protected void takebackButtonToggled()
	{
		System.out.println("takeback button toggled");
	}
	
	protected void surrenderButtonToggled()
	{
		System.out.println("surrender button toggled");
	}
	
	private void tailButtonPressed(ActionEvent event)
	{
		mhp.selectMove(0);
		requestStateLoad(0);
	}
	
	private void prevButtonPressed(ActionEvent event)
	{
		int prev = mhp.getCurrentSelection() - 1;
		mhp.selectMove(prev);
		requestStateLoad(prev);
	}
	
	private void nextButtonPressed(ActionEvent event)
	{
		int next = mhp.getCurrentSelection() + 1;
		mhp.selectMove(next);
		requestStateLoad(next);
	}
	
	private void headButtonPressed(ActionEvent event)
	{
		mhp.selectMove(mhp.getMaxPly());
		requestStateLoad(mhp.getMaxPly());
	}
	
	private void swapPlayerLabels()
	{
		String temp = topLabel.getText();
		topLabel.setText(bottomLabel.getText());
		bottomLabel.setText(temp);
	}
	
	private void addComponents()
	{
		panel.add(mhp.getScrollPane());
		panel.add(topLabel);
		panel.add(bottomLabel);
		panel.add(flipToggleButton);
		panel.add(drawButton);
		panel.add(requestTakebackButton);
		panel.add(surrenderButton);
		panel.add(tailButton);
		panel.add(prevButton);
		panel.add(nextButton);
		panel.add(headButton);
	}
	
	public void setWhiteUsername(String username)
	{
		(flipToggleButton.isSelected() ? topLabel : bottomLabel).setText(username);
	}
	
	public void setBlackUsername(String username)
	{
		(flipToggleButton.isSelected() ? bottomLabel : topLabel).setText(username);
	}
	
	private void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, topLabel, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, bottomLabel, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, topLabel, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, bottomLabel, 0, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, flipToggleButton, 0, SpringLayout.SOUTH, topLabel);
		layout.putConstraint(SpringLayout.WEST, flipToggleButton, 0, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.EAST, flipToggleButton, Spring.width(cellSpring), SpringLayout.WEST, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, flipToggleButton, Spring.height(cellSpring), SpringLayout.NORTH, flipToggleButton);
		
		layout.putConstraint(SpringLayout.NORTH, tailButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, tailButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, tailButton, 0, SpringLayout.EAST, flipToggleButton);
		layout.putConstraint(SpringLayout.EAST, tailButton, Spring.width(cellSpring), SpringLayout.WEST, tailButton);
		
		layout.putConstraint(SpringLayout.NORTH, prevButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, prevButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, prevButton, 0, SpringLayout.EAST, tailButton);
		layout.putConstraint(SpringLayout.EAST, prevButton, Spring.width(cellSpring), SpringLayout.WEST, prevButton);
		
		layout.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, nextButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, nextButton, 0, SpringLayout.EAST, prevButton);
		layout.putConstraint(SpringLayout.EAST, nextButton, Spring.width(cellSpring), SpringLayout.WEST, nextButton);

		layout.putConstraint(SpringLayout.NORTH, headButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, headButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, headButton, 0, SpringLayout.EAST, nextButton);
		layout.putConstraint(SpringLayout.EAST, headButton, Spring.width(cellSpring), SpringLayout.WEST, headButton);
		
		layout.putConstraint(SpringLayout.SOUTH, requestTakebackButton, 0, SpringLayout.NORTH, bottomLabel);
		layout.putConstraint(SpringLayout.WEST, requestTakebackButton, 0, SpringLayout.EAST, flipToggleButton);
		layout.putConstraint(SpringLayout.EAST, requestTakebackButton, Spring.width(cellSpring), SpringLayout.WEST, requestTakebackButton);
		layout.putConstraint(SpringLayout.NORTH, requestTakebackButton, Spring.minus(Spring.height(cellSpring)), SpringLayout.SOUTH, requestTakebackButton);
		
		layout.putConstraint(SpringLayout.WEST, drawButton, 0, SpringLayout.EAST, requestTakebackButton);
		layout.putConstraint(SpringLayout.NORTH, drawButton, 0, SpringLayout.NORTH, requestTakebackButton);
		layout.putConstraint(SpringLayout.SOUTH, drawButton, 0, SpringLayout.SOUTH, requestTakebackButton);
		layout.putConstraint(SpringLayout.EAST, drawButton, Spring.width(cellSpring), SpringLayout.WEST, drawButton);
		
		layout.putConstraint(SpringLayout.WEST, surrenderButton, 0, SpringLayout.EAST, drawButton);
		layout.putConstraint(SpringLayout.NORTH, surrenderButton, 0, SpringLayout.NORTH, requestTakebackButton);
		layout.putConstraint(SpringLayout.SOUTH, surrenderButton, 0, SpringLayout.SOUTH, requestTakebackButton);
		layout.putConstraint(SpringLayout.EAST, surrenderButton, Spring.width(cellSpring), SpringLayout.WEST, surrenderButton);
		
		layout.putConstraint(SpringLayout.NORTH, mhp.getScrollPane(), 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, mhp.getScrollPane(), 0, SpringLayout.NORTH, drawButton);
		layout.putConstraint(SpringLayout.EAST, mhp.getScrollPane(), 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, mhp.getScrollPane(), 0, SpringLayout.WEST, panel);
		
		panel.setLayout(layout);
	}
	
	private void addListeners()
	{
		tailButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				tailButtonPressed(event);
			}
		});
		
		prevButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				prevButtonPressed(event);
			}
		});
		
		nextButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				nextButtonPressed(event);
			}
		});
		
		headButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				headButtonPressed(event);
			}
		});
		
		flipToggleButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				toggleFlipped();
			}
		});
		
		drawButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				drawButtonToggled();
			}
		});
		
		requestTakebackButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				takebackButtonToggled();
			}
		});
		
		surrenderButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				surrenderButtonToggled();
			}
		});
	}
}
