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

public final class ResourceManager implements Serializable
{
	private static final long serialVersionUID = 4974654776275600262L;
	
	private static final int DEFAULT_SQUARE_SIZE = 100;
	private static final int DEFAULT_HINTS = Image.SCALE_SMOOTH;
	
	private static final File[] DEFAULT_TEXTURE_FILES = new File[]
	{
		new File("resources\\assets\\textures\\null.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\marks\\dot.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\white\\white-pawn.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\black\\black-pawn.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\white\\white-knight.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\black\\black-knight.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\white\\white-bishop.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\black\\black-bishop.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\white\\white-rook.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\black\\black-rook.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\white\\white-queen.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\black\\black-queen.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\white\\white-king.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\pieces\\black\\black-king.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\marks\\corners.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\marks\\check-highlight.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\marks\\last-move-mask.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\squares\\diagonal.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\textures\\squares\\anti-diagonal.png".replace('\\', File.separatorChar)),
	};
	
	private static final File[] DEFAULT_ICON_FILES = new File[]
	{
		new File("resources\\assets\\icons\\x.png".replace('\\', File.separatorChar)),
		new File("resources\\assets\\icons\\x-hovering.png".replace('\\', File.separatorChar)),
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
	
	private static final void loadManagerObject(File f) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		MANAGER_OBJECT = (ResourceManager) ois.readObject();
		MANAGER_OBJECT.initObject();
		ois.close();
		fis.close();
	}
	
	private static final void saveManagerObject(File f) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(MANAGER_OBJECT);
		oos.close();
		fos.close();
	}
	
	public static final void initManagerObject(File file)
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
	
	public static final void closeManagerObject(File file)
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
	
	public static final int getSquareSize()
	{
		return MANAGER_OBJECT.squareSize;
	}
	
	public static final Image[] getScaledTextures()
	{
		return MANAGER_OBJECT.scaledTextures;
	}
	
	public static final Image getTexture(int id)
	{
		return MANAGER_OBJECT.scaledTextures[id];
	}
	
	public static final Image getUnscaledTexture(int id)
	{
		return MANAGER_OBJECT.unscaledTextures[id];
	}
	
	public static final Image getIcon(int id)
	{
		return MANAGER_OBJECT.icons[id];
	}
	
	public static final Color getColor(int id)
	{
		return MANAGER_OBJECT.colors[id];
	}
	
	public static final File getTextureFile(int id)
	{
		return MANAGER_OBJECT.textureFiles[id];
	}
	
	public static final void loadTextureFile(int id, File textureFile)
	{
		MANAGER_OBJECT.loadTextureFile(id, textureFile, 0);
	}
	
	public static final void loadIconFile(int id, File iconFile)
	{
		MANAGER_OBJECT.loadIconFile(id, iconFile, 0);
	}
	
	public static final void setColor(int id, Color color)
	{
		MANAGER_OBJECT.colors[id] = color;
	}
	
	public static final int getDefaultSquareSize()
	{
		return DEFAULT_SQUARE_SIZE;
	}
	
	public static final File getDefaultTextureFile(int id)
	{
		return DEFAULT_TEXTURE_FILES[id];
	}
	
	public static final File getDefaultIconFile(int id)
	{
		return DEFAULT_ICON_FILES[id];
	}
	
	public static final Color getDefaultColor(int id)
	{
		return DEFAULT_COLORS[id];
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
	
	private final void initObject()
	{
		initTextures();
		initIcons();
	}
	
	private final void initTextures()
	{
		unscaledTextures = new Image[textureFiles.length];
		scaledTextures = new Image[textureFiles.length];
		for(int id = 0; id < textureFiles.length; id++)
		{
			loadTexture(id);
		}
	}
	
	private final void initIcons()
	{
		icons = new Image[iconFiles.length];
		for(int id = 0; id < iconFiles.length; id++)
		{
			loadIcon(id);
		}
	}
	
	private final void loadTextureFile(int id, File textureFile, int nothing)
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
	
	private final void loadIconFile(int id, File iconFile, int nothing)
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
	
	private final void loadTexture(int id)
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
	
	private final void loadIcon(int id)
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
