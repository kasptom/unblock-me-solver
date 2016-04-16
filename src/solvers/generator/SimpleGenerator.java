package solvers.generator;

import model.Block;
import model.BlockType;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class SimpleGenerator {
    public static List<Block> generate() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, BlockType.HORIZONTAL, 2, new Position(1, 1)));
        blocks.add(new Block(2, BlockType.HORIZONTAL, 2, new Position(2, 2)));
        blocks.add(new Block(3, BlockType.HORIZONTAL, 2, new Position(3, 1)));
        blocks.add(new Block(4, BlockType.HORIZONTAL, 2, new Position(0, 4)));


        return blocks;
    }
}
