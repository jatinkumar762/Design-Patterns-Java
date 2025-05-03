package game;

import game.interfaces.GameObserver;

public class GameLogger implements GameObserver {

    private static GameLogger instance = null;

    private GameLogger(){
    }

    public static GameLogger getInstance(){

        synchronized (GameLogger.class) {
            if (instance == null) {
                instance = new GameLogger();
            }
        }
        return instance;
    }

    @Override
    public void update(String message) {
        System.out.println("Message "+ message);
    }
}
