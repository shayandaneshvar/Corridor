package main.java.model;

public class Block {
    private Boolean isFilled;
    private Boolean isFilledUp;
    private Boolean isFilledDown;
    private Boolean isFilledRight;
    private Boolean isFilledLeft;

    public Block() {
        this.isFilled = false;
        this.isFilledUp = false;
        this.isFilledDown = false;
        this.isFilledRight = false;
        this.isFilledLeft = false;
    }

    public void fillUp() {
        isFilledUp = true;
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

    public void fillSelf() {
        isFilled = true;
    }

    public void emptySelf() {
        isFilled = false;
    }


    public Boolean getFilled() {
        return isFilled;
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
