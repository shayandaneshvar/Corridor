package main.java.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import main.java.model.Block;
import main.java.model.Board;
import main.java.model.Direction;
import main.java.model.Player;
import main.java.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        AtomicReference<KeyCode> shift = new AtomicReference<>();
        AtomicReference<Direction> dir = new AtomicReference<>();
        AtomicInteger xCoord = new AtomicInteger();
        dir.set(Direction.HORIZONTAL);
        xCoord.set(-1);
        shift.set(KeyCode.K);
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dir.set(Direction.HORIZONTAL);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                dir.set(Direction.VERTICAL);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SHIFT) {
                shift.set(KeyCode.SHIFT);
            }
            if (shift.get() == KeyCode.SHIFT && event.getCode() != KeyCode.SHIFT) {
                switch (event.getCode()) {
                    case DIGIT1:
                        if (xCoord.get() == -1) {
                            xCoord.set(1);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT2:
                        if (xCoord.get() == -1) {
                            xCoord.set(2);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT3:
                        if (xCoord.get() == -1) {
                            xCoord.set(3);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT4:
                        if (xCoord.get() == -1) {
                            xCoord.set(4);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT5:
                        if (xCoord.get() == -1) {
                            xCoord.set(5);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT6:
                        if (xCoord.get() == -1) {
                            xCoord.set(6);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT7:
                        if (xCoord.get() == -1) {
                            xCoord.set(7);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    case DIGIT8:
                        if (xCoord.get() == -1) {
                            xCoord.set(8);
                        } else {
                            handleWall(xCoord.get() - 1, handleY(event.
                                    getCode()) - 1, dir.get(), xCoord);
                            dir.set(Direction.HORIZONTAL);
                        }
                        break;
                    default:
                        xCoord.set(-1);
                        shift.set(KeyCode.K);
                }
            }
        });
        scene.setOnKeyPressed(event -> {
            if (shift.get() != KeyCode.SHIFT) {
                shift.set(KeyCode.K);
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
            }
        });
    }

    private int handleY(KeyCode yCoord) {
        switch (yCoord) {
            case DIGIT1:
                return 1;
            case DIGIT2:
                return 2;
            case DIGIT3:
                return 3;
            case DIGIT4:
                return 4;
            case DIGIT5:
                return 5;
            case DIGIT6:
                return 6;
            case DIGIT7:
                return 7;
            case DIGIT8:
                return 8;
        }
        return -1;
    }

    private void handleWall(int x, int y, Direction dir, AtomicInteger xCoord) {
        xCoord.set(-1);
        if (!board.getPlayer1().getWalls()[y][x].isActive() && !board.getPlayer2
                ().getWalls()[y][x].isActive()) {
            if (dir == Direction.HORIZONTAL) {
                if (!board.getGameBoard()[y][x].getFilledDown() && !board.
                        getGameBoard()[y][x + 1].getFilledDown()) {
                    board.getGameBoard()[y][x].fillDown();
                    board.getGameBoard()[y][x + 1].fillDown();
                    board.getGameBoard()[y + 1][x].fillUp();
                    board.getGameBoard()[y + 1][x + 1].fillUp();
                    wallFinalizer(x, y, dir);
                }
            } else {
                if (!board.getGameBoard()[y][x].getFilledRight() && !board.
                        getGameBoard()[y + 1][x].getFilledRight()) {
                    board.getGameBoard()[y][x].fillRight();
                    board.getGameBoard()[y + 1][x].fillRight();
                    board.getGameBoard()[y][x + 1].fillLeft();
                    board.getGameBoard()[y + 1][x + 1].fillLeft();
                    wallFinalizer(x, y, dir);
                }
            }
        }
    }

    private void wallFinalizer(int x, int y, Direction dir) {
        if (turn % 2 == 0) {
            if (board.getPlayer1().getAvailableWalls() > 0) {
                board.getPlayer1().putWall(x, y, dir);
            }
        } else {
            if (board.getPlayer2().getAvailableWalls() > 0) {
                board.getPlayer2().putWall(x, y, dir);
            }
        }
        if (turn % 2 == 0) {
            checkBehaviour();
        } else {
            checkBehaviour();
        }
        turn++;
        board.updateObservers();
    }

    private void checkBehaviour() {
        boolean isGameOver;
        isGameOver = checkPlayers(board.getPlayer1(), 8);
        isGameOver = isGameOver || checkPlayers(board.getPlayer2(), 0);
        if (isGameOver) {
            if (turn % 2 == 0) {
                System.out.println("Player 1 Lost The Game");
            } else {
                System.out.println("Player 2 Lost The Game");
            }
            Runtime.getRuntime().exit(1);
        }
    }

    private boolean checkPlayers(Player player, int row) {
        LinkedBlockingQueue<Block> queue = new LinkedBlockingQueue<>();
        queue.add(board.getGameBoard()[player.getLocation().getValue
                ()][player.getLocation().getKey()]);
        List<Block> visitedBlocks = new ArrayList<>();
        do {
            Block block = queue.poll();
            if (visitedBlocks.contains(block)) {
                continue;
            }
            visitedBlocks.add(block);
            if (isValid(block.getLocation().getKey() + 1, block.getLocation()
                    .getValue(), Sake.RIGHT)) {
                queue.add(board.getGameBoard()[block.getLocation().getValue()]
                        [block.getLocation().getKey() + 1]);
            }
            if (isValid(block.getLocation().getKey() - 1, block.
                    getLocation().getValue(), Sake.LEFT)) {
                queue.add(board.getGameBoard()[block.getLocation().getValue()]
                        [block.getLocation().getKey() - 1]);
            }
            if (isValid(block.getLocation().getKey(), block.getLocation()
                    .getValue() + 1, Sake.DOWN)) {
                queue.add(board.getGameBoard()[block.getLocation().getValue()
                        + 1][block.getLocation().getKey()]);
            }
            if (isValid(block.getLocation().getKey(), block.getLocation()
                    .getValue() - 1, Sake.UP)) {
                queue.add(board.getGameBoard()[block.getLocation().getValue() -
                        1][block.getLocation().getKey()]);
            }
        } while (!queue.isEmpty());
        return visitedBlocks.stream().noneMatch(x -> x.getLocation().getValue()
                == row);
    }

    private boolean isValid(int x, int y, Sake sake) {
        if (x < 0 || y < 0 || x > 8 || y > 8) {
            return false;
        }
        boolean result = false;
        switch (sake) {
            case UP:
                result = !board.getGameBoard()[y][x].getFilledDown();
                break;
            case DOWN:
                result = !board.getGameBoard()[y][x].getFilledUp();
                break;
            case LEFT:
                result = !board.getGameBoard()[y][x].getFilledRight();
                break;
            case RIGHT:
                result = !board.getGameBoard()[y][x].getFilledLeft();
                break;
        }
        return result;
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
            if (!board.getGameBoard()[moving.getLocation().getValue() - 1][moving
                    .getLocation().getKey()].getFilledDown()) {
                if (moving.getLocation().getValue() - 1 == still.getLocation().getValue() &&
                        moving.getLocation().getKey() == still.getLocation().getKey()) {
                    if (moving.getLocation().getValue() - 2 >= 0) {
                        if (!board.getGameBoard()[moving.getLocation().getValue()
                                - 2][moving.getLocation().getKey()].getFilledDown()) {
                            moving.moveUp();
                            moving.moveUp();
                        } else {
                            if (moving.getLocation().getValue() + 1 < 8 &&
                                    !board.getGameBoard()[moving.getLocation().
                                            getValue() - 1][moving.getLocation()
                                            .getKey() + 1].getFilledLeft()) {
                                moving.moveUp();
                                moving.moveRight();
                            } else if (moving.getLocation().getValue() - 1 >= 0
                                    && !board.getGameBoard()[moving.getLocation().
                                    getValue() - 1][moving.getLocation().getKey
                                    () - 1].getFilledRight()) {
                                moving.moveUp();
                                moving.moveLeft();
                            }
                        }
                    }
                } else {
                    moving.moveUp();
                }
            }
        }
    }

    private void handleDown(Player moving, Player still, Board board) {
        if (moving.getLocation().getValue() < 8) {
            turn++;
            if (!board.getGameBoard()[moving.getLocation().getValue() + 1][moving
                    .getLocation().getKey()].getFilledUp()) {
                if (moving.getLocation().getValue() + 1 == still.getLocation().getValue() &&
                        moving.getLocation().getKey() == still.getLocation().getKey()) {
                    if (moving.getLocation().getValue() + 2 < 9) {
                        if (!board.getGameBoard()[moving.getLocation().getValue()
                                + 2][moving.getLocation().getKey()].getFilledUp()) {
                            moving.moveDown();
                            moving.moveDown();
                        } else {
                            if (moving.getLocation().getValue() + 1 <= 8 &&
                                    !board.getGameBoard()[moving.getLocation().
                                            getValue() + 1][moving.getLocation()
                                            .getKey() + 1].getFilledLeft()) {
                                moving.moveDown();
                                moving.moveRight();
                            } else if (moving.getLocation().getValue() - 1 >= 0
                                    && !board.getGameBoard()[moving.getLocation().
                                    getValue() + 1][moving.getLocation().getKey
                                    () - 1].getFilledRight()) {
                                moving.moveDown();
                                moving.moveLeft();
                            }
                        }
                    }
                } else {
                    moving.moveDown();
                }

            }
        }
    }

    private void handleRight(Player moving, Player still, Board board) {
        if (moving.getLocation().getKey() < 8) {
            turn++;
            if (!board.getGameBoard()[moving.getLocation().getValue()][moving
                    .getLocation().getKey() + 1].getFilledLeft()) {
                if (moving.getLocation().getKey() + 1 == still.getLocation().getKey() &&
                        moving.getLocation().getValue() == still.getLocation().getValue()) {
                    if (moving.getLocation().getKey() + 2 <= 8) {
                        if (!board.getGameBoard()[moving.getLocation().getValue()]
                                [moving.getLocation().getKey() + 2].getFilledLeft()) {
                            moving.moveRight();
                            moving.moveRight();
                        } else {
                            if (moving.getLocation().getKey() + 1 <= 8 &&
                                    !board.getGameBoard()[moving.getLocation().
                                            getValue() - 1][moving.getLocation()
                                            .getKey() + 1].getFilledLeft()) {
                                moving.moveUp();
                                moving.moveRight();
                            } else if (moving.getLocation().getKey() + 1 < 9
                                    && !board.getGameBoard()[moving.getLocation().
                                    getValue() + 1][moving.getLocation().getKey
                                    () + 1].getFilledLeft()) {
                                moving.moveDown();
                                moving.moveRight();
                            }
                        }
                    }
                } else {
                    moving.moveRight();
                }

            }
        }
    }

    private void handleLeft(Player moving, Player still, Board board) {
        if (moving.getLocation().getKey() > 0) {
            turn++;
            if (!board.getGameBoard()[moving.getLocation().getValue()][moving
                    .getLocation().getKey() - 1].getFilledRight()) {
                if (moving.getLocation().getKey() - 1 == still.getLocation().getKey() &&
                        moving.getLocation().getValue() == still.getLocation().getValue()) {
                    if (moving.getLocation().getKey() - 2 >= 0) {
                        if (!board.getGameBoard()[moving.getLocation().getValue()]
                                [moving.getLocation().getKey() - 2].getFilledRight()) {
                            moving.moveLeft();
                            moving.moveLeft();
                        } else {
                            if (moving.getLocation().getKey() - 1 >= 0 &&
                                    !board.getGameBoard()[moving.getLocation().
                                            getValue() - 1][moving.getLocation()
                                            .getKey() - 1].getFilledRight()) {
                                moving.moveUp();
                                moving.moveLeft();
                            } else if (moving.getLocation().getKey() + 1 < 9
                                    && !board.getGameBoard()[moving.getLocation().
                                    getValue() + 1][moving.getLocation().getKey
                                    () - 1].getFilledRight()) {
                                moving.moveDown();
                                moving.moveLeft();
                            }
                        }
                    }
                } else {
                    moving.moveLeft();
                }

            }
        }
    }
}
