package main.java.model;

public class Wall {
    private Direction dir;
    private Boolean isActive;

    public Wall() {
        this.isActive = false;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
        isActive = true;
    }

    public Boolean isActive() {
        return isActive;
    }
}
