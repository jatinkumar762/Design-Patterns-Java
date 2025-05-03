package game;

import game.interfaces.DiceStrategy;

public class Dice {

    private DiceStrategy diceStrategy;

    public Dice(DiceStrategy diceStrategy){
        this.diceStrategy = diceStrategy;
    }

    public int roll(){
        return diceStrategy.roll();
    }
}
