package nuchess.view.bitboardviewer;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nuchess.view.graphics.ChessboardGraphics;
import nuchess.view.graphics.LayeredGraphics;
import nuchess.view.graphics.LayeredGraphicsPanel;

public class BitboardBoardView
{
	private LayeredGraphicsPanel lgp;
	private ChessboardGraphics cbg;
	private long displaying;
	
	public BitboardBoardView(int squareSize, boolean flipped)
	{
		LayeredGraphics lg = new LayeredGraphics(squareSize * 8, squareSize * 8);
		cbg = new ChessboardGraphics(squareSize, flipped, lg);
		lgp = new LayeredGraphicsPanel(lg);
		displaying = 0L;
		initGraphics();
		resizeViewPanel();
	}
	
	private void initGraphics()
	{
		cbg.paintBackground();
	}
	
	private void resizeViewPanel()
	{
		lgp.setSize(cbg.getDimensions());
	}
	
	public void display(long bitboard)
	{
		cbg.clear(displaying, ChessboardGraphics.DOT_LAYER);
		cbg.paintDots(bitboard);
		displaying = bitboard;
	}
	
	public BufferedImage getRenderedImage()
	{
		return cbg.getRenderedImage();
	}
	
	public long getDisplaying()
	{
		return displaying;
	}
		
	public JPanel getPanel()
	{
		return lgp;
	}
}
