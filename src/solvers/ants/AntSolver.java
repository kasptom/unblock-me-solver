package solvers.ants;

import model.GameBoard;
import solvers.SolutionStep;
import java.util.Deque;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class AntSolver{

    Deque<SolutionStep> steps;
    GameBoard gameBoard;

    public AntSolver(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void solve() {
        return;
    }


    public Deque<SolutionStep> getSteps() {
        return null;
    }
}
