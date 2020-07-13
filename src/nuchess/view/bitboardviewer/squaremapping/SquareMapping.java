package nuchess.view.bitboardviewer.squaremapping;

public interface SquareMapping
{
	public int rank(int square);
	public int file(int square);
	public int diag(int square);
	public int anti(int square);
	public int square(int rank, int file);
	public int square(int bitIndex);
	public String getMappingID();
}
