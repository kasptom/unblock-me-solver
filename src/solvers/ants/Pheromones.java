package solvers.ants;

import model.GameBoard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Orlando Bloom on 2016-05-08.
 */
public class Pheromones {
    private Map<GBPair, Pheromone> pheromones;
    public Pheromones() { pheromones = new HashMap<>(); }

    /**
     * Checks value of pheromone's intensity. If there is no pheromone
     * to check, creates one and then returns its intensity.
     * @param timestamp Timestamp of check operation. Used for decay purposes.
     * @param gb1
     * @param gb2
     * @return Pheromone's intensity
     */
    public double check(long timestamp, GameBoard gb1, GameBoard gb2) {
        Pheromone p = get(gb1, gb2);
        if(p != null)
            return p.get(timestamp);
        p = new Pheromone(timestamp);
        pheromones.put(new GBPair(gb1, gb2), p);
        return p.get(timestamp);
    }

    /**
     * Returns Pheromone stored between given GameBoards
     * @param gb1
     * @param gb2
     * @return Pheromone. If there is no pheromone stored, returns NULL.
     */
    public Pheromone get(GameBoard gb1, GameBoard gb2) {
        GBPair p1 = new GBPair(gb1, gb2);
        GBPair p2 = new GBPair(gb2, gb1);
        if(pheromones.containsKey(p1))
            return pheromones.get(p1);
        else if(pheromones.containsKey(p2))
            return pheromones.get(p2);
        else
            return null;
    }
}

/**
 * Simple pair
 */
class GBPair {
    private GameBoard gb1, gb2;
    public GBPair(GameBoard gb1, GameBoard gb2) {
        this.gb1 = gb1;
        this.gb2 = gb2;
    }
    @Override
    public boolean equals(Object a) {
        if(!(a instanceof GBPair))
            return false;
        if(((GBPair) a).gb1.equals(gb1))
            if(((GBPair) a).gb2.equals(gb2))
                return true;
        return false;
    }
    @Override
    public int hashCode() {
        int h1 = gb1.hashCode();
        h1 += gb2.hashCode();
        return h1;
    }
    //public GameBoard get1() { return gb1; }
    //public GameBoard get2() { return gb2; }
}