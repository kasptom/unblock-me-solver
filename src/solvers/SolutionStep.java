package solvers;

import model.Block;

/**
 * Created by throzendar on 16/04/16.
 */
public class SolutionStep {
    private Block block;
    private int step;

    public SolutionStep(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public int getStep() {
        return step;
    }
}
