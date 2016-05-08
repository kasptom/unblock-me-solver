package solvers.ants;

import model.Block;
import model.GameBoard;
import solvers.generator.SimpleGenerator;

import java.lang.reflect.GenericArrayType;
import java.util.List;

public class PheromonesTest {
    @org.junit.Test
    public void checkIdenticalSetup() {
        Pheromones p = new Pheromones();
        long timestamp  = 0;
        List<Block> list = SimpleGenerator.generate();
        GameBoard gb1 = new GameBoard(list);
        list.remove(0);
        GameBoard gb2 = new GameBoard(list);


        p.check(timestamp, gb1, gb2);

        Pheromone received = p.get(gb1, gb2);
        if(received == null)
            System.out.println("null");
        System.out.println(received.get(timestamp));
    }

    @org.junit.Test
    public void checkReverseSetup() {
        Pheromones p = new Pheromones();
        long timestamp  = 0;
        List<Block> list = SimpleGenerator.generate();
        GameBoard gb1 = new GameBoard(list);
        list.remove(0);
        GameBoard gb2 = new GameBoard(list);


        p.check(timestamp, gb1, gb2);

        Pheromone received = p.get(gb2, gb1);
        if(received == null)
            System.out.println("null");
        System.out.println(received.get(timestamp));
    }
}
