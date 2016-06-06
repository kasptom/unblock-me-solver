package solvers.ants;

import controller.GameBoardConfig;
import model.GameBoard;
import solvers.SolutionStep;
import solvers.generator.SimpleGenerator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class Ant {
    public static double PHEROMONE_FOR_ANT = GameBoardConfig.PHEROMONE_FOR_ANT;

    Pheromones pheromones;
    private GameBoard currentState;// = new GameBoard(SimpleGenerator.generate());
    private long timestamp;// = 0L;
    private List<GameBoard> memory;// = new ArrayList<>();
    private Deque<SolutionStep> path;
    private Random random;

    public Ant(Pheromones pheromones, GameBoard initialState, long timestamp) {
        this.currentState = initialState;
        this.timestamp = timestamp;
        this.memory = new ArrayList<>();
        this.pheromones = pheromones;
        this.random = new Random();
        this.path = new ArrayDeque<>();
    }

    public Deque<SolutionStep> getPath() { return path; }

    public boolean step(){
        if (check())
            return true;
        Map<GameBoard, SolutionStep> steps = this.currentState.getPossibleTransitions();
        Map<GameBoard, Double> probabilities = new HashMap<>();

        for(Map.Entry<GameBoard, SolutionStep> step : steps.entrySet())
            probabilities.put(step.getKey(), getSingleProbability(pheromones.safeGet(currentState, step.getKey())));
        memory.add(currentState);
        currentState = chooseWithProbability(probabilities);
        path.push(steps.get(currentState));
        return false;
    }
    private double getSingleProbability(Pheromone p) {
        return Math.pow(p.get(timestamp), getAlpha()) + Math.pow(1.0/p.getDistance(), getBeta());
    }
    private GameBoard chooseWithProbability(Map<GameBoard, Double> probabilities) {
        //WZOREK NA PRAWDOPODOBIENSTWO
        //System.out.println("probs count: " + probabilities.values().size());
        double sum = probabilities.values().stream().reduce(0.0, (x, y) -> x+y);
        //System.out.println("sum: " + sum);
        double roll = random.nextDouble();
        roll *= sum;
        for(Map.Entry<GameBoard, Double> entry : probabilities.entrySet()) {
            roll -= entry.getValue();
            if(roll <= 0.0)
                return entry.getKey();
        }
        throw new RuntimeException("There is an error in probability choosing!\n"
                + "or in possibleTransitions generation\n"
                + "{\troll: " + roll + "\n"
                + " \tprobs count: " + probabilities.values().size() + "\n"
                + " \tsum: " + sum + "\n}");
    }
    private double getAlpha() {
        return 1.0 - ((double)timestamp)/GameBoardConfig.TRIALS;
    }
    private double getBeta() {
        double x = ((double)timestamp)/GameBoardConfig.TRIALS;
        x *= x;
        return x;
        //return ((double)timestamp)/GameBoardConfig.TRIALS;
    }

    public boolean check(){
        return this.currentState.isFinished();
    }

    public void spread(){
        //ROZKLADANIE FEROMONOW
        double pheromoneAdd = PHEROMONE_FOR_ANT / memory.size();
//        pheromoneAdd = Math.tan(1.5*pheromoneAdd);
        pheromoneAdd *= pheromoneAdd;
//        pheromoneAdd = Math.sin(pheromoneAdd);
        for(int i = memory.size()-1; i >= 1; i--) {
            Pheromone p = pheromones.get(memory.get(i-1), memory.get(i));
            if(p == null)
                throw new RuntimeException("[EE] Ant.spread(): Failed to get pheromone!");
            p.add(pheromoneAdd, timestamp, memory.size() - i);
        }
//        for(int i=1; i < memory.size(); i++) {
//            Pheromone p = pheromones.get(memory.get(i - 1), memory.get(i));
//            if(p == null)
//                throw new RuntimeException("[EE] Ant.spread(): Failed to get pheromone!");
//            p.add(pheromoneAdd, timestamp);
//        }
    }

    public double solutionQuality() {
        return 1.0 / memory.size();
        //TODO do comparable
    }

    public void printPath() {
        ArrayList<SolutionStep> antSteps = new ArrayList(getPath());
        Collections.reverse(antSteps);
        System.out.println(antSteps);
    }

}
