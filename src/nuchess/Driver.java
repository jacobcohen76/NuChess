package nuchess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.control.MainController;
import nuchess.engine.Piece;
import nuchess.view.ViewFrame;
import nuchess.view.graphics.ColorIDs;
import nuchess.view.graphics.ResourceManager;
import nuchess.view.graphics.TextureIDs;
import nuchess.view.home.HomeView;
import nuchess.view.home.MenuView;
import nuchess.view.settings.SettingTextureSelection;
import nuchess.view.settings.SettingsColorSelection;
import nuchess.view.settings.SettingsView;
import nuchess.view.tabs.TabbedView;

public class Driver
{
	public static final String OS = System.getProperty("os.name");
	
	public static void main(String args[])
	{
		bootup();
		ViewFrame view = getNewViewFrame();
		view.display(constructNewMainController());
		view.setVisible(true);
		
//		SettingsView settingsView = new SettingsView("Textures", 500, 900, 200, 30, 3, 2);
//		
//		settingsView.addSetting("NULL", new SettingTextureSelection(TextureIDs.NULL));
//		settingsView.addSetting("Dot", new SettingTextureSelection(TextureIDs.DOT));
//		settingsView.addSetting("White Pawn", new SettingTextureSelection(TextureIDs.WHITE_PAWN));
//		settingsView.addSetting("White Knight", new SettingTextureSelection(TextureIDs.WHITE_KNIGHT));
//		settingsView.addSetting("White Bishop", new SettingTextureSelection(TextureIDs.WHITE_BISHOP));
//		settingsView.addSetting("White Rook", new SettingTextureSelection(TextureIDs.WHITE_ROOK));
//		settingsView.addSetting("White Queen", new SettingTextureSelection(TextureIDs.WHITE_QUEEN));
//		settingsView.addSetting("White King", new SettingTextureSelection(TextureIDs.WHITE_KING));
//		settingsView.addSetting("Black Pawn", new SettingTextureSelection(TextureIDs.BLACK_PAWN));
//		settingsView.addSetting("Black Knight", new SettingTextureSelection(TextureIDs.BLACK_KNIGHT));
//		settingsView.addSetting("Black Bishop", new SettingTextureSelection(TextureIDs.BLACK_BISHOP));
//		settingsView.addSetting("Black Rook", new SettingTextureSelection(TextureIDs.BLACK_ROOK));
//		settingsView.addSetting("Black Queen", new SettingTextureSelection(TextureIDs.BLACK_QUEEN));
//		settingsView.addSetting("Black King", new SettingTextureSelection(TextureIDs.BLACK_KING));
//		settingsView.addSetting("Border", new SettingTextureSelection(TextureIDs.BORDER));
//		settingsView.addSetting("Highlight", new SettingTextureSelection(TextureIDs.HIGHLIGHT));
//		settingsView.addSetting("Mask", new SettingTextureSelection(TextureIDs.MASK));
//		settingsView.addSetting("Dark Square", new SettingTextureSelection(TextureIDs.DARK_SQUARE));
//		settingsView.addSetting("Light Square", new SettingTextureSelection(TextureIDs.LIGHT_SQUARE));
//		
//		settingsView.addSetting("Tab", new SettingsColorSelection("Tab", ColorIDs.TAB));
//		settingsView.addSetting("Hovering Tab", new SettingsColorSelection("Hovering Tab", ColorIDs.HOVERING_TAB));
//		settingsView.addSetting("Selected Tab", new SettingsColorSelection("Selected Tab", ColorIDs.SELECTED_TAB));
//		settingsView.addSetting("Tab Font", new SettingsColorSelection("Tab Font", ColorIDs.TAB_FONT));
//		settingsView.addSetting("Main Menu Background", new SettingsColorSelection("Main Menu Background", ColorIDs.MAIN_MENU_BACKGROUND));
//		settingsView.addSetting("Main Menu Font", new SettingsColorSelection("Main Menu Font", ColorIDs.MAIN_MENU_FONT));
//		
////		settingsView.finalize();
//		
//		view.display(settingsView);
//		view.setVisible(true);
	}
	
	private static ViewFrame getNewViewFrame()
	{
		ViewFrame view = new ViewFrame();
		view.setWindowListener(new WindowListener()
		{
			public void windowOpened(WindowEvent e)			{}
			public void windowClosing(WindowEvent e) 		{ shutdown(); }
			public void windowClosed(WindowEvent e)			{}
			public void windowIconified(WindowEvent e)		{}
			public void windowDeiconified(WindowEvent e)	{}
			public void windowActivated(WindowEvent e)		{}
			public void windowDeactivated(WindowEvent e)	{}
		});
		view.setSize(1000, 1000);
		view.setLocationRelativeTo(null);
		return view;
	}
	
	private static final File RM_PATH = new File("resources/serial/resmanager/resource-manager");
	
	private static void bootup()
	{
		initSystem();
		ResourceManager.initManagerObject(RM_PATH);
	}
	
	private static void shutdown()
	{
		ResourceManager.closeManagerObject(RM_PATH);
		System.exit(0);
	}
	
	private static void initSystem()
	{
		System.setProperty("sun.java2d.opengl","True");
	}
	
	private static MainController constructNewMainController()
	{
		MenuView menuView = new MenuView();
		TabbedView tabbedView = new TabbedView();
		HomeView homeView = new HomeView();
		MainController controller = new MainController(menuView, tabbedView, homeView);
		controller.init();
		return controller;
	}
}
