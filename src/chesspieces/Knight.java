package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Knight extends ChessPiece {
	
	public Knight(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		if (Math.abs(dx) > 2 || Math.abs(dy) > 2) {
			return false;
		}
		
		if (Math.abs(dy/dx) != 2.0 && Math.abs(dy/dx) != 0.5) {
			return false;
		}
		
		return super.canMoveTo(to);
	}
	
	public String toString() {
		return getColor() + "N";
	}
}
