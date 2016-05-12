package solvers.ants;

import model.GameBoard;
import solvers.BruteSolver;
import solvers.SolutionStep;
import solvers.generator.SimpleGenerator;
import solvers.generator.SimplerGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class SimplerAntSolverTest {
    @org.junit.Test
    public void testAlgorithm(){
        BruteSolver bs = new BruteSolver(new GameBoard(SimplerGenerator.generate()));
        bs.solve();
        Pheromones brutallyInitiatedPheromones = new Pheromones();
        GameBoard i;
        GameBoard i1 = new GameBoard(SimplerGenerator.generate());
        Deque<SolutionStep> steps = bs.getSteps();
        while(!steps.isEmpty()) {
            SolutionStep ss = steps.removeLast();
            i = new GameBoard(i1);
            i1.move(i1.getBlocks().get(ss.getBlockId()), ss.getStep());
            brutallyInitiatedPheromones.check(Pheromone.DEFAULT_INITIAL_TIMESTAMP, i, i1);
            brutallyInitiatedPheromones.get(i, i1).add(Pheromone.DEFAULT_INITIAL_PHEROMONE, Pheromone.DEFAULT_INITIAL_TIMESTAMP);
        }
        AlgorithmPrototype1 solver = new AlgorithmPrototype1(new GameBoard(SimplerGenerator.generate()), brutallyInitiatedPheromones);
        int TRIALS = 2;
        Ant ant = solver.run(TRIALS);
        ArrayList<SolutionStep> antSteps = new ArrayList(ant.getPath());
        Collections.reverse(antSteps);
        System.out.println(antSteps);
    }
    @org.junit.Test
    public void testAlgorithm1(){
        BruteSolver bs = new BruteSolver(new GameBoard(SimplerGenerator.generate1()));
        bs.solve();
        Pheromones brutallyInitiatedPheromones = new Pheromones();
        GameBoard i;
        GameBoard i1 = new GameBoard(SimplerGenerator.generate1());
        Deque<SolutionStep> steps = bs.getSteps();
        while(!steps.isEmpty()) {
            SolutionStep ss = steps.removeLast();
            i = new GameBoard(i1);
            i1.move(i1.getBlocks().get(ss.getBlockId()), ss.getStep());
            brutallyInitiatedPheromones.check(Pheromone.DEFAULT_INITIAL_TIMESTAMP, i, i1);
            brutallyInitiatedPheromones.get(i, i1).add(Pheromone.DEFAULT_INITIAL_PHEROMONE, Pheromone.DEFAULT_INITIAL_TIMESTAMP);
        }
        AlgorithmPrototype1 solver = new AlgorithmPrototype1(new GameBoard(SimplerGenerator.generate1()), brutallyInitiatedPheromones);
        int TRIALS = 10;
        Ant ant = solver.run(TRIALS);
        ant.printPath();
    }
}