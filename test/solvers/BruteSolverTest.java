package solvers;

import model.Block;
import model.BlockType;
import model.GameBoard;
import model.Position;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Tomasz Kasprzyk on 2016-05-01.
 */
public class BruteSolverTest {
    List<Block> blocks = Collections.unmodifiableList(new ArrayList<Block>()
    {
        {
            add(new Block(1, BlockType.HORIZONTAL, 2, new Position(0, 2)));
            add(new Block(2, BlockType.VERTICAL, 3, new Position(5, 0)));
            add(new Block(3, BlockType.VERTICAL, 3, new Position(5, 3)));
        }
    });
    GameBoard gameBoard = new GameBoard(blocks);
    BruteSolver solver = new BruteSolver(gameBoard);

    @Test
    public void testNoSolutionFound(){
        solver.solve();
        Assert.assertEquals(solver.getSteps().size(), 0);
    }

}
