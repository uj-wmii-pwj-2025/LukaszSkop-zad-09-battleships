package game;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<int[]> lastSunkShip = null;

    public List<int[]> getLastSunkShipCoords() {
        return lastSunkShip;
    }

    private final List<Ship> ships = new ArrayList<>();
    private final char[][] board = new char[10][10];

    public Board(String mapString) {


        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                board[i][j] = mapString.charAt(i * 10 + j);


        boolean[][] visited = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!visited[i][j] && board[i][j] == '#') {
                    List<int[]> coords = new ArrayList<>();
                    dfs(i, j, visited, coords);
                    ships.add(new Ship(coords));
                }
            }
        }
    }

    private void dfs(int r, int c, boolean[][] visited, List<int[]> coords) {
        if (r < 0 || r >= 10 || c < 0 || c >= 10) return;
        if (visited[r][c] || board[r][c] != '#') return;

        visited[r][c] = true;
        coords.add(new int[]{r, c});

        dfs(r + 1, c, visited, coords);
        dfs(r - 1, c, visited, coords);
        dfs(r, c + 1, visited, coords);
        dfs(r, c - 1, visited, coords);
    }


    public String shoot(int row, int col) {

        char cell = board[row][col];


        if (cell == '~') {
            return "pudło;";
        }


        if (cell == '@') {
            Ship ship = findShip(row, col);
            if (ship.isSunk()) return "trafiony zatopiony;";
            return "trafiony;";
        }


        if (cell == '#') {
            Ship ship = findShip(row, col);

            ship.hit(row, col);
            board[row][col] = '@';

            if (ship.isSunk()) {
                lastSunkShip = ship.getCoords();
                if (allSunk()) return "ostatni zatopiony";
                return "trafiony zatopiony;";
            }
            else {
                lastSunkShip = null;
                return "trafiony;";
            }
        }


        board[row][col] = '~';
        return "pudło;";
    }

    private Ship findShip(int row, int col) {
        for (Ship s : ships)
            if (s.occupies(row, col))
                return s;
        throw new IllegalStateException("Brak statku na podanych współrzędnych");
    }

    private boolean allSunk() {
        for (Ship s : ships)
            if (!s.isSunk())
                return false;
        return true;
    }


    public void printBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");

            }
            System.out.println();
        }
    }
}
