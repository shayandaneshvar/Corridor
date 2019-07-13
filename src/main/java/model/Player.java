package main.java.model;

import javafx.util.Pair;

public class Player {
    private String name;
    private Pair<Integer, Integer> location;
    private Wall[][] walls;
    private Integer availableWalls;

    public Integer getAvailableWalls() {
        return availableWalls;
    }

    private void decrementWalls() {
        availableWalls--;
    }

    public Player(String name, Pair<Integer, Integer> location) {
        this.name = name;
        this.location = location;
        walls = new Wall[8][8];
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                walls[j][i] = new Wall();
            }
        }
        availableWalls = 10;
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

    public void putWall(int x, int y, Direction dir) {
        walls[y][x].setDir(dir);
        decrementWalls();
    }

    public Wall[][] getWalls() {
        return walls;
    }

    public String getName() {
        return name;
    }

    public Pair<Integer, Integer> getLocation() {
        return location;
    }
}
