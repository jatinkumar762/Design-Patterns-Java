package game;

import game.enums.DiceType;

import java.util.List;

/**
 * Factory Pattern
 */
public class GameFactory {

    public SnakeAndLadderGame createGame(String gameId, List<Player> players, List<Snake> snakes, List<Ladder> ladders){

        Board board = createBoard(100, snakes, ladders);

        Dice dice = createDice(DiceType.NORMAL);

        return new SnakeAndLadderGame(gameId, players, dice, board);
    }


    private Board createBoard(int size, List<Snake> snakes, List<Ladder> ladders) {

        return new Board(100, snakes, ladders);
    }

    private Dice createDice(DiceType type) {
        switch (type) {
            case NORMAL: return new Dice(new NormalDiceStrategy(6));
            default: throw new IllegalArgumentException("Unknown dice type");
        }
    }

}
