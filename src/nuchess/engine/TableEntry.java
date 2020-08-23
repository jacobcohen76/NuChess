package nuchess.engine;

public class TableEntry
{
	public static final int NULL				= 0b00;
	public static final int PV					= 0b01;
	public static final int CUT 				= 0b10;
	public static final int ALL					= 0b11;
	
	private static final int packWord1(int score, int age)
	{
		return (score << 16) | age;
	}
	
	private static final int packWord2(int draft, int type, int move)
	{
		return (draft << 18) | (type << 16) | move;
	}
	
	public long key;
	public int word1, word2;
	
	public TableEntry(long key, int score, int age, int draft, int type, int move)
	{
		this.key = key;
		word1 = packWord1(score, age);
		word2 = packWord2(draft, type, move);
	}
	
	public TableEntry()
	{
		this(0L, 0, 0, 0, NULL, CMove.NULL);
	}
	
	public void setScore(int score)
	{
		word1 &= 0x0000FFFF;
		word1 |= score << 16;
	}
	
	public void setAge(int age)
	{
		word1 &= 0xFFFF0000;
		word1 |= age;
	}
	
	public void setDraft(int draft)
	{
		word2 &= 0x0003FFFF;
		word2 |= draft << 18;
	}
	
	public void setType(int type)
	{
		word2 &= 0xFFFCFFFF;
		word2 |= type << 16;
	}
	
	public void setMove(int move)
	{
		word2 &= 0xFFFF0000;
		word2 |= move;
	}
	
	public int getScore()
	{
		return word1 >> 16;
	}
	
	public int getAge()
	{
		return word1 & 0xFFFF;
	}
	
	public int getDraft()
	{
		return word2 >>> 18;
	}
	
	public int getType()
	{
		return (word2 >>> 16) & 0x3;
	}
	
	public int getMove()
	{
		return word2 & 0xFFFF;
	}
}
