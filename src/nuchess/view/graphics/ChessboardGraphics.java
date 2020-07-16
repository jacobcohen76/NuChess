package nuchess.view.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import nuchess.engine.Bits;
import nuchess.engine.CMove;
import nuchess.engine.FENParser;
import nuchess.engine.Square;

public class ChessboardGraphics
{
	private static final long DARK_SQUARE_BB 			= 0xAA55AA55AA55AA55L;
	private static final long LIGHT_SQUARE_BB 			= 0x55AA55AA55AA55AAL;
	private static ResourceManager RESOURCE_MANAGER		= null;
	
	public static final int BACKGROUND_LAYER			= 0;
	public static final int MASK_LAYER					= 1;
	public static final int HIGHLIGHT_LAYER				= 2;
	public static final int DOT_LAYER					= 3;
	public static final int CORNER_LAYER				= 4;
	public static final int PIECE_LAYER					= 5;
	public static final int RING_LAYER					= 6;
	public static final int ARROW_LAYER					= 7;
	public static final int NUM_LAYERS					= 8;
	
	public static ResourceManager getResourceManager()
	{
		return RESOURCE_MANAGER;
	}
	
	public static void initResourceManager(File file)
	{
		try
		{
			loadResourceManager(file);
		}
		catch(Exception ex)
		{
			RESOURCE_MANAGER = new ResourceManager();
		}
	}
	
	public static void closeResourceManager(File file)
	{
		try
		{
			saveResourceManager(file);
		}
		catch(Exception ex)
		{
			
		}
	}
	
	private static void loadResourceManager(File f) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		RESOURCE_MANAGER = (ResourceManager) ois.readObject();
		RESOURCE_MANAGER.initObject();
		ois.close();
		fis.close();
	}
	
	private static void saveResourceManager(File f) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(RESOURCE_MANAGER);
		oos.close();
		fos.close();
	}
	
	private LayeredGraphics lg;
	private Image[] scaledResources;
	private int squareSize;
	private boolean flipped;
	
	public ChessboardGraphics(int squareSize, boolean flipped, LayeredGraphics lg)
	{
		this.squareSize = squareSize;
		this.flipped = flipped;
		this.lg = lg;
		
		lg.setSize(getWidth(), getHeight());
		for(int i = 0; i < NUM_LAYERS; i++)
		{
			lg.addNewLayer();
		}
		
		scaledResources = RESOURCE_MANAGER.getScaledTextures(squareSize, Image.SCALE_SMOOTH);
		RESOURCE_MANAGER.addSubscriber(this);
	}
	
	public Image[] getScaledResources()
	{
		return scaledResources;
	}
	
	public Graphics2D getLayer(int layer)
	{
		return lg.getGraphics(layer);
	}
	
	public int getSquareSize()
	{
		return squareSize;
	}
	
	public void setFlipped(boolean b)
	{
		flipped = b;
	}
	
	public boolean isFlipped()
	{
		return flipped;
	}
	
	public void write(String formatName, File output) throws IOException
	{
		ImageIO.write(lg.merge(), formatName, output);
	}
	
	public void setScaledResource(Image scaledResource, int index)
	{
		scaledResources[index] = scaledResource;
	}
	
	public void setSquareSize(int squareSize)
	{
		this.squareSize = squareSize;
		resizeLayeredGraphics(getWidth(), getHeight());
	}
	
	public BufferedImage getRenderedImage()
	{
		return lg.merge();
	}
	
	public void clear(int layer)
	{
		lg.clear(layer);
	}
	
	public void clear(long bitboard, int layer)
	{
		while(bitboard != 0)
		{
			clear(Bits.bitscanForward(bitboard), layer);
			bitboard &= bitboard ^ -bitboard;
		}
	}
	
	public void clear(int square, int layer)
	{
		lg.clear(layer, getX(square), getY(square), squareSize, squareSize);
	}
	
	public void clearAll()
	{
		for(int layer = 0; layer < lg.numLayers(); layer++)
		{
			lg.clear(layer);
		}
	}
	
	public void paintFEN(String FEN)
	{
		int i = 0, rank = 7, file = 0;
		char c;
		
		while(i < FEN.length() && (c = FEN.charAt(i++)) != ' ')
		{
			if(FENParser.isDigit(c))
			{
				file += c - '0';
			}
			else if(FENParser.isPiece(c))
			{
				paint(scaledResources[TextureIDs.pieceID(c)], Square.makeSquare(rank, file), PIECE_LAYER);
				file++;
			}
			else if(c == '/')
			{
				rank--;
				file = 0;
			}
		}
	}
	
	public void paintBackground()
	{
		paint(scaledResources[TextureIDs.DARK_SQUARE], DARK_SQUARE_BB, BACKGROUND_LAYER);
		paint(scaledResources[TextureIDs.LIGHT_SQUARE], LIGHT_SQUARE_BB, BACKGROUND_LAYER);
	}
		
	public void paintMovedMask(CMove move)
	{
		if(move.hashCode() != 0)
		{	
			paintMovedMask(move.from());
			paintMovedMask(move.to());
		}
	}
	
	public void paintMovedMask(int square)
	{
		paint(scaledResources[TextureIDs.MASK], square, MASK_LAYER);
	}
	
	public void paintDots(long bitboard)
	{
		paint(scaledResources[TextureIDs.DOT], bitboard, DOT_LAYER);
	}
	
	public void paintDot(int square)
	{
		paint(scaledResources[TextureIDs.DOT], square, DOT_LAYER);
	}
	
	public void paintCorners(long bitboard)
	{
		while(bitboard != 0)
		{
			paintCorners(Bits.bitscanForward(bitboard));
			bitboard &= bitboard ^ -bitboard;
		}
	}
	
	public void paintCorners(int square)
	{
		paint(scaledResources[TextureIDs.BORDER], square, CORNER_LAYER);
	}
	
	public void paintCheckHighlight(long bitboard)
	{
		while(bitboard != 0)
		{
			paintCheckHighlight(Bits.bitscanForward(bitboard));
			bitboard &= bitboard ^ -bitboard;
		}
	}
	
	public void paintCheckHighlight(int square)
	{
		paint(scaledResources[TextureIDs.HIGHLIGHT], square, HIGHLIGHT_LAYER);
	}
	
	private void paint(Image img, long bitboard, int layer)
	{
		while(bitboard != 0)
		{
			paint(img, Bits.bitscanForward(bitboard), layer);
			bitboard &= bitboard ^ -bitboard;
		}
	}
	
	private void paint(Image img, int square, int layer)
	{
		lg.getGraphics(layer).drawImage(img, getX(square), getY(square), squareSize, squareSize, null);
	}
	
	public Dimension getDimensions()
	{
		return new Dimension(getWidth(), getHeight());
	}
	
	public int getWidth()
	{
		return (squareSize << 3);
	}
	
	public int getHeight()
	{
		return (squareSize << 3);
	}
	
	public int getX(int square)
	{
		return (flipped ? (7 - Square.file(square)) : (Square.file(square))) * squareSize;
	}
	
	public int getY(int square)
	{
		return (flipped ? (Square.rank(square)) : (7 - Square.rank(square))) * squareSize;
	}
	
	public int getRank(int y)
	{
		return (flipped ? (y) : (getHeight() - y)) / squareSize;
	}
	
	public int getFile(int x)
	{
		return (flipped ? (getWidth() - x) : (x)) / squareSize;
	}
	
	public int getSquare(int x, int y)
	{
		return Square.makeSquare(getRank(y), getFile(x));
	}
	
	private void resizeLayeredGraphics(int w, int h)
	{
		lg.setSize(w, h);
		int startCount = lg.numLayers();
		for(int layer = 0; layer < startCount; layer++)
		{
			lg.addNewLayer();
//			lg.getGraphics(lg.numLayers() - 1)
//				.drawImage(lg.getImage(0), 0, 0, w, h, null);
			lg.remove(0);
		}
	}
}
