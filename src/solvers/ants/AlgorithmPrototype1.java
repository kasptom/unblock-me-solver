package solvers.ants;

import controller.GameBoardConfig;
import model.GameBoard;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by Andrzej Duda on 2016-05-08.
 */
public class AlgorithmPrototype1 {
    public static final int ANT_QUANTITY = GameBoardConfig.ANT_QUANTITY;

    private GameBoard initialState;
    private Pheromones pheromones;
    private long timestamp;
    private ExecutorService executor;

    public AlgorithmPrototype1(GameBoard initialState, Pheromones pheromones) {
        this(initialState);
        this.pheromones = pheromones;
    }

    public AlgorithmPrototype1(GameBoard initialState) {
        this.initialState = initialState;
        this.pheromones = new Pheromones();
        this.timestamp = 0L;
        this.executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()); //TODO fork-join-pool
    }

    public Ant run(long trials) {
        System.out.println("Shall unleash " + ANT_QUANTITY + " ants " + trials + " times.");
        timestamp = 0L;
        Ant result = null;
        while(timestamp < trials) {
            System.out.println("Generation: " + timestamp);
            Ant pretender = unleashAnthill();
            if(result == null || (result.solutionQuality() < pretender.solutionQuality()))
                result = pretender;
            System.out.println("\n\t\t\tresult: " + pretender.getPath().size());
            timestamp++;
        }
        System.out.println("\nFinal result: " + result.getPath().size());
//        System.out.println("result: " + result.getPath().size());
        return result;
    }

    public Ant unleashAnthill() {
        //TODO consider fork-join-pools or sth
        Ant[] ants = new Ant[ANT_QUANTITY];
        Future[] futures = new Future[ANT_QUANTITY];
        for(int i = 0; i < ANT_QUANTITY; i++) {
            Ant ant = createAnt();
            ants[i] = ant;
            futures[i] = executor.submit(() -> unleashAnt(ant));
        }
        for (Future f : futures) {
            try {
                f.get();
                //f.get(1, TimeUnit.SECONDS);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Arrays.sort(ants, (x,y) -> -Double.compare(x.solutionQuality(), y.solutionQuality()));
        Ant winner = ants[0];
        //winner.spread();
        for(int i=0; i < ants.length*0.1; i++) {
            ants[i].spread();
        }
        /*for(Ant a : ants) {
            a.spread();
            if(winner == null)
                winner = a;
            else {
                if(a.solutionQuality() > winner.solutionQuality())
                    winner = a;
            }
        }*/
        return winner;
    }




    public Ant createAnt() { return new Ant(pheromones, initialState, timestamp); }
    public void unleashAnt(Ant ant) { while(!ant.step()); }
}