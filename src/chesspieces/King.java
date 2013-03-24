package chesspieces;

import util.Position;
import chess.ChessBoard;

public class King extends ChessPiece {
	
	private boolean canCastle = true;

	public King(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		if (canCastle) {
			
			// castle logic
		}
		
		// king can move in radius of 1
		if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
			return false;
		}
		
		// same code as Queen; King is a noob Queen
		// not necessary??? bc king can only move 1 space
		if (getDir(from, to) == -1) { return false; }
		
		return super.canMoveTo(to) && !inCheckAt(to);
	}
	
	public void move(Position dest) {
		super.move(dest);
		canCastle = false;
	}
	
	// POSSIBLE ERRORS STILL vvvv
	
	// checks if King would be in check at any given Position pos
	public boolean inCheckAt(Position pos) {
		char color = getColor();
		ChessBoard board = getBoard();
		boolean inCheck = false;
		
		// delete king from board to see if pieces can move past it
		King king = this;
		board.deletePieceAt(getPos());
		
		// check entire board to see if any opp color piece can move to pos
		for(int y = 7; y >= 0; y--) {
			for(int x = 0; x < 8; x++) {
				Position curr = new Position(x,y);
				
				if (board.hasPieceAt(curr)) {
					ChessPiece piece = board.getPieceAt(curr);
					
					if (piece.getColor() != color) {
						
						// if a piece of opposite color can move to pos, 
						// then king would be in check
						if (piece.canMoveTo(pos)) {
							inCheck =  true;
							break;
						}
						
						// if a piece already exists at pos, remove existing piece 
						// (to simulate king moving there) and see if opp piece can move there
						if (board.hasPieceAt(pos)) {
							ChessPiece temp = board.getPieceAt(pos);
							board.deletePieceAt(pos);
							
							if (piece.canMoveTo(pos)) {
								inCheck = true;
							}
							
							board.putPiece(temp, pos);
						}
					}
				}
			}
		}
		
		// replace king
		board.putPiece(king, king.getPos());
		return inCheck;
	}
	
	public String toString() {
		return getColor() + "K";
	}
}
