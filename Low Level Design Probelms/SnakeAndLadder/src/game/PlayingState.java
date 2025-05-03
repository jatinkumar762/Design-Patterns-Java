package game;

import game.interfaces.GameState;

public class PlayingState implements GameState {

    @Override
    public void startGame(SnakeAndLadderGame game) {
        //throw IllegalStateException
    }

    @Override
    public void playTurn(SnakeAndLadderGame game) {
        /*
        Player current = game.getCurrentPlayer();
        int diceValue = game.getDice().roll();
        // Game logic here
        game.notifyObservers(current.getName() + " rolled " + diceValue);
        */
    }

    @Override
    public void endGame(SnakeAndLadderGame game) {
        game.setGameState(new FinishedState());
    }
}
