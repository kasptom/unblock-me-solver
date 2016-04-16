package model;


public class Block {
    private int ID;
    private BlockType type;
    private int size;
    private Position position;

    public Block(int ID, BlockType type, int size, Position position) {
        this.ID = ID;
        this.type = type;
        this.size = size;
        this.position = position;
    }

    public int getID() {
        return ID;
    }

    public BlockType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public Position getPosition() {
        return position;
    }
}
