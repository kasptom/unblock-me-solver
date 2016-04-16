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

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }

        this.blocks = new HashMap<>();

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            this.blocks.put(i, block);

            if (block.getType() == BlockType.HORIZONTAL) {
                for (int j = 0, pos = block.getPosition().getPosX(); j < block.getSize(); j++, pos++) {
                    board[pos][block.getPosition().getPosY()] = block.getID();
                }
            } else {
                for (int j = 0, pos = block.getPosition().getPosY(); j < block.getSize(); j++, pos++) {
                    board[block.getPosition().getPosX()][pos] = block.getID();
                }
            }
        }

        this.special = special;
    }

    public Map<Integer, Block> getBlocks() {
        return blocks;
    }

    public Block getSpecial() {
        return special;
    }

    public void printDump() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
