package nuchess.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public final class MagicBitboards
{
	static
	{
		ROOK_TABLE = readTable(new File("resources/serial/magic/rook-table"));
		BISHOP_TABLE = readTable(new File("resources/serial/magic/bishop-table"));
	}
	
	private static final long[][] readTable(File f)
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			long[][] table = (long[][]) ois.readObject();
			ois.close();
			return table;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}
	
	public static final long rookAttacks(int square, long blockers)
	{
		return ROOK_TABLE[square][rookKey(square, blockers)];
	}
	
	public static final long bishopAttacks(int square, long blockers)
	{
		return BISHOP_TABLE[square][bishopKey(square, blockers)];
	}
	
	private static final int rookKey(int square, long blockers)
	{
		return (int) (((blockers & ROOK_MASK[square]) * ROOK_MAGIC[square]) >>> (64 - ROOK_SHIFT[square]));
	}
	
	private static final int bishopKey(int square, long blockers)
	{
		return (int) (((blockers & BISHOP_MASK[square]) * BISHOP_MAGIC[square]) >>> (64 - BISHOP_SHIFT[square]));
	}
	
	private static final long[][] ROOK_TABLE;
	private static final long[][] BISHOP_TABLE;
	
	private static final long[] ROOK_MASK = new long[]
	{
		0x000101010101017EL, 0x000202020202027CL, 0x000404040404047AL, 0x0008080808080876L, 0x001010101010106EL, 0x002020202020205EL, 0x004040404040403EL, 0x008080808080807EL,
		0x0001010101017E00L, 0x0002020202027C00L, 0x0004040404047A00L, 0x0008080808087600L, 0x0010101010106E00L, 0x0020202020205E00L, 0x0040404040403E00L, 0x0080808080807E00L,
		0x00010101017E0100L, 0x00020202027C0200L, 0x00040404047A0400L, 0x0008080808760800L, 0x00101010106E1000L, 0x00202020205E2000L, 0x00404040403E4000L, 0x00808080807E8000L,
		0x000101017E010100L, 0x000202027C020200L, 0x000404047A040400L, 0x0008080876080800L, 0x001010106E101000L, 0x002020205E202000L, 0x004040403E404000L, 0x008080807E808000L,
		0x0001017E01010100L, 0x0002027C02020200L, 0x0004047A04040400L, 0x0008087608080800L, 0x0010106E10101000L, 0x0020205E20202000L, 0x0040403E40404000L, 0x0080807E80808000L,
		0x00017E0101010100L, 0x00027C0202020200L, 0x00047A0404040400L, 0x0008760808080800L, 0x00106E1010101000L, 0x00205E2020202000L, 0x00403E4040404000L, 0x00807E8080808000L,
		0x007E010101010100L, 0x007C020202020200L, 0x007A040404040400L, 0x0076080808080800L, 0x006E101010101000L, 0x005E202020202000L, 0x003E404040404000L, 0x007E808080808000L,
		0x7E01010101010100L, 0x7C02020202020200L, 0x7A04040404040400L, 0x7608080808080800L, 0x6E10101010101000L, 0x5E20202020202000L, 0x3E40404040404000L, 0x7E80808080808000L,
	};
	
	private static final long[] BISHOP_MASK = new long[]
	{
		0x0040201008040200L, 0x0000402010080400L, 0x0000004020100A00L, 0x0000000040221400L, 0x0000000002442800L, 0x0000000204085000L, 0x0000020408102000L, 0x0002040810204000L,
		0x0020100804020000L, 0x0040201008040000L, 0x00004020100A0000L, 0x0000004022140000L, 0x0000000244280000L, 0x0000020408500000L, 0x0002040810200000L, 0x0004081020400000L,
		0x0010080402000200L, 0x0020100804000400L, 0x004020100A000A00L, 0x0000402214001400L, 0x0000024428002800L, 0x0002040850005000L, 0x0004081020002000L, 0x0008102040004000L,
		0x0008040200020400L, 0x0010080400040800L, 0x0020100A000A1000L, 0x0040221400142200L, 0x0002442800284400L, 0x0004085000500800L, 0x0008102000201000L, 0x0010204000402000L,
		0x0004020002040800L, 0x0008040004081000L, 0x00100A000A102000L, 0x0022140014224000L, 0x0044280028440200L, 0x0008500050080400L, 0x0010200020100800L, 0x0020400040201000L,
		0x0002000204081000L, 0x0004000408102000L, 0x000A000A10204000L, 0x0014001422400000L, 0x0028002844020000L, 0x0050005008040200L, 0x0020002010080400L, 0x0040004020100800L,
		0x0000020408102000L, 0x0000040810204000L, 0x00000A1020400000L, 0x0000142240000000L, 0x0000284402000000L, 0x0000500804020000L, 0x0000201008040200L, 0x0000402010080400L,
		0x0002040810204000L, 0x0004081020400000L, 0x000A102040000000L, 0x0014224000000000L, 0x0028440200000000L, 0x0050080402000000L, 0x0020100804020000L, 0x0040201008040200L,
	};
	
	private static final long[] ROOK_MAGIC = new long[]
	{
		0x0A8002C000108020L, 0x06C00049B0002001L, 0x0100200010090040L, 0x2480041000800801L, 0x0280028004000800L, 0x0900410008040022L, 0x0280020001001080L, 0x2880002041000080L,
		0xA000800080400034L, 0x0004808020004000L, 0x2290802004801000L, 0x0411000D00100020L, 0x0402800800040080L, 0x000B000401004208L, 0x2409000100040200L, 0x0001002100004082L,
		0x0022878001E24000L, 0x1090810021004010L, 0x0801030040200012L, 0x0500808008001000L, 0x0A08018014000880L, 0x8000808004000200L, 0x0201008080010200L, 0x0801020000441091L,
		0x0000800080204005L, 0x1040200040100048L, 0x0000120200402082L, 0x0D14880480100080L, 0x0012040280080080L, 0x0100040080020080L, 0x9020010080800200L, 0x0813241200148449L,
		0x0491604001800080L, 0x0100401000402001L, 0x4820010021001040L, 0x0400402202000812L, 0x0209009005000802L, 0x0810800601800400L, 0x4301083214000150L, 0x204026458E001401L,
		0x0040204000808000L, 0x8001008040010020L, 0x8410820820420010L, 0x1003001000090020L, 0x0804040008008080L, 0x0012000810020004L, 0x1000100200040208L, 0x430000A044020001L,
		0x0280009023410300L, 0x00E0100040002240L, 0x0000200100401700L, 0x2244100408008080L, 0x0008000400801980L, 0x0002000810040200L, 0x8010100228810400L, 0x2000009044210200L,
		0x4080008040102101L, 0x0040002080411D01L, 0x2005524060000901L, 0x0502001008400422L, 0x489A000810200402L, 0x0001004400080A13L, 0x4000011008020084L, 0x0026002114058042L,
	};
	
	public static final long[] BISHOP_MAGIC = new long[]
	{
		0x89A1121896040240L, 0x2004844802002010L, 0x2068080051921000L, 0x62880A0220200808L, 0x0004042004000000L, 0x0100822020200011L, 0xC00444222012000AL, 0x0028808801216001L,
		0x0400492088408100L, 0x0201C401040C0084L, 0x00840800910A0010L, 0x0000082080240060L, 0x2000840504006000L, 0x30010C4108405004L, 0x1008005410080802L, 0x8144042209100900L,
		0x0208081020014400L, 0x004800201208CA00L, 0x0F18140408012008L, 0x1004002802102001L, 0x0841000820080811L, 0x0040200200A42008L, 0x0000800054042000L, 0x88010400410C9000L,
		0x0520040470104290L, 0x1004040051500081L, 0x2002081833080021L, 0x000400C00C010142L, 0x941408200C002000L, 0x0658810000806011L, 0x0188071040440A00L, 0x4800404002011C00L,
		0x0104442040404200L, 0x0511080202091021L, 0x0004022401120400L, 0x80C0040400080120L, 0x8040010040820802L, 0x0480810700020090L, 0x0102008E00040242L, 0x0809005202050100L,
		0x8002024220104080L, 0x0431008804142000L, 0x0019001802081400L, 0x0200014208040080L, 0x3308082008200100L, 0x041010500040C020L, 0x4012020C04210308L, 0x208220A202004080L,
		0x0111040120082000L, 0x6803040141280A00L, 0x2101004202410000L, 0x8200000041108022L, 0x0000021082088000L, 0x0002410204010040L, 0x0040100400809000L, 0x0822088220820214L,
		0x0040808090012004L, 0x00910224040218C9L, 0x0402814422015008L, 0x0090014004842410L, 0x0001000042304105L, 0x0010008830412A00L, 0x2520081090008908L, 0x40102000A0A60140L,
	};
	
	private static final int[] ROOK_SHIFT = new int[]
	{
		12, 11, 11, 11, 11, 11, 11, 12,
		11, 10, 10, 10, 10, 10, 10, 11,
		11, 10, 10, 10, 10, 10, 10, 11,
		11, 10, 10, 10, 10, 10, 10, 11,
		11, 10, 10, 10, 10, 10, 10, 11,
		11, 10, 10, 10, 10, 10, 10, 11,
		11, 10, 10, 10, 10, 10, 10, 11,
		12, 11, 11, 11, 11, 11, 11, 12,
	};
	
	private static final int[] BISHOP_SHIFT = new int[]
	{
		6, 5, 5, 5, 5, 5, 5, 6,
		5, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 7, 7, 7, 7, 5, 5,
		5, 5, 7, 9, 9, 7, 5, 5,
		5, 5, 7, 9, 9, 7, 5, 5,
		5, 5, 7, 7, 7, 7, 5, 5,
		5, 5, 5, 5, 5, 5, 5, 5,
		6, 5, 5, 5, 5, 5, 5, 6,
	};
}