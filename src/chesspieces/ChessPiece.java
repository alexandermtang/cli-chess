package chesspieces;

import java.util.ArrayList;

import util.Position;
import chess.ChessBoard;

public abstract class ChessPiece implements ChessConstants {
	
	private ChessBoard board;
	private char color;
	private Position pos; 
	
	public ChessPiece(ChessBoard board, char color, Position pos) {
		this.board = board;
		this.color = color;
		this.pos = pos;
	}
	
	public char getColor() { 
		return color; 
	}
	
	public Position getPos() { 
		return pos; 
	}
	
	protected ChessBoard getBoard() { 
		return board; 
	}
	
	// checks if piece is allowed to move to this position
	public boolean canMoveTo(Position dest) {
		
		// if moving piece means putting your king in check, return false;
		King king = board.getKing(getColor());
		ChessPiece curr = board.deletePieceAt(getPos());
		if (king.inCheckAt(king.getPos())) {
			board.putPiece(curr, curr.getPos());
			return false;
		}
		board.putPiece(curr, curr.getPos());
			
		// returns true if piece are different colors or if nothing exists at dest
		return board.hasPieceAt(dest) ? board.getPieceAt(dest).getColor() != color : true;
	}
	
	public void move(Position dest) {
		ChessBoard board = getBoard();
		board.deletePieceAt(pos);
		board.putPiece(this, dest);
	}
	
	public ArrayList<Position> possibleMoves() {
		ArrayList<Position> possibleMoves = new ArrayList<Position>();
		for(int y = 7; y >= 0; y--) {
			for(int x = 0; x < 8; x++) {
				Position p = new Position(x,y);
				if (canMoveTo(p)) {	
					possibleMoves.add(p); 
				}
			}
		}
		return possibleMoves;
	}
	
	public void setPos(Position to) {
		pos.setXY(to.getX(), to.getY());
	}
	
	// used by Queen, Bishop, Rook to see if it can travel 
	// to destination without another piece blocking it
	protected boolean travel(Position from, Position to) {
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		// if same no change in position, return false
		if (dx == 0 && dy == 0) { return false; }
		
		int dir = getDir(from, to);
		Position next = getNextPos(from, dir);
		
		return recursiveTravel(to, next, dir);
	}
	
	protected int getDir(Position from, Position to) {
		int dir = -1;
		
		double dx = (double)to.getX() - from.getX();
		double dy = (double)to.getY() - from.getY();
		
		// if not straight or diagonal, return -1
		if (!(dx == 0 || dy == 0 || Math.abs(dy/dx) == 1.0)) {
			return dir;
		}
		
			 if (dx == 0 && dy >  0) { dir = NORTH;     } 
		else if (dx >  0 && dy == 0) { dir = EAST;      } 
		else if (dx == 0 && dy <  0) { dir = SOUTH;     }
		else if (dx <  0 && dy == 0) { dir = WEST;      } 
		else if (dx >  0 && dy >  0) { dir = NORTHEAST; }
		else if (dx >  0 && dy <  0) { dir = SOUTHEAST; }
		else if (dx <  0 && dy <  0) { dir = SOUTHWEST; }
		else if (dx <  0 && dy >  0) { dir = NORTHWEST; }
			 
		return dir;
	}
	
	// gets adjacent or diagonal position one spot away from curr depending on dir 
	private Position getNextPos(Position curr, int dir) {
		int x = curr.getX();
		int y = curr.getY();
		
		Position next = null;
			 if (dir == NORTH    ) { next = new Position(x  ,y+1); } 
		else if (dir == EAST     ) { next = new Position(x+1,y  ); } 
		else if (dir == SOUTH    ) { next = new Position(x  ,y-1); } 
		else if (dir == WEST     ) { next = new Position(x-1,y  ); }
		else if (dir == NORTHEAST) { next = new Position(x+1,y+1); } 
		else if (dir == SOUTHEAST) { next = new Position(x+1,y-1); } 
		else if (dir == SOUTHWEST) { next = new Position(x-1,y-1); } 
		else if (dir == NORTHWEST) { next = new Position(x-1,y+1); }
			 
		return next;
	}
	
	// recursively travels from curr to dest
	private boolean recursiveTravel(Position dest, Position curr, int dir) {	
		if (dest.equals(curr)) { return true; }
		if (!Position.validPos(curr.getX(), curr.getY())) { return false; }
		if (getBoard().hasPieceAt(curr))  { return false; }
		
		Position next = getNextPos(curr, dir);
		
		return recursiveTravel(dest, next, dir);
	}
	
	public abstract String toString();
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ChessPiece)) {
			return false;
		}
		ChessPiece other = (ChessPiece)o;
		return toString().equals(other.toString()) && getPos().equals(other.getPos());
	}
}