package nuchess.engine;

public class TranspositionTable
{
	private TableEntry[] entries;
	
	public TranspositionTable(int size)
	{
		entries = new TableEntry[size];
		for(int i = 0; i < entries.length; i++)
		{
			entries[i] = new TableEntry();
		}
	}
	
	public void put(long key, int score, int age, int draft, int type, int move)
	{
		TableEntry entry = get(key);
		entry.setScore(score);
		entry.setAge(age);
		entry.setDraft(draft);
		entry.setType(type);
		entry.setMove(move);
	}
	
	public TableEntry get(long key)
	{
		return entries[(int) (key % entries.length)];
	}
}
