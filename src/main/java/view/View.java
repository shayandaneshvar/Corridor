package main.java.view;

import javafx.scene.Scene;
import main.java.model.Board;
import main.java.model.Observable;

public class View implements Observer {
    private Scene scene;

    public View(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    private void drawBoard(Board board) {
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board.getPlayer1().getLocation().getKey() == i &&
                        board.getPlayer1().getLocation().getValue() == j) {
                    System.out.print("X ");
                } else if (board.getPlayer2().getLocation().getKey() == i &&
                        board.getPlayer2().getLocation().getValue() == j) {
                    System.out.print("O ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("-------------------------");
    }

    @Override
    public void update(Observable observable) {
        drawBoard((Board) observable);
    }
}
