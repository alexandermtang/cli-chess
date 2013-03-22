package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Queen extends ChessPiece {

	public Queen(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		// return false if not straight or diagonal
		if ((dx != 0 && dy != 0) && (Math.abs(dy/dx) != 1.0)) {
			return false;
		}
		
		return super.canMoveTo(to) && travel(from, to); 
	}
	
	public String toString() {
		return getColor() + "Q";
	}
}
