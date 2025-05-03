package game.interfaces;

import game.SnakeAndLadderGame;

public interface GameState {
    void startGame(SnakeAndLadderGame game);
    void playTurn(SnakeAndLadderGame game);
    void endGame(SnakeAndLadderGame game);
}
