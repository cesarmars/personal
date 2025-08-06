package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

public class World {
//    private static final int Width = 30;
//    private static final int Height = 20;
//    private static final int W_W = 30;
//    private static final int W_H = 15;

    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */


    private static void drawSquare(TETile[][] world, int startX, int startY, int size, TETile tile) {
        int endX = startX + size - 1;
        int endY = startY + size - 1;
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                if (i >= 0 && i < world.length && j >= 0 && j < world[0].length) {
                    world[i][j] = tile;
                }
            }
        }
    }

    private static void displaySquareCount(int count) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(1, 20 - 2, "Squares drawn: " + count);
        StdDraw.show();
    }
}
