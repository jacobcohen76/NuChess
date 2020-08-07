package nuchess.engine;

public final class Square
{
	public static final char fileCharacter(int file)
	{
		switch(file)
		{
			case file_a:	return 'a';
			case file_b:	return 'b';
			case file_c:	return 'c';
			case file_d:	return 'd';
			case file_e:	return 'e';
			case file_f:	return 'f';
			case file_g:	return 'g';
			case file_h:	return 'h';
			default:		return '?';
		}
	}
	
	public static final char rankCharacter(int rank)
	{
		return (char) ('1' + rank);
	}
	
	public static final String coord(int square)
	{
		return "" + (char) ('a' + file(square)) + (rank(square) + 1);
	}
	
	public static final int makeSquare(int rank, int file)
	{
		return (rank << 3) + file;
	}
	
	public static final int makeSquare(String coord)
	{
		return makeSquare(coord.charAt(1) - '1', coord.charAt(0) - 'a');
	}
	
	public static final int rank(int square)
	{
		return square >> 3;
	}
	
	public static final int file(int square)
	{
		return square & 7;
	}
	
	public static final int diag(int rank, int file)
	{
		return rank - file + 7;
	}
	
	public static final int diag(int square)
	{
		return diag(rank(square), file(square));
	}
	
	public static final int anti(int rank, int file)
	{
		return rank + file;
	}
	
	public static final int anti(int square)
	{
		return anti(rank(square), file(square));
	}
	
	public static final long bitboard(int square)
	{
		return 1L << square;
	}
	
	public static final int
		a1 =  0, b1 =  1, c1 =  2, d1 =  3, e1 =  4, f1 =  5, g1 =  6, h1 =  7,
		a2 =  8, b2 =  9, c2 = 10, d2 = 11, e2 = 12, f2 = 13, g2 = 14, h2 = 15,
		a3 = 16, b3 = 17, c3 = 18, d3 = 19, e3 = 20, f3 = 21, g3 = 22, h3 = 23,
		a4 = 24, b4 = 25, c4 = 26, d4 = 27, e4 = 28, f4 = 29, g4 = 30, h4 = 31,
		a5 = 32, b5 = 33, c5 = 34, d5 = 35, e5 = 36, f5 = 37, g5 = 38, h5 = 39,
		a6 = 40, b6 = 41, c6 = 42, d6 = 43, e6 = 44, f6 = 45, g6 = 46, h6 = 47,
		a7 = 48, b7 = 49, c7 = 50, d7 = 51, e7 = 52, f7 = 53, g7 = 54, h7 = 55,
		a8 = 56, b8 = 57, c8 = 58, d8 = 59, e8 = 60, f8 = 61, g8 = 62, h8 = 63,
		NULL = 64;
	
	public static final int
		rank_1 = 0, rank_2 = 1, rank_3 = 2, rank_4 = 3, rank_5 = 4, rank_6 = 5, rank_7 = 6, rank_8 = 7;
	
	public static final int
		file_a = 0, file_b = 1, file_c = 2, file_d = 3, file_e = 4, file_f = 5, file_g = 6, file_h = 7;
}
