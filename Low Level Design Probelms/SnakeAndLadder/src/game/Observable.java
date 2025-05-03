package game;

import game.interfaces.GameObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<GameObserver> observers = new ArrayList<>();

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    protected void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }
}
