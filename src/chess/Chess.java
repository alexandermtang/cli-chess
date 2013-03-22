package chess;

import java.util.Scanner;

import util.CheckException;
import util.IllegalMoveException;
import util.IllegalPositionException;
import util.PieceNotFoundException;
import util.Position;
import util.WrongTurnException;
import chesspieces.King;

public class Chess {
	
	private static void runGame() {
		ChessBoard board = new ChessBoard();
		BoardController bc = new BoardController(board);
		char turn = 'w';
		
		Scanner sc = new Scanner(System.in);
		
		// FIX END GAME REQUIREMENTS
		boolean draw = false;
		
		while (true) {
			System.out.println(board);
			
			// need to print check if king is under check
			King king = turn == 'w' ? board.getKing('w') : board.getKing('b');
			if (king.inCheckAt(king.getPos())) {
				// if piece is a King and has no possible moves and is under check, checkmate
				if (king.possibleMoves().isEmpty()) {
					String color = turn == 'w' ? "Black" : "White";
					System.out.println("Checkmate\n" + color + " wins");
					return;
				}
				
				System.out.println("Check");
			}
		
			System.out.print(turn == 'w' ? "White's move: " : "Black's move: ");
			String[] inputs = sc.nextLine().trim().split(" ");
			
			// remove "q" command later
			if (inputs[0].equals("resign") || inputs[0].equals("q")) {
				System.out.println((turn == 'w' ? "White" : "Black") + " resigns");
				System.out.println((turn == 'w' ? "Black" : "White") + " wins");
				return;
			}
			
			// FIX DRAW LOGIC
			if (draw && inputs[0].equals("draw")) {
				return;
			} else {
				draw = false;
			}
			
			if (inputs.length > 2) {
				String s = inputs[2];
				
				// check if player wants a draw
				if (s.equals("draw?")) {
					draw = true;
					break;
				}
				
				// promotePiece logic
			}
			
			try {
				Position from = Position.parsePos(inputs[0]);
				Position to = Position.parsePos(inputs[1]);
				bc.movePiece(from, to, turn);
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
		runGame();
	}

}
