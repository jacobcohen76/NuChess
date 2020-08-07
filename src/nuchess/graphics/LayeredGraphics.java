package nuchess.graphics;


import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nuchess.util.Pair;

/**
 * 
 * @author Jacob Cohen
 *
 */
public class LayeredGraphics
{
	public static final Map<Object, Object> QUALITY_RENDERING_HINTS;
	public static final Map<Object, Object> SPEED_RENDERING_HINTS;
	public static final Map<Object, Object> DEFAULT_RENDERING_HINTS;
	
	static
	{
		QUALITY_RENDERING_HINTS = new HashMap<Object, Object>();
		QUALITY_RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		QUALITY_RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		QUALITY_RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		QUALITY_RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		QUALITY_RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		QUALITY_RENDERING_HINTS.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		SPEED_RENDERING_HINTS = new HashMap<Object, Object>();
		SPEED_RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		SPEED_RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		SPEED_RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		SPEED_RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		SPEED_RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		SPEED_RENDERING_HINTS.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
		DEFAULT_RENDERING_HINTS = new HashMap<Object, Object>();
		DEFAULT_RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		DEFAULT_RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		DEFAULT_RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
		DEFAULT_RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		DEFAULT_RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
		DEFAULT_RENDERING_HINTS.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	private int w, h, imageType;
	private Map<?, ?> hints;
	private ArrayList<Pair<BufferedImage, Graphics2D>> layers;
	
	public LayeredGraphics(int w, int h, int imageType, Map<?, ?> hints)
	{
		this.w = w;
		this.h = h;
		this.imageType = imageType;
		this.hints = hints;
		layers = new ArrayList<Pair<BufferedImage, Graphics2D>>();
	}
	
	public LayeredGraphics(int w, int h)
	{
		this(w, h, BufferedImage.TYPE_INT_ARGB, DEFAULT_RENDERING_HINTS);
	}
	
	public void setImageType(int imageType)
	{
		this.imageType = imageType;
	}
	
	public void setHints(Map<?, ?> hints)
	{
		this.hints = hints;
	}
	
	public void setSize(int w, int h)
	{
		this.w = w;
		this.h = h;
	}
	
	public Dimension getNewDimensions()
	{
		return new Dimension(w, h);
	}
	
	private Pair<BufferedImage, Graphics2D> getNewLayer()
	{
		BufferedImage img = new BufferedImage(w, h, imageType);
		Graphics2D g2D = img.createGraphics();
		g2D.setRenderingHints(hints);
		return new Pair<BufferedImage, Graphics2D>(img, g2D);
	}
	
	public void addNewLayer()
	{
		layers.add(getNewLayer());
	}
	
	public void addNewLayerAt(int index)
	{
		layers.add(index, getNewLayer());
	}
	
	public int numLayers()
	{
		return layers.size();
	}
	
	public void clear(Graphics2D g2D, int x, int y, int w, int h)
	{
		g2D.setComposite(AlphaComposite.Clear);
		g2D.fillRect(x, y, w, h);
		g2D.setComposite(AlphaComposite.SrcOver);
	}
	
	public void clear(int index)
	{
		BufferedImage bimg = getImage(index);
		clear(getGraphics(index), 0, 0, bimg.getWidth(), bimg.getHeight());
	}
	
	public void clear(int index, int x, int y, int w, int h)
	{
		clear(getGraphics(index), x, y, w, h);
	}
	
	public Pair<BufferedImage, Graphics2D> remove(int index)
	{
		return layers.remove(index);
	}
	
	public BufferedImage getImage(int index)
	{
		return layers.get(index).first;
	}
	
	public Graphics2D getGraphics(int index)
	{
		return layers.get(index).second;
	}
	
	public void swap(int index1, int index2)
	{
		Pair<BufferedImage, Graphics2D> temp = layers.get(index1);
		layers.set(index1, layers.get(index2));
		layers.set(index2, temp);
	}
	
	public void removeAll()
	{
		while(numLayers() != 0)
		{
			remove(0);
		}
	}
	
	public BufferedImage merge()
	{		
		Pair<BufferedImage, Graphics2D> layer = getNewLayer();
		
		for(Pair<BufferedImage, Graphics2D> p : layers)
		{
			layer.second.drawImage(p.first, 0, 0, p.first.getWidth(), p.first.getHeight(), null);
		}
		
		return layer.first;
	}
	
	public void render(Graphics g, ImageObserver observer)
	{
		for(Pair<BufferedImage, Graphics2D> p : layers)
		{
			g.drawImage(p.first, 0, 0, p.first.getWidth(), p.first.getHeight(), observer);
		}
	}
}
