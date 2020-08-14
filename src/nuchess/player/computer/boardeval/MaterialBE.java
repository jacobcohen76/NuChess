package nuchess.player.computer.boardeval;

import nuchess.engine.Chessboard;
import nuchess.engine.Piece;

public class MaterialBE implements BoardEvaluator
{
	private static final int[] VALUE = new int[]
	{
		  0, //SPACING
		100, //PAWN
		300, //KNIGHT
		300, //BISHOP
		500, //ROOK
		900, //QUEEN
		  0, //KING
	};
	
	@Override
	public int evaluate(Chessboard board)
	{
		return	VALUE[Piece.PAWN]   * (board.material[Piece.WHITE_PAWN   + board.toMove] - board.material[Piece.WHITE_PAWN   + board.toMove ^ 1]) +
				VALUE[Piece.KNIGHT] * (board.material[Piece.WHITE_KNIGHT + board.toMove] - board.material[Piece.WHITE_KNIGHT + board.toMove ^ 1]) +
				VALUE[Piece.BISHOP] * (board.material[Piece.WHITE_BISHOP + board.toMove] - board.material[Piece.WHITE_BISHOP + board.toMove ^ 1]) +
				VALUE[Piece.ROOK]   * (board.material[Piece.WHITE_ROOK   + board.toMove] - board.material[Piece.WHITE_ROOK   + board.toMove ^ 1]) +
				VALUE[Piece.QUEEN]  * (board.material[Piece.WHITE_QUEEN  + board.toMove] - board.material[Piece.WHITE_QUEEN  + board.toMove ^ 1]) +
				VALUE[Piece.KING]   * (board.material[Piece.WHITE_KING   + board.toMove] - board.material[Piece.WHITE_KING   + board.toMove ^ 1]);
	}
}
