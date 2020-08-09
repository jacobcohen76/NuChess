package nuchess.ui.game.view;

import java.util.List;

import nuchess.engine.CMove;
import nuchess.engine.Square;

class MoveSelector
{
	private static final short[] NULL_MOVES = new short[] {};
	
	private static int to(short move)
	{
		return (move & 0xFC0) >> 6;
	}
	
	private long moveables, storedMovables;
	private long destinations;
	private int from;
	private short[][] moves;
	private boolean enabled;
	
	protected ChessBoardView parent;
	
	public MoveSelector()
	{
		moveables = storedMovables = 0;
		destinations = 0;
		from = Square.NULL;
		moves = new short[64][64];
		parent = null;
		enabled = true;
	}
	
	public void setEnabled(boolean b)
	{
		if(b == true)
		{
			moveables = storedMovables;
			enabled = true;
		}
		else
		{
			moveables = 0;
			enabled = false;
		}
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void select(int square)
	{
		if(isDestination(square))
		{
			requestMove(from, square);
			deselect();
		}
		else if(from != square && isMoveable(square))
		{
			deselect();
			setFrom(square);
		}
		else
			deselect();
	}
	
	public void deselect()
	{
		destinations = 0;
		from = 64;
	}
	
	public boolean isClickable(int square)
	{
		return isMoveable(square) || isDestination(square);
	}
	
	public boolean isMoveable(int square)
	{
		return ((moveables >> square) & 1) == 1;
	}
	
	public void clearSelectableMoves()
	{
		for(short[] array : moves)
			clear(array);
	}
	
	public void setSelectableMoves(List<CMove> selectableMoves)
	{
		clearSelectableMoves();
		for(CMove move : selectableMoves)
		{
			moves[move.from()][move.to()] = (short) move.hashCode();
		}
	}
	
	public void setMoveableSquares(long moveableSquares)
	{
		moveables = storedMovables = moveableSquares;
		if(isEnabled() == false)
		{
			moveables = 0;
		}
	}
	
	public long getDestinations()
	{
		return destinations;
	}
	
	public int getFrom()
	{
		return from;
	}
	
	public short[] getMovesFrom()
	{
		return from < 64 ? moves[from] : NULL_MOVES;
	}
	
	private boolean isDestination(int square)
	{
		return ((destinations >> square) & 1) == 1;
	}
	
	private long getDestinationMask(int from)
	{
		long destinationMask = 0;
		for(short move : moves[from])
		{
			if(move != 0)
			{
				destinationMask |= (1L << to(move));
			}
		}
		return destinationMask;
	}
	
	private void setFrom(int square)
	{
		destinations = getDestinationMask(square);
		from = square;
	}
	
	private void clear(short[] array)
	{
		for(int i = 0; i < array.length; i++)
			array[i] = 0;
	}
	
	private void requestMove(int from, int to)
	{
		switch(CMove.flags(moves[from][to]))
		{
			case CMove.KNIGHT_PROMO:
			case CMove.BISHOP_PROMO:
			case CMove.ROOK_PROMO:
			case CMove.QUEEN_PROMO:
				parent.requestPromoMove(false, from, to);
				break;
				
			case CMove.KNIGHT_PROMO_CAP:
			case CMove.BISHOP_PROMO_CAP:
			case CMove.ROOK_PROMO_CAP:
			case CMove.QUEEN_PROMO_CAP:
				parent.requestPromoMove(true, from, to);
				break;
				
			default:
				parent.requestMove(new CMove(moves[from][to]));
				break;
		}
	}
}
