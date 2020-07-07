package nuchess.control.player;

public abstract class Computer<Move> extends Player<Move>
{
	private static final long serialVersionUID = -5199764752116960751L;
	
	public Computer(String username, String userid)
	{
		super(username, userid);
	}
	
	@Override
	public final Move selectMove()
	{
		return computeMove();
	}
	
	public abstract Move computeMove();
}
