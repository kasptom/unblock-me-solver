package solvers.ants;

public class Pheromone {
    public static final double DEFAULT_INITIAL_PHEROMONE = 1.0;
    public static final long DEFAULT_INITIAL_TIMESTAMP = 0L;
    public static final double DECAY_FACTOR = 0.9;

    /**
     * Pheromone's intensity
     */
    private double value;
    /**
     * Time of last intensity's refresh
     */
    private long timestamp;

    public Pheromone() { new Pheromone(DEFAULT_INITIAL_PHEROMONE); }
    /**
     * @param value Initial value of pheromone's intensity
     */
    public Pheromone(double value) { new Pheromone(value, DEFAULT_INITIAL_TIMESTAMP); }
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
        for (long timeElapsed = newTimestamp - timestamp; timeElapsed > 0; timeElapsed--)
            value *= DECAY_FACTOR;
        timestamp = newTimestamp;
    }

    /**
     * Adds pheromone
     * @param value Added pheromone's intensity
     * @param timestamp timestamp of operation (for decay purposes)
     */
    public synchronized void add(double value, long timestamp) {
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