package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Pawn extends ChessPiece {

	private boolean firstTurn;
	
	public Pawn(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
		this.firstTurn = true;
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		int x0 = from.getX();
		int y0 = from.getY();
			
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		ChessBoard board = getBoard();
		char color = getColor();
		
		int xf1 = x0-1;
		int xf2 = x0+1;
		int yf = y0 + (color == 'w' ? 1 : -1);
		Position forwardL = new Position(xf1,yf);
		Position forwardR = new Position(xf2,yf);
		Position forward = new Position(x0,yf);
		
		if (to.equals(forwardL) && board.hasPieceAt(forwardL)) {
			return board.getPieceAt(forwardL).getColor() != color;
		}
		if (to.equals(forwardR) && board.hasPieceAt(forwardR)) {
			return board.getPieceAt(forwardR).getColor() != color;
		}
		if (to.equals(forward) && board.hasPieceAt(forward)) {
			return false;
		}
		
		int dir = getDir(from, to);
		// if White, dir must be NORTH
		if (color == 'w' && dir != NORTH) {
			return false;
		}
		// if Black, dir must be SOUTH
		if (color == 'b' && dir != SOUTH) {
			return false;
		}
		
		if (firstTurn) {
			// still need enpassant
			if (Math.abs(dy) > 2) {
				return false;
			}
			
			
			int yf2 = y0 + (color == 'w' ? 2 : -2);
			Position twoForward = new Position(x0,yf2);
			if (to.equals(twoForward) && board.hasPieceAt(twoForward)) {
				return false;
			}
			
		} else if (Math.abs(dy) > 1) {
			return false;
		}
		
		return super.canMoveTo(to);
	}
	
	public void move(Position dest) {
		super.move(dest);
		firstTurn = false;
	}
	
	public String toString() {
		return getColor() + "p";
	}
}
