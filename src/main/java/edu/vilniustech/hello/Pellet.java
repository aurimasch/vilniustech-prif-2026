package edu.vilniustech.hello;

public class Pellet {

    private final boolean[][] remaining;

    public Pellet(char[][] layout) {
        remaining = new boolean[layout.length][layout[0].length];
        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                remaining[row][col] = layout[row][col] == '.';
            }
        }
    }

    public boolean eatAt(int row, int col) {
        if (row < 0 || col < 0 || row >= remaining.length || col >= remaining[0].length) {
            return false;
        }
        if (!remaining[row][col]) {
            return false;
        }
        remaining[row][col] = false;
        return true;
    }

    public boolean isEmpty() {
        for (boolean[] row : remaining) {
            for (boolean hasPellet : row) {
                if (hasPellet) {
                    return false;
                }
            }
        }
        return true;
    }

    public char symbolAt(int row, int col) {
        if (row < 0 || col < 0 || row >= remaining.length || col >= remaining[0].length) {
            return ' ';
        }
        return remaining[row][col] ? '.' : ' ';
    }
}
