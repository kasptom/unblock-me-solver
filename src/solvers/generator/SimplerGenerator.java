package solvers.generator;

import controller.GameBoardConfig;
import model.Block;
import model.BlockType;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class SimplerGenerator{
    public static List<Block> generate() {
        GameBoardConfig.getInstance("res/configs/simplergameboard.config");
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, BlockType.HORIZONTAL, 2, new Position(0, 1)));
        blocks.add(new Block(2, BlockType.HORIZONTAL, 2, new Position(1, 0)));

        return blocks;
    }
    public static List<Block> generate1() {
        GameBoardConfig.getInstance("res/configs/simplergameboard2.config");
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, BlockType.HORIZONTAL, 2, new Position(0, 1)));
        blocks.add(new Block(2, BlockType.VERTICAL, 2, new Position(3, 0)));

        return blocks;
    }
}

/*
[] [2] [2]
[1] [1] []
[] [] []
 */