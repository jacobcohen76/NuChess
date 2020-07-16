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
}
