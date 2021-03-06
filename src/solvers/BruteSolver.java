package solvers;

import model.Block;
import model.GameBoard;

import java.util.*;

/**
 * Created by throzendar on 16/04/16.
 */
public class BruteSolver {

    /**
     * combinations: List of already checked combinations
     * steps:
     */
    List<int[][]> combinations;
    Deque<SolutionStep> steps;
    GameBoard gameBoard;

    /**
     * BruteSolver constructor
     * @param gameBoard Initial state of the game board to solve
     */
    public BruteSolver(GameBoard gameBoard) {
        combinations = new LinkedList<>();
        steps = new ArrayDeque<>();
        this.gameBoard = gameBoard;
    }

    public void solve() {
        solveStep(gameBoard, 0);
    }

    public Deque<SolutionStep> getSteps() {
        return steps;
    }

    private boolean solveStep(GameBoard gameBoard, int lastBlockID) {
        System.out.println(steps);
        gameBoard.printDump();
        // Check if game is already finished
        if (gameBoard.isFinished()) {
            return true;
        }

        if (isChecked(gameBoard)) {
            System.out.println("Zdejmuje!");
            steps.pop();
            return false;
        }

        addCombination(gameBoard);

        // If game can proceed
        if (gameBoard.canMove(gameBoard.getBlocks().get(1), 1)) {
            GameBoard newBoard = new GameBoard(gameBoard);
            newBoard.move(newBoard.getBlocks().get(1), 1);
            System.out.println("Dodaje! [" + newBoard.getBlocks().get(1).getID() + ", " + 1 + "]");
            steps.push(new SolutionStep(newBoard.getBlocks().get(1).getID(), 1));
            if(solveStep(newBoard, 0)) {
                return true;
            }
        } else {
            // If something is blocking
            for (Block block : gameBoard.getBlocks().values()) {
                if (block.getID() != 1 && block.getID() != lastBlockID) {
                    for (int step = 1; gameBoard.canMove(block, step); step++) {
                        GameBoard newBoard = new GameBoard(gameBoard);
                        newBoard.move(newBoard.getBlocks().get(block.getID()), step);
                        System.out.println("Dodaje! [" + block.getID() + ", " + step + "]");
                        steps.push(new SolutionStep(block.getID(), step));
                        if (solveStep(newBoard, 0)) {
                            return true;
                        }
                    }


                    for (int step = -1; gameBoard.canMove(block, step); step--) {
                        GameBoard newBoard = new GameBoard(gameBoard);
                        newBoard.move(newBoard.getBlocks().get(block.getID()), step);
                        System.out.println("Dodaje! [" + block.getID() + ", " + step + "]");
                        steps.push(new SolutionStep(block.getID(), step));
                        if (newBoard.canMove(newBoard.getBlocks().get(block.getID()), step - 1)) {
                            if (solveStep(newBoard, 0)) {
                                return true;
                            }
                        } else {
                            if (solveStep(newBoard, block.getID())) {
                                return true;
                            }
                        }
                    }
                }
            }

            System.out.println("Zdejmuje!");
            steps.pop();
            return false;
        }
        System.out.println("Zdejmuje!");
        if(!steps.isEmpty())
            steps.pop();
        return false;
    }

    private void addCombination(GameBoard board) {
        int combination[][] = new int[board.SIZE][board.SIZE];
        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j < board.SIZE; j++) {
                combination[i][j] = board.getBoard()[i][j];
            }
        }
        combinations.add(combination);
    }

    private boolean isChecked(GameBoard board) {
        for (int[][] combination : combinations) {
            if (isEqual(board, combination)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEqual(GameBoard board, int[][] combination) {
        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j < board.SIZE; j++) {
                if (board.getBoard()[i][j] != combination[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
