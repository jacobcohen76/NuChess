package nuchess;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.control.BitboardBuilderController;
import nuchess.control.ChessGameController;
import nuchess.control.Controller;
import nuchess.control.FENBuilderController;
import nuchess.engine.CBoard;
import nuchess.engine.ChessEngine;
import nuchess.view.ViewFrame;
import nuchess.view.bitboardviewer.BitboardBuilderView;
import nuchess.view.fenbuilder.FENBuilderView;
import nuchess.view.gameview.ChessGameView;
import nuchess.view.graphics.ChessboardGraphics;
import nuchess.view.homeview.HomeView;
import nuchess.view.tabs.TabbedView;

public class Driver
{
	public static final String OS = System.getProperty("os.name");
	
	private static final int SQUARE_SIZE = 100;
	private static final int EXTRA_SPACING = 500;
	private static final boolean FLIPPED = false;
	
	private static File saveDirectory = new File("/home/jacob/Desktop");
	
	public static File getCurrentSaveDirectory()
	{
		return saveDirectory;
	}
	
	public static void setCurrentSaveDirectory(File file)
	{
		saveDirectory = file;
	}
	
	public static void main(String args[])
	{
		bootup();
		ViewFrame view = getNewViewFrame();
		loadDefaultView(view, SQUARE_SIZE, FLIPPED);
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
		view.setSize(SQUARE_SIZE * 8 + EXTRA_SPACING, SQUARE_SIZE * 8 + EXTRA_SPACING);
		view.setLocationRelativeTo(null);
		return view;
	}
	
	private static final File RM_PATH = new File("resources/serial/resmanager/resource-manager");
	
	private static void bootup()
	{
		initSystem();
		ChessboardGraphics.initResourceManager(RM_PATH);
	}
	
	private static void shutdown()
	{
		ChessboardGraphics.closeResourceManager(RM_PATH);
		System.exit(0);
	}
	
	private static void initSystem()
	{
		System.out.println(OS);
	}
	
	private static void loadDefaultView(ViewFrame view, int squareSize, boolean flipped)
	{
		TabbedView tv = new TabbedView();
		tv.getPanel().setBackground(Color.RED);
		addHome(tv);
		addNewChessGameTab(tv, squareSize, flipped);
		addNewFENBuilder(tv, squareSize, flipped);
		addNewBitboardBuilder(tv, squareSize, flipped);
		view.display(tv.getPanel());
	}
	
	private static void addHome(TabbedView tv)
	{
		HomeView hv = new HomeView();
		tv.addTab(hv);
	}
	
	private static void addNewChessGameTab(TabbedView tv, int squareSize, boolean flipped)
	{
		ChessGameView cv = new ChessGameView(squareSize, flipped);
		ChessEngine engine = new ChessEngine();
		Controller controller = new ChessGameController(engine, cv);
		controller.init();
		tv.addTab(controller.getView());
	}
	
	private static void addNewFENBuilder(TabbedView tv, int squareSize, boolean flipped)
	{
		CBoard board = new CBoard();
		FENBuilderView fbv = new FENBuilderView(squareSize, flipped);
		Controller controller = new FENBuilderController(board, fbv);
		controller.init();
		tv.addTab(controller.getView());
	}
	
	private static void addNewBitboardBuilder(TabbedView tv, int squareSize, boolean flipped)
	{
		BitboardBuilderView bbvv = new BitboardBuilderView(squareSize, flipped);
		Controller controller = new BitboardBuilderController(bbvv);
		controller.init();
		tv.addTab(controller.getView());
	}
}
