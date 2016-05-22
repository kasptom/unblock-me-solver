package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.GameBoard;
import solvers.BruteSolver;
import solvers.SolutionStep;
import solvers.ants.*;
import solvers.generator.SimpleGenerator;

import java.util.*;

/**
 * Created by Tomasz Kasprzyk on 2016-04-19.
 */
public class GameBoardController implements EventHandler<ActionEvent>{
    private static int xSize = GameBoardConfig.SIZE;
    private static int ySize = GameBoardConfig.SIZE;
    private static double colors[][] = {
            {1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}, {1.0, 1.0, 0.0}, {0.0, 1.0, 1.0}, {1.0, 0.0, 1.0},
            {0.5, 0.5, 0.0}, {0.0, 0.5, 0.5}, {0.5, 0.0, 0.5}, {1.0, 0.5, 0.0}, {0.0, 1.0, 0.5}, {1.0, 0.0, 0.5},
            {0.75, 0.25, 0.0}, {0.0, 0.25, 0.75}, {0.75, 0.0, 0.25}, {0.5, 1.0, 0.0}, {0.0, 0.5, 1.0}, {0.5, 0.0, 1.0},
    };
    private Button[][] buttons;
    private Button nextButton;
    private Button prevButton;
    GameBoard gameBoard;
    Image imageRedBlock = new Image("file:res/red.png");
    Image imageGrayBlock = new Image("file:res/gray.png");

    BackgroundImage backgroundRed = new BackgroundImage( imageRedBlock, BackgroundRepeat.NO_REPEAT,
                                                                        BackgroundRepeat.NO_REPEAT,
                                                                        BackgroundPosition.DEFAULT,
                                                                            BackgroundSize.DEFAULT);
    BackgroundImage backgroundGray = new BackgroundImage( imageGrayBlock, BackgroundRepeat.NO_REPEAT,
                                                                            BackgroundRepeat.NO_REPEAT,
                                                                            BackgroundPosition.DEFAULT,
                                                                            BackgroundSize.DEFAULT);
    ArrayList<SolutionStep> steps;
    int currentStep = -1;

    public GameBoardController(){
        //List<Block> blocks = SimpleGenerator.generate();
        this.gameBoard = new GameBoard(GameBoardConfig.getInstance().generateInitialBoard());
        updateGUI();

        if(GameBoardConfig.BRUTE) {
            BruteSolver solver = new BruteSolver(gameBoard);
            solver.solve();
            steps = new ArrayList(solver.getSteps());
        }else {
            Pheromones pheromones = new Pheromones();
            AlgorithmPrototype1 solver = new AlgorithmPrototype1(new GameBoard(GameBoardConfig.getInstance().generateInitialBoard()), pheromones);
            Ant ant = solver.run(GameBoardConfig.TRIALS);
            steps = new ArrayList(ant.getPath());
        }
        Collections.reverse(steps);
        System.out.println(steps);
    }

    public void handle(ActionEvent event) {

            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    if (event.getSource() == buttons[j][i])
                        System.out.println("x="+i+", y="+j);
                }
            }
        if(event.getSource() == nextButton){
            nextStep();
        }
        if(event.getSource() == prevButton){
            previousStep();
        }
    }
    private void nextStep(){
        currentStep++;
        if( currentStep == steps.size()) {
            currentStep = steps.size()-1;
            return;
        }
        System.out.println(steps.get(currentStep));
        gameBoard.move(gameBoard.getBlocks().get(steps.get(currentStep).getBlockId()), steps.get(currentStep).getStep());
        updateGUI();
    }
    private void previousStep(){

        if( currentStep == -1) {
            return;
        }
        System.out.println(steps.get(currentStep));
        gameBoard.move(gameBoard.getBlocks().get(steps.get(currentStep).getBlockId()), -steps.get(currentStep).getStep());
        currentStep--;
        updateGUI();
    }
    private void updateGUI(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int[][] board = gameBoard.getBoard();
                try{
                    for(int j=0; j<ySize; j++){
                        for(int i=0; i<xSize; i++){
                            if(board[j][i]==1){
                                buttons[j][i].setBackground(new Background(backgroundRed));
                                buttons[j][i].setText("1");
                            }
                            if(board[j][i] > 1){
                               // buttons[j][i].setBackground(new Background(backgroundGreen));
                                /*long hue = (20 + (board[j][i]*20)) % 360;
                                double brh = 0.5 + 0.5*(board[j][i]%2);
                                buttons[j][i].setBackground(new Background(new BackgroundFill(Color.hsb(hue ,1,brh), null, null)));*/
                                int x =board[j][i]-1;
                                double r = colors[x][0];
                                double g = colors[x][1];
                                double b = colors[x][2];
                                buttons[j][i].setBackground(new Background(new BackgroundFill(Color.color(r ,g, b), null, null)));
                                buttons[j][i].setText(String.valueOf(board[j][i]));
                            }
                            if(board[j][i] == 0){
                                buttons[j][i].setBackground(new Background(backgroundGray));
                                buttons[j][i].setText(null);
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
