package main.java.controller;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.java.model.Board;
import main.java.model.Player;
import main.java.view.View;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 792, 886);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Corridor");
        scene.setFill(Color.GRAY);
        Player player1 = new Player("Player1", new Pair<>(4, 0));
        Player player2 = new Player("Player2", new Pair<>(4, 8));
        Controller controller = new Controller(new Board(player1, player2),
                new View(scene,root));
        controller.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
