package solvers.ants;

import controller.GameBoardConfig;

public class Pheromone {
    public static final double DEFAULT_INITIAL_PHEROMONE = GameBoardConfig.DEFAULT_INITIAL_PHEROMONE;
    public static final long DEFAULT_INITIAL_TIMESTAMP = GameBoardConfig.DEFAULT_INITIAL_TIMESTAMP;
    public static final double DECAY_FACTOR = GameBoardConfig.DECAY_FACTOR;

    /**
     * Pheromone's intensity
     */
    private double value;
    /**
     * Time of last intensity's refresh
     */
    private long timestamp;
    /**
     * Distance to exit state
     */
    private int distance;

    public Pheromone() { this(DEFAULT_INITIAL_TIMESTAMP); }
    /**
     * @param initialTimestamp Initial value of pheromone's intensity
     */
    public Pheromone(long initialTimestamp) { this(DEFAULT_INITIAL_PHEROMONE, initialTimestamp); }
    /**
     * @param value Initial value of pheromone's intensity
     * @param initalTimestamp Timestamp of pheromone's data creation
     */
    public Pheromone(double value, long initalTimestamp) {
        this.value = value;
        this.timestamp = initalTimestamp;
    }

    /**
     * @param timestamp timestamp of check
     * @return current value of pheromone's intensity
     */
    public synchronized double get(long timestamp) {
        if(timestamp == this.timestamp) //pheromone was already refreshed
            return value;
        if(timestamp < this.timestamp) //pheromone was already refreshed in future
            futureException();
        decay(timestamp);
        return value;
    }
    private void decay(long newTimestamp) {
        for (long timeElapsed = newTimestamp - timestamp; timeElapsed > 0; timeElapsed--) {
            //System.out.println("Pheromone.decay: " + value);
            value *= DECAY_FACTOR;
            //System.out.println("Pheromone.decay * factor: " + value);
        }
        timestamp = newTimestamp;

    }

    /**
     *
     * @return last remembered distance to exit state
     */
    public synchronized int getDistance() {
        return distance;
    }

    /**
     * Adds pheromone
     * @param value Added pheromone's intensity
     * @param timestamp timestamp of operation (for decay purposes)
     * @param distance distance to exit state
     */
    public synchronized void add(double value, long timestamp, int distance) {
        if(this.distance > distance)
            this.distance = distance;
        if(timestamp == this.timestamp)
            this.value += value;
        else if (timestamp < this.timestamp)
            futureException();
        else {
            decay(timestamp);
            this.value += value;
        }
    }

    /**
     * Sets pheromone's parameters to fixed values
     * @param value pheromone's intensity
     * @param timestamp timestamp of operation
     */
    public synchronized void set(double value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    private void futureException() { throw new RuntimeException("Pheromone received call from the past!"); }
}