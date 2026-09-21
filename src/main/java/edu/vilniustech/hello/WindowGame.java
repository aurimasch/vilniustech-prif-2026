package edu.vilniustech.hello;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class WindowGame {

    private final Map map;
    private final Pacman pacman;
    private final Ghost[] ghosts;
    private final GameScene scene;
    private final GameRules rules;
    private final WindowGameRenderer renderer;
    private final Score score;
    private final Random random;

    private JLabel status;
    private boolean running;

    public WindowGame() {
        map = new Map();
        pacman = new Pacman(9, 8);
        ghosts = new Ghost[] {
            new Ghost(3, 8, 0, 1),
            new Ghost(3, 10, 0, -1),
        };
        scene = new PacmanGameScene(map, pacman, ghosts);
        rules = new GameRules();
        renderer = new WindowGameRenderer();
        score = new Score();
        random = new Random();
        running = true;
    }

    public void run() {
        SwingUtilities.invokeLater(this::showWindow);
    }

    private void showWindow() {
        status = new JLabel(playingStatus(), SwingConstants.CENTER);

        JFrame frame = new JFrame("Pac-Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(renderer, BorderLayout.CENTER);
        frame.add(status, BorderLayout.SOUTH);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                handleKey(toCommand(event));
            }
        });

        renderer.render(scene);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }

    private void handleKey(char key) {
        if (!running || key == 0) {
            return;
        }
        if (key == 'Q') {
            running = false;
            System.exit(0);
        }

        GameRules.MoveResult result = rules.movePacman(pacman, key, map, map.getPellets(), score);
        if (result != GameRules.MoveResult.MOVED) {
            return;
        }

        if (rules.allPelletsEaten(map.getPellets())) {
            renderer.render(scene);
            running = false;
            status.setText("YOU WIN! Score: " + score.getValue());
            return;
        }

        rules.moveGhosts(ghosts, map, random);
        renderer.render(scene);
        status.setText(playingStatus());

        if (rules.isPacmanCaught(pacman, ghosts)) {
            running = false;
            status.setText("GAME OVER! Ghost caught Pac-Man.");
        }
    }

    private String playingStatus() {
        return "Score: " + score.getValue() + " | Move: W/A/S/D, quit: Q";
    }

    private static char toCommand(KeyEvent event) {
        return switch (event.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> 'W';
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> 'S';
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> 'A';
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> 'D';
            case KeyEvent.VK_Q, KeyEvent.VK_ESCAPE -> 'Q';
            default -> Character.toUpperCase(event.getKeyChar());
        };
    }
}
