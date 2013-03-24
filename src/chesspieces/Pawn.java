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
		
		int xf1 = x0 - 1;
		int xf2 = x0 + 1;
		int yf  = y0 + (color == 'w' ? 1 : -1);
		Position forwardL = new Position(xf1,yf);
		Position forwardR = new Position(xf2,yf);
		Position forward  = new Position(x0 ,yf);
		
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
		if (color == 'w' && dir != NORTH) { return false; }
		// if Black, dir must be SOUTH
		if (color == 'b' && dir != SOUTH) { return false; }
		
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
	
	public void promote(char pieceChar) {
		ChessBoard board = getBoard();
		Position pos = getPos();
		char color = getColor();
		ChessPiece newPiece;
		
		switch (pieceChar) {
		case 'R': newPiece = new Rook  (board, color, pos);
				  break;
		case 'N': newPiece = new Knight(board, color, pos);
				  break;
		case 'B': newPiece = new Bishop(board, color, pos);
				  break;
		case 'p': newPiece = new Pawn  (board, color, pos);
				  break;
		default : newPiece = new Queen (board, color, pos);
			      break;
		}
		
		board.deletePieceAt(pos);
		board.putPiece(newPiece, pos);
	}
	
	// returns true if pawn moved to last row on board
	public boolean inLastRow() {
		boolean res = false;
		int y = getColor() == 'w' ? 7 : 0;
		for (int x = 0; x < 8; x++) {
			res = res || getPos().equals(new Position(x,y));
		}
		
		return res;
	}

	public String toString() {
		return getColor() + "p";
	}
}
