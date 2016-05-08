package solvers;

import model.GameBoard;
import org.junit.Assert;
import solvers.generator.SimpleGenerator;

import java.util.Map;

public class PossibleTransitionsTest {
    @org.junit.Test
    public void testNeighbourhood() {
        GameBoard gb = new GameBoard(SimpleGenerator.generate());
        Map<GameBoard, SolutionStep> steps = gb.getPossibleTransitions();
        Assert.assertFalse(steps.isEmpty());
        Assert.assertEquals(6, steps.size());
        for(Map.Entry<GameBoard, SolutionStep> entry : steps.entrySet()) {
            entry.getKey().printDump();
        }
    }
}
