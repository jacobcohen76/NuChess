package nuchess.view.settings;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import nuchess.engine.Color;
import nuchess.engine.Piece;
import nuchess.view.graphics.ResourceManager;

public class SettingPieceSelection extends Setting
{
	private static final long serialVersionUID = 7928359005982110872L;
	
	private TextureBrowseButton chooseWhiteButton, chooseBlackButton;
	private File defaultSelection;
	private int piece;
	
	public SettingPieceSelection(File defaultSelection, int piece)
	{
		this.defaultSelection = defaultSelection;
		this.piece = piece;
//		setOpaque(false);
		initComponents();
		putConstraints();
		initListeners();
		addComponents();
	}
	
	public SettingPieceSelection(int piece)
	{
		this(null, piece);
	}
	
	protected void initComponents()
	{
		chooseWhiteButton = new TextureBrowseButton(Piece.pieceCode(piece, Color.WHITE), "White");
		chooseBlackButton = new TextureBrowseButton(Piece.pieceCode(piece, Color.BLACK), "Black");
	}
	
	protected void putConstraints()
	{
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, chooseWhiteButton, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, chooseWhiteButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, chooseBlackButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, chooseBlackButton, 0, SpringLayout.EAST, this);
		setLayout(layout);
	}
	
	protected void initListeners()
	{
		
	}
	
	protected void addComponents()
	{
		add(chooseWhiteButton);
		add(chooseBlackButton);
	}
}
