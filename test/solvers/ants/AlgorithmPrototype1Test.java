package solvers.ants;

import model.GameBoard;
import solvers.BruteSolver;
import solvers.SolutionStep;
import solvers.generator.SimpleGenerator;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Deque;

/**
 * Created by Tomasz Kasprzyk on 2016-05-08.
 */
public class AlgorithmPrototype1Test {
    //GameBoard gameBoard = new GameBoard(SimpleGenerator.generate());
    //AlgorithmPrototype1 solver = new AlgorithmPrototype1(gameBoard);

    @org.junit.Test
    public void testAlgorithm(){
        BruteSolver bs = new BruteSolver(new GameBoard(SimpleGenerator.generate()));
        bs.solve();
        Pheromones brutallyInitiatedPheromones = new Pheromones();
        GameBoard i;
        GameBoard i1 = new GameBoard(SimpleGenerator.generate());
        Deque<SolutionStep> steps = bs.getSteps();
        while(!steps.isEmpty()) {
            SolutionStep ss = steps.removeLast();
            i = new GameBoard(i1);
            i1.move(i1.getBlocks().get(ss.getBlockId()), ss.getStep());
            brutallyInitiatedPheromones.check(Pheromone.DEFAULT_INITIAL_TIMESTAMP, i, i1);
            brutallyInitiatedPheromones.get(i, i1).add(Pheromone.DEFAULT_INITIAL_PHEROMONE, Pheromone.DEFAULT_INITIAL_TIMESTAMP);
        }
        AlgorithmPrototype1 solver = new AlgorithmPrototype1(new GameBoard(SimpleGenerator.generate()), brutallyInitiatedPheromones);
        int TRIALS = 2;
        solver.run(TRIALS);
    }
}
