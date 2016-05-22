package controller;

import model.Block;
import model.BlockType;
import model.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz Kasprzyk on 2016-05-22.
 */
public class GameBoardConfig {
    private static GameBoardConfig instance = null;
    public static int SIZE;
    public static int board[][];

    /* ANT SOLVER PARAMETERS */
    public static int ANT_QUANTITY;
    public static int TRIALS;
    // TODO define other parameters
    /* END OF ANT SOLVER PARAMETERS */

    protected GameBoardConfig(String configFilePath) throws IOException {
        File configFile = new File(configFilePath);
        FileReader fileReader = new FileReader(configFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String[] parsed = reader.readLine().split("="); // SIZE = <integer>

        SIZE = Integer.parseInt(parsed[1].trim());
        while(!reader.readLine().trim().equals("GAMEBOARD_BEGIN"));

        board = new int[SIZE][SIZE];

        for(int i=0; i<SIZE; i++){
            parsed = reader.readLine().trim().split("\\s+", SIZE);
            for(int j=0; j<SIZE; j++){
                board[i][j] = Integer.parseInt(parsed[j]);
            }
        }

        while(!reader.readLine().trim().equals("GAMEBOARD_END"));
        /*
            ==========================================================
                            ANT SOLVER SETTINGS
            ==========================================================
         */
        while(!reader.readLine().trim().equals("ANT_SETTINGS_BEGIN"));
        /*  ========================================================== */
        parsed = reader.readLine().split("="); // ANT_QUANTITY = <integer>
        ANT_QUANTITY = Integer.parseInt(parsed[1].trim());

        parsed = reader.readLine().split("="); // TRIALS = <integer>
        TRIALS = Integer.parseInt(parsed[1].trim());

        // TODO parse here other parameters
        /*  ========================================================== */
        while(!reader.readLine().trim().equals("ANT_SETTINGS_END"));
    }

    public static synchronized GameBoardConfig getInstance()  {
        return getInstance(null);
    }

    public static synchronized GameBoardConfig getInstance(String configFilePath)  {
        if( null == instance )
            try {
                instance = new GameBoardConfig(configFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return instance;
    }

    public synchronized List<Block> generateInitialBoard(){

        List<Block> blocks = new ArrayList<Block>();
        /* if block with an id is found then foundedIds[id - 1] == true */
        boolean[] foundedIds = new boolean[SIZE * SIZE];

        for(int i=0; i< SIZE; i++){
            for(int j=0; j< SIZE; j++){
                if(board[i][j] == 0)
                    continue;
                if( !foundedIds[board[i][j] - 1] ){ //block with a new id found
                    foundedIds[board[i][j] - 1] = true;
                    int blockId = board[i][j];
                    Position position = new Position(j,i);
                    BlockType type = null;
                    int blockSize = 1;
                    if(board[i+1][j] == blockId) {
                        type = BlockType.VERTICAL;
                        while(i+blockSize < SIZE && board[i+blockSize][j] == blockId)
                            blockSize++;
                    }else if(board[i][j+1] == blockId) {
                        type = BlockType.HORIZONTAL;
                        while(j+blockSize < SIZE && board[i][j+blockSize] == blockId)
                            blockSize++;
                    }
                    Block block = new Block(blockId, type, blockSize, position);
                    blocks.add(block);
                }
            }
        }
        for(Block block : blocks){
            System.out.print(block);
        }
        return blocks;
    }
}

class ConfigFileParseException extends Exception{}
