package edu.vilniustech.hello;

import java.util.Random;

public class GameRules {

    public enum MoveResult {
        MOVED,
        BLOCKED,
        UNKNOWN_KEY
    }

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public boolean canMoveTo(Map map, int row, int col) {
        if (row < 0 || col < 0 || row >= map.getHeight() || col >= map.getWidth()) {
            return false;
        }
        return !map.isWall(row, col);
    }

    public MoveResult movePacman(Pacman pacman, char key, Map map, Pellet pellets, Score score) {
        int nextRow = pacman.getRow();
        int nextCol = pacman.getCol();

        switch (Character.toUpperCase(key)) {
            case 'W' -> nextRow--;
            case 'S' -> nextRow++;
            case 'A' -> nextCol--;
            case 'D' -> nextCol++;
            default -> {
                return MoveResult.UNKNOWN_KEY;
            }
        }

        if (!canMoveTo(map, nextRow, nextCol)) {
            return MoveResult.BLOCKED;
        }

        eatPellet(pellets, score, pacman.getRow(), pacman.getCol());
        pacman.setPosition(nextRow, nextCol);
        eatPellet(pellets, score, pacman.getRow(), pacman.getCol());

        return MoveResult.MOVED;
    }

    public boolean allPelletsEaten(Pellet pellets) {
        return pellets.isEmpty();
    }

    private void eatPellet(Pellet pellets, Score score, int row, int col) {
        if (pellets.eatAt(row, col)) {
            score.add(1);
        }
    }

    public void moveGhosts(Ghost[] ghosts, Map map, Random random) {
        for (Ghost ghost : ghosts) {
            moveGhost(ghost, map, random);
        }
    }

    public boolean isPacmanCaught(Pacman pacman, Ghost[] ghosts) {
        for (Ghost ghost : ghosts) {
            if (ghost.isAt(pacman.getRow(), pacman.getCol())) {
                return true;
            }
        }
        return false;
    }

    private void moveGhost(Ghost ghost, Map map, Random random) {
        int nextRow = ghost.getRow() + ghost.getDr();
        int nextCol = ghost.getCol() + ghost.getDc();

        if (!canMoveTo(map, nextRow, nextCol)) {
            pickNewGhostDirection(ghost, map, random);
            nextRow = ghost.getRow() + ghost.getDr();
            nextCol = ghost.getCol() + ghost.getDc();
        }

        if (canMoveTo(map, nextRow, nextCol)) {
            ghost.setPosition(nextRow, nextCol);
        }
    }

    private void pickNewGhostDirection(Ghost ghost, Map map, Random random) {
        int start = random.nextInt(DIRECTIONS.length);

        for (int offset = 0; offset < DIRECTIONS.length; offset++) {
            int[] direction = DIRECTIONS[(start + offset) % DIRECTIONS.length];
            if (canMoveTo(map, ghost.getRow() + direction[0], ghost.getCol() + direction[1])) {
                ghost.setDirection(direction[0], direction[1]);
                return;
            }
        }
    }
}
