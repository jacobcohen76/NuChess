package nuchess.view.graphics;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class ResourceManager implements Serializable
{
	private static final long serialVersionUID = 4974654776275600262L;
	
	private static final int DEFAULT_SQUARE_SIZE = 100;
	private static final int DEFAULT_HINTS = Image.SCALE_SMOOTH;
	
	private static final File[] DEFAULT_TEXTURE_FILES = new File[]
	{
		new File("resources\\assets\\textures\\null.png"),
		new File("resources\\assets\\textures\\marks\\dot.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-pawn.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-pawn.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-knight.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-knight.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-bishop.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-bishop.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-rook.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-rook.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-queen.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-queen.png"),
		new File("resources\\assets\\textures\\pieces\\white\\white-king.png"),
		new File("resources\\assets\\textures\\pieces\\black\\black-king.png"),
		new File("resources\\assets\\textures\\marks\\corners.png"),
		new File("resources\\assets\\textures\\marks\\check-highlight.png"),
		new File("resources\\assets\\textures\\marks\\last-move-mask.png"),
		new File("resources\\assets\\textures\\squares\\diagonal.png"),
		new File("resources\\assets\\textures\\squares\\anti-diagonal.png"),
	};
	
	private static final File[] DEFAULT_ICON_FILES = new File[]
	{
		new File("resources\\assets\\icons\\x.png"),
		new File("resources\\assets\\icons\\x-hovering.png"),
	};
	
	private static final Color[] DEFAULT_COLORS = new Color[]
	{
		new Color( 34,  34,  34),
		new Color( 42,  42,  42),
		new Color( 57,  57,  57),
		new Color(255, 255, 255),
		new Color( 53,  24, 122),
		new Color(255, 255, 255),
		new Color(  0,   0,   0),
	};
	
	private static ResourceManager MANAGER_OBJECT;
	
	private static void loadManagerObject(File f) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		MANAGER_OBJECT = (ResourceManager) ois.readObject();
		MANAGER_OBJECT.initObject();
		ois.close();
		fis.close();
	}
	
	private static void saveManagerObject(File f) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(MANAGER_OBJECT);
		oos.close();
		fos.close();
	}
	
	public static void initManagerObject(File file)
	{
		try
		{
			loadManagerObject(file);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error loading manager object from \"" + file + "\"");
			MANAGER_OBJECT = new ResourceManager();
		}
	}
	
	public static void closeManagerObject(File file)
	{
		try
		{
			saveManagerObject(file);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static int getSquareSize()
	{
		return MANAGER_OBJECT.squareSize;
	}
	
	public static Image[] getScaledTextures()
	{
		return MANAGER_OBJECT.scaledTextures;
	}
	
	public static Image getTexture(int id)
	{
		return MANAGER_OBJECT.scaledTextures[id];
	}
	
	public static Image getUnscaledTexture(int id)
	{
		return MANAGER_OBJECT.unscaledTextures[id];
	}
	
	public static Image getIcon(int id)
	{
		return MANAGER_OBJECT.icons[id];
	}
	
	public static Color getColor(int id)
	{
		return MANAGER_OBJECT.colors[id];
	}
	
	public static File getTextureFile(int id)
	{
		return MANAGER_OBJECT.textureFiles[id];
	}
	
	public static void loadTextureFile(int id, File textureFile)
	{
		MANAGER_OBJECT.loadTextureFile(id, textureFile, 0);
	}
	
	public static void loadIconFile(int id, File iconFile)
	{
		MANAGER_OBJECT.loadIconFile(id, iconFile, 0);
	}
	
	transient private Image[] unscaledTextures, scaledTextures, icons;
	private File[] textureFiles, iconFiles;
	private Color[] colors;
	private int squareSize, hints;
	
	private ResourceManager(File[] textureFiles, File[] iconFiles, Color[] colors, int squareSize, int hints)
	{
		this.textureFiles = textureFiles;
		this.iconFiles = iconFiles;
		this.colors = colors;
		this.squareSize = squareSize;
		this.hints = hints;
		initObject();
	}
	
	private ResourceManager()
	{
		this(DEFAULT_TEXTURE_FILES, DEFAULT_ICON_FILES, DEFAULT_COLORS, DEFAULT_SQUARE_SIZE, DEFAULT_HINTS);
	}
	
	private void initObject()
	{
		initTextures();
		initIcons();
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
	
	private void initIcons()
	{
		icons = new Image[iconFiles.length];
		for(int id = 0; id < iconFiles.length; id++)
		{
			loadIcon(id);
		}
	}
	
	private void loadTextureFile(int id, File textureFile, int nothing)
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
	
	private void loadIconFile(int id, File iconFile, int nothing)
	{
		try
		{
			icons[id] = ImageIO.read(iconFile);
			iconFiles[id] = iconFile;
		}
		catch(Exception ex1)
		{
			try
			{
				icons[id] = ImageIO.read(DEFAULT_TEXTURE_FILES[id]);
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
				System.exit(0);
			}
		}
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
	
	private void loadIcon(int id)
	{
		try
		{
			icons[id] = ImageIO.read(iconFiles[id]);
		}
		catch(Exception ex1)
		{
			try
			{
				icons[id] = ImageIO.read(DEFAULT_ICON_FILES[id]);
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
				System.exit(0);
			}
		}
	}
}
