package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Block;
import model.GameBoard;
import solvers.BruteSolver;
import solvers.SolutionStep;
import solvers.generator.SimpleGenerator;

import java.util.Deque;
import java.util.List;

/**
 * Created by Tomasz Kasprzyk on 2016-04-19.
 */
public class GameBoardController implements EventHandler<ActionEvent>{
    private static int xSize = 6;
    private static int ySize = 6;
    private Button[][] buttons;
    private Button nextButton;
    private Button prevButton;
    private int[][] board;
    Image imageRedBlock = new Image("file:res/red.png");
    Image imageGreenBlock = new Image("file:res/green.png");
    Image imageGrayBlock = new Image("file:res/gray.png");

    BackgroundImage backgroundRed = new BackgroundImage( imageRedBlock, BackgroundRepeat.NO_REPEAT,
                                                                        BackgroundRepeat.NO_REPEAT,
                                                                        BackgroundPosition.DEFAULT,
                                                                            BackgroundSize.DEFAULT);
    BackgroundImage backgroundGreen = new BackgroundImage( imageGreenBlock, BackgroundRepeat.NO_REPEAT,
                                                                            BackgroundRepeat.NO_REPEAT,
                                                                            BackgroundPosition.DEFAULT,
                                                                            BackgroundSize.DEFAULT);
    BackgroundImage backgroundGray = new BackgroundImage( imageGrayBlock, BackgroundRepeat.NO_REPEAT,
                                                                            BackgroundRepeat.NO_REPEAT,
                                                                            BackgroundPosition.DEFAULT,
                                                                            BackgroundSize.DEFAULT);
    Deque<SolutionStep> steps;

    public GameBoardController(){
        List<Block> blocks = SimpleGenerator.generate();
        GameBoard gameBoard = new GameBoard(blocks);
        board = gameBoard.getBoard();
        updateGUI();
//        gameBoard.move(gameBoard.getBlocks().get(2), 1);
//        gameBoard.printDump();
//        System.out.println(gameBoard.canMove(gameBoard.getBlocks().get(2), -3));

        BruteSolver solver = new BruteSolver(gameBoard);
        solver.solve();
        System.out.println(solver.getSteps());
        steps = solver.getSteps();
    }

    public void handle(ActionEvent event) {

            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    if (event.getSource() == buttons[j][i]) {
                        System.out.println("x="+i+", y="+j);
                     /*   if (board[j][i] == 'w') {
                            buttons[j][i].setGraphic(new ImageView(imageShip));
                            board[j][i] = 's';
                        } else {
                            buttons[j][i].setGraphic(new ImageView(imageWater));
                            board[j][i] = 'w';
                        }*/
                    }
                }
            }
        if(event.getSource() == nextButton){

        }
        if(event.getSource() == prevButton){

        }
    }

    private void moveBlocks(){

    }

    private void updateGUI(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int j=0; j<ySize; j++){
                        for(int i=0; i<xSize; i++){
                            if(board[j][i]==1){
                                buttons[j][i].setBackground(new Background(backgroundRed));
                                buttons[j][i].setText("1");
                            }
                            if(board[j][i] > 1){
                                buttons[j][i].setBackground(new Background(backgroundGreen));
                                buttons[j][i].setText(String.valueOf(board[j][i]));
                            }
                            if(board[j][i] == 0){
                                buttons[j][i].setBackground(new Background(backgroundGray));
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                //System.out.println("inside board["+y+"]"+"["+x+"] = "+myBoard.board[y][x]);
            }
        });

    }

    public void setButtons(Button[][] buttons, Button prevButton, Button nextButton){
        this.buttons = buttons;
        this.prevButton = prevButton;
        this.nextButton = nextButton;
    }

}
