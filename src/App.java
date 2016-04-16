import model.Block;
import model.GameBoard;
import solvers.generator.SimpleGenerator;

import java.util.List;

/**
 * Created by throzendar on 16/04/16.
 */
public class App {
    public static void main(String[] args) {
        List<Block> blocks = SimpleGenerator.generate();
        GameBoard gameBoard = new GameBoard(blocks, blocks.get(0));

        gameBoard.printDump();
    }
}
