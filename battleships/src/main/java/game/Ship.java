package game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Ship {
    private final List<int[]> coords;
    private final Set<String> hitSegments = new HashSet<>();

    public Ship(List<int[]> coords) {
        this.coords = coords;
    }

    public boolean occupies(int row, int col) {
        for (int[] c : coords)
            if (c[0] == row && c[1] == col)
                return true;
        return false;
    }

    public boolean isHit(int row, int col) {
        return hitSegments.contains(key(row, col));
    }

    public void hit(int row, int col) {
        hitSegments.add(key(row, col));
    }

    public boolean isSunk() {
        return hitSegments.size() == coords.size();
    }

    private String key(int r, int c) {
        return r + "," + c;
    }
    public List<int[]> getCoords() {
        return coords;
    }
}
