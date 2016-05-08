package solvers.ants;

import model.GameBoard;
import solvers.generator.SimpleGenerator;

import java.util.*;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class Ant {
    public static double PHEROMONE_FOR_ANT = 1.0;

    Pheromones pheromones;
    private GameBoard currentState;// = new GameBoard(SimpleGenerator.generate());
    private long timestamp;// = 0L;
    private List<GameBoard> memory;// = new ArrayList<>();
    private Random random;

    public Ant(Pheromones pheromones, GameBoard initialState, long timestamp) {
        this.currentState = initialState;
        this.timestamp = timestamp;
        this.memory = new ArrayList<>();
        this.pheromones = pheromones;
        this.random = new Random();
    }

    public boolean step(){
        if (check())
            return true;
        List<GameBoard> steps = this.currentState.getPossibleTransitions();
        Map<GameBoard, Double> probabilities = new HashMap<>();

        for(GameBoard step : steps)
            probabilities.put(step, pheromones.check(timestamp, currentState, step));
        memory.add(currentState);
        currentState = chooseWithProbability(probabilities);
        return false;
    }
    private GameBoard chooseWithProbability(Map<GameBoard, Double> probabilities) {
        double sum = probabilities.values().stream().reduce((x, y) -> x+y).get();
        double roll = random.nextDouble();
        roll *= sum;
        for(Map.Entry<GameBoard, Double> entry : probabilities.entrySet()) {
            roll -= entry.getValue();
            if(roll <= 0)
                return entry.getKey();
        }
        throw new RuntimeException("There is an error in probability choosing!");
    }

    public boolean check(){
        return this.currentState.isFinished();
    }

    public void spread(){
        for(int i=1; i < memory.size(); i++) {
            //FIXME NullPointerException - how to add a new pair?
            pheromones.get(memory.get(i - 1), memory.get(i)).add(PHEROMONE_FOR_ANT / memory.size(), timestamp);
        }
    }
}
