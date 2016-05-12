package solvers.ants;

import model.Block;
import model.GameBoard;
import solvers.generator.SimpleGenerator;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class AntSolverTest {
    @org.junit.Test
    public void solve() throws Exception {
        List<Block> blocks = SimpleGenerator.generate();
        GameBoard gameBoard = new GameBoard(blocks);
        AntSolver solver = new AntSolver(gameBoard);
        solver.printState();
    }

}