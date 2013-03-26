package chesspieces;

import java.util.ArrayList;

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
		
		// king can only move in radius of 1
		if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
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
			// piece must be a rook and of same color as king
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
		if (pos == null) { return false; }
		
		char color = getColor();
		ChessBoard board = getBoard();
		boolean inCheck = false;
		
		// put king at pos and save current piece at pos as pieceAtPos
		Position currPos = new Position(getPos().getX(), getPos().getY());
		ChessPiece king = board.deletePieceAt(getPos());
		ChessPiece pieceAtPos = board.deletePieceAt(pos);
		
		board.putPiece(king,pos);
		
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
					}
				}
			}
		}
		
		// replace king and pieceAtPos
		board.putPiece(pieceAtPos,pos);
		board.putPiece(king,currPos);
		
		return inCheck;
	}
	
	// stalemate if king is not in check but if king moves, will be in check
	// also no pieces of same color can move
	public boolean inStalemate() {
		boolean noPiecesCanMove = true;
		for (ChessPiece piece : getBoard().getAllPieces()) {
			if (piece.getColor() == getColor()) { 
				noPiecesCanMove = noPiecesCanMove && piece.possibleMoves().isEmpty();
			}
		}
		return !inCheckAt(getPos()) && noPiecesCanMove;
	}
	
	public ArrayList<ChessPiece> getPiecesThatBlockCheck() {
		char color = getColor();
		ChessBoard board = getBoard();
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		
		for (ChessPiece piece : board.getAllPieces()) {
			if (piece.getColor() == color && !piece.equals(this)) {
				for (int i = 0; i < piece.possibleMoves().size(); i++) {
					if (!pieces.contains(piece)) { pieces.add(piece); }
				}
			}
		}
		return pieces;
	}
	
	public String toString() {
		return getColor() + "K";
	}
}
