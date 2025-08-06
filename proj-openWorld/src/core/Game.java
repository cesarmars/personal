package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Game {
    private final int width;
    private final int height;
    private final Random rand;
    public TETile[][] world;
    private final Set<Position> usedTiles = new HashSet<>();
    private final List<Position> roomCenters = new ArrayList<>();
    private Position avatarPos;
    public static List<Character> movementFromLastGame = new ArrayList<>();
    public int coinsHolder;

    public Game(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.world = new TETile[width][height];
        this.rand = new Random(seed);
    }

    private static void fillWithNothing(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }


    public void markRoomTiles(int x, int y, int w, int h) {
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                usedTiles.add(new Position(i, j));
            }
        }
    }

    public void addRoom(int x, int y, int w, int h) {
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                if (i == x || i == x + w - 1 || j == y || j == y + h - 1) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
        markRoomTiles(x, y, w, h);
        int centerX = x + w / 2;
        int centerY = y + h / 2;
        roomCenters.add(new Position(centerX, centerY));
    }

    public void connectAllRooms() {
        for (int i = 1; i < roomCenters.size(); i++) {
            Position firstPosition = roomCenters.get(i - 1);
            Position secondPosition = roomCenters.get(i);
            drawLHallWay(firstPosition.x_axis, firstPosition.y_axis, secondPosition.x_axis, secondPosition.y_axis);
        }
    }

    public boolean isRoomOverlapping(int x, int y, int w, int h) {
        for (int i = x - 1; i <= x + w; i++) {
            for (int j = y - 1; j <= y + h; j++) {
                Position p = new Position(i, j);
                if (usedTiles.contains(p)) return true;
            }
        }
        return false;
    }

    public void generateBaseWorld() {
        fillWithNothing(world);
        generateRooms(30);
        connectAllRooms();
        coins(100);
        if (!roomCenters.isEmpty()) {
            avatarPos = roomCenters.get(0);
            world[avatarPos.x_axis][avatarPos.y_axis] = Tileset.AVATAR;
        }
    }

    private void generateRooms(int count) {
        for (int i = 0; i < count; i++) {
            int w = 4 + rand.nextInt(8); // width 4–8
            int h = 4 + rand.nextInt(6); // height 4–8
            int x = rand.nextInt(width - w - 1);
            int y = rand.nextInt(height - h - 1);
            if (!isRoomOverlapping(x, y, w, h)) {
                addRoom(x, y, w, h);
            }
        }
    }

    public char moveAvatar(int changeInX, int changeInY) {
        int newX = avatarPos.x_axis + changeInX;
        int newY = avatarPos.y_axis + changeInY;
        if (newX < 0 || newX >= width || newY < 0 || newY >= height){
            return 0;
        }
        TETile nextTile = world[newX][newY];
        if (!nextTile.equals(Tileset.FLOOR) && !nextTile.equals(Tileset.COIN)){
            return 0;
        }
        if (nextTile.equals(Tileset.COIN)) {
            coinsHolder--;
        }
        world[avatarPos.x_axis][avatarPos.y_axis] = Tileset.FLOOR;
        avatarPos = new Position(newX, newY);
        world[newX][newY] = Tileset.AVATAR;
        return 0;
    }

    public void moveAvatarForChar(char c) {
        switch (c) {
            case 'w':
                moveAvatar(0, 1);
                break;
            case 's':
                moveAvatar(0, -1);
                break;
            case 'a':
                moveAvatar(-1, 0);
                break;
            case 'd':
                moveAvatar(1, 0);
                break;
            default:
                break;
        }
    }

    public void keyboardInputForMovement() {
        final int viewWidth = 80;
        final int viewHeight = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        boolean colonIsPressed = false;
        while(true){
            while(StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);
                if (colonIsPressed == false) {
                    if (c == ':') {
                        colonIsPressed = true;
                    } else {
                        switch(c){
                            case 'w':
                                movementFromLastGame.add('w');
                                moveAvatar(0,1);
                                break;
                            case 's':
                                movementFromLastGame.add('s');
                                moveAvatar(0,-1);
                                break;
                            case 'a':
                                movementFromLastGame.add('a');
                                moveAvatar(-1,0);
                                break;
                            case 'd':
                                movementFromLastGame.add('d');
                                moveAvatar(1,0);
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (colonIsPressed && (c == 'Q' || c == 'q')) {
                    Main.saveGame();
                    System.exit(0);
                }
            }

            int worldWidth = world.length;
            int worldHeight = world[0].length;
            int cameraX = Math.max(0, Math.min(avatarPos.x_axis - viewWidth / 2, worldWidth - viewWidth));
            int cameraY = Math.max(0, Math.min(avatarPos.y_axis - viewHeight / 2, worldHeight - viewHeight));
            StdDraw.clear(Color.BLACK);
            for (int x = 0; x < viewWidth; x++) {
                for (int y = 0; y < viewHeight; y++) {
                    int worldX = cameraX + x;
                    int worldY = cameraY + y;
                    if (worldX >= 0 && worldX < worldWidth && worldY >= 0 && worldY < worldHeight) {
                        world[worldX][worldY].draw(x, y);
                    }
                }
            }
            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();
            String hudDescription = " ";
            TETile tileOfInterest = world[mouseX][mouseY];
            if (mouseX >= 0 && mouseX < width && mouseY >= 0 && mouseY < height) {
                hudDescription = tileOfInterest.description();
            }
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(width / 2.0, height - 1, hudDescription);
            StdDraw.show();
            StdDraw.pause(40);
        }
    }



    public void drawLHallWay(int x1, int y1, int x2, int y2){
        if (rand.nextBoolean()) {
            drawHorizontalHallway(x1, x2, y1);
            drawVerticalHallway(y1, y2, x2);
        } else {
            drawVerticalHallway(y1, y2, x1);
            drawHorizontalHallway(x1, x2, y2);
        }
    }


    public void drawHorizontalHallway(int x1, int x2, int y) {
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);

        for (int x = start; x <= end; x++) {
            world[x][y] = Tileset.FLOOR;
            if (y > 0 && !world[x][y - 1].equals(Tileset.FLOOR)) {
                world[x][y - 1] = Tileset.WALL;
            }
            if (y < height - 1 && !world[x][y + 1].equals(Tileset.FLOOR)) {
                world[x][y + 1] = Tileset.WALL;
            }
        }
    }

    public void drawVerticalHallway(int y1, int y2, int x) {
        int start = Math.min(y1, y2);
        int end = Math.max(y1, y2);

        for (int y = start; y <= end; y++) {
            world[x][y] = Tileset.FLOOR;
            if (x > 0 && !world[x - 1][y].equals(Tileset.FLOOR)) {
                world[x - 1][y] = Tileset.WALL;
            }
            if (x < width - 1 && !world[x + 1][y].equals(Tileset.FLOOR)) {
                world[x + 1][y] = Tileset.WALL;
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

    public void coins(int num_of_coins) {
        Random rand = new Random();
        coinsHolder = num_of_coins;
        int i = 0;
        while (i < num_of_coins) {
            int x = rand.nextInt(world.length);
            int y = rand.nextInt(world[0].length);

            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.COIN;
                i ++;
            }
        }
    }
}
