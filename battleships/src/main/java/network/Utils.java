package network;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Utils {

    public static int row(char c) {
        return c - 'A';
    }

    public static int col(String s) {
        return Integer.parseInt(s) - 1;
    }

    public static String randomShot(Random r) {
        return "" + (char)('A' + r.nextInt(10)) + (r.nextInt(10) + 1);
    }


    public static String readMapFile(String mapFilePath) throws IOException {

        return Files.readString(Path.of(mapFilePath)).replace("\n","").replace("\r","");
    }

}
