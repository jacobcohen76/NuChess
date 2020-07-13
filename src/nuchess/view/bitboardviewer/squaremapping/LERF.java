package nuchess.view.bitboardviewer.squaremapping;

public class LERF implements SquareMapping
{
	@Override
	public int rank(int square)
	{
		return square >> 3;
	}
	
	@Override
	public int file(int square)
	{
		return square & 7;
	}
	
	@Override
	public int diag(int square)
	{
		return rank(square) - file(square) + 7;
	}
	
	@Override
	public int anti(int square)
	{
		return rank(square) + file(square);
	}
	
	@Override
	public int square(int rank, int file)
	{
		return (rank << 3) + file;
	}
	
	@Override
	public int square(int bitIndex)
	{
		return bitIndex;
	}
	
	@Override
	public String getMappingID()
	{
		return "LERF";
	}
}
