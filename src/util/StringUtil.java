package util;

public class StringUtil
{
	public static String repeat(char c, int length)
	{
		return String.valueOf(new char[length]).replace('\0', c);
	}
	
	public static int longest(String[] lines)
	{
		int longest = 0;
		for(String line : lines)
		{
			if(longest < line.length())
			{
				longest = line.length();
			}
		}
		return longest;
	}
	
	public static final boolean contains(String str, char ch)
	{
		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == ch)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void makeSameLength(String[] lines, char pad, int longestLine)
	{
		for(int i = 0; i < lines.length; i++)
		{
			lines[i] += repeat(pad, longestLine - lines[i].length());
		}
	}
	
	public static void makeSameLength(String[] lines, char pad)
	{
		makeSameLength(lines, pad, longest(lines));
	}
	
	public static String merge(String lhs, char gapCharacter, int gapSize, String rhs)
	{
		String gap = repeat(gapCharacter, gapSize), lineReplacement;
		String[] left = lhs.split("\n"), right = rhs.split("\n");
		int longestLeft = longest(left), longestRight = longest(right), n, i;
		StringBuilder sb = new StringBuilder();
		
		makeSameLength(left, gapCharacter, longestLeft);
		makeSameLength(right, gapCharacter, longestRight);
		
		n = Integer.min(left.length, right.length);
		for(i = 0; i < n; i++)
		{
			sb.append(left[i]);
			sb.append(gap);
			sb.append(right[i]);
			sb.append('\n');
		}
		n = Integer.max(left.length, right.length);
		
		if(left.length < right.length)
		{
			//more lines on rhs than lhs
			lineReplacement = repeat(gapCharacter, longestLeft);
			while(i < n)
			{
				sb.append(lineReplacement);
				sb.append(gap);
				sb.append(right[i]);
				sb.append('\n');
				i++;
			}
		}
		else
		{
			//more lines on lhs than rhs
			lineReplacement = repeat(gapCharacter, longestRight);
			while(i < n)
			{
				sb.append(left[i]);
				sb.append(gap);
				sb.append(lineReplacement);
				sb.append('\n');
				i++;
			}
		}
		
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
