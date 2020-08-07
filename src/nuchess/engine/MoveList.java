package nuchess.engine;

public class MoveList
{
	public CMove[] array;
	public int n;
	
	public MoveList(int size)
	{
		array = new CMove[size];
		n = 0;
	}
	
	public MoveList()
	{
		this(256);
	}
	
	public void add(CMove move)
	{
		array[n++] = move;
	}
	
	public boolean isEmpty()
	{
		return n == 0;
	}
}
