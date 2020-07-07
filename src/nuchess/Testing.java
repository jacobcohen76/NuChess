package nuchess;

import nuchess.engine.CMove;
import nuchess.engine.ChessEngine;

public class Testing
{
	public static void main(String args[])
	{
		ChessEngine engine = new ChessEngine();
		engine.loadFEN("6k1/b7/3b4/8/4Q2Q/8/8/K6Q b - - 0 1");
		for(CMove move : engine.generateLegalMoves())
		{
			System.out.println(move + "\t" + engine.getSAN(move));
		}
	}
}
