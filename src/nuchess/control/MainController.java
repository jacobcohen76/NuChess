package nuchess.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import nuchess.engine.CBoard;
import nuchess.engine.ChessEngine;
import nuchess.view.View;
import nuchess.view.bitboardeditor.BitboardEditorView;
import nuchess.view.chessgameview.ChessGameView;
import nuchess.view.fenboardeditor.FENBoardEditorView;
import nuchess.view.homeview.HomeView;
import nuchess.view.homeview.MenuView;
import nuchess.view.tabs.TabbedView;

public class MainController implements Controller
{
	private MenuView menuView;
	private TabbedView tabbedView;
	private HomeView homeView;
	
	public MainController(MenuView menuView, TabbedView tabbedView, HomeView homeView)
	{
		this.menuView = menuView;
		this.tabbedView = tabbedView;
		this.homeView = homeView;
	}
	
	public void init()
	{
		menuView.display(tabbedView);
		tabbedView.addHomeTab(homeView);
		tabbedView.requestDisplay(tabbedView.getNumTabs() - 1);
		initMainMenuBar();
	}
	
	public void saveGraphicsAs()
	{
		
	}
	
	public void saveAs()
	{
		
	}
	
	public void close()
	{
		
	}
	
	public View getView()
	{
		return menuView;
	}
	
	private void saveAsActionPerformed(ActionEvent e)
	{
		
	}
	
	private void saveGraphicsAsActionPerformed(ActionEvent e)
	{
		tabbedView.saveGraphicsAs();
	}
	
	private void newGameActionPerformed(ActionEvent e)
	{
		addNewTab(constructNewChessGameController());
	}
	
	private void newAIActionPerformed(ActionEvent e)
	{
		
	}
	
	private void FENBoardEditorActionPerformed(ActionEvent e)
	{
		addNewTab(constructNewFENBoardEditor());
	}
	
	private void bitboardEditorActionPerformed(ActionEvent e)
	{
		addNewTab(constructNewBitboardEditor());
	}
	
	private void addNewTab(Controller controller)
	{
		controller.init();
		tabbedView.addNewTab(controller.getView());
		tabbedView.requestDisplay(tabbedView.getNumTabs() - 1);
	}
	
	private Controller constructNewChessGameController()
	{
		ChessEngine engine = new ChessEngine();
		ChessGameView view = new ChessGameView();
		ChessGameController controller = new ChessGameController(engine, view);
		return controller;
	}
	
	private Controller constructNewBitboardEditor()
	{
		BitboardEditorView view = new BitboardEditorView();
		BitboardEditorController controller = new BitboardEditorController(view);
		return controller;
	}
	
	private Controller constructNewFENBoardEditor()
	{
		CBoard board = new CBoard();
		FENBoardEditorView view = new FENBoardEditorView();
		FENBoardEditorController controller = new FENBoardEditorController(board, view);
		return controller;
	}
	
	private void initMainMenuBar()
	{
		menuView.add(constructFileMenu());
		menuView.add(constructToolMenu());
		menuView.add(constructHelpMenu());
	}
	
	private JMenuItem constructFileMenu()
	{
		JMenu fileMenu = new JMenu();
		fileMenu.setText("File");
		
		JMenu newItemMenu = new JMenu();
		newItemMenu.setText("New");
		fileMenu.add(newItemMenu);
		
		JMenuItem newGame = new JMenuItem();
		newGame.setText("Game");
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				newGameActionPerformed(e);
			}
		});
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newItemMenu.add(newGame);
		
		JMenuItem newAI = new JMenuItem();
		newAI.setText("AI");
		newAI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				newAIActionPerformed(e);
			}
		});
		newItemMenu.add(newAI);
		
		JMenuItem saveAs = new JMenuItem();
		saveAs.setText("Save As...");
		saveAs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveAsActionPerformed(e);
			}
		});
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(saveAs);
		
		JMenuItem saveGraphicsAs = new JMenuItem();
		saveGraphicsAs.setText("Save Graphics As...");
		saveGraphicsAs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveGraphicsAsActionPerformed(e);
			}
		});
		saveGraphicsAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(saveGraphicsAs);
		
		return fileMenu;
	}
	
	private JMenuItem constructToolMenu()
	{
		JMenu toolMenu = new JMenu();
		toolMenu.setText("Tools");
		
		JMenuItem FENBoardEditor = new JMenuItem();
		FENBoardEditor.setText("FEN Board Editor");
		FENBoardEditor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				FENBoardEditorActionPerformed(e);
			}
		});
		toolMenu.add(FENBoardEditor);
		
		JMenuItem bitboardEditor = new JMenuItem();
		bitboardEditor.setText("Bitboard Editor");
		bitboardEditor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				bitboardEditorActionPerformed(e);
			}
		});
		toolMenu.add(bitboardEditor);
		
		return toolMenu;
	}
	
	private JMenuItem constructHelpMenu()
	{
		JMenu helpMenu = new JMenu();
		helpMenu.setText("Help");
		
		return helpMenu;
	}
}
