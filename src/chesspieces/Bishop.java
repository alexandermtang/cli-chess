package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Bishop extends ChessPiece {

	public Bishop(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		// return false if not diagonal
		if (Math.abs(dy/dx) != 1.0) {
			return false;
		}
		
		boolean canTravel = travel(from, to);
		
		return super.canMoveTo(to) && canTravel;
	}
	
	public String toString() {
		return getColor() + "B";
	}
}
