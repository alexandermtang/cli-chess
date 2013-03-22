package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Rook extends ChessPiece {
	
	public Rook(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		int dir = getDir(dx, dy);
		
		// if not n,e,s,w, return false
		if (dir != NORTH &&
			dir != EAST  &&
			dir != SOUTH &&
			dir != WEST  ) {
			return false;
		}
		
		return super.canMoveTo(to) && travel(from, to); 
	}
	
	public String toString() {
		return getColor() + "R";
	}
}
