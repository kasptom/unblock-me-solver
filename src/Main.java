/**
 * Created by Tomasz Kasprzyk on 2016-04-11.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application implements EventHandler<ActionEvent> {
    private static int xSize = 6;
    private static int ySize = 6;
    private boolean ready = false;

    Image imageWater = new Image("file:res/water.png");
    Image imageMiss = new Image("file:res/miss.png");
    Image imageShip = new Image("file:res/ship.png");
    Image imageDown = new Image("file:res/down.png");

    public static char[][] board = new char[xSize][ySize];

    static Button[][] buttons = new Button[ySize][xSize];
    static Button[][] buttons2 = new Button[ySize][xSize];
    Label[][] labels = new Label[2][xSize];
    Label[][] labels2 = new Label[2][xSize];

    public static void main(String[] args) {
        //System.out.println("File separator: " + fs);
        launch(args);
        System.exit(0);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Unblock me solver");
        BorderPane mainLayout = new BorderPane();
        GridPane layout = new GridPane();
        GridPane layout2 = new GridPane();  //opponents layout

        layout.setPrefSize(280, 280);
        layout2.setPrefSize(280, 280);

        mainLayout.setLeft(layout);
        mainLayout.setRight(layout2);
        Scene scene = new Scene(mainLayout, (280 + 280), 280);
        primaryStage.setScene(scene);
        layout.setPadding(new Insets(5));


        for (int i = 0; i < xSize; i++) {
            labels[0][i] = new Label(String.valueOf(i + 1));
            labels[1][i] = new Label(String.valueOf((char) ('A' + i)));
            labels[0][i].setPrefSize(22, 22);
            labels[1][i].setPrefSize(22, 22);

            labels2[0][i] = new Label(String.valueOf(i + 1));
            labels2[1][i] = new Label(String.valueOf((char) ('A' + i)));
            labels2[0][i].setPrefSize(22, 22);
            labels2[1][i].setPrefSize(22, 22);
            for (int j = 0; j < ySize; j++) {
                board[j][i] = 'w';
                buttons[j][i] = new Button();
                buttons[j][i].setOnAction(this);   /*this - class that handles an event */
                //buttons[j][i].setGraphic(new ImageView(imageWater));
                buttons[j][i].setPrefSize(22, 22);
                buttons[j][i].setPadding(Insets.EMPTY);
                // layout.getChildren().add(buttons[i][j]);
                layout.add(buttons[j][i], i + 1, j + 1);

                buttons2[j][i] = new Button();
                buttons2[j][i].setOnAction(this);   /*this - class that handles an event */
                //buttons[j][i].setGraphic(new ImageView(imageWater));
                buttons2[j][i].setPrefSize(22, 22);
                buttons2[j][i].setPadding(Insets.EMPTY);
                // layout.getChildren().add(buttons[i][j]);
                layout2.add(buttons2[j][i], i + 1, j + 1);
            }
            layout.add(labels[0][i], 0, i + 1);
            layout.add(labels[1][i], i + 1, 0);

            layout2.add(labels2[0][i], 0, i + 1);
            layout2.add(labels2[1][i], i + 1, 0);
        }
        //add bottom description
        GridPane bottomGrid = new GridPane();
        Label opponentLabel = new Label("Szablon");
        mainLayout.setBottom(bottomGrid);
        bottomGrid.add(opponentLabel, 1, 0);
        bottomGrid.setAlignment(Pos.CENTER);

        primaryStage.show();
    }

    public void handle(ActionEvent event) {

        if (!ready) {
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    if (event.getSource() == buttons[j][i]) {
                        // System.out.println("x="+i+", y="+j);
                        if (board[j][i] == 'w') {
                            buttons[j][i].setGraphic(new ImageView(imageShip));
                            board[j][i] = 's';
                        } else {
                            buttons[j][i].setGraphic(new ImageView(imageWater));
                            board[j][i] = 'w';
                        }
                    }
                }
            }
        }
    }
}


