package nuchess.view.fenbuilder;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import nuchess.control.FENBuilderController;
import nuchess.engine.Piece;

public class FENBuilderView
{
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final int NULL_BUTTON = -1;
	
	private JPanel panel;
	private FENBuilderBoardView boardView;
	private FENSettingsView settingsView;
	private PieceSelectorPanel pieceSelector;
	private JTextField FENtextField;
	private long draggedBB, occ;
	private int piece, lockedButton;
	
	protected boolean shiftHeld;
	
	public FENBuilderController parent;
	
	public FENBuilderView(int piece, int squareSize, boolean flipped)
	{
		panel = new JPanel();
		boardView = new FENBuilderBoardView(squareSize, flipped);
		settingsView = new FENSettingsView("En Passant Square", "To Move", "Fullmove Clock", "Halfmove Clock");
		pieceSelector = new PieceSelectorPanel(squareSize, piece);
		FENtextField = new JTextField();
		
		this.piece = piece;
		
		lockedButton = NULL_BUTTON;
		draggedBB = occ = 0L;
		shiftHeld = false;
		parent = null;
		
		linkObjects();
		initListeners();
		putConstraints();
		addComponents();
		
		pieceSelector.getPanel().setCursor(HAND_CURSOR);
		boardView.getPanel().setCursor(HAND_CURSOR);
		
		pieceSelector.initGraphics();
		
		shiftHeld = false;
	}
	
	public FENBuilderView(int squareSize, boolean flipped)
	{
		this(Piece.WHITE_PAWN, squareSize, flipped);
	}
	
	protected void pressed(int button, int square)
	{
		if(lockedButton == NULL_BUTTON)
		{
			draggedBB = 0L;
			lockedButton = button;
			if(button == MouseEvent.BUTTON1 && ((occ >> square) & 1) == 0)
				parent.put(piece, square);
			else if(button == MouseEvent.BUTTON3 && ((occ >> square) & 1) == 1)
				parent.capture(square);
		}
	}
	
	protected void released(int button, int square)
	{
		if(lockedButton == button)
		{
			lockedButton = NULL_BUTTON;
		}
	}
	
	protected void dragged(int square)
	{
		if(shiftHeld)
		{
			if(((draggedBB >> square) & 1) == 0)
			{
				draggedBB |= (1L << square);
				if(lockedButton == MouseEvent.BUTTON1 && ((occ >> square) & 1) == 0)
					parent.put(piece, square);
				else if(lockedButton == MouseEvent.BUTTON3 && ((occ >> square) & 1) == 1)
					parent.capture(square);
			}
		}
		else
		{
			draggedBB = 0L;
		}
	}
	
	private void linkObjects()
	{
		boardView.parent = this;
		pieceSelector.parent = this;
	}
	
	private void initListeners()
	{
		panel.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					shiftHeld = true;
				}
			}
			
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					shiftHeld = false;
				}
			}
			
			public void keyTyped(KeyEvent e)
			{
				
			}
		});
		
		FENtextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					parent.setFEN(FENtextField.getText());
				}
				else if(e.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					shiftHeld = true;
				}
			}
			
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					shiftHeld = false;
				}
			}
			
			public void keyTyped(KeyEvent e)
			{
				
			}
		});
	}
	
	private void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, boardView.getPanel(), 0, SpringLayout.HORIZONTAL_CENTER, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, boardView.getPanel(), 0, SpringLayout.VERTICAL_CENTER, panel);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, pieceSelector.getPanel(), 0, SpringLayout.VERTICAL_CENTER, boardView.getPanel());
		layout.putConstraint(SpringLayout.EAST, pieceSelector.getPanel(), 0, SpringLayout.WEST, boardView.getPanel());
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, settingsView.getPanel(), 0, SpringLayout.VERTICAL_CENTER, boardView.getPanel());
		layout.putConstraint(SpringLayout.WEST, settingsView.getPanel(), 0, SpringLayout.EAST, boardView.getPanel());
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, FENtextField, 0, SpringLayout.HORIZONTAL_CENTER, boardView.getPanel());
		layout.putConstraint(SpringLayout.NORTH, FENtextField, 0, SpringLayout.SOUTH, boardView.getPanel());
		layout.putConstraint(SpringLayout.EAST, FENtextField, 0, SpringLayout.EAST, boardView.getPanel());
		layout.putConstraint(SpringLayout.WEST, FENtextField, 0, SpringLayout.WEST, boardView.getPanel());
		
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(boardView.getPanel());
		panel.add(settingsView.getPanel());
		panel.add(pieceSelector.getPanel());
		panel.add(FENtextField);
	}
	
	protected Image[] getScaledResources()
	{
		return boardView.getScaledResources();
	}
	
	public void display(String FEN)
	{
		FENtextField.setText(FEN);
		boardView.clearPieceLayer(occ);
		boardView.paintFEN(FEN);
	}
		
	public void repaint()
	{
		panel.repaint();
		pieceSelector.repaint();
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public void setOccupancy(long occ)
	{
		this.occ = occ;
	}
	
	public void setPiece(int piece)
	{
		this.piece = piece;
	}
}
