package nuchess.control.copy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import nuchess.engine.CBoard;
import nuchess.engine.ChessEngine;
import nuchess.view.TabbedView;
import nuchess.view.View;
import nuchess.view.ViewFrame;
import nuchess.view.bitboardeditor.BitboardEditorView;
import nuchess.view.chessgame.ChessGameView;
import nuchess.view.fenboardeditor.FENBoardEditorView;
import nuchess.view.graphics.ColorIDs;
import nuchess.view.graphics.TextureIDs;
import nuchess.view.home.HomeView;
import nuchess.view.home.MenuView;
import nuchess.view.settings.SettingTextureSelection;
import nuchess.view.settings.SettingsColorSelection;
import nuchess.view.settings.SettingsDialog;
import nuchess.view.settings.SettingsView;

public class MainController implements Controller
{
	private ViewFrame view;
	private MenuView menuView;
	private TabbedView tabbedView;
	private HomeView homeView;
	
	private SettingsDialog graphicsSettingsDialog;
	
	public MainController(ViewFrame view, MenuView menuView, TabbedView tabbedView, HomeView homeView)
	{
		this.view = view;
		this.menuView = menuView;
		this.tabbedView = tabbedView;
		this.homeView = homeView;
		
		graphicsSettingsDialog = constructNewGraphicsSettingsDialog();
	}
	
	public void init()
	{
		menuView.display(tabbedView);
		tabbedView.openTab(homeView);
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
		openTab(constructNewChessGameController());
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
	
	private void graphicsSettingsActionPerformed(ActionEvent e)
	{
		graphicsSettingsDialog.setVisible(true);
	}
	
	private void openTab(Controller controller)
	{
		controller.init();
		tabbedView.openTab(controller.getView());
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
		menuView.addLeft(constructFileMenu());
		menuView.addLeft(constructToolMenu());
		menuView.addLeft(constructSettingsMenu());
		menuView.addLeft(constructHelpMenu());
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
	
	private JMenuItem constructSettingsMenu()
	{
		JMenu settingsMenu = new JMenu();
		settingsMenu.setText("Settings");
		
		JMenuItem graphicsSettings = new JMenuItem();
		graphicsSettings.setText("Graphics Settings");
		graphicsSettings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				graphicsSettingsActionPerformed(e);
			}
		});
		settingsMenu.add(graphicsSettings);
		
		return settingsMenu;
	}
	
	private JMenuItem constructHelpMenu()
	{
		JMenu helpMenu = new JMenu();
		helpMenu.setText("Help");
		
		return helpMenu;
	}
	
	private SettingsDialog constructNewGraphicsSettingsDialog()
	{
		SettingsView textureSettingsView = new SettingsView("Textures", 500, 900, 200, 30, 3, 2);
		SettingsView colorSettingsView = new SettingsView("Colors", 500, 900, 200, 30, 3, 2);
		
		textureSettingsView.addSetting("NULL", new SettingTextureSelection(TextureIDs.NULL));
		textureSettingsView.addSetting("Dot", new SettingTextureSelection(TextureIDs.DOT));
		textureSettingsView.addSetting("White Pawn", new SettingTextureSelection(TextureIDs.WHITE_PAWN));
		textureSettingsView.addSetting("White Knight", new SettingTextureSelection(TextureIDs.WHITE_KNIGHT));
		textureSettingsView.addSetting("White Bishop", new SettingTextureSelection(TextureIDs.WHITE_BISHOP));
		textureSettingsView.addSetting("White Rook", new SettingTextureSelection(TextureIDs.WHITE_ROOK));
		textureSettingsView.addSetting("White Queen", new SettingTextureSelection(TextureIDs.WHITE_QUEEN));
		textureSettingsView.addSetting("White King", new SettingTextureSelection(TextureIDs.WHITE_KING));
		textureSettingsView.addSetting("Black Pawn", new SettingTextureSelection(TextureIDs.BLACK_PAWN));
		textureSettingsView.addSetting("Black Knight", new SettingTextureSelection(TextureIDs.BLACK_KNIGHT));
		textureSettingsView.addSetting("Black Bishop", new SettingTextureSelection(TextureIDs.BLACK_BISHOP));
		textureSettingsView.addSetting("Black Rook", new SettingTextureSelection(TextureIDs.BLACK_ROOK));
		textureSettingsView.addSetting("Black Queen", new SettingTextureSelection(TextureIDs.BLACK_QUEEN));
		textureSettingsView.addSetting("Black King", new SettingTextureSelection(TextureIDs.BLACK_KING));
		textureSettingsView.addSetting("Border", new SettingTextureSelection(TextureIDs.BORDER));
		textureSettingsView.addSetting("Highlight", new SettingTextureSelection(TextureIDs.HIGHLIGHT));
		textureSettingsView.addSetting("Mask", new SettingTextureSelection(TextureIDs.MASK));
		textureSettingsView.addSetting("Dark Square", new SettingTextureSelection(TextureIDs.DARK_SQUARE));
		textureSettingsView.addSetting("Light Square", new SettingTextureSelection(TextureIDs.LIGHT_SQUARE));
		
		colorSettingsView.addSetting("Tab", new SettingsColorSelection("Tab", ColorIDs.TAB));
		colorSettingsView.addSetting("Hovering Tab", new SettingsColorSelection("Hovering Tab", ColorIDs.HOVERING_TAB));
		colorSettingsView.addSetting("Selected Tab", new SettingsColorSelection("Selected Tab", ColorIDs.SELECTED_TAB));
		colorSettingsView.addSetting("Tab Font", new SettingsColorSelection("Tab Font", ColorIDs.TAB_FONT));
		colorSettingsView.addSetting("Main Menu Background", new SettingsColorSelection("Main Menu Background", ColorIDs.MAIN_MENU_BACKGROUND));
		colorSettingsView.addSetting("Main Menu Font", new SettingsColorSelection("Main Menu Font", ColorIDs.MAIN_MENU_FONT));
		
		return new SettingsDialog(view.getFrame(), textureSettingsView, colorSettingsView);
	}
}
