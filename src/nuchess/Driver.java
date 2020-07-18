package nuchess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.control.BitboardBuilderController;
import nuchess.control.ChessGameController;
import nuchess.control.FENBuilderController;
import nuchess.engine.CBoard;
import nuchess.engine.ChessEngine;
import nuchess.view.ViewFrame;
import nuchess.view.bitboardviewer.BitboardBuilderView;
import nuchess.view.fenbuilder.FENBuilderView;
import nuchess.view.gameview.ChessGameView;
import nuchess.view.graphics.ChessboardGraphics;

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
		switch(args[0])
		{
			case "game":
				loadNewChessGame(view, SQUARE_SIZE);
				break;
			case "fen-builder":
				loadFENBuilder(view, SQUARE_SIZE, FLIPPED);
				break;
			case "bitboard-viewer":
				loadBitboardViewer(view, SQUARE_SIZE, FLIPPED);
				break;
		}
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
	
	private static void loadNewChessGame(ViewFrame view, int squareSize)
	{
		ChessGameView cv = new ChessGameView(squareSize);
		ChessEngine engine = new ChessEngine();
		ChessGameController controller = new ChessGameController(engine, cv);
		controller.init();
		view.display(cv.getPanel());
	}
	
	private static void loadFENBuilder(ViewFrame view, int squareSize, boolean flipped)
	{
		CBoard board = new CBoard();
		FENBuilderView fbv = new FENBuilderView(squareSize, flipped);
		FENBuilderController controller = new FENBuilderController(board, fbv);
		controller.init();
		view.display(controller.getViewPanel());
	}
	
	private static void loadBitboardViewer(ViewFrame view, int squareSize, boolean flipped)
	{
		BitboardBuilderView bbvv = new BitboardBuilderView(squareSize, flipped);
		BitboardBuilderController controller = new BitboardBuilderController(bbvv);
		controller.init();
		view.display(controller.getViewPanel());
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
}
