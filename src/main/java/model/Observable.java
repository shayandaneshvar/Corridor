package main.java.model;

import main.java.view.Observer;

public interface Observable {

    void addObserver(Observer observer);

    void updateObservers();
}
