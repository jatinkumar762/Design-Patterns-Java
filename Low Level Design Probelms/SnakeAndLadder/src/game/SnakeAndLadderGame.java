package game;

import game.interfaces.GameState;

import java.util.List;

/**
 * manages a single game
 */
public class SnakeAndLadderGame extends Observable {

    private String gameId;
    private Dice dice;
    private Board board;
    private List<Player> players;
    private int currentPlayer;
    private int playerCount;
    private boolean isGameOver;
    private Player winner;
    private GameState gameState;

    public SnakeAndLadderGame(String gameId, List<Player> players, Dice dice, Board board){
        this.gameId = gameId;
        this.dice = dice;
        this.currentPlayer = 0;
        this.players = players;
        this.board = board;
        this.playerCount = players.size();
        this.isGameOver = false;
        this.winner = null;
        this.gameState = new WaitingState();
        players.forEach(player -> this.addObserver(player));
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public String getGameId() {
        return gameId;
    }

    /*
    public void startGame() {
        gameState.startGame(this);
    }

    public void playTurn() {
        gameState.playTurn(this);
    }

    public void endGame() {
        gameState.endGame(this);
    }

    public boolean isFinished() {
        // The game is finished if we're in the FinishedState
        return state instanceof FinishedState;
    }
    */

    public void start(){

        while (!isGameOver){

            Player player = players.get(currentPlayer);

            System.out.println("Player Turn: " + player.getId());

            int rollNum = dice.roll();
            System.out.println("Rolled dice: "+ rollNum);

            if(board.isSafe(player.getPosition() + rollNum)){

                int jumpPostion = board.getJumpPosition(player.getPosition() + rollNum);

                player.setPosition(jumpPostion);

                System.out.println(String.format("Player %s, postion: %d", player.getId(), player.getPosition()));

                if(board.isWinner(player.getPosition())) {
                    winner = player;

                    stopGame();

                    System.out.println("Winner Player: " + player.getId());

                    break;
                }
            }

            currentPlayer = (currentPlayer + 1) % playerCount;
        }
    }

    private void stopGame(){
        this.isGameOver = true;
    }
}
