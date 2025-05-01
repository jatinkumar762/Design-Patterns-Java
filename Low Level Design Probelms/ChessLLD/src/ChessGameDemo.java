import dtos.Move;
import enums.GameState;

import java.util.Scanner;

public class ChessGameDemo {

    public static void main(String args[]){

        ChessGame chessGame = new ChessGame();

        chessGame.startGame();

        Scanner sc = new Scanner(System.in);

        while (chessGame.getGameState().equals(GameState.IN_PROGRESS)) {

            System.out.println("Enter move (fromRow fromCol toRow toCol) or 'exit': ");

            String input = sc.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                chessGame.stopGame("User exit");
                break;
            }

            String[] parts = input.trim().split(" ");

            if(parts.length == 4){

                int fromRow = Integer.parseInt(parts[0]);
                int fromCol = Integer.parseInt(parts[1]);
                int toRow = Integer.parseInt(parts[2]);
                int toCol = Integer.parseInt(parts[3]);

                Move move = new Move(fromRow, fromCol, toRow, toCol);

                boolean gameEnded = chessGame.makeMove(move);

                if(gameEnded){
                    break;
                }
            }

        }

        sc.close();
    }
}
