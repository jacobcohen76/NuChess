package nuchess.view.chessgameview;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.control.ChessGameController;
import nuchess.engine.CMove;
import nuchess.view.View;

public class ChessGameView implements View
{
	private static final boolean DEFAULT_FLIPPED = false;
	
	private JPanel panel;
	private ChessBoardView boardView;
	private ActionPanel actionPanel;
	
	public ChessGameController controller;
	
	public ChessGameView(boolean flipped)
	{
		initComponents(flipped);
		putConstraints();
		addComponents();
		linkObjects();
	}
	
	public ChessGameView()
	{
		this(DEFAULT_FLIPPED);
	}
	
	private void initComponents(boolean flipped)
	{
		panel = new JPanel();
		boardView = new ChessBoardView(flipped);
		actionPanel = new ActionPanel();
		
		panel.setOpaque(false);
		actionPanel.getPanel().setPreferredSize(new Dimension(300, 300));
	}
	
	private void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, boardView.getPanel(), 0, SpringLayout.HORIZONTAL_CENTER, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, boardView.getPanel(), 0, SpringLayout.VERTICAL_CENTER, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, actionPanel.getPanel(), 0, SpringLayout.VERTICAL_CENTER, boardView.getPanel());
		layout.putConstraint(SpringLayout.WEST, actionPanel.getPanel(), 0, SpringLayout.EAST, boardView.getPanel());
		panel.setLayout(layout);
	}
	
	private void addComponents()
	{
		panel.add(boardView.getPanel());
		panel.add(actionPanel.getPanel());
	}
	
	private void linkObjects()
	{
		boardView.parent = this;
		actionPanel.parent = this;
	}
	
	protected void requestMove(CMove move)
	{
		controller.make(move);
	}
	
	protected void displayState(int ply)
	{
		boardView.displayState(ply);
	}
	
	protected void setFlipped(boolean flipped)
	{
		boardView.setFlipped(flipped);
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public void setMoveableSquares(long moveableSquares)
	{
		boardView.setMoveableSquares(moveableSquares);
	}
	
	public void setInitialState(long checkBB, long occBB, String FEN)
	{
		boardView.setOccupancy(occBB);
		boardView.setInitialState(new BoardViewState(checkBB, occBB, new CMove((short) 0), FEN));
	}
	
	public void addNewState(long checkBB, long occBB, CMove move, String FEN, String SAN)
	{
		boardView.setOccupancy(occBB);
		boardView.addNewState(new BoardViewState(checkBB, occBB, move, FEN));
		boardView.displayState(boardView.mostRecentState());
		actionPanel.addMove(SAN);
		actionPanel.getPanel().repaint();
	}
	
	public void setSelectableMoves(List<CMove> moves)
	{
		boardView.setSelectableMoves(moves);
	}
	
	public void setSelectionEnabled(boolean b)
	{
		boardView.setSelectionEnabled(b);
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
		return "Chess Game";
	}
}
