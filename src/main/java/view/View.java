package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import main.java.controller.Choice;
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

    public static void drawGameOver(String name, Color color) {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 150, true,
                SceneAntialiasing.BALANCED);
        stage.setTitle("Game Over");
        scene.setFill(color);
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
        grid.setPadding(new Insets(12));
        root.getChildren().add(grid);
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                Label label = new Label(" ");
                label.setMinSize(28, 32);
//                label.setBackground(new Background(new BackgroundFill(new LinearGradient(0, 0, 1, 1, true,
//                        CycleMethod.NO_CYCLE, new Stop(0, Color.DARKBLUE),
//                        new Stop(0.3, Color.YELLOWGREEN),
//                        new Stop(0.7, Color.MAGENTA),
//                        new Stop(1, Color.DARKRED)), CornerRadii.EMPTY,
//                        Insets.EMPTY)));
                String borderStyle =
                        borderStyleFactory(board.getGameBoard()[j][i]);
                grid.add(label, i, j);
                if (board.getPlayer1().getLocation().getKey() == i &&
                        board.getPlayer1().getLocation().getValue() == j) {
                    System.out.print("X ");
                    label.setText(" ");
                    label.setStyle("-fx-border-style: " + borderStyle + ";-fx-border-color" +
                            ":#000000;-fx-border-width:5px;" +
                            "-fx-background-radius: 4% ;"
                            + "-fx-background-color: Crimson;-fx-padding: " +
                            "32px;" +
                            "-fx-text-fill: WHITE;-fx-effect: dropshadow" +
                            "(three-pass-box, darkred, 10, 0, 0, 0);");
                } else if (board.getPlayer2().getLocation().getKey() == i &&
                        board.getPlayer2().getLocation().getValue() == j) {
                    System.out.print("O ");
                    label.setText(" ");
                    label.setStyle("-fx-border-style: " + borderStyle + ";-fx-border-color" +
                            ":#000000;-fx-border-width:5px;" +
                            "-fx-background-radius: 4% ;"
                            + "-fx-background-color: darkgreen;-fx-padding:" +
                            " " +
                            "32px;" +
                            "-fx-text-fill: #000000;-fx-effect: dropshadow" +
                            "(three-pass-box,blue, 10, 0, 0,0 );");
                } else {
                    System.out.print("# ");
                    label.setStyle("-fx-border-style:" + borderStyle + ";-fx" +
                            "-border-color:#000000;" +
                            "-fx-border-width:5px;-fx-background-radius: 4% ;"
                            + "-fx-background-color: #efceba;-fx-padding: 32px;" +
                            "-fx-text-fill: #efceba;-fx-effect: dropshadow(" +
                            "three-pass-box, violet, 10, 0, 1, 1);");
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

    public static void drawMainMenu(Group root, Scene scene) {
        root.getChildren().clear();
        VBox vBox = new VBox();
        scene.setFill(Color.ROYALBLUE);
        root.getChildren().addAll(vBox);
        Title title = new Title("Quoridors");
        MenuItem singleplayer = new MenuItem("Single Player", 500, 180);
        MenuItem multiplayer = new MenuItem("Multiplayer", 500, 180);
        MenuItem exit = new MenuItem("Exit", 500, 180);
        MenuBox menuBox = new MenuBox(singleplayer, multiplayer, exit);
        vBox.getChildren().addAll(title, menuBox);
        vBox.setTranslateX(152d);
        vBox.setTranslateY(64d);
        vBox.setSpacing(68d);
        singleplayer.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            Group root1 = new Group();
            Scene scene1 = new Scene(root1, 220, 200);
            scene1.setFill(Color.ORANGE);
            stage.setScene(scene1);
            stage.show();
            MenuItem easy = new MenuItem("Easy", 200, 60);
            MenuItem hard = new MenuItem("Medium", 200, 60);
            MenuItem extreme = new MenuItem("Hard", 200, 60);
            VBox vBox1 = new VBox();
            vBox1.setSpacing(4);
            vBox1.setPadding(new Insets(8d));
            root1.getChildren().addAll(vBox1);
            vBox1.getChildren().addAll(easy, hard, extreme);
            easy.setOnMouseClicked(event1 -> {
                Choice.handleChoice(Choice.EASY, root, scene, "You", "Random");
                stage.close();
            });
            hard.setOnMouseClicked(event1 -> {
                Choice.handleChoice(Choice.MEDIUM, root, scene, "You",
                        "Dijkstra");
                stage.close();
            });
            extreme.setOnMouseClicked(event1 -> {
                Choice.handleChoice(Choice.HARD, root, scene, "You", "AI");
                stage.close();
            });
        });
        multiplayer.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            Group root1 = new Group();
            Scene scene1 = new Scene(root1, 220, 132);
            scene1.setFill(Color.ORANGE);
            stage.setScene(scene1);
            stage.show();
            TextField textField = new TextField();
            textField.setPromptText("1st Player");
            TextField textField1 = new TextField();
            MenuItem menuItem = new MenuItem("Submit", 200, 60);
            textField1.setPromptText("2nd Player");
            VBox vBox1 = new VBox();
            vBox1.setSpacing(4);
            vBox1.setPadding(new Insets(8d));
            root1.getChildren().addAll(vBox1);
            vBox1.getChildren().addAll(textField, textField1, menuItem);
            menuItem.setOnMouseClicked(event1 -> {
                Choice.handleChoice(Choice.MULTIPLAYER, root, scene,
                        textField.getText(), textField1.getText());
                stage.close();
            });
        });
        exit.setOnMouseClicked(event -> Choice.handleChoice(Choice.EXIT, root
                , scene, "", ""));
    }

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(500, 100);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(3);
            bg.setFill(null);
            Text text = new Text(name);
            text.setFill(Color.NAVY);
            text.setFont(Font.font("Bauhaus LT Medium", FontWeight.SEMI_BOLD,
                    64));
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
