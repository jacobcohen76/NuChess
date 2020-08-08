package nuchess.driver;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;

import nuchess.engine.CMove;
//import nuchess.engine.ChessEngine;
import nuchess.engine.Chessboard;
import nuchess.engine.MoveList;

public class Testing
{
	public static final long n = Math.round(Math.pow(10.0, 9.0));
	public static final long u64 = 0xFFFFFFFFFFFFFFFFL;
	public static final HashMap<String, Long> moveCountMap = new HashMap<String, Long>();
	
	public static void main(String args[])
	{
//		board = new Chessboard("r3k2r/p1p1qpb1/bn1ppnp1/1B1PN3/1p2P3/P1N2Q1p/1PPB1PPP/R3K2R b KQkq - 1 2");
//		
//		CMove move = board.generateLegalMoves().iterator().next();
//		System.out.println(board.inCheck());
//		board.make(move);
//		System.out.println(board.inCheck());
//		board.unmake(move);
//		System.out.println(board.inCheck());
//		
//		board = new Chessboard("r3k2r/p1p1qpb1/bn1ppnp1/3PN3/1p2P3/P1N2Q1p/1PPB1PPP/R3KB1R w KQkq - 1 2");
//		System.out.println(board.inCheck());
//		System.out.println(board.getASCIIboard(Piece.WHITE_BISHOP));
//		board.make(new CMove(Square.d2, Square.b5, CMove.QUIET));
//		System.out.println(board.inCheck());
//		System.out.println(board.toMove + " = toMove");
//		System.out.println(board.getASCIIboard(Piece.WHITE_BISHOP));
//		System.out.println(board.attacksTo(board.king[board.toMove], board.toMove ^ 1, board.bitboards[Chessboard.OCC]));
//		System.out.println(board.generateLegalMoves());
		
		runPerft("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 6);
	}
	
	private static Chessboard board;
//	private static ChessEngine engine;
//	private static long TOTAL_NODE_COUNT;
	
	private static void runPerft(String FEN, int depth)
	{
		long start, end;
		
//		TOTAL_NODE_COUNT= 0;
		board = new Chessboard(FEN);
//		engine = new ChessEngine(FEN);
		
//		System.out.println("start");
//		System.out.println(engine.getFEN());
//		System.out.println(board.getFEN());
//		System.out.println();
		
		MathContext mc100 = new MathContext(100);
		MathContext mc8 = new MathContext(8);

		start = System.nanoTime();
		long perftResults = perft(depth);
		end = System.nanoTime();
		
		BigDecimal nodes = new BigDecimal(perftResults, mc100);
		BigDecimal billion = new BigDecimal(1000000000L, mc100);
		BigDecimal time = new BigDecimal(end - start, mc100);
		
		System.out.println(time.divide(billion, mc100) + " s");
		System.out.println(nodes.multiply(billion).divide(time, mc8) + " nodes / sec");
		System.out.println(nodes);
	}
	
	private static long perft(int depth)
	{
//		TOTAL_NODE_COUNT++;
		if(depth <= 0)
		{
			return 1L;
		}
		else
		{
			long nodes = 0L;
//			List<CMove> engineMoves = engine.generateLegalMoves();
			MoveList moves = board.generateMoves();
//			if(engineMoves.size() > moves.size())
//			{
//				Comparator<CMove> comparator = new Comparator<CMove>()
//				{
//					@Override
//					public int compare(CMove o1, CMove o2)
//					{
//						return Integer.compare(o1.hashCode(), o2.hashCode());
//					}
//				};
//				engineMoves.sort(comparator);
//				moves.sort(comparator);
//				System.out.println(engineMoves);
//				System.out.println(moves);
//				engineMoves.removeAll(moves);
//				System.out.println(engineMoves);
//				System.out.println(board.getASCIIboard());
//				System.out.println("board in check  = " + board.inCheck());
//				System.out.println("engine in check = " + engine.inCheck());
//				System.out.println();
//				System.out.println(depth);
//				System.out.println(engine.getFEN());
//				System.out.println(board.getFEN());
//				System.exit(0);
//			}
//			else if(engineMoves.size() < moves.size())
//			{
//				Comparator<CMove> comparator = new Comparator<CMove>()
//				{
//					@Override
//					public int compare(CMove o1, CMove o2)
//					{
//						return Integer.compare(o1.hashCode(), o2.hashCode());
//					}
//				};
//				engineMoves.sort(comparator);
//				moves.sort(comparator);
//				System.out.println(engineMoves);
//				System.out.println(moves);
//				moves.removeAll(engineMoves);
//				System.out.println(moves);
//				System.out.println(board.getASCIIboard());
//				System.out.println("board in check  = " + board.inCheck());
//				System.out.println("engine in check = " + engine.inCheck());
//				System.out.println();
//				System.out.println(depth);
//				System.out.println(engine.getFEN());
//				System.out.println(board.getFEN());
//				System.exit(0);
//			}
			for(int i = 0; i < moves.n; i++)
			{
//				engine.make(move);
				if(board.canMake(moves.array[i]))
				{
					board.make(moves.array[i]);
					nodes += perft(depth - 1);
					board.unmake(moves.array[i]);
				}
//				engine.unmake(move);
			}
			return nodes;
		}
	}
}
