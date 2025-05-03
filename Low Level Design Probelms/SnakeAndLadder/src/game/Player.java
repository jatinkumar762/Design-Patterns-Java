package game;

import game.interfaces.GameObserver;

public class Player implements GameObserver {
    private String id;
    private String name;
    private int position;

    public Player(String id, String name){
        this.id = id;
        this.name = name;
        position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void update(String message) {
        System.out.println("Player " + name + " received " + message);
    }
}
