package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Queen extends ChessPiece {

	public Queen(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		// return if not a compass direction (ie. n,e,s,w,ne,se,sw,nw)
		if (getDir(from, to) == -1) { return false; }
		
		return super.canMoveTo(to) && travel(from, to); 
	}
	
	public String toString() {
		return getColor() + "Q";
	}
}
