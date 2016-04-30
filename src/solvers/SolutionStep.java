package solvers;

import model.Block;

/**
 * Created by throzendar on 16/04/16.
 */
public class SolutionStep {
    private int blockId;
    private int step;

    public SolutionStep(int blockId, int step) {
        this.blockId = blockId;
        this.step = step;
    }

    public int getBlockId() {
        return blockId;
    }

    public int getStep() {
        return step;
    }

    @Override
    public String toString() {
        return "{" +
                "#" + blockId +
                ", " + step +
                '}';
    }
}
