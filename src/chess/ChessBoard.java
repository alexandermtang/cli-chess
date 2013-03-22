package chess;

import util.Position;
import chesspieces.Bishop;
import chesspieces.ChessPiece;
import chesspieces.King;
import chesspieces.Knight;
import chesspieces.Pawn;
import chesspieces.Queen;
import chesspieces.Rook;

public class ChessBoard {
	
	private ChessPiece[][] board;
	private King wK;
	private King bK;
	
	public ChessBoard() {
		this.board = new ChessPiece[8][8];
		this.bK = new King(this, 'b', new Position(4,7));
		this.wK = new King(this, 'w', new Position(4,0));
		createBoard();
	}
	
	private void createBoard() {
		// fill black
		board[0][7] = new Rook  (this, 'b', new Position(0,7));
		board[1][7] = new Knight(this, 'b', new Position(1,7));
		board[2][7] = new Bishop(this, 'b', new Position(2,7));
		board[3][7] = new Queen (this, 'b', new Position(3,7));
		board[4][7] = bK;
		board[5][7] = new Bishop(this, 'b', new Position(5,7));
		board[6][7] = new Knight(this, 'b', new Position(6,7));
		board[7][7] = new Rook  (this, 'b', new Position(7,7));
		
		// fill white
		board[0][0] = new Rook  (this, 'w', new Position(0,0));
		board[1][0] = new Knight(this, 'w', new Position(1,0));
		board[2][0] = new Bishop(this, 'w', new Position(2,0));
		board[4][4] = new Queen (this, 'w', new Position(4,4));
		board[4][0] = wK;
		board[5][0] = new Bishop(this, 'w', new Position(5,0));
		board[6][0] = new Knight(this, 'w', new Position(6,0));
		board[7][0] = new Rook  (this, 'w', new Position(7,0));
		
		// fill Pawns
		for(int x = 0; x < 8; x++) {
			board[x][6] = new Pawn(this, 'b', new Position(x,6));
			board[x][1] = new Pawn(this, 'w', new Position(x,1));
		}
	}
	
	public boolean hasPieceAt(Position pos) {
		return getPieceAt(pos) != null;
	}
	
	public ChessPiece getPieceAt(Position pos)  {
		int x = pos.getX(); 
		int y = pos.getY();
		return board[x][y];
	}
	
	public void deletePieceAt(Position pos) {
		int x = pos.getX(); 
		int y = pos.getY();
		board[x][y] = null;
	}
	
	public void putPiece(ChessPiece piece, Position pos) {
		int x = pos.getX(); 
		int y = pos.getY();
		board[x][y] = piece;
		piece.setPos(pos);
	}
	
	public King getKing(char c) {
		return c == 'w' ? wK : bK;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n   a  b  c  d  e  f  g  h\n");
		for(int y = 7; y >= 0; y--) {
			sb.append(y+1 + " ");
			for(int x = 0; x < 8; x++) {
				ChessPiece piece = board[x][y];
				if (board[x][y] instanceof ChessPiece) {
					sb.append(piece.toString());
				} else if (areEven(x,y) || areOdd(x,y)){
					sb.append("##");
				} else {
					sb.append("  ");
				}
				sb.append(" ");
			}
			sb.append(y+1 + "\n");
		}
		sb.append("   a  b  c  d  e  f  g  h\n");
		return sb.toString();
	}
	
	private boolean areEven(int m, int n) {
		return m % 2 == 0 && n % 2 == 0;
	}
	
	private boolean areOdd(int m, int n) {
		return m % 2 != 0 && n % 2 != 0;
	}
}
