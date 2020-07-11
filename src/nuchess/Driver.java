package nuchess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.control.ChessGameController;
import nuchess.control.FENBuilderController;
import nuchess.engine.CBoard;
import nuchess.engine.ChessEngine;
import nuchess.view.ViewFrame;
import nuchess.view.fenbuilder.FENBuilderView;
import nuchess.view.gameview.ChessGameView;
import nuchess.view.graphics.ChessboardGraphics;

public class Driver
{	
	public static void main(String args[])
	{
		if(args.length <= 0)
		{
			return;
		}
		
		bootup();
		
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
		
		int squareSize = 100;
		int extraSpacing = 500;
		boolean flipped = false;
		
		view.setSize(squareSize * 8 + extraSpacing, squareSize * 8 + extraSpacing);
		view.setLocationRelativeTo(null);
		
		switch(args[0])
		{
			case "game":
				loadNewChessGame(view, squareSize);
				break;
			case "fen-builder":
				loadFENBuilder(view, squareSize, flipped);
				break;
		}
		
		view.setVisible(true);
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
//		System.setProperty("sun.java2d.opengl", "true");
	}
}
