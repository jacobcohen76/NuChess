package nuchess.driver;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import nuchess.graphics.ResourceManager;
import nuchess.ui.ViewFrame;
import nuchess.ui.root.control.RootControl;
import nuchess.ui.root.view.HomeView;
import nuchess.ui.root.view.RootView;

public class Driver
{
	public static final String OS = System.getProperty("os.name");
	
	public static void main(String... args)
	{
		bootup();
		ViewFrame frame = getNewViewFrame();
		frame.display(constructNewRootController());
		frame.setVisible(true);
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
	
	private static RootControl constructNewRootController()
	{
		RootView rootView = new RootView();
		HomeView homeView = new HomeView();
		RootControl control = new RootControl(rootView, homeView);
		control.init();
		return control;
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
}
