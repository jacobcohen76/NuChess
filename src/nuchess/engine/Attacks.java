package nuchess.engine;

/**
 * 
 * @author Jacob Cohen
 *
 */
public final class Attacks
{
	public static final long pawnSinglePush(int square, int toMove)
	{
		return PAWN_SINGLE_PUSH[toMove][square];
	}
	
	public static final long pawnDoublePush(int square, int toMove)
	{
		return PAWN_DOUBLE_PUSH[toMove][square];
	}
	
	public static final long pawnAttacks(int square, int toMove)
	{
		return PAWN_ATTACKS[toMove][square];
	}
	
	public static final long knightAttacks(int square)
	{
		return KNIGHT_ATTACKS[square];
	}
	
	public static final long bishopAttacks(int square, long blockers)
	{
		return MagicBitboard.bishopAttacks(square, blockers);
	}
	
	public static final long rookAttacks(int square, long blockers)
	{
		return MagicBitboard.rookAttacks(square, blockers);
	}
	
	public static final long queenAttacks(int square, long blockers)
	{
		return bishopAttacks(square, blockers) | rookAttacks(square, blockers);
	}
	
	public static final long kingAttacks(int square)
	{
		return KING_ATTACKS[square];
	}
	
	public static final long nonSlidingAttacks(int pieceType, int square)
	{
		switch(pieceType)
		{
			case Piece.WHITE_PAWN:									return pawnAttacks(square, Color.WHITE);
			case Piece.BLACK_PAWN:									return pawnAttacks(square, Color.BLACK);
			case Piece.WHITE_KNIGHT:	case Piece.BLACK_KNIGHT:	return knightAttacks(square);
			case Piece.WHITE_KING:		case Piece.BLACK_KING:		return kingAttacks(square);
			default:												return 0L;
		}
	}
	
	public static final long slidingAttacks(int pieceType, int square, long blockers)
	{
		switch(pieceType)
		{
			case Piece.WHITE_ROOK:		case Piece.BLACK_ROOK:		return rookAttacks(square, blockers);
			case Piece.WHITE_BISHOP:	case Piece.BLACK_BISHOP:	return bishopAttacks(square, blockers);
			case Piece.WHITE_QUEEN:		case Piece.BLACK_QUEEN:		return queenAttacks(square, blockers);
			default:												return 0L;
		}
	}
	
	public static final long attacks(int pieceType, int square, long blockers)
	{
		switch(pieceType)
		{
			case Piece.WHITE_PAWN:									return pawnAttacks(square, Color.WHITE);
			case Piece.BLACK_PAWN:									return pawnAttacks(square, Color.BLACK);
			case Piece.WHITE_KNIGHT:	case Piece.BLACK_KNIGHT:	return knightAttacks(square);
			case Piece.WHITE_KING:		case Piece.BLACK_KING:		return kingAttacks(square);
			case Piece.WHITE_ROOK:		case Piece.BLACK_ROOK:		return rookAttacks(square, blockers);
			case Piece.WHITE_BISHOP:	case Piece.BLACK_BISHOP:	return bishopAttacks(square, blockers);
			case Piece.WHITE_QUEEN:		case Piece.BLACK_QUEEN:		return queenAttacks(square, blockers);
			default:												return 0L;
		}
	}
	
	private static final long[][] PAWN_ATTACKS = new long[][]
	{
		//WHITE
		{
			0x0000000000000200L, 0x0000000000000500L, 0x0000000000000A00L, 0x0000000000001400L, 0x0000000000002800L, 0x0000000000005000L, 0x000000000000A000L, 0x0000000000004000L,
			0x0000000000020000L, 0x0000000000050000L, 0x00000000000A0000L, 0x0000000000140000L, 0x0000000000280000L, 0x0000000000500000L, 0x0000000000A00000L, 0x0000000000400000L,
			0x0000000002000000L, 0x0000000005000000L, 0x000000000A000000L, 0x0000000014000000L, 0x0000000028000000L, 0x0000000050000000L, 0x00000000A0000000L, 0x0000000040000000L,
			0x0000000200000000L, 0x0000000500000000L, 0x0000000A00000000L, 0x0000001400000000L, 0x0000002800000000L, 0x0000005000000000L, 0x000000A000000000L, 0x0000004000000000L,
			0x0000020000000000L, 0x0000050000000000L, 0x00000A0000000000L, 0x0000140000000000L, 0x0000280000000000L, 0x0000500000000000L, 0x0000A00000000000L, 0x0000400000000000L,
			0x0002000000000000L, 0x0005000000000000L, 0x000A000000000000L, 0x0014000000000000L, 0x0028000000000000L, 0x0050000000000000L, 0x00A0000000000000L, 0x0040000000000000L,
			0x0200000000000000L, 0x0500000000000000L, 0x0A00000000000000L, 0x1400000000000000L, 0x2800000000000000L, 0x5000000000000000L, 0xA000000000000000L, 0x4000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
		},
		
		//BLACK
		{
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000002L, 0x0000000000000005L, 0x000000000000000AL, 0x0000000000000014L, 0x0000000000000028L, 0x0000000000000050L, 0x00000000000000A0L, 0x0000000000000040L,
			0x0000000000000200L, 0x0000000000000500L, 0x0000000000000A00L, 0x0000000000001400L, 0x0000000000002800L, 0x0000000000005000L, 0x000000000000A000L, 0x0000000000004000L,
			0x0000000000020000L, 0x0000000000050000L, 0x00000000000A0000L, 0x0000000000140000L, 0x0000000000280000L, 0x0000000000500000L, 0x0000000000A00000L, 0x0000000000400000L,
			0x0000000002000000L, 0x0000000005000000L, 0x000000000A000000L, 0x0000000014000000L, 0x0000000028000000L, 0x0000000050000000L, 0x00000000A0000000L, 0x0000000040000000L,
			0x0000000200000000L, 0x0000000500000000L, 0x0000000A00000000L, 0x0000001400000000L, 0x0000002800000000L, 0x0000005000000000L, 0x000000A000000000L, 0x0000004000000000L,
			0x0000020000000000L, 0x0000050000000000L, 0x00000A0000000000L, 0x0000140000000000L, 0x0000280000000000L, 0x0000500000000000L, 0x0000A00000000000L, 0x0000400000000000L,
			0x0002000000000000L, 0x0005000000000000L, 0x000A000000000000L, 0x0014000000000000L, 0x0028000000000000L, 0x0050000000000000L, 0x00A0000000000000L, 0x0040000000000000L,
		},
	};
	
	private static final long[][] PAWN_SINGLE_PUSH = new long[][]
	{
		//WHITE
		{
			0x0000000000000100L, 0x0000000000000200L, 0x0000000000000400L, 0x0000000000000800L, 0x0000000000001000L, 0x0000000000002000L, 0x0000000000004000L, 0x0000000000008000L,
			0x0000000000010000L, 0x0000000000020000L, 0x0000000000040000L, 0x0000000000080000L, 0x0000000000100000L, 0x0000000000200000L, 0x0000000000400000L, 0x0000000000800000L,
			0x0000000001000000L, 0x0000000002000000L, 0x0000000004000000L, 0x0000000008000000L, 0x0000000010000000L, 0x0000000020000000L, 0x0000000040000000L, 0x0000000080000000L,
			0x0000000100000000L, 0x0000000200000000L, 0x0000000400000000L, 0x0000000800000000L, 0x0000001000000000L, 0x0000002000000000L, 0x0000004000000000L, 0x0000008000000000L,
			0x0000010000000000L, 0x0000020000000000L, 0x0000040000000000L, 0x0000080000000000L, 0x0000100000000000L, 0x0000200000000000L, 0x0000400000000000L, 0x0000800000000000L,
			0x0001000000000000L, 0x0002000000000000L, 0x0004000000000000L, 0x0008000000000000L, 0x0010000000000000L, 0x0020000000000000L, 0x0040000000000000L, 0x0080000000000000L,
			0x0100000000000000L, 0x0200000000000000L, 0x0400000000000000L, 0x0800000000000000L, 0x1000000000000000L, 0x2000000000000000L, 0x4000000000000000L, 0x8000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
		},
		
		//BLACK
		{
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000001L, 0x0000000000000002L, 0x0000000000000004L, 0x0000000000000008L, 0x0000000000000010L, 0x0000000000000020L, 0x0000000000000040L, 0x0000000000000080L,
			0x0000000000000100L, 0x0000000000000200L, 0x0000000000000400L, 0x0000000000000800L, 0x0000000000001000L, 0x0000000000002000L, 0x0000000000004000L, 0x0000000000008000L,
			0x0000000000010000L, 0x0000000000020000L, 0x0000000000040000L, 0x0000000000080000L, 0x0000000000100000L, 0x0000000000200000L, 0x0000000000400000L, 0x0000000000800000L,
			0x0000000001000000L, 0x0000000002000000L, 0x0000000004000000L, 0x0000000008000000L, 0x0000000010000000L, 0x0000000020000000L, 0x0000000040000000L, 0x0000000080000000L,
			0x0000000100000000L, 0x0000000200000000L, 0x0000000400000000L, 0x0000000800000000L, 0x0000001000000000L, 0x0000002000000000L, 0x0000004000000000L, 0x0000008000000000L,
			0x0000010000000000L, 0x0000020000000000L, 0x0000040000000000L, 0x0000080000000000L, 0x0000100000000000L, 0x0000200000000000L, 0x0000400000000000L, 0x0000800000000000L,
			0x0001000000000000L, 0x0002000000000000L, 0x0004000000000000L, 0x0008000000000000L, 0x0010000000000000L, 0x0020000000000000L, 0x0040000000000000L, 0x0080000000000000L,
		},
	};
	
	private static final long[][] PAWN_DOUBLE_PUSH = new long[][]
	{
		//WHITE
		{
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000001000000L, 0x0000000002000000L, 0x0000000004000000L, 0x0000000008000000L, 0x0000000010000000L, 0x0000000020000000L, 0x0000000040000000L, 0x0000000080000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
		},
		
		//BLACK
		{
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
			0x0000000100000000L, 0x0000000200000000L, 0x0000000400000000L, 0x0000000800000000L, 0x0000001000000000L, 0x0000002000000000L, 0x0000004000000000L, 0x0000008000000000L,
			0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L, 0x0000000000000000L,
		},
	};
	
	private static final long[] KNIGHT_ATTACKS = new long[]
	{
		0x0000000000020400L, 0x0000000000050800L, 0x00000000000A1100L, 0x0000000000142200L, 0x0000000000284400L, 0x0000000000508800L, 0x0000000000A01000L, 0x0000000000402000L,
		0x0000000002040004L, 0x0000000005080008L, 0x000000000A110011L, 0x0000000014220022L, 0x0000000028440044L, 0x0000000050880088L, 0x00000000A0100010L, 0x0000000040200020L,
		0x0000000204000402L, 0x0000000508000805L, 0x0000000A1100110AL, 0x0000001422002214L, 0x0000002844004428L, 0x0000005088008850L, 0x000000A0100010A0L, 0x0000004020002040L,
		0x0000020400040200L, 0x0000050800080500L, 0x00000A1100110A00L, 0x0000142200221400L, 0x0000284400442800L, 0x0000508800885000L, 0x0000A0100010A000L, 0x0000402000204000L,
		0x0002040004020000L, 0x0005080008050000L, 0x000A1100110A0000L, 0x0014220022140000L, 0x0028440044280000L, 0x0050880088500000L, 0x00A0100010A00000L, 0x0040200020400000L,
		0x0204000402000000L, 0x0508000805000000L, 0x0A1100110A000000L, 0x1422002214000000L, 0x2844004428000000L, 0x5088008850000000L, 0xA0100010A0000000L, 0x4020002040000000L,
		0x0400040200000000L, 0x0800080500000000L, 0x1100110A00000000L, 0x2200221400000000L, 0x4400442800000000L, 0x8800885000000000L, 0x100010A000000000L, 0x2000204000000000L,
		0x0004020000000000L, 0x0008050000000000L, 0x00110A0000000000L, 0x0022140000000000L, 0x0044280000000000L, 0x0088500000000000L, 0x0010A00000000000L, 0x0020400000000000L,
	};
	
	private static final long[] KING_ATTACKS = new long[]
	{
		0x0000000000000302L, 0x0000000000000705L, 0x0000000000000E0AL, 0x0000000000001C14L, 0x0000000000003828L, 0x0000000000007050L, 0x000000000000E0A0L, 0x000000000000C040L,
		0x0000000000030203L, 0x0000000000070507L, 0x00000000000E0A0EL, 0x00000000001C141CL, 0x0000000000382838L, 0x0000000000705070L, 0x0000000000E0A0E0L, 0x0000000000C040C0L,
		0x0000000003020300L, 0x0000000007050700L, 0x000000000E0A0E00L, 0x000000001C141C00L, 0x0000000038283800L, 0x0000000070507000L, 0x00000000E0A0E000L, 0x00000000C040C000L,
		0x0000000302030000L, 0x0000000705070000L, 0x0000000E0A0E0000L, 0x0000001C141C0000L, 0x0000003828380000L, 0x0000007050700000L, 0x000000E0A0E00000L, 0x000000C040C00000L,
		0x0000030203000000L, 0x0000070507000000L, 0x00000E0A0E000000L, 0x00001C141C000000L, 0x0000382838000000L, 0x0000705070000000L, 0x0000E0A0E0000000L, 0x0000C040C0000000L,
		0x0003020300000000L, 0x0007050700000000L, 0x000E0A0E00000000L, 0x001C141C00000000L, 0x0038283800000000L, 0x0070507000000000L, 0x00E0A0E000000000L, 0x00C040C000000000L,
		0x0302030000000000L, 0x0705070000000000L, 0x0E0A0E0000000000L, 0x1C141C0000000000L, 0x3828380000000000L, 0x7050700000000000L, 0xE0A0E00000000000L, 0xC040C00000000000L,
		0x0203000000000000L, 0x0507000000000000L, 0x0A0E000000000000L, 0x141C000000000000L, 0x2838000000000000L, 0x5070000000000000L, 0xA0E0000000000000L, 0x40C0000000000000L,
	};
}
