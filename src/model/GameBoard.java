package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
    private static final int SIZE = 6;
    private static final int SOLUTION_ROW = 2;
    private int[][] board;
    private Map<Integer, Block> blocks;

    public GameBoard(List<Block> blocks) {
        board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }

        this.blocks = new HashMap<>();

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            this.blocks.put(block.getID(), block);

            if (block.getType() == BlockType.VERTICAL) {
                for (int j = 0, pos = block.getPosition().getPosY(); j < block.getSize(); j++, pos++) {
                    board[pos][block.getPosition().getPosX()] = block.getID();
                }
            } else {
                for (int j = 0, pos = block.getPosition().getPosX(); j < block.getSize(); j++, pos++) {
                    board[block.getPosition().getPosY()][pos] = block.getID();
                }
            }
        }
    }

    public GameBoard(GameBoard gameBoard) {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(gameBoard.board[i], 0, board[i], 0, SIZE);
        }

        for (Map.Entry<Integer, Block> entry: gameBoard.blocks.entrySet()) {
            blocks.put(entry.getKey(), new Block(entry.getValue()));
        }
    }

    public Map<Integer, Block> getBlocks() {
        return blocks;
    }


    public void printDump() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean canMove(Block block, int step) {
        int posX;
        int posY;

        if (step == 0) {
            return true;
        }

        if (block.getType() == BlockType.HORIZONTAL) {
            posY = block.getPosition().getPosY();
            posX = block.getPosition().getPosX() + block.getSize() + step - 1;

            if(posX >= SIZE || posX < 0) {
                return false;
            }

            for (int i = block.getPosition().getPosX() + 1; i <= posX; i++) {
                if (board[posY][posX] != 0) {
                    return false;
                }
            }
            return true;
        } else {
            posX = block.getPosition().getPosX();
            posY = block.getPosition().getPosY() + block.getSize() + step - 1;

            if(posY >= SIZE || posY < 0) {
                return false;
            }

            for (int i = block.getPosition().getPosY() + 1; i <= posY; i++) {
                if (board[posY][posX] != 0) {
                    return false;
                }
            }
            return true;
        }

    }

    public boolean isFinished() {
        int prisonerSize = blocks.get(1).getSize();
        Position prisonerPos = blocks.get(1).getPosition();
        if (prisonerPos.getPosX() == SIZE - prisonerSize) {
            return true;
        } else {
            return false;
        }
    }

    public boolean move(Block block, int step) {

        return false;
    }
}
