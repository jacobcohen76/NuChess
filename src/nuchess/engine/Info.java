package nuchess.engine;

public class Info
{
	public static int pack(int halfmoveClock, int epTarget, int castlingRights)
	{
		return	halfmoveClock | epTarget << 7 | castlingRights << 14;
	}
	
	public static int setCaptured(int info, int piece)
	{
		return info | (piece << 18);
	}
	
	public static int halfmoveClock(int info)
	{
		return info & 0x7F;
	}
	
	public static int epTarget(int info)
	{
		return (info >> 7) & 0x3F;
	}
	
	public static int castlingRights(int info)
	{
		return (info >> 14) & 0xF;
	}
	
	public static int captured(int info)
	{
		return (info >> 18) & 0xF;
	}
}
