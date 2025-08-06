package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import tileengine.TERenderer;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;
import java.io.File;

public class Main {
    private static long lastSeed = 0L;
    private static String filename = "save.txt";
    public static void enterSeed(int width, int height, TERenderer renderer) {
        String seedInput = "";
        StdDraw.clear();
        StdDraw.setFont(new Font("Arial", Font.BOLD, 25));
        StdDraw.text(0.5, 0.8, "Enter Seed followed by S");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 's' || key == 'S') {
                    if (!seedInput.isEmpty()) {
                        lastSeed = Long.parseLong(seedInput);
                        renderer.initialize(width, height);
                        Game game = new Game(width, height, lastSeed);
                        game.generateBaseWorld();
                        game.keyboardInputForMovement();
                        renderer.renderFrame(game.getWorld());
                    }
                    break;
                }
                if (Character.isDigit(key)) {
                    seedInput += key;

                    StdDraw.clear();
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 25));
                    StdDraw.text(0.5, 0.8, "Enter Seed Followed with S:");
                    StdDraw.text(0.5, 0.7, seedInput);
                    StdDraw.show();
                }
            }
        }
    }

    public static void main(String[] args) {
        int width = 120;
        int height = 50;

        TERenderer renderer = new TERenderer();
        renderer.initialize(width, height);

        showMainMenu(width, height, renderer);
    }

    public static void showMainMenu(int width, int height, TERenderer renderer) {
        StdDraw.setCanvasSize(500, 500);

        // Title
        StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
        StdDraw.text(0.5, 0.8, "CS 61B: BYOW");

        // Sub-Titles
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.text(0.5, 0.6, "New Game (N)");
        StdDraw.text(0.5, 0.5, "Load Game (L)");
        StdDraw.text(0.5, 0.4, "Quit (Q)");
        StdDraw.show();

        // Starts, loads and quits
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (key == 'N' || key == 'n') {
                    enterSeed(width, height, renderer);
                    break;
                } else if (key == 'L' || key == 'l') {
                    loadGame();
                    Game game = new Game(width, height, lastSeed);
                    game.generateBaseWorld();
                    for (char characters : Game.movementFromLastGame) {
                        game.moveAvatarForChar(characters);
                    }
                    renderer.renderFrame(game.getWorld());
                    game.keyboardInputForMovement();
                    return;
                } else if (key == 'Q' || key == 'q') {
                    System.exit(0);
                }
            }
        }
    }

    public static void saveGame() {
        Out out = new Out(filename);
        out.println(lastSeed);
        for (char keyInputted : Game.movementFromLastGame) {
            out.print(keyInputted);
        }
        out.close();
    }

    public static void loadGame() {
        File file = new File(filename);
        if (file.exists()) {
            In in = new In(file);
            lastSeed = Long.parseLong(in.readLine());
            String stringOfKeys = in.readLine();
            Character[] arrayOfKeys = new Character[stringOfKeys.length()];
            Game.movementFromLastGame.clear();
            for (int i = 0; i < stringOfKeys.length(); i++) {
                Game.movementFromLastGame.add(stringOfKeys.charAt(i));
            }
            in.close();
        }
    }
}
