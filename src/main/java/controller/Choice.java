package main.java.controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Pair;
import main.java.model.Board;
import main.java.model.Player;
import main.java.view.View;

public enum Choice {
    EASY, MEDIUM, HARD, MULTIPLAYER, EXIT;

    public static void handleChoice(Choice choice, Group root, Scene scene,
                                    String p1, String p2) {
        Player player1 = new Player(p1, new Pair<>(4, 0));
        Player player2 = new Player(p2, new Pair<>(4, 8));
        Controller controller = new Controller(new Board(player1, player2),
                new View(scene, root));
        controller.startView();
        switch (choice) {
            case EASY:
                controller.easy();
                break;
            case MEDIUM:
                controller.medium();
                break;
            case HARD:
                controller.hard();
                break;
            case MULTIPLAYER:
                controller.multiPlayer();
                break;
            case EXIT:
                Runtime.getRuntime().exit(1);
                break;
        }
    }
}
