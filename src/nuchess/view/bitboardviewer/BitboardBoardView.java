package nuchess.view.bitboardviewer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nuchess.engine.Bits;
import nuchess.view.bitboardviewer.squaremapping.LERF;
import nuchess.view.bitboardviewer.squaremapping.SquareMapping;
import nuchess.view.bitboardviewer.squaremarking.ResourceMarking;
import nuchess.view.bitboardviewer.squaremarking.SquareMarking;
import nuchess.view.graphics.ChessboardGraphics;
import nuchess.view.graphics.LayeredGraphics;
import nuchess.view.graphics.LayeredGraphicsPanel;
import nuchess.view.graphics.TextureIDs;

public class BitboardBoardView
{
	private LayeredGraphicsPanel lgp;
	private ChessboardGraphics cbg;
	private SquareMapping mapping;
	private SquareMarking marking;
	private long displaying;
	
	public BitboardBoardView(int squareSize, boolean flipped)
	{
		LayeredGraphics lg = new LayeredGraphics(squareSize * 8, squareSize * 8);
		cbg = new ChessboardGraphics(squareSize, flipped, lg);
		lgp = new LayeredGraphicsPanel(lg);
		mapping = new LERF();
		marking = new ResourceMarking(cbg.getScaledResources(), TextureIDs.DOT);
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
	
	public BufferedImage getRenderedImage()
	{
		return cbg.getRenderedImage();
	}
	
	public void display(long bitboard)
	{
		clear(mapping, marking, displaying, ChessboardGraphics.DOT_LAYER);
		display(mapping, marking, bitboard, ChessboardGraphics.DOT_LAYER);
		displaying = bitboard;
	}
	
	private void display(SquareMapping mapping, SquareMarking marking, long bitboard, int layer)
	{
		Graphics2D g = cbg.getLayer(layer);
		while(bitboard != 0)
		{
			marking.markSquare(cbg.getSquareSize(), cbg.isFlipped(), Bits.bitscanForward(bitboard), mapping, g);
			bitboard &= bitboard ^ -bitboard;
		}
	}
	
	private void clear(SquareMapping mapping, SquareMarking marking, long bitboard, int layer)
	{
		Graphics2D g = cbg.getLayer(layer);
		while(bitboard != 0)
		{
			marking.clearSquare(cbg.getSquareSize(), cbg.isFlipped(), Bits.bitscanForward(bitboard), mapping, g);
			bitboard &= bitboard ^ -bitboard;
		}
	}
		
	public JPanel getPanel()
	{
		return lgp;
	}
}
