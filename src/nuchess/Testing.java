package nuchess;

import nuchess.engine.FENParser;

public class Testing
{
	public static void main(String args[])
	{
		String crString = "Kqk";
		System.out.println(FENParser.isCastlingRights(crString));
	}
}
