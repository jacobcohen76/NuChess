package nuchess.view.graphics;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class ResourceManager implements Serializable
{
	private static final long serialVersionUID = 4974654776275600262L;
	private static final int DEFAULT_SQUARE_SIZE = 100;
	private static final int DEFAULT_HINTS = Image.SCALE_SMOOTH;
	
	public static final File[] DEFAULT_TEXTURE_FILES = new File[]
	{
		new File("resources\\assets\\textures\\null.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-pawn.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-knight.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-bishop.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-rook.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-queen.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-king.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-pawn.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-knight.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-bishop.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-rook.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-queen.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-king.png"),
		new File("resources\\assets\\textures\\marks\\dot.png"),
		new File("resources\\assets\\textures\\marks\\corners.png"),
		new File("resources\\assets\\textures\\marks\\check-highlight.png"),
		new File("resources\\assets\\textures\\marks\\last-move-mask.png"),
		new File("resources\\assets\\textures\\squares\\diagonal.png"),
		new File("resources\\assets\\textures\\squares\\anti-diagonal.png"),
	};
	
	transient private Image[] unscaledTextures, scaledTextures;
	private File[] textureFiles;
	private int squareSize, hints;
	
	public ResourceManager(File[] textureFiles, int squareSize, int hints)
	{
		this.textureFiles = textureFiles;
		this.squareSize = squareSize;
		this.hints = hints;
		initObject();
	}
	
	public ResourceManager()
	{
		this(DEFAULT_TEXTURE_FILES, DEFAULT_SQUARE_SIZE, DEFAULT_HINTS);
	}
	
	private void initTextures()
	{
		unscaledTextures = new Image[textureFiles.length];
		scaledTextures = new Image[textureFiles.length];
		for(int id = 0; id < textureFiles.length; id++)
		{
			loadTexture(id);
		}
	}
	
	public void initObject()
	{
		initTextures();
	}
	
	public int getSquareSize()
	{
		return squareSize;
	}
	
	public Image[] getScaledTextures()
	{
		return scaledTextures;
	}
	
	public Image getTexture(int id)
	{
		return scaledTextures[id];
	}
	
	public Image getUnscaledTexture(int id)
	{
		return unscaledTextures[id];
	}
		
	public void loadTextureFile(int id, File textureFile)
	{
		try
		{
			unscaledTextures[id] = ImageIO.read(textureFile);
			textureFiles[id] = textureFile;
		}
		catch(Exception ex1)
		{
			try
			{
				unscaledTextures[id] = ImageIO.read(DEFAULT_TEXTURE_FILES[id]);
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
				System.exit(0);
			}
		}
		scaledTextures[id] = unscaledTextures[id].getScaledInstance(squareSize, squareSize, hints);
	}
	
	private void loadTexture(int id)
	{
		try
		{
			unscaledTextures[id] = ImageIO.read(textureFiles[id]);
		}
		catch(Exception ex1)
		{
			try
			{
				unscaledTextures[id] = ImageIO.read(DEFAULT_TEXTURE_FILES[id]);
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
				System.exit(0);
			}
		}
		scaledTextures[id] = unscaledTextures[id].getScaledInstance(squareSize, squareSize, hints);
	}
}
