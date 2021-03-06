package nuchess.ui.feneditor.view;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import nuchess.engine.Piece;
import nuchess.ui.Tab;
import nuchess.ui.View;
import nuchess.ui.feneditor.control.FENEditorControl;

public class FENEditorView implements View
{
	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final int NULL_BUTTON = -1;
	private static final boolean DEFAULT_FLIPPED = false;
	
	private JPanel panel;
	private FENBoardView boardView;
	private PieceSelectorPanel pieceSelector;
	private JTextField FENtextField;
	private Tab tab;
	private long draggedBB, occ;
	private int piece, lockedButton;
	
	public FENEditorControl controller;
	
	public FENEditorView(int piece, boolean flipped)
	{
		panel = new JPanel();
		boardView = new FENBoardView(flipped);
		pieceSelector = new PieceSelectorPanel(piece);
		FENtextField = new JTextField();
		tab = new Tab(this);
		tab.addCloseButton();
		
		this.piece = piece;
		
		lockedButton = NULL_BUTTON;
		draggedBB = occ = 0L;
		controller = null;
		
		linkObjects();
		initListeners();
		putConstraints();
		addComponents();
		
		panel.setOpaque(false);
		pieceSelector.getPanel().setCursor(HAND_CURSOR);
		boardView.getPanel().setCursor(HAND_CURSOR);
		
		pieceSelector.initGraphics();
	}
	
	public FENEditorView(boolean flipped)
	{
		this(Piece.WHITE_PAWN, flipped);
	}
	
	public FENEditorView()
	{
		this(Piece.WHITE_PAWN, DEFAULT_FLIPPED);
	}
	
	protected void pressed(int button, int square)
	{
		if(lockedButton == NULL_BUTTON)
		{
			draggedBB = 0L;
			lockedButton = button;
			if(button == MouseEvent.BUTTON1 && ((occ >> square) & 1) == 0)
				controller.put(piece, square);
			else if(button == MouseEvent.BUTTON3 && ((occ >> square) & 1) == 1)
				controller.capture(square);
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
		if(0 <= square && square <= 64)
		{
			if(((draggedBB >> square) & 1) == 0)
			{
				draggedBB |= (1L << square);
				if(lockedButton == MouseEvent.BUTTON1 && ((occ >> square) & 1) == 0)
					controller.put(piece, square);
				else if(lockedButton == MouseEvent.BUTTON3 && ((occ >> square) & 1) == 1)
					controller.capture(square);
			}
			else
			{
				draggedBB = 0L;
			}
		}
	}
	
	private void linkObjects()
	{
		boardView.parent = this;
		pieceSelector.parent = this;
	}
	
	private void initListeners()
	{		
		FENtextField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					controller.setFEN(FENtextField.getText());
				}
			}
			
			public void keyReleased(KeyEvent e)
			{
				
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
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, FENtextField, 0, SpringLayout.HORIZONTAL_CENTER, boardView.getPanel());
		layout.putConstraint(SpringLayout.NORTH, FENtextField, 0, SpringLayout.SOUTH, boardView.getPanel());
		layout.putConstraint(SpringLayout.EAST, FENtextField, 0, SpringLayout.EAST, boardView.getPanel());
		layout.putConstraint(SpringLayout.WEST, FENtextField, 0, SpringLayout.WEST, boardView.getPanel());
		
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(boardView.getPanel());
		panel.add(pieceSelector.getPanel());
		panel.add(FENtextField);
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
	
	public BufferedImage getRenderedImage()
	{
		return boardView.getRenderedImage();
	}
	
	public void close()
	{
		controller.close();
	}
	
	public void saveGraphicsAs()
	{
		controller.saveGraphicsAs();
	}
	
	public String getTitle()
	{
		return "FEN Builder";
	}
	
	public Tab getTab()
	{
		return tab;
	}
}
