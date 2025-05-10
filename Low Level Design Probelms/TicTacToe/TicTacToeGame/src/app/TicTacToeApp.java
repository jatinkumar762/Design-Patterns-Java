package app;

import app.enums.Symbol;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeApp {

    public static void main(String args[]){

        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Ram", Symbol.O));
        playerList.add(new Player("Shyam", Symbol.X));

        TicTacToeGame ticTacToeGame = new TicTacToeGame(0, 3, playerList);

        ticTacToeGame.startGame();

        ticTacToeGame.displayBoard();

        Logger.log("Winner :"+ ticTacToeGame.getWinner().getName());
    }
}
