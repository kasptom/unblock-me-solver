package model;

import org.junit.Assert;
import solvers.generator.SimpleGenerator;

import java.util.Arrays;

public class GameBoardTest {
    @org.junit.Test
    public void testIdenticalBoards() {
        GameBoard gb1 = new GameBoard(SimpleGenerator.generate());
        GameBoard gb2 = new GameBoard(SimpleGenerator.generate());
        Assert.assertEquals(gb1, gb2);
    }

    @org.junit.Test
    public void testArrays() {
        int[] a1 = {1, 2};
        int[] a2 = {1, 2};
        Assert.assertTrue(Arrays.equals(a1, a2));
    }
}
