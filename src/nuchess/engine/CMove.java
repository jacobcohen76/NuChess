package nuchess.engine;

public final class CMove
{	
	private short move;
	
	public CMove(short raw16bits)
	{
		move = raw16bits;
	}
	
	public CMove(int from, int to, int flags)
	{
		move = 0;
		move |= from << 0;
		move |= to << 6;
		move |= flags << 12;
	}
	
	public CMove(String from, String to, int flags)
	{
		this(Square.makeSquare(from), Square.makeSquare(to), flags);
	}
	
	public final int from()
	{
		return move & 0x3F;
	}
	
	public final int to()
	{
		return (move & 0xFC0) >> 6;
	}
	
	public final int flags()
	{
		return (move >> 12) & 0xF;
	}
	
	public final int captureTarget()
	{
		return	flags() == EP_CAPTURE ?
					epCaptureTarget() :
					to();
	}
	
	public final int epCaptureTarget()
	{
		return to() + ((from() - to()) < 0 ? -8 : +8);
	}
	
	public final boolean isCapture()
	{
		return ((move >> 14) & 1) == 1;
	}
	
	public final boolean isPromo()
	{
		return ((move >> 15) & 1) == 1;
	}
	
	public final String toString()
	{
		return Square.coord(from()) + " -> " + Square.coord(to()) + ": " + flagsToString(flags());
	}
	
	public final int hashCode()
	{
		return move;
	}
	
	public final int promotedTo()
	{
		switch(flags())
		{
			case KNIGHT_PROMO:	case KNIGHT_PROMO_CAP:	return Piece.KNIGHT;
			case BISHOP_PROMO:	case BISHOP_PROMO_CAP:	return Piece.BISHOP;
			case ROOK_PROMO: 	case ROOK_PROMO_CAP:	return Piece.ROOK;
			case QUEEN_PROMO:	case QUEEN_PROMO_CAP:	return Piece.QUEEN;
			default:									return Piece.NULL;
		}
	}
	
	public final long captureBitboard()
	{
		return	isCapture() ?
					1L << captureTarget() :
					0L;
	}
	
	public static final int flags(boolean promotion, boolean capture, boolean special1, boolean special0)
	{
		int flags = 0;
		flags |= (special0	? 1 : 0) << 0;
		flags |= (special1	? 1 : 0) << 1;
		flags |= (capture	? 1 : 0) << 2;
		flags |= (promotion	? 1 : 0) << 3;
		return flags;
	}
	
	public static final String flagsToString(int flags)
	{
		switch(flags)
		{
			case QUIET:					return "QUIET";
			case DOUBLE_PAWN_PUSH:		return "DOUBLE_PAWN_PUSH";
			case KING_CASTLE:			return "KING_CASTLE";
			case QUEEN_CASTLE:			return "QUEEN_CASTLE";
			case CAPTURE:				return "CAPTURE";
			case EP_CAPTURE:			return "EP_CAPTURE";
			case KNIGHT_PROMO:			return "KNIGHT_PROMO";
			case BISHOP_PROMO:			return "BISHOP_PROMO";
			case ROOK_PROMO:			return "ROOK_PROMO";
			case QUEEN_PROMO:			return "QUEEN_PROMO";
			case KNIGHT_PROMO_CAP:		return "KNIGHT_PROMO_CAP";
			case BISHOP_PROMO_CAP:		return "BISHOP_PROMO_CAP";
			case ROOK_PROMO_CAP:		return "ROOK_PROMO_CAP";
			case QUEEN_PROMO_CAP:		return "QUEEN_PROMO_CAP";
			default:					return "?";
		}
	}
	
	public static final int QUIET				= 0b0000;
	public static final int DOUBLE_PAWN_PUSH	= 0b0001;
	public static final int KING_CASTLE			= 0b0010;
	public static final int QUEEN_CASTLE		= 0b0011;
	public static final int CAPTURE				= 0b0100;
	public static final int EP_CAPTURE			= 0b0101;
	public static final int KNIGHT_PROMO		= 0b1000;
	public static final int BISHOP_PROMO		= 0b1001;
	public static final int ROOK_PROMO			= 0b1010;
	public static final int QUEEN_PROMO			= 0b1011;
	public static final int KNIGHT_PROMO_CAP	= 0b1100;
	public static final int BISHOP_PROMO_CAP	= 0b1101;
	public static final int ROOK_PROMO_CAP		= 0b1110;
	public static final int QUEEN_PROMO_CAP		= 0b1111;
}

