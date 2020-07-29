package nuchess.control.copy;

import nuchess.view.View;
import nuchess.view.settings.SettingsView;

public class SettingsController implements Controller
{
	private SettingsView view;
	
	public SettingsController(SettingsView view)
	{
		this.view = view;
		linkObjects();
	}
	
	private void linkObjects()
	{
		view.controller = this;
	}
	
	public void init()
	{
		
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
		return view;
	}
}
