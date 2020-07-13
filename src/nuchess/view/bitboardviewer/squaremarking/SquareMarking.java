package nuchess.view.bitboardviewer.squaremarking;

import java.awt.Graphics2D;

import nuchess.view.bitboardviewer.squaremapping.SquareMapping;

public interface SquareMarking
{
	public void markSquare(int squareSize, boolean flipped, int bitIndex, SquareMapping mapping, Graphics2D g);
	public void markSquare(int squareSize, boolean flipped, int rank, int file, Graphics2D g);
	public void clearSquare(int squareSize, boolean flipped, int bitIndex, SquareMapping mapping, Graphics2D g);
	public void clearSquare(int squareSize, boolean flipped, int rank, int file, Graphics2D g);
	public int getX(int squareSize, boolean flipped, int file);
	public int getY(int squareSize, boolean flipped, int rank);
	public String getMarkingID();
}
