package main.java.model;

import javafx.util.Pair;

public class Player {
    private String name;
    private Pair<Integer, Integer> location;
    private Wall[][] walls;

    public Player(String name, Pair<Integer, Integer> location) {
        this.name = name;
        this.location = location;
        walls = new Wall[8][8];
    }

    public void moveUp() {
        location = new Pair<>(location.getKey(), location.getValue() - 1);
    }

    public void moveDown() {
        location = new Pair<>(location.getKey(), location.getValue() + 1);
    }

    public void moveRight() {
        location = new Pair<>(location.getKey() + 1, location.getValue());
    }

    public void moveLeft() {
        location = new Pair<>(location.getKey() - 1, location.getValue());
    }

    public String getName() {
        return name;
    }

    public Pair<Integer, Integer> getLocation() {
        return location;
    }
}
