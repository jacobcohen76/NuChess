package nuchess.engine;

import java.util.Random;

public class Zobrist
{
	private static final long SEED = 0L;
	private static final long NULL_KEY = 0L;
	
	public static final long[][] PIECE;
	public static final long[] ENPASSANT;
	public static final long[] CASTLE;
	public static final long BLACK;
	
	static
	{
		Random rand = new Random(SEED);
		fillRandomNumbers(PIECE = new long[14][64], rand);
		fillRandomNumbers(ENPASSANT = new long[8], rand);
		fillRandomNumbers(CASTLE = new long[16], rand);
		BLACK = rand.nextLong();
	}
	
	private static void fillRandomNumbers(long[][] numbers, Random rand)
	{
		for(int i = 0; i < numbers.length; i++)
		{
			for(int j = 0; j < numbers[i].length; j++)
			{
				numbers[i][j] = rand.nextLong();
			}
		}
	}
	
	private static void fillRandomNumbers(long[] numbers, Random rand)
	{
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = rand.nextLong();
		}
	}
	
	/**
	 * Gets the zobrist-key for the given position. Will throw an array-index-out-of-bounds
	 * exception if castlingRights or epSquare is not within its range.
	 * 
	 * @param board the chess-board with information regarding all the pieces and their positions.
	 * @param castlingRights the 4-bit boolean array containing castling rights. [0, 15]
	 * @param epSquare the square to move to inorder to perform an ep-capture. [0, 64]
	 * @param toMove the Color whose turn it is to move. [0, 1]
	 * 
	 * @return the zobrist key for the given position
	 */
	public static long getKey(CBoard board, int castlingRights, int epSquare, int toMove)
	{
		long key = NULL_KEY, occ = board.occ();
		while(occ != 0)
		{
			int square = Bits.bitscanForward(occ);
			key ^= PIECE[board.pieceAt(square)][square];
			occ &= occ ^ -occ;
		}
		while(castlingRights != 0)
		{
			int bit = Bits.bitscanForward(castlingRights);
			key ^= CASTLE[bit];
			castlingRights &= castlingRights ^ -castlingRights;
		}
		if(epSquare != Square.NULL)
		{
			key ^= ENPASSANT[Square.file(epSquare)];
		}
		if(toMove == Color.BLACK)
		{
			key ^= BLACK;
		}
		return key;
	}
}
