package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.model.Block;
import main.java.model.Board;
import main.java.model.Observable;

public class View implements Observer {
    private Scene scene;
    private Group root;

    public View(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;
    }

    public static void drawGameOver(String name) {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 150, true,
                SceneAntialiasing.BALANCED);
        stage.setTitle("Game Over");
        scene.setFill(Color.CRIMSON);
        stage.setScene(scene);
        stage.setResizable(false);
        VBox vBox = new VBox();
        root.getChildren().add(vBox);
        Label gameOver = new Label(name + " " + "Has Won!");
        gameOver.setPadding(new Insets(26d));
        gameOver.setFont(Font.font("Bauhaus LT Medium", FontWeight.SEMI_BOLD,
                20));
        MenuItem ok = new MenuItem("OK", 200, 30);
        vBox.getChildren().addAll(gameOver, ok);
        vBox.setSpacing(14d);
        vBox.setTranslateX(46d);
        stage.show();
        vBox.setPadding(new Insets(4d));
        ok.setOnMouseClicked(event -> Runtime.getRuntime().exit(1));
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
                String borderStyle =
                        borderStyleFactory(board.getGameBoard()[j][i]);
                label.setStyle("-fx-border-style: " + borderStyle + ";-fx-border-color" +
                        ":RED;-fx-border-width:4px;" +
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
                    label.setStyle("-fx-border-style:" + borderStyle + ";-fx" +
                            "-border-color:RED; -fx-border-width:4px;" +
                            "-fx-background-color: BLACK;-fx-padding: 32px;" +
                            "-fx-text-fill: BLACK");
                }
            }
            System.out.println(" ");
        }
        System.out.println("-------------------------");
    }

    private String borderStyleFactory(Block block) {
        String result = "";
        if (block.getFilledUp()) {
            result += "SOLID ";
        } else {
            result += "DASHED ";
        }
        if (block.getFilledRight()) {
            result += "SOLID ";
        } else {
            result += "DASHED ";
        }
        if (block.getFilledDown()) {
            result += "SOLID ";
        } else {
            result += "DASHED ";
        }
        if (block.getFilledLeft()) {
            result += "SOLID ";
        } else {
            result += "DASHED ";
        }
        return result;
    }

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(375, 60);
            bg.setStroke(Color.BLACK.darker());
            bg.setStrokeWidth(3);
            bg.setFill(null);
            Text text = new Text(name);
            text.setFill(Color.GRAY.darker());
            text.setFont(Font.font("NPITerme", FontWeight.SEMI_BOLD, 54));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());
            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

    /**
     *
     */
    public static class MenuItem extends StackPane {

        /**
         * @param name
         * @param width
         * @param height
         */
        public MenuItem(String name, int width, int height) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true,
                    CycleMethod.NO_CYCLE, new Stop(0, Color.GREENYELLOW),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.GREENYELLOW));
            Rectangle bg = new Rectangle(width, height);
            bg.setOpacity(0.45);
            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Bauhaus LT Medium", FontWeight.SEMI_BOLD,
                    28));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                text.setFill(Color.WHITE);
                bg.setFill(gradient);

            });
            setOnMouseExited(event -> {
                text.setFill(Color.DARKGREY);
                bg.setFill(Color.BLACK);
            });
            setOnMousePressed(event -> {
                bg.setFill(Color.BLUE);
            });
            setOnMouseReleased(event -> bg.setFill(gradient));
        }
    }

    @Override
    public void update(Observable observable) {
        drawBoard((Board) observable);
    }
}
