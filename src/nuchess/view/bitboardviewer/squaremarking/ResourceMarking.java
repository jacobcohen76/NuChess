package nuchess.view.bitboardviewer.squaremarking;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;

import nuchess.view.bitboardviewer.squaremapping.SquareMapping;

public class ResourceMarking implements SquareMarking
{
	private Image[] scaledResources;
	private int resourceIndex;
	
	public ResourceMarking(Image[] scaledResources, int resourceIndex)
	{
		this.scaledResources = scaledResources;
		this.resourceIndex = resourceIndex;
	}
	
	public ResourceMarking()
	{
		this(null, -1);
	}
	
	public void setScaledResources(Image[] scaledResources)
	{
		this.scaledResources = scaledResources;
	}
	
	public void setResourceIndex(int resourceIndex)
	{
		this.resourceIndex = resourceIndex;
	}
	
	@Override
	public void markSquare(int squareSize, boolean flipped, int bitIndex, SquareMapping mapping, Graphics2D g)
	{
		markSquare(squareSize, flipped, mapping.rank(mapping.square(bitIndex)), mapping.file(mapping.square(bitIndex)), g);
	}

	@Override
	public void markSquare(int squareSize, boolean flipped, int rank, int file, Graphics2D g)
	{
		g.drawImage(scaledResources[resourceIndex], getX(squareSize, flipped, file), getY(squareSize, flipped, rank), squareSize, squareSize, null);
	}

	@Override
	public void clearSquare(int squareSize, boolean flipped, int bitIndex, SquareMapping mapping, Graphics2D g)
	{
		clearSquare(squareSize, flipped, mapping.rank(mapping.square(bitIndex)), mapping.file(mapping.square(bitIndex)), g);
	}

	@Override
	public void clearSquare(int squareSize, boolean flipped, int rank, int file, Graphics2D g)
	{
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(getX(squareSize, flipped, file), getY(squareSize, flipped, rank), squareSize, squareSize);
		g.setComposite(AlphaComposite.SrcOver);
	}
	
	@Override
	public int getX(int squareSize, boolean flipped, int file)
	{
		return (flipped ? (7 - file) : file) * squareSize;
	}
	
	@Override
	public int getY(int squareSize, boolean flipped, int rank)
	{
		return (flipped ? rank : (7 - rank)) * squareSize;
	}

	@Override
	public String getMarkingID()
	{
		return "ResourceMarking";
	}
}
