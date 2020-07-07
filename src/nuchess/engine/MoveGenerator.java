package nuchess.engine;

import java.util.LinkedList;
import java.util.List;

public class MoveGenerator
{
	public static final List<CMove> generateMoves(CBoard board, int toMove, int castlingRights, int epSquare, boolean inCheck)
	{
		List<CMove> moves = new LinkedList<CMove>();
		long occ = board.occ();
		long enemy = board.bitboard(toMove ^ 1);
		generatePawnMoves(moves, occ, enemy, board.bitboard(Piece.WHITE_PAWN + toMove), toMove, toMove == Color.WHITE ? Square.rank_8 : Square.rank_1, epSquare == Square.NULL ? 0 : (1L << epSquare));
		generateKnightMoves(moves, occ, enemy, board.bitboard(Piece.WHITE_KNIGHT + toMove));
		generateBishopMoves(moves, occ, enemy, board.bitboard(Piece.pieceCode(Piece.BISHOP, toMove)));
		generateRookMoves(moves, occ, enemy, board.bitboard(Piece.pieceCode(Piece.ROOK, toMove)));
		generateQueenMoves(moves, occ, enemy, board.bitboard(Piece.pieceCode(Piece.QUEEN, toMove)));
		generateKingMoves(moves, occ, enemy, board.bitboard(Piece.pieceCode(Piece.KING, toMove)));
		if(inCheck == false)
		{
			generateCastleMoves(moves, board, occ, toMove, castlingRights);
		}
		return moves;
	}
	
	private static final void generatePawnMoves(List<CMove> moves, long occ, long enemy, long fromSet, int toMove, int promoRank, long epBB)
	{
		while(fromSet != 0L)
		{
			int from = Bits.bitscanForward(fromSet);
			if((Attacks.pawnSinglePush(from, toMove) & occ) == 0)
			{
				addPawnPushes(moves, from, Attacks.pawnSinglePush(from, toMove), promoRank);
				addMoves(moves, from, Attacks.pawnDoublePush(from, toMove) & ~occ, CMove.DOUBLE_PAWN_PUSH);
			}
			addPawnCaptures(moves, from, Attacks.pawnAttacks(from, toMove) & enemy, promoRank);
			addMoves(moves, from, Attacks.pawnAttacks(from, toMove) & epBB, CMove.EP_CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private static final void addPawnPushes(List<CMove> moves, int from, long toSet, int promoRank)
	{
		while(toSet != 0L)
		{
			int to = Bits.bitscanForward(toSet);
			if(Square.rank(to) == promoRank)
			{
				moves.add(new CMove(from, to, CMove.KNIGHT_PROMO));
				moves.add(new CMove(from, to, CMove.BISHOP_PROMO));
				moves.add(new CMove(from, to, CMove.ROOK_PROMO));
				moves.add(new CMove(from, to, CMove.QUEEN_PROMO));
			}
			else
				moves.add(new CMove(from, to, CMove.QUIET));
			toSet &= toSet ^ -toSet;
		}
	}
	
	private static final void addPawnCaptures(List<CMove> moves, int from, long toSet, int promoRank)
	{
		while(toSet != 0L)
		{
			int to = Bits.bitscanForward(toSet);
			if(Square.rank(to) == promoRank)
			{
				moves.add(new CMove(from, to, CMove.KNIGHT_PROMO_CAP));
				moves.add(new CMove(from, to, CMove.BISHOP_PROMO_CAP));
				moves.add(new CMove(from, to, CMove.ROOK_PROMO_CAP));
				moves.add(new CMove(from, to, CMove.QUEEN_PROMO_CAP));
			}
			else
				moves.add(new CMove(from, to, CMove.CAPTURE));
			toSet &= toSet ^ -toSet;
		}
	}
	
	private static final void generateKnightMoves(List<CMove> moves, long occ, long enemy, long fromSet)
	{
		while(fromSet != 0L)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, from, Attacks.knightAttacks(from) & ~occ, CMove.QUIET);
			addMoves(moves, from, Attacks.knightAttacks(from) & enemy, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private static final void generateBishopMoves(List<CMove> moves, long occ, long enemy, long fromSet)
	{
		while(fromSet != 0L)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, from, Attacks.bishopAttacks(from, occ) & ~occ, CMove.QUIET);
			addMoves(moves, from, Attacks.bishopAttacks(from, occ) & enemy, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private static final void generateRookMoves(List<CMove> moves, long occ, long enemy, long fromSet)
	{
		while(fromSet != 0L)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, from, Attacks.rookAttacks(from, occ) & ~occ, CMove.QUIET);
			addMoves(moves, from, Attacks.rookAttacks(from, occ) & enemy, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private static final void generateQueenMoves(List<CMove> moves, long occ, long enemy, long fromSet)
	{
		while(fromSet != 0L)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, from, Attacks.queenAttacks(from, occ) & ~occ, CMove.QUIET);
			addMoves(moves, from, Attacks.queenAttacks(from, occ) & enemy, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private static final void generateKingMoves(List<CMove> moves, long occ, long enemy, long fromSet)
	{
		while(fromSet != 0L)
		{
			int from = Bits.bitscanForward(fromSet);
			addMoves(moves, from, Attacks.kingAttacks(from) & ~occ, CMove.QUIET);
			addMoves(moves, from, Attacks.kingAttacks(from) & enemy, CMove.CAPTURE);
			fromSet &= fromSet ^ -fromSet;
		}
	}
	
	private static final void addMoves(List<CMove> moves, int from, long toSet, int flags)
	{
		while(toSet != 0L)
		{
			moves.add(new CMove(from, Bits.bitscanForward(toSet), flags));
			toSet &= toSet ^ -toSet;
		}
	}
	
	private static final void generateCastleMoves(List<CMove> moves, CBoard board, long occ, int toMove, int castlingRights)
	{
		if(toMove == Color.WHITE)
		{
			if(((castlingRights >> 3) & 1) == 1 && (WHITE_KING_CASTLE_OCC & occ) == 0 && !board.isAttacked(Square.f1, toMove ^ 1))
				moves.add(new CMove(Square.e1, Square.g1, CMove.KING_CASTLE));
			if(((castlingRights >> 2) & 1) == 1 && (WHITE_QUEEN_CASTLE_OCC & occ) == 0 && !board.isAttacked(Square.d1, toMove ^ 1))
				moves.add(new CMove(Square.e1, Square.c1, CMove.QUEEN_CASTLE));
		}
		else if(toMove == Color.BLACK)
		{
			if(((castlingRights >> 1) & 1) == 1 && (BLACK_KING_CASTLE_OCC & occ) == 0 && !board.isAttacked(Square.f8, toMove ^ 1))
				moves.add(new CMove(Square.e8, Square.g8, CMove.KING_CASTLE));
			if(((castlingRights >> 0) & 1) == 1 && (BLACK_QUEEN_CASTLE_OCC & occ) == 0 && !board.isAttacked(Square.d8, toMove ^ 1))
				moves.add(new CMove(Square.e8, Square.c8, CMove.QUEEN_CASTLE));
		}
	}
	
	private static final long WHITE_KING_CASTLE_OCC = 0x0000000000000060L;
	private static final long BLACK_KING_CASTLE_OCC = 0x6000000000000000L;
	private static final long WHITE_QUEEN_CASTLE_OCC = 0x000000000000000EL;
	private static final long BLACK_QUEEN_CASTLE_OCC = 0x0E00000000000000L;
}
