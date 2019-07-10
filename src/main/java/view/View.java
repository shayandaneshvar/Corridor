package main.java.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.java.model.Board;
import main.java.model.Observable;

public class View implements Observer {
    private Scene scene;
    private Group root;

    public View(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;
    }

    public Scene getScene() {
        return scene;
    }

    private void drawBoard(Board board) {
        root.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        root.getChildren().add(grid);
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                Label label = new Label("Z");
                label.setMinSize(28, 32);
                label.setStyle("-fx-border-style: DOTTED DOTTED DOTTED " +
                        "DOTTED;-fx-border-color:RED;-fx-border-width:4px;" +
                        "-fx-background-color: BLACK;-fx-padding: 32px;" +
                        "-fx-text-fill: WHITE");
                grid.add(label, i, j);
                if (board.getPlayer1().getLocation().getKey() == i &&
                        board.getPlayer1().getLocation().getValue() == j) {
                    System.out.print("X ");
                    label.setText("X");
                } else if (board.getPlayer2().getLocation().getKey() == i &&
                        board.getPlayer2().getLocation().getValue() == j) {
                    System.out.print("O ");
                    label.setText("Y");
                } else {
                    System.out.print("# ");
                    label.setStyle("-fx-border-style: DOTTED DOTTED DOTTED " +
                            "DOTTED;-fx-border-color:RED;" +
                            "-fx-border-width:4px;" +
                            "-fx-background-color: BLACK;-fx-padding: 32px;" +
                            "-fx-text-fill: BLACK");
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
