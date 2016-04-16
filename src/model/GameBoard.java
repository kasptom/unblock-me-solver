package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
    private static final int SIZE = 6;
    private int[][] board;
    private Map<Integer, Block> blocks;
    private Block special;

    public GameBoard(List<Block> blocks, Block special) {
        board = new int[SIZE][SIZE];
        this.blocks = new HashMap<>();

        for (int i = 0; i < blocks.size(); i++) {
            this.blocks.put(i, blocks.get(i));
        }

        this.special = special;
    }

    public int[][] getBoard() {
        return board;
    }

    public Map<Integer, Block> getBlocks() {
        return blocks;
    }

    public Block getSpecial() {
        return special;
    }
}
