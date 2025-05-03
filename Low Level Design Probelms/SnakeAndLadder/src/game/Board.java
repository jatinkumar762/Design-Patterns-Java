package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Map<Integer, Integer> jump;

    public Board(int size, List<Snake> snakes, List<Ladder> ladders){
        this.size = size;

        this.snakes = snakes;
        this.ladders = ladders;

        this.jump = new HashMap<>();

        snakes.forEach(snake -> jump.put(snake.getEnd(), snake.getStart()));
        ladders.forEach(ladder -> jump.put(ladder.getStart(), ladder.getEnd()));

    }

    public int getSize() {
        return size;
    }

    public int getJumpPosition(int position){
        return jump.getOrDefault(position, position);
    }

    public boolean isSafe(int position){
        return position>=0 && position <= size;
    }

    public boolean isWinner(int position){
        return position == size;
    }
}
