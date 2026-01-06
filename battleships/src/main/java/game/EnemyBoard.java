package game;

import java.util.ArrayList;
import java.util.List;

public class EnemyBoard {
    private final char[][] map = new char[10][10];

    public EnemyBoard() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                map[i][j] = '?';
    }


    public void markShot(int row, int col, String result) {
        switch (result) {
            case "pudło;":
                if (map[row][col] != '.') {
                    map[row][col] = '~';
                }
                break;
            case "trafiony;":
                map[row][col] = '#';
                break;
            case "trafiony zatopiony;":
                map[row][col] = '#';

                revealSunkShip(row, col);
                break;
        }
    }


    private void revealSunkShip(int startRow, int startCol) {
        List<int[]> shipSegments = new ArrayList<>();
        boolean[][] visited = new boolean[10][10];


        findShipSegments(startRow, startCol, visited, shipSegments);


        for (int[] seg : shipSegments) {
            int r = seg[0];
            int c = seg[1];


            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = r + dr;
                    int nc = c + dc;

                    if (nr >= 0 && nr < 10 && nc >= 0 && nc < 10 && (map[nr][nc] == '?' || map[nr][nc] == '~')) {
                        map[nr][nc] = '.';
                    }
                }
            }
        }
    }


    private void findShipSegments(int r, int c, boolean[][] visited, List<int[]> segments) {
        if (r < 0 || r >= 10 || c < 0 || c >= 10) return;
        if (visited[r][c]) return;
        if (map[r][c] != '#') return;

        visited[r][c] = true;
        segments.add(new int[]{r, c});


        findShipSegments(r + 1, c, visited, segments);
        findShipSegments(r - 1, c, visited, segments);
        findShipSegments(r, c + 1, visited, segments);
        findShipSegments(r, c - 1, visited, segments);
    }


    public void print() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}