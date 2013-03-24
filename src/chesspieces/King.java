package chesspieces;

import util.Position;
import chess.ChessBoard;

public class King extends ChessPiece {
	
	private boolean canCastle;

	public King(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
		canCastle = true;
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		if (canCastle) {
			Rook rook = getCastlingRook(to);
			
			if (rook != null) {
				boolean res = !inCheckWhileCastling(to) && canCastle && rook.canCastle();
				return res && super.canMoveTo(to) && !inCheckAt(to);
			}
		} 
		
		if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
			// king can only move in radius of 1
			return false;
		}
		
		return super.canMoveTo(to) && !inCheckAt(to);
	}
	
	private Rook getCastlingRook(Position pos) {
		Rook rook = null;

			 if (pos.equals(c1)) { rook = getRookHelper(a1); } 
		else if (pos.equals(g1)) { rook = getRookHelper(h1); }
		else if (pos.equals(c8)) { rook = getRookHelper(a8); }
		else if (pos.equals(g8)) { rook = getRookHelper(h8); }
			 
		return rook;
	}
	
	private Rook getRookHelper(Position pos) {
		ChessBoard board = getBoard();
		Rook rook = null;
		if (board.hasPieceAt(pos)) {
			ChessPiece piece = board.getPieceAt(pos);
			if (piece instanceof Rook && piece.getColor() == getColor()) {
				rook = (Rook)piece;
			}
		}
		return rook;
	}
	
	// return true if king will ever be in check while castling
	private boolean inCheckWhileCastling(Position dest) {
		boolean res = this.inCheckAt(getPos());
		
			 if (dest.equals(c1)) { res = res || this.inCheckAt(d1); } 
		else if (dest.equals(g1)) { res = res || this.inCheckAt(f1); }
		else if (dest.equals(c8)) { res = res || this.inCheckAt(d8); }
		else if (dest.equals(g8)) { res = res || this.inCheckAt(f8); }
			 
		res = res || this.inCheckAt(dest);
		return res;
	}
	
	public void move(Position dest) {
		super.move(dest);
		
		// if castling must move itself and the accompanying rook
		if (canCastle && (dest.equals(c1) || dest.equals(g1) || dest.equals(c8) || dest.equals(g8))) {
			Rook rook = getCastlingRook(dest); 
			rook.move(rook.getCastlePos()); 
		}
		
		canCastle = false;
	}
	
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
							ChessPiece temp = board.deletePieceAt(pos);
							
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
