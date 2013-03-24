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
 
	public void movePiece(Position from, Position to, char turn) 
	throws IllegalMoveException, PieceNotFoundException, 
		   WrongTurnException, CheckException {
		// check if piece actually exists at from
		if (!board.hasPieceAt(from)) { 
			throw new PieceNotFoundException("No piece exists at " + from);
		}
		
		ChessPiece piece = board.getPieceAt(from);
		
		// check if turn matches color of piece being moved
		if (piece.getColor() != turn) {
			throw new WrongTurnException("Cannot move a " + 
					(turn == 'w' ? "Black" : "White") + " piece");
		}
		
		if (from.equals(to)) {
			throw new IllegalMoveException("Piece was not moved\n" + possibleMovesToString(from, piece.possibleMoves()));
		}
		
		// check if King of current turn player is in check
		King king = board.getKing(turn);
		if (king.inCheckAt(king.getPos())) {
			
			// if the currently selected piece is not the king that is under check, throw exception
			if (!piece.equals(king)) {
				StringBuilder sb = new StringBuilder();
				sb.append(king + " is in check at " + king.getPos() + "\n");
				sb.append(possibleMovesToString(king.getPos(), king.possibleMoves()));
				throw new CheckException(sb.toString());
			}
		}
		
		if (!piece.canMoveTo(to)) {
 			// get all possible moves and throw IllegalMoveException
			ArrayList<Position> possibleMoves = piece.possibleMoves();
			
			String message = piece + " at " + from + " cannot move to " + to + "\n" +
					possibleMovesToString(from, possibleMoves);
			
			throw new IllegalMoveException(message);
		}
		
		piece.move(to);
	}
	
	private String possibleMovesToString(Position from, ArrayList<Position> possibleMoves) {
		StringBuilder sb = new StringBuilder();
		sb.append("Possible Moves:");
		for (Position p : possibleMoves) {
			sb.append("\n" + from + " " + p);
		}
		return sb.toString();
	}
	
	public void promotePiece(Position pos, char pieceChar) {
		
		char color = board.getPieceAt(pos).getColor();
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
}
