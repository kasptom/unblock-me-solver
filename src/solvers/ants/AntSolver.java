package solvers.ants;

import model.Block;
import model.GameBoard;
import solvers.SolutionStep;

import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class AntSolver{

    Deque<SolutionStep> steps;
    GameBoard gameBoard;
    ArrayList<byte[][]> states;

    public AntSolver(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        byte[][] state0 = new byte[GameBoard.SIZE][GameBoard.SIZE];
        int[][] board = gameBoard.getBoard();
        for(int i=0; i < GameBoard.SIZE; i++){
            for(int j=0; j < GameBoard.SIZE; j++)
                state0[i][j] = (byte)board[i][j];
        }
        states = new ArrayList<>();
        states.add(state0);
    }

    public void solve() {
        for(Block block : gameBoard.getBlocks().values()){
            for(int i=-4; i <= 4; i++){

            }
        }
        return;
    }


    public Deque<SolutionStep> getSteps() {
        return null;
    }
    public void printState(){
        for(int i=0; i < GameBoard.SIZE; i++){
            for(int j=0; j < GameBoard.SIZE; j++)
                System.out.print(" "+states.get(0)[i][j]);
            System.out.println();
        }
    }

    private void putState(int[][] board){
        byte[][] newState = new byte[GameBoard.SIZE][GameBoard.SIZE];

    }

    ///
    public void launchAnt(int time){
        //FIXME commented compile error
        // Ant ant = new Ant();
        //while(!ant.step());
    }
}
