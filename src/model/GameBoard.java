package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
    public static final int SIZE = 6;
    public static final int SOLUTION_ROW = 2;
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

        blocks = new HashMap<>(gameBoard.blocks.size());
        for (Map.Entry<Integer, Block> entry : gameBoard.blocks.entrySet()) {
            blocks.put(entry.getKey(), new Block(entry.getValue()));
        }
    }

    public Map<Integer, Block> getBlocks() {
        return blocks;
    }

    public int[][] getBoard() {
        return board;
    }

    public void printDump() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean canMove(Block block, int step) {
        int posX;
        int posY;
        boolean isFoward = step < 0 ? false : true;

        if (step == 0) {
            return true;
        }

        if (block.getType() == BlockType.HORIZONTAL) {
            posY = block.getPosition().getPosY();
            posX = (step >= 0 ? block.getPosition().getPosX() + block.getSize() + step - 1 : block.getPosition().getPosX() + step);

            if (posX >= SIZE || posX < 0) {
                return false;
            }

            if (isFoward) {
                for (int i = block.getPosition().getPosX() + block.getSize(); i <= posX; i++) {
                    if (board[posY][i] != 0) {
                        return false;
                    }
                }
            } else {
                for (int i = block.getPosition().getPosX() - 1; i >= posX; i--) {
                    if (board[posY][posX] != 0) {
                        return false;
                    }
                }
            }


            return true;
        } else {
            posX = block.getPosition().getPosX();
            posY = (step >= 0 ? block.getPosition().getPosY() + block.getSize() + step - 1 : block.getPosition().getPosY() + step);

            if (posY >= SIZE || posY < 0) {
                return false;
            }

            if (isFoward) {
                for (int i = block.getPosition().getPosY() + block.getSize(); i <= posY; i++) {
                    if (board[i][posX] != 0) {
                        return false;
                    }
                }
            } else {
                for (int i = block.getPosition().getPosY() - 1; i >= posY; i--) {
                    if (board[i][posX] != 0) {
                        return false;
                    }
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

    public void move(Block block, int step) {
        Position position = block.getPosition();
        BlockType blockType = block.getType();
        if (blockType == BlockType.HORIZONTAL) {
            for (int i = 0, pos = block.getPosition().getPosX(); i < block.getSize(); i++, pos++) {
                board[block.getPosition().getPosY()][pos] = 0;
            }

            position.setPosX(position.getPosX() + step);
            for (int j = 0, pos = block.getPosition().getPosX(); j < block.getSize(); j++, pos++) {
                board[block.getPosition().getPosY()][pos] = block.getID();
            }
        } else if (blockType == BlockType.VERTICAL) {
            for (int i = 0, pos = block.getPosition().getPosY(); i < block.getSize(); i++, pos++) {
                board[pos][block.getPosition().getPosX()] = 0;
            }

            position.setPosY(position.getPosY() + step);
            for (int j = 0, pos = block.getPosition().getPosY(); j < block.getSize(); j++, pos++) {
                board[pos][block.getPosition().getPosX()] = block.getID();
            }
        }
    }

    /**
     * Creates the neighbourhood
     * @return list of neighbouring GameBoards
     */
    public List<GameBoard> getPossibleTransitions() {
        ArrayList<GameBoard> list = new ArrayList<>();
        for(Block b : blocks.values()) {
            int step = 1;
            while(canMove(b, step)) {
                list.add(createTransition(b, step));
                step++;
            }
            step = -1;
            while(canMove(b, step)) {
                list.add(createTransition(b, step));
                step--;
            }
        }
        return list;
    }
    private GameBoard createTransition(Block b, int step) {
        GameBoard gb = new GameBoard(this);
        gb.move(b, step);
        return gb;
    }
}
