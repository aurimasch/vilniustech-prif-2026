package edu.vilniustech.hello;

public class GameRenderer {

    public void render(GameScene scene, Score score) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int row = 0; row < scene.getHeight(); row++) {
            for (int col = 0; col < scene.getWidth(); col++) {
                System.out.print(scene.symbolAt(row, col));
            }
            System.out.println();
        }
        System.out.println("Score: " + score.getValue());
    }
}
