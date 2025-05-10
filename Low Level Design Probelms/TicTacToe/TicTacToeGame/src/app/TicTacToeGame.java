package app;

import app.enums.Symbol;

import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {

    List<Player> playerList;
    int playerTurn;
    Dashboard dashboard;
    Player winner;
    Scanner scanner;

    public TicTacToeGame(int playerTurn, int size, List<Player> playerList) {
        this.playerTurn = playerTurn;

        this.dashboard = new Dashboard(size);

        this.scanner = new Scanner(System.in);

        this.playerList = playerList;
    }

    public void startGame() {

        while (true) {

            Player player = playerList.get(playerTurn);

            Logger.log("Player Turn :" + player.getSymbol().name());

            Logger.log("Enter row: ");
            int row = scanner.nextInt();

            Logger.log("Enter col: ");
            int col = scanner.nextInt();

            dashboard.setPosition(row, col, player.getSymbol());

            if(dashboard.isWinner(player.getSymbol())){
                winner = player;
                break;
            }

            displayBoard();

            playerTurn = nextPlayer(playerTurn);
        }

    }

    public void displayBoard(){
        dashboard.show();
    }

    public Player getWinner() {
        return winner;
    }

    private int nextPlayer(int playerTurn){
        return 1 - playerTurn;
    }

}
