package nuchess.view.graphics;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class ResourceManager implements Serializable
{
	private static final long serialVersionUID = 4974654776275600262L;
	
	public static final File[] DEFAULT_TEXTURE_FILES = new File[]
	{
		new File("resources/assets/textures/null.png"),
		new File("resources/assets/textures/pieces/white/white-pawn.png"),
		new File("resources/assets/textures/pieces/white/white-knight.png"),
		new File("resources/assets/textures/pieces/white/white-bishop.png"),
		new File("resources/assets/textures/pieces/white/white-rook.png"),
		new File("resources/assets/textures/pieces/white/white-queen.png"),
		new File("resources/assets/textures/pieces/white/white-king.png"),
		new File("resources/assets/textures/pieces/black/black-pawn.png"),
		new File("resources/assets/textures/pieces/black/black-knight.png"),
		new File("resources/assets/textures/pieces/black/black-bishop.png"),
		new File("resources/assets/textures/pieces/black/black-rook.png"),
		new File("resources/assets/textures/pieces/black/black-queen.png"),
		new File("resources/assets/textures/pieces/black/black-king.png"),
		new File("resources/assets/textures/marks/dot.png"),
		new File("resources/assets/textures/marks/corners.png"),
		new File("resources/assets/textures/marks/check-highlight.png"),
		new File("resources/assets/textures/marks/last-move-mask.png"),
		new File("resources/assets/textures/squares/diagonal.png"),
		new File("resources/assets/textures/squares/anti-diagonal.png"),
	};
	
	transient private Image[] textures;
	transient private List<ChessboardGraphics> cbgList;
	private File[] textureFiles;
	
	public ResourceManager(File[] textureFiles)
	{
		this.textureFiles = textureFiles;
		initObject();
	}
	
	public ResourceManager()
	{
		this(DEFAULT_TEXTURE_FILES);
	}
	
	private void initTextures()
	{
		textures = new Image[textureFiles.length];
		for(int id = 0; id < textureFiles.length; id++)
		{
			loadTexture(id);
		}
	}
	
	public void initObject()
	{
		cbgList = new LinkedList<ChessboardGraphics>();
		initTextures();
	}
	
	public void addSubscriber(ChessboardGraphics cbg)
	{
		cbgList.add(cbg);
	}
	
	public void removeSubscriber(ChessboardGraphics cbg)
	{
		cbgList.remove(cbg);
	}
	
	public Image[] getScaledTextures(int pixelSize, int hints)
	{
		Image[] scaledTextures = new Image[textures.length];
		for(int id = 0; id < textures.length; id++)
		{
			scaledTextures[id] = getScaledTexture(id, pixelSize, hints);
		}
		return scaledTextures;
	}
	
	public Image getScaledTexture(int id, int pixelSize, int hints)
	{
		return textures[id].getScaledInstance(pixelSize, pixelSize, hints);
	}
	
	public void loadTextureFile(int id, File textureFile)
	{
		try
		{
			textures[id] = ImageIO.read(textureFile);
			textureFiles[id] = textureFile;
		}
		catch(Exception ex1)
		{
			try
			{
				textures[id] = ImageIO.read(DEFAULT_TEXTURE_FILES[id]);
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
				System.exit(0);
			}
		}
		for(ChessboardGraphics cbg : cbgList)
		{
			cbg.setScaledResource(getScaledTexture(id, cbg.getSquareSize(), Image.SCALE_SMOOTH), id);
		}
	}
	
	private void loadTexture(int id)
	{
		try
		{
			textures[id] = ImageIO.read(textureFiles[id]);
		}
		catch(Exception ex1)
		{
			try
			{
				textures[id] = ImageIO.read(DEFAULT_TEXTURE_FILES[id]);
			}
			catch(Exception ex2)
			{
				ex2.printStackTrace();
				System.exit(0);
			}
		}
		for(ChessboardGraphics cbg : cbgList)
		{
			cbg.setScaledResource(getScaledTexture(id, cbg.getSquareSize(), Image.SCALE_SMOOTH), id);
		}
	}
}
