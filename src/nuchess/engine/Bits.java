package nuchess.engine;

import util.StringUtil;

public class Bits
{
	public static final int bitCount(byte u8)
	{
		return bitCount(Byte.toUnsignedInt(u8));
	}
	
	public static final int bitCount(short u16)
	{
		return bitCount(Short.toUnsignedInt(u16));
	}
	
	public static final int bitCount(int u32)
	{
		return Integer.bitCount(u32);
	}
	
	public static final int bitCount(long u64)
	{
		return Long.bitCount(u64);
	}
		
	public static final int bitscanForward(int u32)
	{
		return Integer.numberOfTrailingZeros(u32);
	}
	
	public static final int bitscanForward(long u64)
	{
		return Long.numberOfTrailingZeros(u64);
	}
	
	public static final int bitscanReverse(int u32)
	{
		return	u32 == 0 ?
					0 :
					31 - Integer.numberOfLeadingZeros(u32);
	}
	
	public static final int bitscanReverse(long u64)
	{
		return	u64 == 0 ?
					0 :
					63 - Long.numberOfLeadingZeros(u64);
	}
	
	public static String toBinaryString(byte u8)
	{
		return toBinaryString(Byte.toUnsignedInt(u8), 8);
	}
	
	public static String toBinaryString(short u16)
	{
		return toBinaryString(Short.toUnsignedInt(u16), 16);
	}
	
	public static String toBinaryString(int u32)
	{
		return toBinaryString(u32, 32);
	}
	
	public static String toBinaryString(long u64)
	{
		return toBinaryString(u64, 64);
	}
	
	public static String toBinaryString(int u32, int numBits)
	{
		String binaryString = Integer.toUnsignedString(u32, 2);
		return (binaryString.length() < numBits ?
					StringUtil.repeat('0', numBits - binaryString.length()) + binaryString :
					binaryString.length() > numBits ?
						binaryString.substring(binaryString.length() - numBits, binaryString.length()) :
						binaryString).toUpperCase();
	}
	
	public static String toBinaryString(long u64, int numBits)
	{
		String binaryString = Long.toUnsignedString(u64, 2);
		return (binaryString.length() < numBits ?
					StringUtil.repeat('0', numBits - binaryString.length()) + binaryString :
					binaryString.length() > numBits ?
						binaryString.substring(binaryString.length() - numBits, binaryString.length()) :
						binaryString).toUpperCase();
	}
	
	public static String toHexString(byte u8)
	{
		return toHexString(Byte.toUnsignedInt(u8), 2);
	}
	
	public static String toHexString(short u16)
	{
		return toHexString(Short.toUnsignedInt(u16), 4);
	}
	
	public static String toHexString(int u32)
	{
		return toHexString(u32, 8);
	}
	
	public static String toHexString(long u64)
	{
		return toHexString(u64, 16);
	}
		
	public static String toHexString(int u32, int numBits)
	{
		String hexString = Integer.toUnsignedString(u32, 16);
		return (hexString.length() < numBits ?
					StringUtil.repeat('0', numBits - hexString.length()) + hexString :
					hexString.length() > numBits ?
						hexString.substring(hexString.length() - numBits, hexString.length()) :
						hexString).toUpperCase();
	}
	
	public static String toHexString(long u64, int numBits)
	{
		String hexString = Long.toUnsignedString(u64, 16);
		return (hexString.length() < numBits ?
					StringUtil.repeat('0', numBits - hexString.length()) + hexString :
					hexString.length() > numBits ?
						hexString.substring(hexString.length() - numBits, hexString.length()) :
						hexString).toUpperCase();
	}
	
	public static long valueOf(String str, int base)
	{
		switch(base)
		{
			case 2:
		}
		return 0L;
	}
	
	public static long valueOfBinaryString(String binaryString)
	{
		if(binaryString.length() > 64)
		{
			throw new Error("binary string is out of range for a 64 bit integer");
		}
		else if(!checkString(binaryString, '0', '1'))
		{
			throw new Error("binary string is not comprise of only '0' and '1'");
		}
		else
		{
			long value = 0L;
			for(int bit = 0; bit < binaryString.length(); bit++)
			{
				value |= (binaryString.charAt(bit) == '1' ? 1L : 0L) << bit;
			}
			return value;
		}
	}
	
	public static long valueOfHexString(String hexString)
	{
		hexString = hexString.toUpperCase();
		if(hexString.length() > 16)
		{
			throw new Error("hex string is out of range for a 64 bit integer");
		}
		else if(!checkString(hexString, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'))
		{
			throw new Error("hex string is not comprise of only 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, or F");
		}
		else
		{
			long value = 0L;
			for(int i = 0; i < hexString.length(); i++)
			{
				value |= (valueOf(hexString.charAt(i))) << (i * 4);
			}
			return value;
		}
	}
	
	private static long valueOf(char ch)
	{
		switch(ch)
		{
			case '0':	return 0x0L;
			case '1':	return 0x1L;
			case '2':	return 0x2L;
			case '3':	return 0x3L;
			case '4':	return 0x4L;
			case '5':	return 0x5L;
			case '6':	return 0x6L;
			case '7':	return 0x7L;
			case '8':	return 0x8L;
			case '9':	return 0x9L;
			case 'A':	return 0xAL;
			case 'B':	return 0xBL;
			case 'C':	return 0xCL;
			case 'D':	return 0xDL;
			case 'E':	return 0xEL;
			case 'F':	return 0xFL;
			default:	return -1L;
		}
	}
	
	public static boolean checkString(String str, char... characters)
	{
		for(char ch : characters)
		{
			str = str.replaceAll(String.valueOf(ch), "");
		}
		return str.length() == 0;
	}
}
