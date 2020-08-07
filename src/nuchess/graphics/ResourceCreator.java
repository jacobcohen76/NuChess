package nuchess.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ResourceCreator
{
	public static BufferedImage createSolidSquare(int squareSize, Color squareColor)
	{
		BufferedImage img = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setColor(squareColor);
		g.fillRect(0, 0, squareSize, squareSize);
		return img;
	}
	
	public static BufferedImage createDotSquare(int squareSize, int dotDiameter, Color dotColor)
	{
		BufferedImage img = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHints(LayeredGraphics.QUALITY_RENDERING_HINTS);
		g.setColor(dotColor);
		g.fillOval((squareSize - dotDiameter) / 2, (squareSize - dotDiameter) / 2, dotDiameter, dotDiameter);
		return img;
	}
	
	public static BufferedImage createNullTexture(int squareSize)
	{
		BufferedImage img = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHints(LayeredGraphics.QUALITY_RENDERING_HINTS);
		
		int quarterSquare = squareSize / 2;
		
		g.setColor(new Color(0xFF00FF));
		g.fillRect(0, 0, quarterSquare, quarterSquare);
		g.fillRect(quarterSquare, quarterSquare, quarterSquare, quarterSquare);
		
		g.setColor(new Color(0x000000));
		g.fillRect(quarterSquare, 0, quarterSquare, quarterSquare);
		g.fillRect(0, quarterSquare, quarterSquare, quarterSquare);
		
		return img;
	}
	
	public static BufferedImage createSquareBorder(int squareSize, int borderWidth, int borderHeight, Color borderColor)
	{
		BufferedImage img = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setColor(borderColor);
		
		//corner 1
		g.fillRect(0, 0, borderWidth, borderHeight);
		g.fillRect(borderWidth, 0, borderHeight - borderWidth, borderWidth);
		
		//corner 2
		g.fillRect(squareSize - borderWidth, 0, borderWidth, borderHeight);
		g.fillRect(squareSize - borderHeight, 0, borderHeight - borderWidth, borderWidth);
		
		//corner 3
		g.fillRect(0, squareSize - borderHeight, borderWidth, borderHeight);
		g.fillRect(borderWidth, squareSize - borderWidth, borderHeight - borderWidth, borderWidth);
		
		//corner 4
		g.fillRect(squareSize - borderWidth, squareSize - borderHeight, borderWidth, borderHeight);
		g.fillRect(squareSize - borderHeight, squareSize - borderWidth, borderHeight - borderWidth, borderWidth);
		
		return img;
	}
	
	public static BufferedImage createPieceHighlight(int squareSize, Color highlightColor)
	{
		BufferedImage img = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		int red = highlightColor.getRed(), green = highlightColor.getGreen(), blue = highlightColor.getBlue();
		
		int mid = squareSize / 2;
		int diameter = 0;
		int inc = mid / 255;
		
		for(int alpha = 255; 0 <= alpha; --alpha)
		{
			Color c = new Color(red, green, blue, alpha);
			g.setColor(c);
			g.drawOval(mid - diameter / 2, mid - diameter / 2, diameter, diameter);
			diameter += inc;
			g.drawOval(mid - diameter / 2, mid - diameter / 2, diameter, diameter);
			diameter += inc;
		}
		
		return img;
	}
}
