package chess;

import java.util.NoSuchElementException;
import java.util.Scanner;

import util.CheckException;
import util.IllegalMoveException;
import util.IllegalPositionException;
import util.PieceNotFoundException;
import util.Position;
import util.WrongTurnException;
import chesspieces.ChessPiece;
import chesspieces.King;

public class Chess {
	
	private static void runGame() {
		ChessBoard board = new ChessBoard();
		BoardController bc = new BoardController(board);
		char turn = 'w';
		
		Scanner sc = new Scanner(System.in);
		
		boolean proposeDraw = false;
		char playerProposingDraw = 'w';
		char requestedPromotionPiece = 'Q';
		
		while (true) {
			System.out.println(board);
			
			// need to print check if king is under check
			King king = turn == 'w' ? board.getKing('w') : board.getKing('b');
			if (king.inCheckAt(king.getPos())) {
				// if King has no possible moves, is under check, and no pieces can block check, checkmate
				
				// !!!!!!!!!!!also have to check if any other piece can move to block check
				
				if (king.possibleMoves().isEmpty() && king.getPiecesThatBlockCheck().isEmpty()) {
					String color = turn == 'w' ? "Black" : "White";
					System.out.println("Checkmate\n" + color + " wins");
					return;
				}
				System.out.println("Check");
			}
			
			// check for stalemate
			if (king.inStalemate()) {
				System.out.println("Stalemate");
				return;
			}
		
			System.out.print(turn == 'w' ? "White's move: " : "Black's move: ");
			String[] inputs = sc.nextLine().trim().split(" ");
			
			// remove "q" command later
			if (inputs[0].equals("resign") || inputs[0].equals("q")) {
				System.out.println((turn == 'w' ? "White" : "Black") + " resigns");
				System.out.println((turn == 'w' ? "Black" : "White") + " wins");
				return;
			}
			
			// if it is playerProposingDraw's turn again, set proposeDraw to false
			if (playerProposingDraw == turn) { proposeDraw = false; }
			
			// check if draw was proposed on last turn
			if (proposeDraw && playerProposingDraw != turn && inputs[0].equals("draw")) { return; }
			
			if (inputs.length > 2) {
				String s = inputs[2];
				
				// check if player wants a draw
				if (s.equals("draw?")) {
					proposeDraw = true;
					playerProposingDraw = turn;
				} else {
					requestedPromotionPiece = s.charAt(0);
				}
			}
			
			try {
				Position from = Position.parsePos(inputs[0]);
				Position to = Position.parsePos(inputs[1]);
				bc.movePiece(from, to, turn, requestedPromotionPiece);
			} catch (WrongTurnException e) {
				System.err.println(e.getMessage());
				continue;
			} catch (PieceNotFoundException e) {
				System.err.println(e.getMessage());
				continue;
			} catch (IllegalPositionException e) {
				System.err.println(e.getMessage());
				continue;
			} catch (IllegalMoveException e) {
				System.err.println(e.getMessage());
				continue;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Need more arguments");
				continue;
			} catch (CheckException e) {
				System.err.println(e.getMessage());
				continue;
			}
			
			// changes turns
			turn = turn == 'w' ? 'b' : 'w';
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			runGame();
		} catch (NoSuchElementException e) {
			// used for EOF
			System.exit(0);
		}
	}

}
