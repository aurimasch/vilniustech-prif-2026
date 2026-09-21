package edu.vilniustech.hello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class WindowGameRenderer extends JPanel {

    private static final int TILE_SIZE = 32;

    private GameScene scene;

    public WindowGameRenderer() {
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void render(GameScene scene) {
        this.scene = scene;
        int width = scene.getWidth() * TILE_SIZE;
        int height = scene.getHeight() * TILE_SIZE;
        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (scene == null) {
            return;
        }

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int row = 0; row < scene.getHeight(); row++) {
            for (int col = 0; col < scene.getWidth(); col++) {
                drawTile(g, col * TILE_SIZE, row * TILE_SIZE, scene.symbolAt(row, col));
            }
        }
    }

    private void drawTile(Graphics2D g, int x, int y, char symbol) {
        switch (symbol) {
            case '#' -> {
                g.setColor(new Color(33, 33, 222));
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            }
            case '.' -> {
                g.setColor(new Color(255, 184, 174));
                int size = 6;
                g.fillOval(x + (TILE_SIZE - size) / 2, y + (TILE_SIZE - size) / 2, size, size);
            }
            case 'C' -> {
                g.setColor(Color.YELLOW);
                g.fillOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
            }
            case 'G' -> {
                g.setColor(Color.RED);
                g.fillOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
            }
            default -> {
            }
        }
    }
}
