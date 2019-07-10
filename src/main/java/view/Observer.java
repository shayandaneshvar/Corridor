package main.java.view;

import main.java.model.Observable;

public interface Observer {
    void update(Observable observable);
}
