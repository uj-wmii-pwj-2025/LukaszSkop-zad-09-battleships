



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapAdder {

    private final Path path;
    private final BattleshipGenerator generator;

    public MapAdder(BattleshipGenerator generator, String pathStr) {
        this.generator = generator;
        this.path = Path.of(pathStr);
    }


    public String generateAndSaveMap() {
        String map = generator.generateMap();
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, map);

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        return map;
    }

    public static void main(String[] args) {

        BattleshipGenerator generator = BattleshipGenerator.defaultInstance();


        MapAdder manager = new MapAdder(generator,
                "battleships/random_map.txt");


        String map = manager.generateAndSaveMap();

    }
}
