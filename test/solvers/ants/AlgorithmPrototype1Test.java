package solvers.ants;

import model.GameBoard;
import solvers.generator.SimpleGenerator;

/**
 * Created by Tomasz Kasprzyk on 2016-05-08.
 */
public class AlgorithmPrototype1Test {
    GameBoard gameBoard = new GameBoard(SimpleGenerator.generate());
    AlgorithmPrototype1 solver = new AlgorithmPrototype1(gameBoard);

    @org.junit.Test
    public void testAlgorithm(){
        int TRIALS = 2;
        solver.run(TRIALS);
    }
}
