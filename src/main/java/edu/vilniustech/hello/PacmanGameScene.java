package edu.vilniustech.hello;

public class PacmanGameScene implements GameScene {

    private static final char PACMAN_SYMBOL = 'C';
    private static final char GHOST_SYMBOL = 'G';

    private final Map map;
    private final Pacman pacman;
    private final Ghost[] ghosts;

    public PacmanGameScene(Map map, Pacman pacman, Ghost[] ghosts) {
        this.map = map;
        this.pacman = pacman;
        this.ghosts = ghosts;
    }

    @Override
    public int getHeight() {
        return map.getHeight();
    }

    @Override
    public int getWidth() {
        return map.getWidth();
    }

    @Override
    public char symbolAt(int row, int col) {
        if (pacman.getRow() == row && pacman.getCol() == col) {
            return PACMAN_SYMBOL;
        }
        for (Ghost ghost : ghosts) {
            if (ghost.isAt(row, col)) {
                return GHOST_SYMBOL;
            }
        }
        if (map.isWall(row, col)) {
            return '#';
        }
        return map.getPellets().symbolAt(row, col);
    }
}
