package nuchess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.control.ChessGameController;
import nuchess.engine.ChessEngine;
import nuchess.view.ViewFrame;
import nuchess.view.gameview.ChessGameView;
import nuchess.view.graphics.ChessboardGraphics;


public class Driver
{	
	public static void main(String args[])
	{
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
		
		view.setSize(800, 800);
		view.setLocationRelativeTo(null);
		
		ChessGameView cv = new ChessGameView(64);
		
		ChessEngine engine = new ChessEngine();
		ChessGameController controller = new ChessGameController(engine, cv);
		
		controller.start();
		view.display(cv.getPanel());
		view.setVisible(true);
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
