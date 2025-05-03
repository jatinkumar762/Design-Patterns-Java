package game;

import game.interfaces.GameState;

public class FinishedState implements GameState {
    @Override
    public void startGame(SnakeAndLadderGame game) {
        //throw IllegalStateException
    }

    @Override
    public void playTurn(SnakeAndLadderGame game) {
        //throw IllegalStateException
    }

    @Override
    public void endGame(SnakeAndLadderGame game) {
        game.notifyObservers("Game over! Winner: " + game.getWinner().getName());
    }
}
