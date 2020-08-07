package nuchess.player;

import java.io.Serializable;

public abstract class Player implements Serializable
{
	private static final long serialVersionUID = -7647221331149589179L;
	
	private String username;
	private String userid;
	
	public Player(String username, String userid)
	{
		this.username = username;
		this.userid = userid;
	}
		
	public final String getUsername()
	{
		return username;
	}
	
	public final String getUserID()
	{
		return userid;
	}
	
	public void changeUsername(String newUsername)
	{
		username = newUsername;
	}
}
