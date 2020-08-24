package nuchess.driver;

import nuchess.engine.CMove;
import nuchess.engine.Chessboard;
import nuchess.engine.TranspositionTable;
import nuchess.player.computer.algorithm.Computer;
import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.boardeval.BoardFeature;
import nuchess.player.computer.boardeval.RelativeBishopMaterial;
import nuchess.player.computer.boardeval.RelativeKnightMaterial;
import nuchess.player.computer.boardeval.RelativePawnMaterial;
import nuchess.player.computer.boardeval.RelativeQueenMaterial;
import nuchess.player.computer.boardeval.RelativeRookMaterial;
import nuchess.player.computer.moveorder.MoveOrderer;

public class Testing
{
	public static void main(String... args)
	{
		BoardFeature[] features = new BoardFeature[]
		{
			new RelativePawnMaterial(),
			new RelativeKnightMaterial(),
			new RelativeBishopMaterial(),
			new RelativeRookMaterial(),
			new RelativeQueenMaterial(),
		};
		int[] weights = new int[]
		{
			100,
			300,
			300,
			500,
			900,
		};
		
		int ttSize = 10000000;
		int recurseDepth = 11;
		
		String username = "CPU";
		Chessboard board = new Chessboard();
		BoardEvaluator be = new BoardEvaluator(features, weights, features.length);
		MoveOrderer mo = null;
		TranspositionTable tt = new TranspositionTable(ttSize);
		
		Computer computer = new Computer(username, board, be, mo, tt, recurseDepth);
		board.loadFEN("8/8/8/8/4k3/8/8/R3K2R w - - 0 1");
		
		long start, end;
		CMove move;
		
		while(true)
		{
			start = System.currentTimeMillis();
			move = computer.computeMove(board.getFEN());
			end = System.currentTimeMillis();
			
			board.make(move);
			
			System.out.println(board.getFEN());
			System.out.println(move);
			System.out.println(end - start + " ms");
			System.out.println();
		}
	}
}
