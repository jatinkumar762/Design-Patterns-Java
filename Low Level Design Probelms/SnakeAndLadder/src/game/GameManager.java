package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton Pattern
 */
public class GameManager {

    private Map<String, SnakeAndLadderGame> snakeAndLadderGames;
    private static GameManager instance = null;
    private GameFactory gameFactory;

    private GameManager(){
        snakeAndLadderGames = new HashMap<>();
        gameFactory = new GameFactory();
    }

    public static GameManager getInstance(){

        synchronized (GameManager.class) {
            if (instance == null) {
                instance = new GameManager();
            }
        }

        return instance;
    }

    public void addGame(String gameId, List<Player> players, List<Snake> snakes, List<Ladder> ladders){

        SnakeAndLadderGame game = gameFactory.createGame(gameId, players, snakes, ladders);

        game.addObserver(GameLogger.getInstance());

        snakeAndLadderGames.put(gameId, game);
    }

    public void startGame(String gameId){

        SnakeAndLadderGame snakeAndLadderGame = snakeAndLadderGames.get(gameId);

        if(snakeAndLadderGame == null){
            //throw error
            return;
        }

        new Thread(() -> {

            snakeAndLadderGame.start();

            /*
                // Play game
                game.startGame();
                while (!game.isFinished()) {
                    game.playTurn();
                }
                game.endGame();
             */
        }).start();
    }
}
