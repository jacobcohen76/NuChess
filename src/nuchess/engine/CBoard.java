package nuchess.engine;

public final class CBoard
{
	private long[] bitboards;
	
	public CBoard(long P, long N, long B, long R, long Q, long K, long p, long n, long b, long r, long q, long k)
	{
		bitboards = new long[15];
		
		bitboards[Color.WHITE] = P | N | B | R | Q | K;
		bitboards[Color.BLACK] = p | n | b | r | q | k;
		
		bitboards[Piece.WHITE_PAWN]		= P;
		bitboards[Piece.WHITE_KNIGHT]	= N;
		bitboards[Piece.WHITE_BISHOP]	= B;
		bitboards[Piece.WHITE_ROOK]		= R;
		bitboards[Piece.WHITE_QUEEN]	= Q;
		bitboards[Piece.WHITE_KING]		= K;
		
		bitboards[Piece.BLACK_PAWN]		= p;
		bitboards[Piece.BLACK_KNIGHT]	= n;
		bitboards[Piece.BLACK_BISHOP]	= b;
		bitboards[Piece.BLACK_ROOK]		= r;
		bitboards[Piece.BLACK_QUEEN]	= q;
		bitboards[Piece.BLACK_KING]		= k;
		
		bitboards[OCC] = bitboards[Color.WHITE] | bitboards[Color.BLACK];
	}
	
	public CBoard()
	{
		this
		(
				0x000000000000FF00L, 0x0000000000000042L, 0x0000000000000024L, 0x0000000000000081L, 0x0000000000000008L, 0x0000000000000010L,
				0x00FF000000000000L, 0x4200000000000000L, 0x2400000000000000L, 0x8100000000000000L, 0x0800000000000000L, 0x1000000000000000L 
		);
	}
	
	public final void put(int piece, int square)
	{
		bitboards[piece] |= 1L << square;
		bitboards[piece & 1] |= 1L << square;
		bitboards[OCC] |= 1L << square;
	}
	
	public final void capture(int piece, int square)
	{
		bitboards[piece] &= ~(1L << square);
		bitboards[piece & 1] &= ~(1L << square);
		bitboards[OCC] &= ~(1L << square);
	}
	
	public final void promote(int promoted, int promotedTo, int square)
	{
		bitboards[promoted] &= ~(1L << square);
		bitboards[promotedTo] |= 1L << square;
	}
	
	public final void move(int piece, int from, int to)
	{
		bitboards[piece] &= ~(1L << from);
		bitboards[piece & 1] &= ~(1L << from);
		bitboards[OCC] &= ~(1L << from);
		
		bitboards[piece] |= 1L << to;
		bitboards[piece & 1] |= 1L << to;
		bitboards[OCC] |= 1L << to;
	}
	
	public final void clearBitboards()
	{
		for(int bb = 0; bb < 15; bb++)
		{
			bitboards[bb] = 0L;
		}
	}
	
	public final long bitboard(int bb)
	{
		return bitboards[bb];
	}
	
	public final long occ()
	{
		return bitboards[OCC];
	}
	
	public final int pieceAt(int square)
	{
		for(int bb = Piece.WHITE_PAWN; bb <= Piece.BLACK_KING; bb++)
		{
			if(((bitboards[bb] >> square) & 1) == 1)
			{
				return bb;
			}
		}
		return Piece.NULL;
	}
	
	/**
	 * Returns the color of the piece at the square.
	 * Assumes that the square is not empty, if the square
	 * is empty this function will always return BLACK.
	 * 
	 * @param square the square to check which color the piece on it is.
	 * @return the color of the piece at the specified square
	 */
	public final int colorAt(int square)
	{
		return isWhite(square) ? Color.WHITE : Color.BLACK;
	}
	
	public final int kingSquare(int color)
	{
		return Long.numberOfTrailingZeros(bitboards[Piece.WHITE_KING + color]);
	}
	
	public final boolean isEmpty(int square)
	{
		return ((bitboards[OCC] >> square) & 1) == 0;
	}
	
	public final boolean isWhite(int square)
	{
		return ((bitboards[Color.WHITE] >> square) & 1) == 1;
	}
	
	public final boolean isBlack(int square)
	{
		return ((bitboards[Color.BLACK] >> square) & 1) == 1;
	}
	
	public final boolean contains(int pieceCode, int square)
	{
		return ((bitboards[pieceCode] >> square) & 1) == 1;
	}
	
	public final long occAfter(CMove move)
	{
		return	move.flags() == CMove.EP_CAPTURE ?
					((occ() & ~(1L << move.from())) & ~(1L << move.epCaptureTarget()) | (1L << move.to())) :
					((occ() & ~(1L << move.from())) | (1L << move.to()));
	}
	
	public final long attacksTo(int square, int by)
	{
		return attacksTo(square, by, occ());
	}
	
	public final long attacksTo(int square, int by, long occ)
	{
		return	Attacks.pawnAttacks   (square, by ^ 1) & bitboards[Piece.WHITE_PAWN   + by] |
				Attacks.knightAttacks (square)         & bitboards[Piece.WHITE_KNIGHT + by] |
				Attacks.bishopAttacks (square, occ)    & bitboards[Piece.WHITE_BISHOP + by] |
				Attacks.rookAttacks   (square, occ)    & bitboards[Piece.WHITE_ROOK   + by] |
				Attacks.queenAttacks  (square, occ)    & bitboards[Piece.WHITE_QUEEN  + by] |
				Attacks.kingAttacks   (square)         & bitboards[Piece.WHITE_KING   + by];
	}
	
	public final boolean isAttacked(int square, int by)
	{
		return isAttacked(square, by, occ());
	}
	
	public final boolean isAttacked(int square, int by, long occ)
	{
		return	(Attacks.pawnAttacks   (square, by ^ 1) & bitboards[Piece.WHITE_PAWN   + by]) != 0 ||
				(Attacks.knightAttacks (square)         & bitboards[Piece.WHITE_KNIGHT + by]) != 0 ||
				(Attacks.bishopAttacks (square, occ)    & bitboards[Piece.WHITE_BISHOP + by]) != 0 ||
				(Attacks.rookAttacks   (square, occ)    & bitboards[Piece.WHITE_ROOK   + by]) != 0 ||
				(Attacks.queenAttacks  (square, occ)    & bitboards[Piece.WHITE_QUEEN  + by]) != 0 ||
				(Attacks.kingAttacks   (square)         & bitboards[Piece.WHITE_KING   + by]) != 0;
	}
	
	public static final int OCC = Piece.BLACK_KING + 1;
}
