

import java.util.Map;
import java.util.Random;

public class BattleshipGeneratorImplementation implements BattleshipGenerator {
    private static final int N = 10;
    private static final Random random = new Random();

    @Override
    public String generateMap() {
        char[][] board = new char[N][N];


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = '.';
            }
        }

        Map<Integer, Integer> ships = Map.of(
                4, 1,
                3, 2,
                2, 3,
                1, 4
        );

        for (var entry : ships.entrySet()) {
            int shipLen = entry.getKey();
            int shipCount = entry.getValue();
            for (int i = 0; i < shipCount; i++) {
                placeShip(board, shipLen);
            }
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                res.append(board[i][j]);
            }
            res.append("\n");
        }
        return res.toString();
    }

    private boolean canPlaceShip(char[][] board, int startRow, int startCol, int shipLen, boolean isVertical) {

        if (isVertical) {
            if (startRow + shipLen > N) return false;
        } else {
            if (startCol + shipLen > N) return false;
        }
        // Checking the rectangle around the ship we would like to place without going out of bounds


        int minRow = Math.max(0, startRow - 1);
        int maxRow = Math.min(N - 1, isVertical ? startRow + shipLen : startRow + 1);
        int minCol = Math.max(0, startCol - 1);
        int maxCol = Math.min(N - 1, isVertical ? startCol + 1 : startCol + shipLen);


        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                if (board[row][col] == '#') {
                    return false;
                }
            }
        }

        return true;
    }

    private void placeShip(char[][] board, int shipLen) {
        boolean isPlaced = false;

        while (!isPlaced) {
            boolean isVertical = random.nextBoolean();
            int startRow = random.nextInt(N);
            int startCol = random.nextInt(N);

            if (canPlaceShip(board, startRow, startCol, shipLen, isVertical)) {
                for (int i = 0; i < shipLen; i++) {
                    int currRow = isVertical ? startRow + i : startRow;
                    int currCol = isVertical ? startCol : startCol + i;
                    board[currRow][currCol] = '#';
                }
                isPlaced = true;
            }
        }
    }
}