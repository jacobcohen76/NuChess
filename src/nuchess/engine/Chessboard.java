package nuchess.engine;

public final class Chessboard
{
	public static final int OCC                   = 0xE;
	
	private static final int KCW                  = 0b1000;
	private static final int QCW                  = 0b0100;
	private static final int KCB                  = 0b0010;
	private static final int QCB                  = 0b0001;
	
	private static final long KCW_OCC             = 0x0000000000000060L;
	private static final long QCW_OCC             = 0x000000000000000EL;
	private static final long KCB_OCC             = 0x6000000000000000L;
	private static final long QCB_OCC             = 0x0E00000000000000L;
	
	private static final int modifiedCastlingRights(int square)
	{
		switch(square)
		{
			case Square.a1:		return 0b1011;
			case Square.e1:		return 0b0011;
			case Square.h1:		return 0b0111;
			case Square.a8:		return 0b1110;
			case Square.e8:		return 0b1100;
			case Square.h8:		return 0b1101;
			default:			return 0b1111;
		}
	}
	
	public long[] bitboards;
	public long zobristKey, checks;
	public int[] material, king, unmakeInfo;
	public int toMove, ply, halfmoveClock, castlingRights, epTarget;
	
	public Chessboard(String FEN)
	{
		this();
		loadFEN(FEN);
	}
	
	public Chessboard()
	{
		bitboards = new long[15];
		zobristKey = 0L;
		checks = 0L;
		material = new int[15];
		king = new int[2];
		unmakeInfo = new int[200];
		toMove = Color.WHITE;
		ply = 0;
		halfmoveClock = 0;
		castlingRights = 0;
		epTarget = Square.NULL;
	}
	
	public MoveList generateMoves()
	{
		MoveList moves = new MoveList();
		return inCheck() ? generateMovesInCheck(moves) : generateMoves(moves);
	}
	
	private MoveList generateMovesInCheck(MoveList moves)
	{
		if(getNumChecks() > 1)
		{
			addKingMoves(moves);
		}
		else
		{
			int source = Bits.bitscanForward(checks);
			long between = sliding(source, toMove ^ 1) ? Bitboards.between(king[toMove], source) : 0L;
			addPawnMoves(moves, between, checks);
			addKnightMoves(moves, between, checks);
			addBishopMoves(moves, between, checks);
			addRookMoves(moves, between, checks);
			addQueenMoves(moves, between, checks);
			addPromotionMoves(moves, between, checks);
			addKingMoves(moves);
		}
		return moves;
	}
	
	private MoveList generateMoves(MoveList moves)
	{
		addPawnMoves(moves);
		addKnightMoves(moves);
		addBishopMoves(moves);
		addRookMoves(moves);
		addQueenMoves(moves);
		addKingMoves(moves);
		addPromotionMoves(moves);
		addCastleMoves(moves);
		return moves;
	}
	
	public boolean canMake(CMove move)
	{
		return	(contains(Piece.WHITE_KING + toMove, move.from()) ?
					(!isAttacked(move.to(), toMove ^ 1, occAfter(move))) :
					(attacksTo(king[toMove], toMove ^ 1, occAfter(move)) & ~move.captureBitboard()) == 0);
	}
	
	public void make(CMove move)
	{
		int moving = pieceAt(move.from(), toMove);
		unmakeInfo[ply] = Info.pack(halfmoveClock, epTarget, castlingRights);
		halfmoveClock++;
		if(move.flags() == CMove.KING_CASTLE)
		{
			if(toMove == Color.WHITE)	move(Piece.WHITE_ROOK, Square.h1, Square.f1);
			else						move(Piece.BLACK_ROOK, Square.h8, Square.f8);
		}
		else if(move.flags() == CMove.QUEEN_CASTLE)
		{
			if(toMove == Color.WHITE)	move(Piece.WHITE_ROOK, Square.a1, Square.d1);
			else						move(Piece.BLACK_ROOK, Square.a8, Square.d8);
		}
		else if(move.flags() == CMove.EP_CAPTURE)
		{
			if(toMove == Color.WHITE)	capture(Piece.BLACK_PAWN, move.to() - 8);
			else						capture(Piece.WHITE_PAWN, move.to() + 8);
		}
		else
		{
			if(move.isCapture())
			{
				unmakeInfo[ply] = Info.setCaptured(unmakeInfo[ply], capture(move.to()));
			}
			if(move.isPromo())
			{
				moving = Piece.pieceCode(move.promotedTo(), toMove);
				promote(Piece.WHITE_PAWN + toMove, moving, move.from());
			}
		}
		move(moving, move.from(), move.to());
		epTarget = move.flags() == CMove.DOUBLE_PAWN_PUSH ? (toMove == Color.WHITE ? -8 : +8) + move.to() : Square.NULL;
		toMove ^= 1;
		checks = attacksTo(king[toMove], toMove ^ 1, bitboards[OCC]);
		ply++;
	}
	
	public void unmake(CMove move)
	{
		ply--;
		toMove ^= 1;
		move(move.to(), move.from());
		if(move.flags() == CMove.KING_CASTLE)
		{
			if(toMove == Color.WHITE)	move(Piece.WHITE_ROOK, Square.f1, Square.h1);
			else						move(Piece.BLACK_ROOK, Square.f8, Square.h8);
		}
		else if(move.flags() == CMove.QUEEN_CASTLE)
		{
			if(toMove == Color.WHITE)	move(Piece.WHITE_ROOK, Square.d1, Square.a1);
			else						move(Piece.BLACK_ROOK, Square.d8, Square.a8);
		}
		else if(move.flags() == CMove.EP_CAPTURE)
		{
			if(toMove == Color.WHITE)	put(Piece.BLACK_PAWN, move.to() - 8);
			else						put(Piece.WHITE_PAWN, move.to() + 8);
		}
		else
		{
			if(move.isCapture())
			{
				put(Info.captured(unmakeInfo[ply]), move.to());
			}
			if(move.isPromo())
			{
				promote(Piece.pieceCode(move.promotedTo(), toMove), Piece.WHITE_PAWN + toMove, move.from());
			}
		}
		checks = attacksTo(king[toMove], toMove ^ 1, bitboards[OCC]);
		halfmoveClock = Info.halfmoveClock(unmakeInfo[ply]);
		castlingRights = Info.castlingRights(unmakeInfo[ply]);
		epTarget = Info.epTarget(unmakeInfo[ply]);
	}
	
	public int getNumChecks()
	{
		return Bits.bitCount(checks);
	}
	
	public boolean inCheck()
	{
		return checks != 0L;
	}
	
	public void promote(int piece, int promotion, int square)
	{
		bitboards[piece] &= ~(1L << square);
		bitboards[piece & 1] &= ~(1L << square);
//		zobristKey ^= 
		
		bitboards[promotion] |= 1L << square;
		bitboards[promotion & 1] |= 1L << square;
//		zobristKey ^= 
	}
	
	public void move(int from, int to)
	{
		move(pieceAt(from, toMove), from, to);
	}
	
	public void move(int piece, int from, int to)
	{
		bitboards[piece] &= ~(1L << from);
		bitboards[piece & 1] &= ~(1L << from);
		bitboards[OCC] &= ~(1L << from);
//		zobristKey ^= 
		
		bitboards[piece] |= 1L << to;
		bitboards[piece & 1] |= 1L << to;
		bitboards[OCC] |= 1L << to;
//		zobristKey ^= 
		
		if(Piece.pieceType(piece) == Piece.PAWN)
		{
			halfmoveClock = 0;
		}
		else if(king[toMove] == from)
		{
			king[toMove] = to;
		}
		castlingRights &= modifiedCastlingRights(from);
//		zobristKey ^= 
	}
	
	public int capture(int square)
	{
		int piece = pieceAt(square, toMove ^ 1);
		remove(piece, square);
		halfmoveClock = 0;
		castlingRights &= modifiedCastlingRights(square);
//		zobristKey ^= 
		return piece;
	}
	
	public void capture(int piece, int square)
	{
		remove(piece, square);
		halfmoveClock = 0;
		castlingRights &= modifiedCastlingRights(square);
//		zobristKey ^= 
	}
	
	public void put(int piece, int square)
	{
		bitboards[piece] |= 1L << square;
		bitboards[piece & 1] |= 1L << square;
		bitboards[OCC] |= 1L << square;
		
		material[piece]++;
		material[piece & 1]++;
		material[OCC]++;
		
		if(Piece.pieceType(piece) == Piece.KING)
		{
			king[piece & 1] = square;
		}
//		zobristKey ^= 
	}
	
	public void remove(int piece, int square)
	{
		bitboards[piece] &= ~(1L << square);
		bitboards[piece & 1] &= ~(1L << square);
		bitboards[OCC] &= ~(1L << square);
		
		material[piece]--;
		material[piece & 1]--;
		material[OCC]--;
		
//		zobristKey ^= 
	}
	
	public boolean isAttacked(int square, int by, long occ)
	{
		return	(Attacks.pawnAttacks   (square, by ^ 1) & bitboards[Piece.WHITE_PAWN   + by]) != 0 ||
				(Attacks.knightAttacks (square)         & bitboards[Piece.WHITE_KNIGHT + by]) != 0 ||
				(Attacks.bishopAttacks (square, occ)    & bitboards[Piece.WHITE_BISHOP + by]) != 0 ||
				(Attacks.rookAttacks   (square, occ)    & bitboards[Piece.WHITE_ROOK   + by]) != 0 ||
				(Attacks.queenAttacks  (square, occ)    & bitboards[Piece.WHITE_QUEEN  + by]) != 0 ||
				(Attacks.kingAttacks   (square)         & bitboards[Piece.WHITE_KING   + by]) != 0;
	}
	
	public boolean contains(int piece, int square)
	{
		return ((bitboards[piece] >> square) & 1) == 1;
	}
	
	public boolean sliding(int square)
	{
		return	((bitboards[Piece.WHITE_BISHOP] >> square) & 1) == 1 ||
				((bitboards[Piece.BLACK_BISHOP] >> square) & 1) == 1 ||
				((bitboards[Piece.WHITE_ROOK]   >> square) & 1) == 1 ||
				((bitboards[Piece.BLACK_ROOK]   >> square) & 1) == 1 ||
				((bitboards[Piece.WHITE_QUEEN]  >> square) & 1) == 1 ||
				((bitboards[Piece.BLACK_QUEEN]  >> square) & 1) == 1;
	}
	
	public boolean sliding(int square, int color)
	{
		return	((bitboards[Piece.WHITE_BISHOP + color] >> square) & 1) == 1 ||
				((bitboards[Piece.WHITE_ROOK   + color] >> square) & 1) == 1 ||
				((bitboards[Piece.WHITE_QUEEN  + color] >> square) & 1) == 1;
	}
	
	public boolean empty(int square)
	{
		return ((bitboards[OCC] >> square) & 1) == 0;
	}
	
	public int pieceAt(int square)
	{
		     if(((bitboards[Piece.WHITE_PAWN]   >> square) & 1) == 1)     return Piece.WHITE_PAWN;
		else if(((bitboards[Piece.BLACK_PAWN]   >> square) & 1) == 1)     return Piece.BLACK_PAWN;
		else if(((bitboards[Piece.WHITE_KNIGHT] >> square) & 1) == 1)     return Piece.WHITE_KNIGHT;
		else if(((bitboards[Piece.BLACK_KNIGHT] >> square) & 1) == 1)     return Piece.BLACK_KNIGHT;
		else if(((bitboards[Piece.WHITE_BISHOP] >> square) & 1) == 1)     return Piece.WHITE_BISHOP;
		else if(((bitboards[Piece.BLACK_BISHOP] >> square) & 1) == 1)     return Piece.BLACK_BISHOP;
		else if(((bitboards[Piece.WHITE_ROOK]   >> square) & 1) == 1)     return Piece.WHITE_ROOK;
		else if(((bitboards[Piece.BLACK_ROOK]   >> square) & 1) == 1)     return Piece.BLACK_ROOK;
		else if(((bitboards[Piece.WHITE_QUEEN]  >> square) & 1) == 1)     return Piece.WHITE_QUEEN;
		else if(((bitboards[Piece.BLACK_QUEEN]  >> square) & 1) == 1)     return Piece.BLACK_QUEEN;
		else if(((bitboards[Piece.WHITE_KING]   >> square) & 1) == 1)     return Piece.WHITE_KING;
		else if(((bitboards[Piece.BLACK_KING]   >> square) & 1) == 1)     return Piece.BLACK_KING;
		else                                                              return Piece.NULL;
	}
	
	public int pieceAt(int square, int color)
	{
		     if(((bitboards[Piece.WHITE_PAWN   + color] >> square) & 1) == 1)     return Piece.WHITE_PAWN   + color;
		else if(((bitboards[Piece.WHITE_KNIGHT + color] >> square) & 1) == 1)     return Piece.WHITE_KNIGHT + color;
		else if(((bitboards[Piece.WHITE_BISHOP + color] >> square) & 1) == 1)     return Piece.WHITE_BISHOP + color;
		else if(((bitboards[Piece.WHITE_ROOK   + color] >> square) & 1) == 1)     return Piece.WHITE_ROOK   + color;
		else if(((bitboards[Piece.WHITE_QUEEN  + color] >> square) & 1) == 1)     return Piece.WHITE_QUEEN  + color;
		else if(((bitboards[Piece.WHITE_KING   + color] >> square) & 1) == 1)     return Piece.WHITE_KING   + color;
		else                                                                      return Piece.NULL;
	}
	
	public final long attacksTo(int square, int by, long occ)
	{
		return	Attacks.pawnAttacks   (square, by ^ 1) & bitboards[Piece.WHITE_PAWN   + by] |
				Attacks.knightAttacks (square)         & bitboards[Piece.WHITE_KNIGHT + by] |
				Attacks.bishopAttacks (square, occ)    & bitboards[Piece.WHITE_BISHOP + by] |
				Attacks.rookAttacks   (square, occ)    & bitboards[Piece.WHITE_ROOK   + by] |
				Attacks.queenAttacks  (square, occ)    & bitboards[Piece.WHITE_QUEEN  + by] |
				Attacks.kingAttacks   (square)         & bitboards[Piece.WHITE_KING   + by];
	}
	
	public long occAfter(CMove move)
	{
		return	move.flags() == CMove.EP_CAPTURE ?
					((bitboards[OCC] & ~(1L << move.from())) & ~(1L << ((toMove == Color.WHITE ? -8 : +8) + move.to())) | (1L << move.to())) :
					((bitboards[OCC] & ~(1L << move.from())) | (1L << move.to()));
	}
	
	public String getASCIIboard()
	{
		StringBuilder sb = new StringBuilder(FENFormatter.ASCII_BOARD_LENGTH);
		for(int rank = Square.rank_8; Square.rank_1 <= rank; rank--)
		{
			for(int file = Square.file_a; file <= Square.file_h; file++)
			{
				sb.append(empty(Square.makeSquare(rank, file)) ? '.' : FENFormatter.pieceCharacter(pieceAt(Square.makeSquare(rank, file))));
				sb.append(' ');
			}
			sb.setCharAt(sb.length() - 1, '\n');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public String getASCIIboard(int bitboardIndex)
	{
		StringBuilder sb = new StringBuilder(FENFormatter.ASCII_BOARD_LENGTH);
		for(int rank = Square.rank_8; Square.rank_1 <= rank; rank--)
		{
			for(int file = Square.file_a; file <= Square.file_h; file++)
			{
				sb.append(contains(bitboardIndex, Square.makeSquare(rank, file)) ? FENFormatter.bitboardCharacter(bitboardIndex) : '.');
				sb.append(' ');
			}
			sb.setCharAt(sb.length() - 1, '\n');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public String getDebugVarInfo()
	{
		return	"zobristKey     = " + Bits.toBinaryString(zobristKey) + " = " + zobristKey + "\n" +
				"toMove         = " + Bits.toBinaryString(toMove) + " = " + (toMove == Color.WHITE ? "WHITE" : "BLACK") + "\n" +
				"ply            = " + Bits.toBinaryString(ply) + " = " + ply + "\n" +
				"fullmoveNumber = " + Bits.toBinaryString((ply >> 1) + 1) + " = " + ((ply >> 1) + 1) + "\n" +
				"halfmoveClock  = " + Bits.toBinaryString(halfmoveClock) + " = " + halfmoveClock + "\n" +
				"castlingRights = " + Bits.toBinaryString(castlingRights) + " = " + FENFormatter.formatFENcastlingRights(castlingRights) + "\n" +
				"epTarget       = " + Bits.toBinaryString(epTarget) + " = " + FENFormatter.formatFENepTarget(epTarget) + "\n" +
				"numChecks      = " + Bits.toBinaryString(getNumChecks()) + " = " + getNumChecks();
	}
	
	public String getFEN()
	{
		return	formatFENlayout() + " " +
				FENFormatter.formatFENtoMove(toMove) + " " +
				FENFormatter.formatFENcastlingRights(castlingRights) + " " + 
				FENFormatter.formatFENepTarget(epTarget) + " " +
				FENFormatter.formatFENhalfmoveClock(halfmoveClock) + " " +
				FENFormatter.formatFENfullmoveNumber(ply);
	}
	
	private String formatFENlayout()
	{
		StringBuilder sb = new StringBuilder(FENFormatter.LONGEST_FEN_LAYOUT);
		int emptyCount = 0;
		for(int rank = Square.rank_8; Square.rank_1 <= rank; rank--)
		{
			for(int file = Square.file_a; file <= Square.file_h; file++)
			{
				if(empty(Square.makeSquare(rank, file)))
				{
					emptyCount++;
				}
				else
				{
					if(emptyCount != 0)						sb.append(emptyCount);
					sb.append(FENFormatter.pieceCharacter(pieceAt(Square.makeSquare(rank, file))));
					emptyCount = 0;
				}
			}
			if(emptyCount != 0)								sb.append(emptyCount);
			if(rank != Square.rank_1)						sb.append("/");
			emptyCount = 0;
		}
		return sb.toString();
	}
	
	public void loadFEN(String FEN)
	{
		clear();
		String[] elements = FEN.trim().split("\\s+");
		loadFENlayout(elements[FENParser.LAYOUT]);
		toMove = FENParser.parseFENtoMove(elements[FENParser.TO_MOVE]);
		castlingRights = FENParser.parseFENcastlingRights(elements[FENParser.CASTLING_RIGHTS]);
		epTarget = FENParser.parseFENepTarget(elements[FENParser.EP_TARGET]);
		halfmoveClock = FENParser.parseFENhalfmoveClock(elements[FENParser.HALFMOVE_CLOCK]);
		ply = ((FENParser.parseFENfullmoveNumber(elements[FENParser.FULLMOVE_NUMBER]) - 1) << 1) + toMove;
		checks = attacksTo(king[toMove], toMove ^ 1, bitboards[OCC]);
	}
	
	private void loadFENlayout(String FENlayout)
	{
		int i = 0, rank = Square.rank_8, file = Square.file_a;
		char ch;
		while(i < FENlayout.length() && (ch = FENlayout.charAt(i++)) != ' ')
		{
			if(ch == '/')
			{
				rank--;
				file = 0;
			}
			else if(FENParser.isDigit(ch))
			{
				file += ch - '0';
			}
			else
			{
				put(FENParser.piece(ch), Square.makeSquare(rank, file));
				file++;
			}
		}
	}
	
	public void clear()
	{
		for(int bb = 0; bb <= OCC; bb++)
		{
			bitboards[bb] = 0L;
			material[bb] = 0;
		}
		zobristKey = 0L;
		toMove = Color.WHITE;
		ply = 0;
		halfmoveClock = 0;
		castlingRights = 0;
	}
	
	public String getSAN(CMove move)
	{
		StringBuilder SAN = new StringBuilder();
		
		if(move.flags() == CMove.KING_CASTLE)
		{
			SAN.append("O-O");
		}
		else if(move.flags() == CMove.QUEEN_CASTLE)
		{
			SAN.append("O-O-O");
		}
		else
		{
			int moving = pieceAt(move.from());
			if(Piece.pieceType(moving) == Piece.PAWN)
			{
				if(move.isCapture())
				{
					SAN.append(Square.fileCharacter(Square.file(move.from())));
				}
			}
			else
			{
				SAN.append(FENFormatter.pieceCharacter(moving));
				long alt = Attacks.attacks(moving, move.to(), bitboards[OCC]) & bitboards[moving] & ~(1L << move.from());
				boolean disambiguateFile = false, disambiguateRank = false;
				int numAlts = 0;
				while(alt != 0)
				{
					int square = Bits.bitscanForward(alt);
					disambiguateFile |= Square.file(square) != Square.rank(move.from());
					disambiguateRank |= Square.rank(square) != Square.rank(move.from());
					alt &= alt ^ -alt;
					numAlts++;
				}
				if(disambiguateFile && disambiguateRank && 2 <= numAlts)
				{
					SAN.append(Square.fileCharacter(Square.file(move.from())));
					SAN.append(Square.rank(move.from()) + 1);
				}
				else if(disambiguateFile)
				{
					SAN.append(Square.fileCharacter(Square.file(move.from())));
				}
				else if(disambiguateRank)
				{
					SAN.append(Square.rank(move.from()) + 1);
				}
			}
			if(move.isCapture())
			{
				SAN.append('x');
			}
			SAN.append(Square.coord(move.to()));
			if(move.flags() == CMove.EP_CAPTURE)
			{
				SAN.append("e.p.");
			}
			else if(move.isPromo())
			{
				SAN.append(FENFormatter.pieceCharacter(Piece.pieceCode(move.promotedTo(), toMove)));
			}
			if(isCheck(move))
			{
				SAN.append('+');
			}
		}
		return SAN.toString();
	}
	
	public final boolean isCheck(CMove move)
	{
		return isDirectCheck(move) || isDiscoveryCheck(move);
	}
	
	public final boolean isDirectCheck(CMove move)
	{
		return (Attacks.attacks(pieceAt(move.from()), move.to(), occAfter(move)) & bitboards[Piece.WHITE_KING + toMove ^ 1]) != 0;
	}
	
	public final boolean isDiscoveryCheck(CMove move)
	{
		return attacksTo(king[toMove ^ 1], toMove, occAfter(move)) != 0;
	}
	
	private void addPawnMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_PAWN + toMove] & ~Bitboards.promotionRank(toMove);
		long epbb = epTarget == Square.NULL ? 0L : (1L << epTarget);
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC], from, CMove.QUIET);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & bitboards[toMove ^ 1], from, CMove.CAPTURE);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & epbb, from, CMove.EP_CAPTURE);
			if((Attacks.pawnSinglePush(from, toMove) & bitboards[OCC]) == 0)
			{
				addMoves(moves, Attacks.pawnDoublePush(from, toMove) & ~bitboards[OCC], from, CMove.DOUBLE_PAWN_PUSH);
			}
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addKnightMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_KNIGHT + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.knightAttacks(from) & ~bitboards[OCC], from, CMove.QUIET);
			addMoves(moves, Attacks.knightAttacks(from) & bitboards[toMove ^ 1], from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addBishopMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_BISHOP + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.bishopAttacks(from, bitboards[OCC]) & ~bitboards[OCC], from, CMove.QUIET);
			addMoves(moves, Attacks.bishopAttacks(from, bitboards[OCC]) & bitboards[toMove ^ 1], from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addRookMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_ROOK + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.rookAttacks(from, bitboards[OCC]) & ~bitboards[OCC], from, CMove.QUIET);
			addMoves(moves, Attacks.rookAttacks(from, bitboards[OCC]) & bitboards[toMove ^ 1], from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addQueenMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_QUEEN + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.queenAttacks(from, bitboards[OCC]) & ~bitboards[OCC], from, CMove.QUIET);
			addMoves(moves, Attacks.queenAttacks(from, bitboards[OCC]) & bitboards[toMove ^ 1], from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addKingMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_KING + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.kingAttacks(from) & ~bitboards[OCC], from, CMove.QUIET);
			addMoves(moves, Attacks.kingAttacks(from) & bitboards[toMove ^ 1], from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addPromotionMoves(MoveList moves)
	{
		long fromSet = bitboards[Piece.WHITE_PAWN + toMove] & Bitboards.promotionRank(toMove);
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC], from, CMove.QUEEN_PROMO);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC], from, CMove.ROOK_PROMO);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC], from, CMove.BISHOP_PROMO);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC], from, CMove.KNIGHT_PROMO);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & bitboards[toMove ^ 1], from, CMove.QUEEN_PROMO_CAP);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & bitboards[toMove ^ 1], from, CMove.ROOK_PROMO_CAP);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & bitboards[toMove ^ 1], from, CMove.BISHOP_PROMO_CAP);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & bitboards[toMove ^ 1], from, CMove.KNIGHT_PROMO_CAP);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addCastleMoves(MoveList moves)
	{
		if(toMove == Color.WHITE)
		{
			if((castlingRights & KCW) != 0 && (KCW_OCC & bitboards[OCC]) == 0 && !isAttacked(Square.f1, toMove ^ 1, bitboards[OCC]))
				moves.add(new CMove(Square.e1, Square.g1, CMove.KING_CASTLE));
			if((castlingRights & QCW) != 0 && (QCW_OCC & bitboards[OCC]) == 0 && !isAttacked(Square.d1, toMove ^ 1, bitboards[OCC]))
				moves.add(new CMove(Square.e1, Square.c1, CMove.QUEEN_CASTLE));
		}
		else
		{
			if((castlingRights & KCB) != 0 && (KCB_OCC & bitboards[OCC]) == 0 && !isAttacked(Square.f8, toMove ^ 1, bitboards[OCC]))
				moves.add(new CMove(Square.e8, Square.g8, CMove.KING_CASTLE));
			if((castlingRights & QCB) != 0 && (QCB_OCC & bitboards[OCC]) == 0 && !isAttacked(Square.d8, toMove ^ 1, bitboards[OCC]))
				moves.add(new CMove(Square.e8, Square.c8, CMove.QUEEN_CASTLE));
		}
	}
	
	private void addPawnMoves(MoveList moves, long block, long source)
	{
		long fromSet = bitboards[Piece.WHITE_PAWN + toMove] & ~Bitboards.promotionRank(toMove);
		long epbb = epTarget == Square.NULL ? 0L : (1L << epTarget);
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC] & block, from, CMove.QUIET);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & source, from, CMove.CAPTURE);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & epbb & source, from, CMove.EP_CAPTURE);
			if((Attacks.pawnSinglePush(from, toMove) & bitboards[OCC]) == 0)
			{
				addMoves(moves, Attacks.pawnDoublePush(from, toMove) & ~bitboards[OCC] & block, from, CMove.DOUBLE_PAWN_PUSH);
			}
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addKnightMoves(MoveList moves, long block, long source)
	{
		long fromSet = bitboards[Piece.WHITE_KNIGHT + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.knightAttacks(from) & ~bitboards[OCC] & block, from, CMove.QUIET);
			addMoves(moves, Attacks.knightAttacks(from) & source, from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addBishopMoves(MoveList moves, long block, long source)
	{
		long fromSet = bitboards[Piece.WHITE_BISHOP + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.bishopAttacks(from, bitboards[OCC]) & ~bitboards[OCC] & block, from, CMove.QUIET);
			addMoves(moves, Attacks.bishopAttacks(from, bitboards[OCC]) & source, from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addRookMoves(MoveList moves, long block, long source)
	{
		long fromSet = bitboards[Piece.WHITE_ROOK + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.rookAttacks(from, bitboards[OCC]) & ~bitboards[OCC] & block, from, CMove.QUIET);
			addMoves(moves, Attacks.rookAttacks(from, bitboards[OCC]) & source, from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addQueenMoves(MoveList moves, long block, long source)
	{
		long fromSet = bitboards[Piece.WHITE_QUEEN + toMove];
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.queenAttacks(from, bitboards[OCC]) & ~bitboards[OCC] & block, from, CMove.QUIET);
			addMoves(moves, Attacks.queenAttacks(from, bitboards[OCC]) & source, from, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addPromotionMoves(MoveList moves, long block, long source)
	{
		long fromSet = bitboards[Piece.WHITE_PAWN + toMove] & Bitboards.promotionRank(toMove);
		while(fromSet != 0)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC] & block, from, CMove.QUEEN_PROMO);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC] & block, from, CMove.ROOK_PROMO);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC] & block, from, CMove.BISHOP_PROMO);
			addMoves(moves, Attacks.pawnSinglePush(from, toMove) & ~bitboards[OCC] & block, from, CMove.KNIGHT_PROMO);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & source, from, CMove.QUEEN_PROMO_CAP);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & source, from, CMove.ROOK_PROMO_CAP);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & source, from, CMove.BISHOP_PROMO_CAP);
			addMoves(moves, Attacks.pawnAttacks(from, toMove) & source, from, CMove.KNIGHT_PROMO_CAP);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private void addMoves(MoveList moves, long toSet, int from, int flags)
	{
		while(toSet != 0)
		{
			moves.add(new CMove(from, Bits.bitscanForward(toSet), flags));
			toSet &= toSet ^ -toSet;
		}
	}
}