package chesspieces;

import util.Position;

public interface ChessConstants {
	
	public final int NORTH     = 0;
	public final int EAST      = 1;
	public final int SOUTH     = 2;
	public final int WEST      = 3;
	public final int NORTHEAST = 4;
	public final int SOUTHEAST = 5;
	public final int SOUTHWEST = 6;
	public final int NORTHWEST = 7;
	
	// some positions used for castling
	
	// rook starting positions, aka corners
	public final Position a1 = new Position(0,0);
	public final Position h1 = new Position(7,0);
	public final Position a8 = new Position(0,7);
	public final Position h8 = new Position(7,7);
	
	// castling positions for rooks
	public final Position d1 = new Position(3,0);
	public final Position f1 = new Position(5,0);
	public final Position d8 = new Position(3,7);
	public final Position f8 = new Position(5,7);	
	
	// castling positions for kings
	public final Position c1 = new Position(2,0);
	public final Position g1 = new Position(6,0);
	public final Position c8 = new Position(2,7);
	public final Position g8 = new Position(6,7);
}
