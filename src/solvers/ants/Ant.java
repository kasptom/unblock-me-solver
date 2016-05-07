package solvers.ants;

import model.GameBoard;
import solvers.generator.SimpleGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class Ant {
    Pheromone pheromone;
    GameBoard currentState = new GameBoard(SimpleGenerator.generate());
    long timestamp = 0L;
    List memory = new ArrayList<>();

    public boolean step(){
        if (check())
            return true;
        List steps = this.currentState.getPossibleTransitions();
        Map probabilities = new HashMap<GameBoard, Double>();

        for(Object step : steps){
            probabilities.put(step, pheromone.check());

        }
    }


    public boolean check(){
        return this.currentState.isFinished();
    }

    public void spread(){
        for(int i=1; i < memory.size(); i++){
            pheromone.get(memory.get(i-1), memory.get(i));
        }
    }



}
