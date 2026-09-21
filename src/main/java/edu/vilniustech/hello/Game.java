package edu.vilniustech.hello;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final Map map;
    private final Pacman pacman;
    private final Ghost[] ghosts;
    private final GameScene scene;
    private final GameRules rules;
    private final GameRenderer renderer;
    private final Score score;
    private final Random random;

    public Game() {
        map = new Map();
        pacman = new Pacman(9, 8);
        ghosts = new Ghost[] {
            new Ghost(3, 8, 0, 1),
            new Ghost(3, 10, 0, -1),
        };
        scene = new PacmanGameScene(map, pacman, ghosts);
        rules = new GameRules();
        renderer = new GameRenderer();
        score = new Score();
        random = new Random();
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            CommandReader input = new CommandReader(scanner);
            renderer.render(scene, score);
            System.out.println("Move: W/A/S/D, quit: Q");

            while (true) {
                Optional<Character> keyOptional = input.readKey();
                if (keyOptional.isEmpty()) {
                    continue;
                }

                char key = keyOptional.get();
                if (key == 'Q') {
                    break;
                }

                GameRules.MoveResult result = rules.movePacman(pacman, key, map, map.getPellets(), score);
                if (result == GameRules.MoveResult.UNKNOWN_KEY) {
                    System.out.println("Unknown key. Use W/A/S/D or Q.");
                    continue;
                }
                if (result == GameRules.MoveResult.BLOCKED) {
                    continue;
                }

                if (rules.allPelletsEaten(map.getPellets())) {
                    renderer.render(scene, score);
                    System.out.println("YOU WIN! Score: " + score.getValue());
                    break;
                }

                rules.moveGhosts(ghosts, map, random);
                renderer.render(scene, score);

                if (rules.isPacmanCaught(pacman, ghosts)) {
                    System.out.println("GAME OVER! Ghost caught Pac-Man.");
                    break;
                }
            }
        }
    }
}
