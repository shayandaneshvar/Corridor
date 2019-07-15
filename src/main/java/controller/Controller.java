package main.java.controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.java.model.Block;
import main.java.model.Board;
import main.java.model.Direction;
import main.java.model.Player;
import main.java.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Controller {
    private Board board;
    private View view;
    private Integer turn;
    private AtomicReference<KeyCode> shift;
    private AtomicReference<Direction> dir;
    private AtomicReference<MouseButton> mouseButton;
    private AtomicInteger xCoord;

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
        turn = 0;
        shift = new AtomicReference<>();
        dir = new AtomicReference<>();
        xCoord = new AtomicInteger();
        dir.set(Direction.HORIZONTAL);
        xCoord.set(-1);
        shift.set(KeyCode.K);
        mouseButton = new AtomicReference<>(MouseButton.PRIMARY);
    }

    public static void initialize(Group root, Scene scene) {
        View.drawMainMenu(root, scene);
    }

    public void multiPlayer() {
        handleInputsMulti(view.getScene());
    }

    public void startView() {
        board.addObserver(view);
        board.updateObservers();
    }

    public void easy() {
        Function randomPlayer = (board) -> {
            if (board.getPlayer2().getAvailableWalls() > 0 && Math.abs(new
                    Random().nextInt()) % 4 == 0) {
                while (turn % 2 != 0) {
                    handleWall(Math.abs(new Random().nextInt()) % 8,
                            Math.abs(new Random().nextInt()) % 8, Direction.
                                    values()[Math.abs(new Random().nextInt())
                                    % 2]);
                }
            } else {
                while (turn % 2 != 0) {
                    switch (Sake.values()[Math.abs(new Random().nextInt())
                            % 4]) {
                        case UP:
                            handleUp(board.getPlayer2(), board.getPlayer1(),
                                    board);
                            break;
                        case DOWN:
                            handleDown(board.getPlayer2(),
                                    board.getPlayer1(), board);
                            break;
                        case RIGHT:
                            handleRight(board.getPlayer2(), board.getPlayer1(),
                                    board);
                            break;
                        case LEFT:
                            handleLeft(board.getPlayer2(), board.getPlayer1(),
                                    board);
                            break;
                    }
                }
            }
        };
        handleInputsSingle(view.getScene(), randomPlayer);
    }

    public void medium() {
        Function dijkstraAlgorithm = (board -> {
            if (board.getPlayer2().getAvailableWalls() > 0 && Math.abs(new
                    Random().nextInt()) % 3 == 0) {
                while (turn % 2 != 0) {
                    handleWall(board.getPlayer1().getLocation().getKey() % 8,
                            board.getPlayer1().getLocation().getValue() % 8,
                            Direction.HORIZONTAL);
                    if (turn % 2 != 0) {
                        int rand = 7 > board.getPlayer2().getLocation().getValue() ?
                                2 : 8 - board.getPlayer2().getLocation().getValue();
                        rand = 7 - Math.abs(new Random().nextInt()) % rand;
                        handleWall(Math.abs(new Random().nextInt()) % 8, rand,
                                Direction.values()[Math.abs(new Random().nextInt()) % 3 % 2]);
                    }
                }
            } else {
                dijkstra(board);
            }
        });
        handleInputsSingle(view.getScene(), dijkstraAlgorithm);
    }

    // FIXME: 7/15/2019
    public void hard() {
        Function fuzzy = (board -> {
            //gonna use Fuzzy Logic Table - and an Defuzzifier
            //USE <<JFUZZYLOGIC>> Library
            if (false) {
                //todo
                //putting walls
            } else {
                //Moves
                dijkstra(board);
            }
        });
        handleInputsSingle(view.getScene(), fuzzy);
    }

    private void dijkstra(Board board) {
        while (turn % 2 != 0) {
            Pair<Integer, Integer> nextMove =
                    findNextMove(board.getPlayer2());
            board.getPlayer2().setLocation(nextMove);
            if (board.getPlayer2().getLocation().equals(board.getPlayer1().getLocation())) {
                board.getPlayer2().moveUp();
            }
            turn++;
        }
        isGameOver();
    }

    //uses Dijkstra Algorithm
    //This situation has been Ignored oo] *can be improved
    private Pair<Integer, Integer> findNextMove(Player player) {
        LinkedBlockingQueue<Triple<Block, Integer, ArrayList<Block>>> queue =
                new LinkedBlockingQueue<>();
        queue.add(new Triple<>(board.getGameBoard()[player.getLocation().getValue
                ()][player.getLocation().getKey()], 0, new ArrayList<>()));
        List<Triple<Block, Integer, ArrayList<Block>>> visitedBlocks =
                new ArrayList<>();
        do {
            Triple<Block, Integer, ArrayList<Block>> block = queue.poll();
            if (visitedBlocks.stream().anyMatch(x -> x.getFirst().getLocation().
                    equals(block.getFirst().getLocation()))) {
                Triple<Block, Integer, ArrayList<Block>> temp;
                if (block.getSecond() < (temp = visitedBlocks.stream().filter(x
                        -> x.getFirst().getLocation().
                        equals(block.getFirst().getLocation())).collect(
                        Collectors.toList()).get(0)).getSecond()) {
                    temp.setSecond(block.getSecond());
                    temp.setThird(block.getThird());
                }
                continue;
            }
            visitedBlocks.add(block);
            int weight;
            if (isValid(block.getFirst().getLocation().getKey() + 1,
                    block.getFirst().getLocation()
                            .getValue(), Sake.RIGHT)) {
                weight = block.getSecond() + 1;
                if (player.getLocation().getKey() == block.getFirst().getLocation().getKey() + 1 &&
                        player.getLocation().getValue() == block.getFirst().getLocation().getValue()) {
                    weight--;
                }
                ArrayList<Block> list = (ArrayList<Block>) block.getThird().clone();
                list.add(block.getFirst());
                queue.add(new Triple<>(board.getGameBoard()[block.getFirst().getLocation().getValue()]
                        [block.getFirst().getLocation().getKey() + 1], weight
                        , list));
            }
            if (isValid(block.getFirst().getLocation().getKey() - 1,
                    block.getFirst().getLocation().getValue(), Sake.LEFT)) {
                weight = block.getSecond() + 1;
                if (player.getLocation().getKey() == block.getFirst().getLocation().getKey() - 1 &&
                        player.getLocation().getValue() == block.getFirst().getLocation().getValue()) {
                    weight--;
                }
                ArrayList<Block> list = (ArrayList<Block>) block.getThird().clone();
                list.add(block.getFirst());
                queue.add(new Triple<>(board.getGameBoard()[block.getFirst().getLocation().getValue()]
                        [block.getFirst().getLocation().getKey() - 1], weight
                        , list));
            }
            if (isValid(block.getFirst().getLocation().getKey(),
                    block.getFirst().getLocation()
                            .getValue() + 1, Sake.DOWN)) {
                weight = block.getSecond() + 1;
                if (player.getLocation().getKey() == block.getFirst().getLocation().getKey() &&
                        player.getLocation().getValue() == block.getFirst().getLocation().getValue() + 1) {
                    weight--;
                }
                ArrayList<Block> list = (ArrayList<Block>) block.getThird().clone();
                list.add(block.getFirst());
                queue.add(new Triple<>(board.getGameBoard()[block.getFirst().getLocation().getValue()
                        + 1][block.getFirst().getLocation().getKey()], weight
                        , list));
            }
            if (isValid(block.getFirst().getLocation().getKey(),
                    block.getFirst().getLocation()
                            .getValue() - 1, Sake.UP)) {
                weight = block.getSecond() + 1;
                if (player.getLocation().getKey() == block.getFirst().getLocation().getKey() &&
                        player.getLocation().getValue() == block.getFirst().getLocation().getValue() - 1) {
                    weight--;
                }
                ArrayList<Block> list = (ArrayList<Block>) block.getThird().clone();
                list.add(block.getFirst());
                queue.add(new Triple<>(board.getGameBoard()[block.getFirst().getLocation().getValue() -
                        1][block.getFirst().getLocation().getKey()], weight,
                        list));
            }
        } while (!queue.isEmpty());
        List<Block> results = visitedBlocks.stream().filter(x -> x.getFirst().getLocation()
                .getValue() == 0).reduce((x, y) -> x.getSecond() > y.getSecond()
                ? y : x).get().getThird();
        if (results.size() <= 1) {
            board.getPlayer2().moveUp();
            return board.getPlayer2().getLocation();
        }
        return results.get(1).getLocation();
    }


    public void handleInputsSingle(Scene scene, Function function) {
        scene.setOnMouseClicked(event -> {
            if (turn % 2 == 0) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    dir.set(Direction.HORIZONTAL);
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    dir.set(Direction.VERTICAL);
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            if (turn % 2 == 0) {
                wallHandler(shift, dir, xCoord, event);
            }
        });
        scene.setOnKeyPressed(event -> {
            if (turn % 2 == 0) {
                if (shift.get() != KeyCode.SHIFT) {
                    shift.set(KeyCode.K);
                    if (event.getCode() == KeyCode.UP) {
                        handleUp(board.getPlayer1(), board.getPlayer2(), board);
                    } else if (event.getCode() == KeyCode.DOWN) {
                        handleDown(board.getPlayer1(), board.getPlayer2(), board);
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        handleRight(board.getPlayer1(), board.getPlayer2(), board);
                    } else if (event.getCode() == KeyCode.LEFT) {
                        handleLeft(board.getPlayer1(), board.getPlayer2(), board);
                    }
                    board.updateObservers();
                    isGameOver();
                }
            }
            function.action(board);
            board.updateObservers();
        });
    }

    private void wallHandler(AtomicReference<KeyCode> shift, AtomicReference
            <Direction> dir, AtomicInteger xCoord, KeyEvent event) {
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
    }

    private void handleInputsMulti(Scene scene) {
        scene.setOnMouseClicked(event -> {
            mouseButton.set(event.getButton());
            if (event.getButton() == MouseButton.PRIMARY) {
                dir.set(Direction.HORIZONTAL);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                dir.set(Direction.VERTICAL);
            }
        });
        scene.setOnKeyReleased(event -> {
            wallHandler(shift, dir, xCoord, event);
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
        handleWall(x, y, dir);

    }

    private void handleWall(int x, int y, Direction dir) {
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
                View.drawGameOver(board.getPlayer2().getName(), Color.ORANGE);
            } else {
                System.out.println("Player 2 Lost The Game");
                View.drawGameOver(board.getPlayer1().getName(), Color.ORANGE);
            }
        }
    }

    /**
     * Uses BFS Algorithm in order to check whether the current player has
     * surrounded him/herself or the other player
     *
     * @param player recently played player
     * @param row    target row
     * @return a boolean
     */
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
            View.drawGameOver(board.getPlayer1().getName(), Color.CRIMSON);
        } else if (board.getPlayer2().getLocation().getValue() == 0) {
            System.out.println("Player2 is the winner!");
            View.drawGameOver(board.getPlayer2().getName(), Color.GREENYELLOW);
        }
    }

    private void handleUp(Player moving, Player still, Board board) {
        if (moving.getLocation().getValue() > 0) {
            if (!board.getGameBoard()[moving.getLocation().getValue() - 1][moving
                    .getLocation().getKey()].getFilledDown()) {
                turn++;
                if (moving.getLocation().getValue() - 1 == still.getLocation().getValue() &&
                        moving.getLocation().getKey() == still.getLocation().getKey()) {
                    if (moving.getLocation().getValue() - 2 >= 0 && !board.getGameBoard()[moving.getLocation().getValue()
                            - 2][moving.getLocation().getKey()].getFilledDown()) {
                        moving.moveUp();
                        moving.moveUp();
                    } else {
                        if (moving.getLocation().getValue() + 1 < 8 &&
                                !board.getGameBoard()[moving.getLocation().
                                        getValue() - 1][moving.getLocation()
                                        .getKey() + 1].getFilledLeft() && mouseButton.get() == MouseButton.SECONDARY) {
                            moving.moveUp();
                            moving.moveRight();
                            mouseButton.set(MouseButton.PRIMARY);
                        } else if (moving.getLocation().getValue() - 1 >= 0
                                && !board.getGameBoard()[moving.getLocation().
                                getValue() - 1][moving.getLocation().getKey
                                () - 1].getFilledRight() && mouseButton.get() == MouseButton.PRIMARY) {
                            moving.moveUp();
                            moving.moveLeft();
                        } else {
                            turn--;
                            mouseButton.set(MouseButton.PRIMARY);
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
            if (!board.getGameBoard()[moving.getLocation().getValue() + 1][moving
                    .getLocation().getKey()].getFilledUp()) {
                turn++;
                if (moving.getLocation().getValue() + 1 == still.getLocation().getValue() &&
                        moving.getLocation().getKey() == still.getLocation().getKey()) {
                    if (moving.getLocation().getValue() + 2 < 9 && !board.getGameBoard()[moving.getLocation().getValue()
                            + 2][moving.getLocation().getKey()].getFilledUp()) {
                        moving.moveDown();
                        moving.moveDown();
                    } else {
                        if (moving.getLocation().getValue() + 1 <= 8 &&
                                !board.getGameBoard()[moving.getLocation().
                                        getValue() + 1][moving.getLocation()
                                        .getKey() + 1].getFilledLeft() && mouseButton.get() == MouseButton.SECONDARY) {
                            moving.moveDown();
                            moving.moveRight();
                            mouseButton.set(MouseButton.PRIMARY);
                        } else if (moving.getLocation().getValue() - 1 >= 0
                                && !board.getGameBoard()[moving.getLocation().
                                getValue() + 1][moving.getLocation().getKey
                                () - 1].getFilledRight() && mouseButton.get() == MouseButton.PRIMARY) {
                            moving.moveDown();
                            moving.moveLeft();
                        } else {
                            turn--;
                            mouseButton.set(MouseButton.PRIMARY);
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
            if (!board.getGameBoard()[moving.getLocation().getValue()][moving
                    .getLocation().getKey() + 1].getFilledLeft()) {
                turn++;
                if (moving.getLocation().getKey() + 1 == still.getLocation().getKey() &&
                        moving.getLocation().getValue() == still.getLocation().getValue()) {

                    if (moving.getLocation().getKey() + 2 < 9 && !board.getGameBoard()[moving.getLocation().getValue()]
                            [moving.getLocation().getKey() + 2].getFilledLeft()) {
                        moving.moveRight();
                        moving.moveRight();
                    } else {
                        if (moving.getLocation().getKey() + 1 <= 8 &&
                                !board.getGameBoard()[moving.getLocation().
                                        getValue() - 1][moving.getLocation()
                                        .getKey() + 1].getFilledDown() && mouseButton.get() == MouseButton.PRIMARY) {
                            moving.moveRight();
                            moving.moveUp();
                        } else if (moving.getLocation().getKey() + 1 < 9
                                && !board.getGameBoard()[moving.getLocation().
                                getValue() + 1][moving.getLocation().getKey
                                () + 1].getFilledUp() && mouseButton.get() == MouseButton.SECONDARY) {
                            moving.moveDown();
                            moving.moveRight();
                            mouseButton.set(MouseButton.PRIMARY);
                        } else {
                            turn--;
                            mouseButton.set(MouseButton.PRIMARY);
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
            if (!board.getGameBoard()[moving.getLocation().getValue()][moving
                    .getLocation().getKey() - 1].getFilledRight()) {
                turn++;
                if (moving.getLocation().getKey() - 1 == still.getLocation().getKey() &&
                        moving.getLocation().getValue() == still.getLocation().getValue()) {

                    if (moving.getLocation().getKey() - 2 >= 0 && !board.getGameBoard()[moving.getLocation().getValue()]
                            [moving.getLocation().getKey() - 2].getFilledRight()) {
                        moving.moveLeft();
                        moving.moveLeft();
                    } else {
                        if (moving.getLocation().getKey() - 1 >= 0 &&
                                !board.getGameBoard()[moving.getLocation().
                                        getValue() - 1][moving.getLocation()
                                        .getKey() - 1].getFilledDown() && mouseButton.get() == MouseButton.SECONDARY) {
                            moving.moveLeft();
                            moving.moveUp();
                            mouseButton.set(MouseButton.PRIMARY);
                        } else if (moving.getLocation().getKey() + 1 < 9
                                && !board.getGameBoard()[moving.getLocation().
                                getValue() + 1][moving.getLocation().getKey
                                () - 1].getFilledUp() && mouseButton.get() == MouseButton.PRIMARY) {
                            moving.moveLeft();
                            moving.moveDown();
                        } else {
                            turn--;
                            mouseButton.set(MouseButton.PRIMARY);
                        }
                    }

                } else {
                    moving.moveLeft();
                }

            }
        }
    }
}