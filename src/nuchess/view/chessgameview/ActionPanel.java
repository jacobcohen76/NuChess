package nuchess.view.chessgameview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

class ActionPanel
{
	private JPanel panel;
	private MoveHistoryPanel mhp;
	private JLabel topLabel, bottomLabel;
	private JToggleButton flipToggleButton, drawButton, requestTakebackButton, surrenderButton;
	private JButton tailButton, prevButton, nextButton, headButton;
	
	protected ChessGameView parent;
	
	public ActionPanel(String whitePlayerLabel, String blackPlayerLabel, int labelWidth, Color selectedColor, Color hoverColor, Font font)
	{
		panel = new JPanel();
		mhp = new MoveHistoryPanel(labelWidth, selectedColor, hoverColor, font);
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
		prevButton.setEnabled(false);
		tailButton.setEnabled(false);
		nextButton.setEnabled(false);
		headButton.setEnabled(false);
		
		mhp.parent = this;
		parent = null;
		
		putConstraints();
		addComponents();
		addListeners();
		setFont(font);
	}
	
	public ActionPanel()
	{
		this("WHITE", "BLACK", 30, Color.GREEN, Color.CYAN, new Font("Consolas", Font.BOLD, 14));
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
	
	private void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.NORTH, topLabel, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, bottomLabel, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, topLabel, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.WEST, bottomLabel, 0, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, flipToggleButton, 0, SpringLayout.SOUTH, topLabel);
		layout.putConstraint(SpringLayout.WEST, flipToggleButton, 0, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, tailButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, tailButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, tailButton, 0, SpringLayout.EAST, flipToggleButton);
		
		layout.putConstraint(SpringLayout.NORTH, prevButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, prevButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, prevButton, 0, SpringLayout.EAST, tailButton);
		
		layout.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, nextButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, nextButton, 0, SpringLayout.EAST, prevButton);

		layout.putConstraint(SpringLayout.NORTH, headButton, 0, SpringLayout.NORTH, flipToggleButton);
		layout.putConstraint(SpringLayout.SOUTH, headButton, 0, SpringLayout.SOUTH, flipToggleButton);
		layout.putConstraint(SpringLayout.WEST, headButton, 0, SpringLayout.EAST, nextButton);
		
		layout.putConstraint(SpringLayout.SOUTH, drawButton, 0, SpringLayout.NORTH, bottomLabel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, drawButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		
		layout.putConstraint(SpringLayout.NORTH, requestTakebackButton, 0, SpringLayout.NORTH, drawButton);
		layout.putConstraint(SpringLayout.SOUTH, requestTakebackButton, 0, SpringLayout.SOUTH, drawButton);
		layout.putConstraint(SpringLayout.EAST, requestTakebackButton, 0, SpringLayout.WEST, drawButton);
		
		layout.putConstraint(SpringLayout.NORTH, surrenderButton, 0, SpringLayout.NORTH, drawButton);
		layout.putConstraint(SpringLayout.SOUTH, surrenderButton, 0, SpringLayout.SOUTH, drawButton);
		layout.putConstraint(SpringLayout.WEST, surrenderButton, 0, SpringLayout.EAST, drawButton);
		
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
