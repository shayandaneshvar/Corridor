package main.java.model;

import javafx.util.Pair;

public class Block {
    private Boolean isFilledUp;
    private Boolean isFilledDown;
    private Boolean isFilledRight;
    private Boolean isFilledLeft;
    private Pair<Integer, Integer> location;

    public Block(int x, int y) {
        this.isFilledUp = false;
        this.isFilledDown = false;
        this.isFilledRight = false;
        this.isFilledLeft = false;
        location = new Pair<>(x, y);
    }

    public Pair<Integer, Integer> getLocation() {
        return location;
    }

    public void fillUp() {
        isFilledUp = true;
    }

    @Override
    public String toString() {
        return "Block{" +
                "isFilledUp=" + isFilledUp +
                ", isFilledDown=" + isFilledDown +
                ", isFilledRight=" + isFilledRight +
                ", isFilledLeft=" + isFilledLeft +
                ", location=" + location.getKey() + "," + location.getValue() +
                '}';
    }

    public void fillDown() {
        isFilledDown = true;
    }

    public void fillLeft() {
        isFilledLeft = true;
    }

    public void fillRight() {
        isFilledRight = true;
    }

    public Boolean getFilledUp() {
        return isFilledUp;
    }

    public Boolean getFilledDown() {
        return isFilledDown;
    }

    public Boolean getFilledRight() {
        return isFilledRight;
    }

    public Boolean getFilledLeft() {
        return isFilledLeft;
    }
}
