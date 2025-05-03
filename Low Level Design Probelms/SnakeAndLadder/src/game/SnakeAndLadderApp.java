package game;

import java.util.List;

public class SnakeAndLadderApp {

    public static void main(String args[]){

        List<Player> players = List.of(new Player("p1", "Alice"), new Player("p2", "Bob"));
        List<Snake> snakes = List.of(new Snake(99, 10), new Snake(90, 30), new Snake(70, 50));
        List<Ladder> ladders = List.of(new Ladder(5, 25), new Ladder(40, 89), new Ladder(60, 97));

        GameManager gameManager = GameManager.getInstance();

        gameManager.addGame("g1", players, snakes, ladders);

        gameManager.startGame("g1");
    }
}
