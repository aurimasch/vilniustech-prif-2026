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

    public void eatAt(int row, int col) {
        if (row < 0 || col < 0 || row >= remaining.length || col >= remaining[0].length) {
            return;
        }
        remaining[row][col] = false;
    }

    public char symbolAt(int row, int col) {
        if (row < 0 || col < 0 || row >= remaining.length || col >= remaining[0].length) {
            return ' ';
        }
        return remaining[row][col] ? '.' : ' ';
    }
}
