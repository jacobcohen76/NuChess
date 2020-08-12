package nuchess.ui.game.view;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import nuchess.engine.CMove;
import nuchess.ui.Tab;
import nuchess.ui.View;
import nuchess.ui.game.control.GameControl;

public class GameView implements View
{
	private static final boolean DEFAULT_FLIPPED = false;
	
	private JPanel panel;
	private ChessBoardView boardView;
	private ActionPanel actionPanel;
	private PromotionDialog promotionDialog;
	private Tab tab;
	
	private String whitePlayerName, blackPlayerName;
	
	public GameControl control;
	
	public GameView(boolean flipped, String whitePlayerName, String blackPlayerName)
	{
		this.whitePlayerName = whitePlayerName;
		this.blackPlayerName = blackPlayerName;
		tab = new Tab(this);
		tab.addCloseButton();
		
		initComponents(flipped);
		putConstraints();
		addComponents();
		linkObjects();
	}
	
	public GameView()
	{
		this(DEFAULT_FLIPPED, "WHITE", "BLACK");
	}
	
	private void initComponents(boolean flipped)
	{
		panel = new JPanel();
		boardView = new ChessBoardView(flipped);
		actionPanel = new ActionPanel();
		promotionDialog = new PromotionDialog();
		panel.setOpaque(false);
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
	
	private void displayPromoDialog(boolean isCapture, int from, int to)
	{
		System.out.println("test");
//		short move = 0;
		if(isCapture)
		{
			promotionDialog.setColor(actionPanel.getMaxPly() & 1);
			promotionDialog.setLocation(boardView.getPanel().getX() + boardView.getX(to), boardView.getPanel().getY() + boardView.getY(to));
			promotionDialog.setVisible(true);
		}
		else
		{
			promotionDialog.setColor(actionPanel.getMaxPly() & 1);
			java.awt.Point p = boardView.getPanel().getLocationOnScreen();
			promotionDialog.setLocation(boardView.getX(to) + p.x, boardView.getY(to) + p.y);
			promotionDialog.setVisible(true);
		}
//		control.make(new CMove(move));
	}
	
	protected void requestMove(CMove move)
	{
		control.make(move);
	}
	
	protected void requestPromoMove(boolean isCapture, int from, int to)
	{
//		parent.setPromoState(true);
//		displayPromoDialog(isCapture, from, to);
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
		control.close();
	}
	
	public void saveGraphicsAs()
	{
		control.saveGraphicsAs();
	}
	
	public String getTitle()
	{
		return "[Standard] " + whitePlayerName + " vs " + blackPlayerName;
	}
	
	public Tab getTab()
	{
		return tab;
	}
	
	public void setWhiteUsername(String username)
	{
		whitePlayerName = username;
		actionPanel.setWhiteUsername(username);
	}
	
	public void setBlackUsername(String username)
	{
		blackPlayerName = username;
		actionPanel.setBlackUsername(username);
	}
}
