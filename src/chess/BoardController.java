package chess;

import java.util.ArrayList;

import util.CheckException;
import util.IllegalMoveException;
import util.PieceNotFoundException;
import util.Position;
import util.WrongTurnException;
import chesspieces.Bishop;
import chesspieces.ChessPiece;
import chesspieces.King;
import chesspieces.Knight;
import chesspieces.Pawn;
import chesspieces.Queen;
import chesspieces.Rook;

public class BoardController {
	
	private ChessBoard board;
	
	public BoardController(ChessBoard board) {
		this.board = board;
	}
 
	public void movePiece(Position from, Position to, char turn, char requestedPromotionPiece) 
	throws IllegalMoveException, PieceNotFoundException, 
		   WrongTurnException, CheckException {
		// check if piece actually exists at from
		if (!board.hasPieceAt(from)) { 
			throw new PieceNotFoundException("No piece exists at " + from);
		}
		
		ChessPiece selectedPiece = board.getPieceAt(from);
		
		// check if turn matches color of piece being moved
		if (selectedPiece.getColor() != turn) {
			throw new WrongTurnException("Cannot move a " + 
					(turn == 'w' ? "Black" : "White") + " piece");
		}
		
		if (from.equals(to)) {
			throw new IllegalMoveException("Piece was not moved\nPossible moves for " + selectedPiece +  " at " + 
					selectedPiece.getPos() + ":" + possibleMovesToString(from, selectedPiece.possibleMoves()));
		}
		
		// check if King of current turn player is in check
		King king = board.getKing(turn);
		if (king.inCheckAt(king.getPos())) {
			ArrayList<ChessPiece> pieces = king.getPiecesThatBlockCheck();
			
			System.out.println("HELLO");
				for (ChessPiece p : pieces) {
					System.out.println(p + " " +p.getPos());
				}
			
			// if the currently selected selectedPiece is not the king that is under check or 
			// any piece that can block check, throw exception
			if (!(selectedPiece.equals(king) || pieces.contains(selectedPiece))) {
				StringBuilder sb = new StringBuilder();
				sb.append(king + " is in check at " + king.getPos() + "\nPossible moves:");
				sb.append(possibleMovesToString(king.getPos(), king.possibleMoves()));
				for (ChessPiece p : pieces) {
					sb.append(possibleMovesToString(p.getPos(), p.possibleMoves()));
				}
				throw new CheckException(sb.toString());
			}
		}
		
		if (!selectedPiece.canMoveTo(to)) {
 			// get all possible moves and throw IllegalMoveException
			String message = selectedPiece + " at " + from + " cannot move to " + to + 
					"\nPossible moves for " + selectedPiece + " at " + 
					selectedPiece.getPos() + ":" + possibleMovesToString(from, selectedPiece.possibleMoves());
			
			throw new IllegalMoveException(message);
		}
		
		selectedPiece.move(to);
		
		// checks for promoting a pawn
		if (selectedPiece instanceof Pawn) {
			Pawn pawn = (Pawn)selectedPiece;
			if (pawn.inLastRow()) {
				pawn.promote(requestedPromotionPiece);
			}
		}
	}
	
	private String possibleMovesToString(Position from, ArrayList<Position> possibleMoves) {
		StringBuilder sb = new StringBuilder();
		for (Position p : possibleMoves) { sb.append("\n" + from + " " + p); }
		return sb.toString();
	}
}
