package solvers.generator;

import model.Block;
import model.BlockType;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class SimpleGenerator {
    public static List<Block> generate() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, BlockType.HORIZONTAL, 2, new Position(0, 2)));
        blocks.add(new Block(2, BlockType.VERTICAL, 2, new Position(2, 1)));
        blocks.add(new Block(3, BlockType.HORIZONTAL, 2, new Position(2, 0)));
        blocks.add(new Block(4, BlockType.HORIZONTAL, 2, new Position(2, 4)));
        blocks.add(new Block(5, BlockType.HORIZONTAL, 2, new Position(4, 0)));
        blocks.add(new Block(6, BlockType.VERTICAL, 2, new Position(4, 1)));
        blocks.add(new Block(7, BlockType.VERTICAL, 2, new Position(4, 3)));

        return blocks;
    }
}
