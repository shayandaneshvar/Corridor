package main.java.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import main.java.model.Board;
import main.java.model.Player;
import main.java.view.View;

public class Controller {
    private Board board;
    private View view;
    private Integer turn;

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
        turn = 0;
    }

    public void initialize() {
        board.addObserver(view);
        board.updateObservers();
        handleInputs(view.getScene());
    }

    private void handleInputs(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (turn % 2 == 0) {
                    handleUp(board.getPlayer1(), board.getPlayer2(), board);
                } else {
                    handleUp(board.getPlayer2(), board.getPlayer1(), board);
                }
            } else if (event.getCode() == KeyCode.DOWN) {
                if (turn % 2 == 0) {
                    handleDown(board.getPlayer1(), board.getPlayer2(), board);
                } else {
                    handleDown(board.getPlayer2(), board.getPlayer1(), board);
                }

            } else if (event.getCode() == KeyCode.RIGHT) {
                if (turn % 2 == 0) {
                    handleRight(board.getPlayer1(), board.getPlayer2(), board);
                } else {
                    handleRight(board.getPlayer2(), board.getPlayer1(), board);
                }

            } else if (event.getCode() == KeyCode.LEFT) {
                if (turn % 2 == 0) {
                    handleLeft(board.getPlayer1(), board.getPlayer2(), board);
                } else {
                    handleLeft(board.getPlayer2(), board.getPlayer1(), board);
                }
            }
            board.updateObservers();
            isGameOver();
        });
    }

    private void isGameOver() {
        if (board.getPlayer1().getLocation().getValue() == 8) {
            System.out.println("Player1 is the winner!");
            Runtime.getRuntime().exit(1);
        } else if (board.getPlayer2().getLocation().getValue() == 0) {
            System.out.println("Player2 is the winner!");
            Runtime.getRuntime().exit(1);
        }
    }

    private void handleUp(Player moving, Player still, Board board) {
        if (moving.getLocation().getValue() > 0) {
            turn++;
            if (moving.getLocation().getValue() - 1 == still.getLocation().getValue() &&
                    moving.getLocation().getKey() == still.getLocation().getKey()) {
                moving.moveUp();
                moving.moveUp();
            } else {
                moving.moveUp();
            }
        }
    }

    private void handleDown(Player moving, Player still, Board board) {
        if (moving.getLocation().getValue() < 8) {
            turn++;
            if (moving.getLocation().getValue() + 1 == still.getLocation().getValue() &&
                    moving.getLocation().getKey() == still.getLocation().getKey()) {
                moving.moveDown();
                moving.moveDown();
            } else {
                moving.moveDown();
            }
        }
    }

    private void handleRight(Player moving, Player still, Board board) {
        if (moving.getLocation().getKey() < 8) {
            turn++;
            if (moving.getLocation().getKey() + 1 == still.getLocation().getKey() &&
                    moving.getLocation().getValue() == still.getLocation().getValue()) {
                moving.moveRight();
                moving.moveRight();
            } else {
                moving.moveRight();
            }
        }
    }

    private void handleLeft(Player moving, Player still, Board board) {
        if (moving.getLocation().getKey() > 0) {
            turn++;
            if (moving.getLocation().getKey() - 1 == still.getLocation().getKey() &&
                    moving.getLocation().getValue() == still.getLocation().getValue()) {
                moving.moveLeft();
                moving.moveLeft();
            } else {
                moving.moveLeft();
            }
        }
    }
}
