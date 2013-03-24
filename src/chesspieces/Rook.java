package chesspieces;

import util.Position;
import chess.ChessBoard;

public class Rook extends ChessPiece {
	
	private boolean canCastle;
	private Position castlePos;
	
	public Rook(ChessBoard board, char color, Position pos) {
		super(board, color, pos);
		canCastle = true;
		
			 if (pos.equals(a1)) { castlePos = d1; } 
		else if (pos.equals(h1)) { castlePos = f1; }
		else if (pos.equals(a8)) { castlePos = d8; }
		else if (pos.equals(h8)) { castlePos = f8; }
	}
	
	public boolean canMoveTo(Position to) {
		Position from = getPos();
		
		int dir = getDir(from, to);
		
		// if not n,e,s,w, return false
		if (dir != NORTH &&
			dir != EAST  &&
			dir != SOUTH &&
			dir != WEST  ) {
			return false;
		}
		
		return super.canMoveTo(to) && travel(from, to); 
	}
	
	public boolean canCastle() {
		return canCastle && canMoveTo(castlePos);
	}
	
	public Position getCastlePos() {
		return castlePos;
	}
	
	public void move(Position dest) {
		super.move(dest);
		canCastle = false;
	}
	
	public String toString() {
		return getColor() + "R";
	}
}
