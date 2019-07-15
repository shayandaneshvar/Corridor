package main.java.model;

import main.java.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class Board implements Observable {
    private Block[][] gameBoard;
    private List<Observer> observers;
    private Player player1;
    private Player player2;

    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        gameBoard = new Block[9][9];
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                gameBoard[j][i] = new Block(i, j);
                if (j == 0) {
                    gameBoard[j][i].fillUp();
                }
                if (j == 8) {
                    gameBoard[j][i].fillDown();
                }
                if (i == 0) {
                    gameBoard[j][i].fillLeft();
                }
                if (i == 8) {
                    gameBoard[j][i].fillRight();
                }
            }
        }
        observers = new ArrayList<>();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Block[][] getGameBoard() {
        return gameBoard;
    }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void updateObservers() {
        observers.stream().forEach(x -> x.update(this));
    }
}
