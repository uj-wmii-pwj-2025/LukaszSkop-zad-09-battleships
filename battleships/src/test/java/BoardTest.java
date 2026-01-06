import game.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class BoardTest {
    @Test
    public void testShootWater(){
        String map = "..........".repeat(10);
        Board board = new Board(map);
        assertEquals("pudło;",board.shoot(0,0));
        assertEquals("pudło;",board.shoot(0,5));

    }
    @Test
    public void testShootSingleSegmentShip() {
        String map = "#........." +".#........" +"..........".repeat(8);
        Board board = new Board(map);
        String result = board.shoot(0, 0);
        assertEquals("trafiony zatopiony;", result);
    }
    @Test
    public void testShootMultiSegmentShip() {
        String map = "##........" +"....#....." +"..........".repeat(8);
        Board board = new Board(map);
        assertEquals("trafiony;",board.shoot(0,0));
        assertEquals("trafiony zatopiony;",board.shoot(0,1));
    }
    @Test
    public void testLastShip(){
        String map = "#........."+ "..........".repeat(9);
        Board board = new Board(map);
        assertEquals("ostatni zatopiony",board.shoot(0,0));
    }
    @Test
    public void multipleShips(){
        String map = "#.#......." +
                "..........".repeat(8) +
                "##........";
        Board board = new Board(map);

        assertEquals("trafiony zatopiony;",board.shoot(0,0));
        assertEquals("trafiony zatopiony;",board.shoot(0,2));
        assertEquals("trafiony;",board.shoot(9,0));
        assertEquals("ostatni zatopiony",board.shoot(9,1));




    }

    @Test
    public void testSameLocationHit(){
        String map = "#........." + "..#......." + "..........".repeat(8);

        Board board = new Board(map);

        board.shoot(0,0);
        String result = board.shoot(0,0);
        assertEquals("trafiony zatopiony;",result);


    }



}
