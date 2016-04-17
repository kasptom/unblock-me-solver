package solvers;

import model.Block;
import model.GameBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by throzendar on 16/04/16.
 */
public class BruteSolver {

    public static void solve(GameBoard gameBoard) {
        List<SolutionStep> solution = new ArrayList<>();

    }

    public static boolean solveStep(GameBoard gameBoard, int lastBlockID) {

        // Check if game is already finished
        if (gameBoard.isFinished()) {
            return true;
        }

        // If game can proceed
        if (gameBoard.canMove(gameBoard.getBlocks().get(1), 1)) {
            GameBoard newBoard = new GameBoard(gameBoard);
            newBoard.move(newBoard.getBlocks().get(1), 1);
            return solveStep(newBoard, 0);
        } else {
            // If something is blocking
            for (Block block: gameBoard.getBlocks().values()) {
                if (block.getID() != 0 && block.getID() != lastBlockID) {
                    for (int step = 1; gameBoard.canMove(block, step); step++) {
                        GameBoard newBoard = new GameBoard(gameBoard);
                        newBoard.move(newBoard.getBlocks().get(block.getID()), step);
                        solveStep(newBoard, 0);
                    }


                    for (int step = -1; gameBoard.canMove(block, step); step--) {
                        GameBoard newBoard = new GameBoard(gameBoard);
                        newBoard.move(newBoard.getBlocks().get(block.getID()), step);
                        if (newBoard.canMove(newBoard.getBlocks().get(block.getID()), step + 1)) {
                            solveStep(newBoard, 0);
                        } else {
                            solveStep(newBoard, block.getID());
                        }
                    }
                }
            }

            return false;
        }
    }
}
