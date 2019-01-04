package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world with tesselating hexagons.
 */
public class MyWorld {

    /**
     * Represents a point on the Cartesian coordinate plane.
     */
    private static class Position {
        private int x;
        private int y;
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    } // End Position class

    // WIDTH and HEIGHT scale with SIDELENGTH (of a hexagon)
    private static final int SIDELENGTH = 4;
    private static final int WIDTH = 11 * SIDELENGTH - 6;
    private static final int HEIGHT = 10 * SIDELENGTH;

    // Random seed
    private static final long SEED = 892382931;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {

        // Tile rendering engine initialized with window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // Fill world with tesselating hexagons and render it to the screen
        fillWithHexagons(world);
        ter.renderFrame(world);

    } // End main

    /**
     * Adds a hexagon of side length s to the given position in the world.
     * @param sideLength side length of hexagon
     * @param position position representing the hexagon's bottom left corner
     * @param world
     * @param tileType TETile to be used
     */
    public static void addHexagon(int sideLength, Position position,
                                  TETile[][] world, TETile tileType) {

        // Remember initial position
        int initX = position.x;
        int initY = position.y;

        // Generate bottom half of hexagon in an upward manner
        for (int i = 0; i < sideLength; i++) {
            int numTilesInRow = sideLength + 2 * i;
            addHexagonRow(numTilesInRow, position, world, tileType);
            position.x -= 1;
            position.y += 1;
        }

        // Generate top half of hexagon in an upward manner
        position.x += 1; // Go to appropriate starting position
        for (int i = sideLength - 1; i >= 0; i--) {
            int numTilesInRow = sideLength + 2 * i;
            addHexagonRow(numTilesInRow, position, world, tileType);
            position.x += 1;
            position.y += 1;
        }

        // Reset position
        position.x = initX;
        position.y = initY;

    } // End addHexagon

    /**
     * Adds a row to a hexagon.
     * @param numTilesInRow the number of tiles in the hexagon's current row
     * @param position position representing the start of the current row
     * @param world
     * @param tileType TETile to be used
     */
    private static void addHexagonRow(int numTilesInRow, Position position,
                                      TETile[][] world, TETile tileType) {

        // Remember initial position
        int initialX = position.x;

        // Add row to hexagon
        for (int i = 0; i < numTilesInRow; i++) {
            world[position.x][position.y] = tileType;
            position.x += 1;
        }

        // Reset position
        position.x = initialX;

    } // End addhexagonRow

    /**
     * Randomly generates a type of TETile.
     * Possible tiles: WALL, FLOOR, GRASS, WATER, FLOWER, SAND, MOUNTAIN, TREE
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        switch(tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOOR;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.FLOWER;
            case 5: return Tileset.SAND;
            case 6: return Tileset.MOUNTAIN;
            case 7: return Tileset.TREE;
            default: return Tileset.TREE;
        }
    } // End generateTileType

    /**
     * Fills the world with tesselating hexagons, column by column.
     * The world will have 5 columns of hexagons, with each column
     * containing 3, 4, 5, 4, and 3 hexagons, respectively, for a
     * total of 19 hexagons. The columns are added from left to right.
     */
    public static void fillWithHexagons(TETile[][] world) {

        // Add column of 3 hexagons
        Position position = new Position(SIDELENGTH - 1, 2 * SIDELENGTH);
        addColumnOfHexagons(3, position, world);

        // Add column of 4 hexagons
        position.x += 2 * SIDELENGTH - 1;
        position.y -= SIDELENGTH;
        addColumnOfHexagons(4, position, world);

        // Add column of 5 hexagons
        position.x += 2 * SIDELENGTH - 1;
        position.y -= SIDELENGTH;
        addColumnOfHexagons(5, position, world);

        // Add column of 4 hexagons
        position.x += 2 * SIDELENGTH - 1;
        position.y += SIDELENGTH;
        addColumnOfHexagons(4, position, world);

        // Add column of 3 hexagons
        position.x += 2 * SIDELENGTH - 1;
        position.y += SIDELENGTH;
        addColumnOfHexagons(3, position, world);

    } // End fillWithHexagons

    /**
     * Adds a column of a given number of hexagons to the world.
     * The hexagons are added in an upward manner.
     * @param numHexagons the number of hexagons in the column
     * @param position position representing the bottom left corner
     *                 of the hexagon at the bottom of the column
     * @param world
     */
    private static void addColumnOfHexagons(int numHexagons, Position position,
                                            TETile[][] world) {

        // Remember initial position
        int initX = position.x;
        int initY = position.y;

        for (int i = 0; i < numHexagons; i++) {
            TETile tileType = randomTile();
            addHexagon(SIDELENGTH, position, world, tileType);
            position.y += SIDELENGTH * 2;
        }

        // Reset position
        position.x = initX;
        position.y = initY;

    } // End addColumnOfHexagons

} /** End MyWorld class */
