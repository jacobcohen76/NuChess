package nuchess.control.player;

public class Human<Move> extends Player<Move>
{
	private static final long serialVersionUID = 7516009368046585466L;

	public Human(String username, String userid)
	{
		super(username, userid);
	}

	@Override
	public Move selectMove()
	{
		return null;
	}
}
