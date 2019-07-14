package main.java.controller;

import main.java.model.Board;

@FunctionalInterface
public interface Function {
    void action(Board board);
}
