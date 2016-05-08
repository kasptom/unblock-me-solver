package solvers;

import model.Block;
import model.GameBoard;
import org.junit.Assert;
import solvers.generator.SimpleGenerator;

import java.util.HashMap;
import java.util.Map;

public class PossibleTransitionsTest {
    @org.junit.Test
    public void testNeighbourhood() {
        GameBoard gb = new GameBoard(SimpleGenerator.generate());
        Map<GameBoard, SolutionStep> steps = gb.getPossibleTransitions();
        for(Map.Entry<GameBoard, SolutionStep> entry : steps.entrySet()) {
            System.out.println(entry.getValue());
        }
        Assert.assertFalse(steps.isEmpty());
        Assert.assertEquals(6, steps.size());
    }

    @org.junit.Test
    public void testTransitionByTwo() {
        GameBoard gb = new GameBoard(SimpleGenerator.generate());
        Map <Integer, Block> list = gb.getBlocks();
        Block b3 = list.get(3);
        System.out.println("Block3: {\n" +
                "ID: " + b3.getID() + "\n" +
                "Position: (" + b3.getPosition().getPosX() + ", " + b3.getPosition().getPosY() + ")\n" +
                "Size: " + b3.getSize() + "\n" +
                "Type: " + b3.getType() + "\n}");
        boolean canMove = gb.canMove(b3, -2);
        Assert.assertTrue(canMove);
        System.out.println("[DD]canMove: " + canMove);
    }

    @org.junit.Test
    public void testNeighbourhoodGenerationConstancy() {
        GameBoard gb = new GameBoard(SimpleGenerator.generate());
        int hash1 = gb.hashCode();
        int hash2 = gb.getBlocks().hashCode();
        gb.getPossibleTransitions();
        Assert.assertEquals(hash1, gb.hashCode());
        Assert.assertEquals(hash2, gb.getBlocks().hashCode());
    }
}
