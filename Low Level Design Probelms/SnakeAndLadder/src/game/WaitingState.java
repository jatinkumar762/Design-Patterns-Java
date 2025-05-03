package game;

import game.interfaces.GameState;

public class WaitingState implements GameState {


    @Override
    public void startGame(SnakeAndLadderGame game) {
        game.setGameState(new PlayingState());
    }

    @Override
    public void playTurn(SnakeAndLadderGame game) {
        //throw IllegalStateException
    }

    @Override
    public void endGame(SnakeAndLadderGame game) {
        //throw IllegalStateException
    }
}
