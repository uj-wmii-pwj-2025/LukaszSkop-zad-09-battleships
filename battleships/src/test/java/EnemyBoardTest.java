import game.EnemyBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyBoardTest {
    private EnemyBoard enemyBoard;
    @BeforeEach
    public void setUp() {
        enemyBoard = new EnemyBoard();
    }




    @Test
    public void missedShot(){
        enemyBoard.markShot(0,0,"pudło;");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setOut(new PrintStream(out));

        enemyBoard.print();

        String output = out.toString();

        String first = output.split("\n")[0];
        assertEquals('~',first.charAt(0));


    }
    @Test
    public void testMarkSunkSingleSegment() {
        enemyBoard.markShot(5, 5, "trafiony zatopiony;");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        enemyBoard.print();

        String output = outContent.toString();
        String lines[] = output.split("\n");


        assertEquals('#', lines[5].charAt(5));


        assertEquals('.', lines[4].charAt(4));
        assertEquals('.', lines[4].charAt(5));
        assertEquals('.', lines[4].charAt(6));
        assertEquals('.', lines[5].charAt(4));
        assertEquals('.', lines[5].charAt(6));
        assertEquals('.', lines[6].charAt(4));
        assertEquals('.', lines[6].charAt(5));
        assertEquals('.', lines[6].charAt(6));
    }
    @Test
    public void testCornerShip() {

        enemyBoard.markShot(0, 0, "trafiony zatopiony;");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        enemyBoard.print();

        String output = outContent.toString();
        String lines[] = output.split("\n");

        assertEquals('#', lines[0].charAt(0));
        assertEquals('.', lines[0].charAt(1));
        assertEquals('.', lines[1].charAt(0));
        assertEquals('.', lines[1].charAt(1));
    }

}
