package game;

import game.interfaces.DiceStrategy;
import java.util.Random;

/**
 * Strategy Pattern
 */
public class NormalDiceStrategy implements DiceStrategy {
    private int face;
    private Random random;

    public NormalDiceStrategy(int face){
        this.face = face;
        this.random = new Random();
    }

    public int roll(){
        return random.nextInt(face) + 1;
    }
}
