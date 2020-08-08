package nuchess.ui.root.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import nuchess.engine.CBoard;
import nuchess.engine.Chessboard;
import nuchess.player.Player;
import nuchess.player.computer.algorithm.MiniMax;
import nuchess.player.computer.evaluator.BoardEvaluator;
import nuchess.player.computer.evaluator.SimpleBoardEvaluator;
import nuchess.player.human.Human;
import nuchess.ui.Control;
import nuchess.ui.View;
import nuchess.ui.bitboardeditor.control.BitboardEditorControl;
import nuchess.ui.bitboardeditor.view.BitboardEditorView;
import nuchess.ui.feneditor.control.FENEditorControl;
import nuchess.ui.feneditor.view.FENEditorView;
import nuchess.ui.game.control.GameControl;
import nuchess.ui.game.view.GameView;
import nuchess.ui.root.view.HomeView;
import nuchess.ui.root.view.RootView;

public class RootControl implements Control
{
	private RootView rootView;
	private HomeView homeView;
	
	public RootControl(RootView rootView, HomeView homeView)
	{
		this.rootView = rootView;
		this.homeView = homeView;
	}
	
	@Override
	public void init()
	{
		openTab(homeView);
		initMainMenuBar();
	}
	
	@Override
	public void saveGraphicsAs()
	{
		
	}

	@Override
	public void saveAs()
	{
		
	}

	@Override
	public void close()
	{
		
	}

	@Override
	public View getView()
	{
		return rootView;
	}
	
	public void openTab(Control control)
	{
		control.init();
		rootView.openTab(control);
	}
	
	public void openTab(View view)
	{
		rootView.openTab(view);
	}
	
	private void saveAsActionPerformed(ActionEvent e)
	{
		
	}
	
	private void saveGraphicsAsActionPerformed(ActionEvent e)
	{
		rootView.saveGraphicsAs();
	}
	
	private void newGameActionPerformed(ActionEvent e)
	{
		openTab(constructNewGameController());
	}
	
	private void newAIActionPerformed(ActionEvent e)
	{
		
	}
	
	private void FENBoardEditorActionPerformed(ActionEvent e)
	{
		openTab(constructNewFENBoardEditor());
	}
	
	private void bitboardEditorActionPerformed(ActionEvent e)
	{
		openTab(constructNewBitboardEditor());
	}
	
//	private void graphicsSettingsActionPerformed(ActionEvent e)
//	{
//		graphicsSettingsDialog.setVisible(true);
//	}
	
	private Control constructNewGameController()
	{
		Chessboard board;
		BoardEvaluator evaluator;
		int depth;
		
		board = new Chessboard();
		evaluator = new SimpleBoardEvaluator();
		depth = 5;
		Player white = new MiniMax("MiniMax, Depth = " + depth, "", board, evaluator, depth);
//		Player white = new Human("Jacob Cohen", "");

		
//		engine = new ChessEngine();
//		evaluator = new MaterialEvaluator();
//		depth = 5;
//		Player black = new MiniMax("MiniMax Material Evaluator, Depth = " + depth, "", engine, evaluator, depth);
		Player black = new Human("Jacob Cohen", "");
		
		GameView view = new GameView();
		GameControl control = new GameControl(view, white, black, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		return control;
	}
	
	private Control constructNewBitboardEditor()
	{
		BitboardEditorView view = new BitboardEditorView();
		BitboardEditorControl controller = new BitboardEditorControl(view);
		return controller;
	}
	
	private Control constructNewFENBoardEditor()
	{
		CBoard board = new CBoard();
		FENEditorView view = new FENEditorView();
		FENEditorControl control = new FENEditorControl(board, view);
		return control;
	}

	
	private void initMainMenuBar()
	{
		rootView.add(constructFileMenu());
		rootView.add(constructToolMenu());
//		rootView.add(constructSettingsMenu());
		rootView.add(constructHelpMenu());
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
	
//	private JMenuItem constructSettingsMenu()
//	{
//		JMenu settingsMenu = new JMenu();
//		settingsMenu.setText("Settings");
//		
//		JMenuItem graphicsSettings = new JMenuItem();
//		graphicsSettings.setText("Graphics Settings");
//		graphicsSettings.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				graphicsSettingsActionPerformed(e);
//			}
//		});
//		settingsMenu.add(graphicsSettings);
//		
//		return settingsMenu;
//	}
	
	private JMenuItem constructHelpMenu()
	{
		JMenu helpMenu = new JMenu();
		helpMenu.setText("Help");
		
		return helpMenu;
	}
}
