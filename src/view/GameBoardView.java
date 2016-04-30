package view;

import controller.GameBoardController;
import javafx.event.ActionEvent;
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

import static javafx.application.Application.launch;

/**
 * Created by Tomasz Kasprzyk on 2016-04-19.
 */
public class GameBoardView {
    private static int xSize = 6;
    private static int ySize = 6;
    private boolean ready = false;

    private int sceneWidth = 600;
    private int sceneHeight = 500;
    private int scale = 3;

    BorderPane mainLayout;
    GameBoardController gameBoardController;

    Image prevImage = new Image("file:res/prev.png");
    Image nextImage = new Image("file:res/next.png");

    private static Button[][] buttons = new Button[ySize][xSize];
    private static Button prevButton = new Button();
    private static Button nextButton = new Button();
    Label[][] labels = new Label[2][xSize];


    public GameBoardView() throws Exception {
        gameBoardController = new GameBoardController();

        mainLayout = new BorderPane();
        GridPane layout = new GridPane();
        GridPane layout2 = new GridPane();  //opponents layout

        layout.setPrefSize(280 * scale, 280 * scale);
        layout2.setPrefSize(280 , 280 );

        mainLayout.setLeft(layout);
        mainLayout.setBottom(layout2);
       // scene = new Scene(mainLayout, sceneWidth, sceneHeight);
       // primaryStage.setScene(scene);
        layout.setPadding(new Insets(5));


        for (int i = 0; i < xSize; i++) {
            labels[0][i] = new Label(String.valueOf(i + 1));
            labels[1][i] = new Label(String.valueOf((char) ('A' + i)));
            labels[0][i].setPrefSize(22 * scale, 22 * scale);
            labels[1][i].setPrefSize(22 * scale, 22 * scale);
            labels[1][i].setAlignment(Pos.CENTER);

            for (int j = 0; j < ySize; j++) {
                //board[j][i] = 'w';
                buttons[j][i] = new Button();
                buttons[j][i].setOnAction(gameBoardController);   //this - class that handles an event
//buttons[j][i].setGraphic(new ImageView(imageWater));
                buttons[j][i].setPrefSize(22 * scale, 22 * scale);
                buttons[j][i].setPadding(Insets.EMPTY);
                // layout.getChildren().add(buttons[i][j]);
                layout.add(buttons[j][i], i + 1, j + 1);
            }
            prevButton.setGraphic(new ImageView(prevImage));
            nextButton.setGraphic(new ImageView(nextImage));
            prevButton.setPrefSize(50,30);
            nextButton.setPrefSize(50,30);
            prevButton.setOnAction(gameBoardController);
            nextButton.setOnAction(gameBoardController);
            layout.add(labels[0][i], 0, i + 1);
            layout.add(labels[1][i], i + 1, 0);
        }
        //add bottom description
        GridPane bottomGrid = new GridPane();
        bottomGrid.add(prevButton, 0, 0);
        bottomGrid.add(nextButton, 1, 0);
        mainLayout.setBottom(bottomGrid);
        bottomGrid.setAlignment(Pos.CENTER);


        gameBoardController.setButtons(buttons, prevButton, nextButton);
    }

    public BorderPane getMainLayout(){
        return mainLayout;
    }


    public int getSceneHeight() {
        return sceneHeight;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

}
