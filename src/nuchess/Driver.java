package nuchess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.control.MainController;
import nuchess.engine.Piece;
import nuchess.view.ViewFrame;
import nuchess.view.graphics.ResourceManager;
import nuchess.view.home.HomeView;
import nuchess.view.home.MenuView;
import nuchess.view.settings.SettingPieceSelection;
import nuchess.view.settings.SettingsView;
import nuchess.view.tabs.TabbedView;

public class Driver
{
	public static final String OS = System.getProperty("os.name");
	
	public static void main(String args[])
	{
		bootup();
		ViewFrame view = getNewViewFrame();
//		view.display(constructNewMainController());
		
		SettingsView settingsView = new SettingsView(500, 500, 200, 30, 3, 2);
		settingsView.addSetting("Pawn", new SettingPieceSelection(Piece.PAWN));
		settingsView.addSetting("Knight", new SettingPieceSelection(Piece.KNIGHT));
		settingsView.addSetting("Bishop", new SettingPieceSelection(Piece.BISHOP));
		settingsView.addSetting("Rook", new SettingPieceSelection(Piece.ROOK));
		settingsView.addSetting("Queen", new SettingPieceSelection(Piece.QUEEN));
		settingsView.addSetting("King", new SettingPieceSelection(Piece.KING));
		view.display(settingsView);
		
		view.setVisible(true);
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
