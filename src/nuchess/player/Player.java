package nuchess.player;

import java.io.Serializable;

public abstract class Player implements Serializable
{
	private static final long serialVersionUID = -7647221331149589179L;
	
	private String username;
	
	public Player(String username)
	{
		this.username = username;
	}
		
	public final String getUsername()
	{
		return username;
	}
	
	public void changeUsername(String newUsername)
	{
		username = newUsername;
	}
}
