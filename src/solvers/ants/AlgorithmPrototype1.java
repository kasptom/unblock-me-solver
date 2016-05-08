package solvers.ants;

import model.GameBoard;

import java.util.concurrent.*;

/**
 * Created by Andrzej Duda on 2016-05-08.
 */
public class AlgorithmPrototype1 {
    public static final int ANT_QUANTITY = 5;

    private GameBoard initialState;
    private Pheromones pheromones;
    private long timestamp;
    private ExecutorService executor;

    public AlgorithmPrototype1(GameBoard initialState) {
        this.initialState = initialState;
        this.pheromones = new Pheromones();
        this.timestamp = 0L;
        this.executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()); //TODO fork-join-pool
    }

    public Ant run(long trials) {
        timestamp = 0L;
        Ant result = null;
        while(timestamp < trials) {
            result = unleashAnthill();
            timestamp++;
        }
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
                f.get(1, TimeUnit.SECONDS);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        for(Ant a : ants)
            a.spread();
        return ants[0];
    }


    public Ant createAnt() { return new Ant(pheromones, initialState, timestamp); }
    public void unleashAnt(Ant ant) { while(!ant.step()); }
}