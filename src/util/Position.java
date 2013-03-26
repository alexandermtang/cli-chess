package util;

public class Position {

	private int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// checks if x,y are coordinates that exist on chess board
	public static boolean isValidPos(int x, int y) {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}
	
	public static Position parsePos(String s) 
	throws IllegalPositionException {
		if (s.length() != 2) {
			throw new IllegalPositionException(s + " does not exist");
		}
		int x = (int)(s.charAt(0)) - 97; 
		int y = Character.getNumericValue(s.charAt(1)) - 1;
		if (!isValidPos(x,y)) { 
			throw new IllegalPositionException(s + " does not exist");
		}
		return new Position(x,y);
	}
	
	public String toString() {
		return (char)(x + 97) + Integer.toString(y + 1);
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Position)) {
			return false;
		}
		Position p = (Position)o;
		return x == p.getX() && y == p.getY();
	}
}
