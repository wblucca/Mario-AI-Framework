package levelGenerators.JariwalaLuccaGenerator;

import engine.core.MarioLevelGenerator;
import engine.core.MarioLevelModel;
import engine.core.MarioTimer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LevelGenerator implements MarioLevelGenerator {

    // Markov table
    HashMap<Chunk, HashMap<Chunk, Double>> transitionMaps = new HashMap<>();


    // Hand-made level chunks
    // Each occupies a rectangular area positioned along the bottom of the map
    // with air above it.

    /*
        X = ground
        S = Brick
        ! = coin power up block
        g = goomba
        k = koopa (green)
        r = koppa (red)
        R = koppa (red + flying)
        @ = power up
        t = pipe normal
        T = pipe with pirana plant
        # = block
        1 = ???
        M = mario
        F = Flag
        C = multi hit coin block?
        % = mushroom floating platform
        o = coin
    */

    private final Chunk HI_GROUND = new Chunk(new String[]{
            "----",
            "XXXX",
            "XXXX",
            "XXXX"
    });

    private final Chunk PLAT = new Chunk(new String[]{
            "-SSSSS-",
            "-------",
            "-------",
            "-------",
            "-------",
            "-------",
            "-------"
    });

    private final Chunk GAP = new Chunk(new String[] {
        "---"
    });

    private final Chunk HILL = new Chunk(new String[] {
            "-----#--#-----",
            "----##--##----",
            "---###--###---",
            "--####--####--",
            "-#####--#####-",
            "XXXXXX--XXXXXX"
    });

    private final Chunk FLAG = new Chunk(new String[] {
            "------------",
            "------------",
            "----------F-",
            "XXXXXXXXXXXX"
    });

    private final Chunk START = new Chunk(new String[] {
            "---",
            "---",
            "-M-",
            "XXX"
    });

    private final Chunk LOWPIPE = new Chunk(new String[] {
            "----",
            "-tt-",
            "-tt-",
            "XXXX"
    });

    private final Chunk HIGHPIPE = new Chunk(new String[] {
            "-tt-",
            "-tt-",
            "-tt-",
            "XXXX"
    });

    private final Chunk LOWPIPEPLANT = new Chunk(new String[] {
            "----",
            "-TT-",
            "-TT-",
            "XXXX"
    });

    private final Chunk HIGHPIPEPLANT = new Chunk(new String[] {
            "-TT-",
            "-TT-",
            "-TT-",
            "XXXX"
    });

    private final Chunk GOOMBA1 = new Chunk(new String[] {
            "-------",
            "-------",
            "-g--g--",
            "XXXXXXX"
    });

    private final Chunk GOOMBA2 = new Chunk(new String[] {
            "--SSS--",
            "-------",
            "-------",
            "-------",
            "-------",
            "-g-g-g-",
            "XXXXXXX"
    });

    private final Chunk KOOPA1 = new Chunk(new String[] {
            "-------",
            "-------",
            "-r-g-g-",
            "XXXXXXX"
    });

    private final Chunk KOOPA2 = new Chunk(new String[] {
            "-------",
            "-------",
            "--k-k--",
            "XXXXXXX"
    });

    private final Chunk KOOPA3 = new Chunk(new String[] {
            "---R---",
            "--SSS--",
            "-------",
            "-------",
            "-------",
            "-------",
            "XXXXXXX"
    });

    private final Chunk MIX1 = new Chunk(new String[] {
            "---!--R---",
            "-SS---SS--",
            "----------",
            "----------",
            "----------",
            "-----r---o",
            "XXXXXXXXXX"
    });

    private final Chunk MIX2 = new Chunk(new String[] {
            "----------",
            "-oo------!",
            "----------",
            "--g-%%--g-",
            "XXX-||-XXX"
    });

    private final Chunk EMPTY = new Chunk(new String[] {
            "----------",
            "----------",
            "----------",
            "XXXXXXXXXX"
    });

    private final Chunk RAMP = new Chunk(new String[] {
            "---------#",
            "--------##",
            "-------###",
            "------####",
            "XXXXXXXXXX"
    });

    public void createHandmadeHashmap() {
        // HI_GROUND chunk transition table
        HashMap<Chunk, Double> hiGroundTable = new HashMap<>();
        hiGroundTable.put(HILL, 0.3);
        hiGroundTable.put(GAP, 0.3);
        hiGroundTable.put(LOWPIPE, 0.3);
        hiGroundTable.put(LOWPIPEPLANT, 0.1);
        transitionMaps.put(HI_GROUND, hiGroundTable);

        // PLAT chunk transition table
        HashMap<Chunk, Double> platTable = new HashMap<>();
        platTable.put(MIX1, 0.3);
        platTable.put(EMPTY, 0.3);
        platTable.put(MIX2, 0.3);
        platTable.put(GOOMBA2, 0.1);
        transitionMaps.put(PLAT, platTable);

        // GAP chunk transition table
        HashMap<Chunk, Double> gapTable = new HashMap<>();
        gapTable.put(GOOMBA2, 0.3);
        gapTable.put(HILL, 0.3);
        gapTable.put(HIGHPIPEPLANT, 0.3);
        gapTable.put(KOOPA3, 0.1);
        transitionMaps.put(GAP, gapTable);

        // HILL chunk transition table
        HashMap<Chunk, Double> hillTable = new HashMap<>();
        hillTable.put(KOOPA3, 0.3);
        hillTable.put(GAP, 0.3);
        hillTable.put(LOWPIPE, 0.3);
        hillTable.put(LOWPIPEPLANT, 0.1);
        transitionMaps.put(HILL, hillTable);

        // LOWPIPE chunk transition table
        HashMap<Chunk, Double> lowPipeTable = new HashMap<>();
        lowPipeTable.put(HILL, 0.3);
        lowPipeTable.put(GAP, 0.3);
        lowPipeTable.put(HIGHPIPEPLANT, 0.3);
        lowPipeTable.put(GOOMBA1, 0.1);
        transitionMaps.put(LOWPIPE, lowPipeTable);

        // HIGHPIPE chunk transition table
        HashMap<Chunk, Double> highPipeTable = new HashMap<>();
        highPipeTable.put(HILL, 0.3);
        highPipeTable.put(GAP, 0.3);
        highPipeTable.put(LOWPIPE, 0.3);
        highPipeTable.put(LOWPIPEPLANT, 0.1);
        transitionMaps.put(HIGHPIPE, highPipeTable);

        // LOWPIPEPLANT chunk transition table
        HashMap<Chunk, Double> lowPipePlantTable = new HashMap<>();
        lowPipePlantTable.put(RAMP, 0.3);
        lowPipePlantTable.put(GAP, 0.3);
        lowPipePlantTable.put(GOOMBA2, 0.3);
        lowPipePlantTable.put(KOOPA3, 0.1);
        transitionMaps.put(LOWPIPEPLANT, lowPipePlantTable);

        // HIGHPIPEPLANT chunk transition table
        HashMap<Chunk, Double> highPipePlantTable = new HashMap<>();
        highPipePlantTable.put(HILL, 0.3);
        highPipePlantTable.put(GAP, 0.3);
        highPipePlantTable.put(MIX1, 0.3);
        highPipePlantTable.put(LOWPIPEPLANT, 0.1);
        transitionMaps.put(HIGHPIPEPLANT, highPipePlantTable);

        // GOOMBA1 chunk transition table
        HashMap<Chunk, Double> goomba1Table = new HashMap<>();
        goomba1Table.put(PLAT, 0.3);
        goomba1Table.put(MIX2, 0.3);
        goomba1Table.put(LOWPIPE, 0.3);
        goomba1Table.put(GAP, 0.1);
        transitionMaps.put(GOOMBA1, goomba1Table);

        // GOOMBA2 chunk transition table
        HashMap<Chunk, Double> goomba2Table = new HashMap<>();
        goomba2Table.put(HILL, 0.3);
        goomba2Table.put(GAP, 0.3);
        goomba2Table.put(LOWPIPE, 0.3);
        goomba2Table.put(LOWPIPEPLANT, 0.1);
        transitionMaps.put(GOOMBA2, goomba2Table);

        // KOOPA1 chunk transition table
        HashMap<Chunk, Double> koopaTable = new HashMap<>();
        koopaTable.put(HILL, 0.3);
        koopaTable.put(PLAT, 0.2);
        koopaTable.put(GAP, 0.1);
        koopaTable.put(LOWPIPE, 0.2);
        koopaTable.put(HIGHPIPE, 0.2);
        transitionMaps.put(KOOPA1, koopaTable);

        // KOOPA2 chunk transition table
        transitionMaps.put(KOOPA2, koopaTable);

        // KOOPA3 chunk transition table
        transitionMaps.put(KOOPA3, koopaTable);

        // MIX1 chunk transition table
        HashMap<Chunk, Double> mixTable = new HashMap<>();
        mixTable.put(EMPTY, 0.2);
        mixTable.put(HI_GROUND, 0.2);
        mixTable.put(PLAT, 0.2);
        mixTable.put(GAP, 0.3);
        mixTable.put(LOWPIPEPLANT, 0.1);
        transitionMaps.put(MIX1, mixTable);

        // MIX2 chunk transition table
        transitionMaps.put(MIX2, mixTable);

        // EMPTY chunk transition table
        HashMap<Chunk, Double> emptyTable = new HashMap<>();
        emptyTable.put(GOOMBA1, 0.1);
        emptyTable.put(GOOMBA2, 0.1);
        emptyTable.put(KOOPA1, 0.1);
        emptyTable.put(KOOPA2, 0.1);
        emptyTable.put(KOOPA3, 0.1);
        emptyTable.put(LOWPIPEPLANT, 0.1);
        emptyTable.put(HIGHPIPEPLANT, 0.1);
        emptyTable.put(GAP, 0.3);
        transitionMaps.put(EMPTY, emptyTable);

        // RAMP chunk transition table
        HashMap<Chunk, Double> rampTable = new HashMap<>();
        rampTable.put(GOOMBA1, 0.1);
        rampTable.put(GOOMBA2, 0.1);
        rampTable.put(KOOPA1, 0.2);
        rampTable.put(KOOPA2, 0.2);
        rampTable.put(KOOPA3, 0.1);
        rampTable.put(GAP, 0.3);
        transitionMaps.put(RAMP, rampTable);

        // START chunk transition table
        HashMap<Chunk, Double> startTable = new HashMap<>();
        startTable.put(EMPTY, 1.0);
        transitionMaps.put(START, startTable);
    }


    private void addChunkPairToHash(Chunk prev, Chunk next) {
        if(transitionMaps.containsKey(prev)) {
            if (transitionMaps.get(prev).containsKey(next)) {
                Double newval = transitionMaps.get(prev).get(next);
                transitionMaps.get(prev).replace(next, newval + 1.0);
            }
            else {
                transitionMaps.get(prev).put(next, 0.0);
            }
        }
        else {
            HashMap<Chunk, Double> inner = new HashMap<>();
            inner.put(next, 1.0);
            transitionMaps.put(prev, inner);
        }

    }

    // The level model for this level
    private MarioLevelModel marioLevelModel;

    // The next x-coordinate to write to during generation
    private int cursorPos = 0;

    /**
     * Selects a chunk based on the given previous chunk
     * @param lastChunk The current chunk to base the next one on
     * @return A String representing the selected chunk
     */
    private Chunk getNextChunk(Chunk lastChunk) {
        // In case of sink code, switch to an already existing chunk
        if(!transitionMaps.containsKey(lastChunk)) {
            for ( Chunk chunk : transitionMaps.keySet() ) {
                lastChunk = chunk;
                break;
            }
        }

        // Map of weights of next chunks
        HashMap<Chunk, Double> weights = transitionMaps.get(lastChunk);

        // Get total weight of all choices
        double weightSum = 0;
        for (Double weight : weights.values()) {
            weightSum += weight;
        }

        // Pick a random number [0, weightSum)
        double rand = weightSum * Math.random();
        double cumulativeWeight = 0;

        // Select chunk based on random value
        for (Chunk chunk : weights.keySet()) {
            // Accumulate weight of next chunk
            cumulativeWeight += weights.get(chunk);

            if (rand <= cumulativeWeight) {
                // Found randomly selected chunk
                return chunk;
            }
        }

        // Shouldn't get here unless something went wrong in the accumulate weight loop
        return null;
    }

    /**
     * Iterates through the characters of the string chunk and add them as
     * blocks/enemies to the level model's map
     * @param chunk The string that describes a chunk of blocks to add
     */
    private void addChunkToMap(Chunk chunk) {
        // Coords to write character to in map
        int x = cursorPos;
        int y = marioLevelModel.getHeight() - chunk.getHeight();

        // Iterate over chunk, one char at a time
        char[][] blocks = chunk.getBlocks();
        for (int i = 0; i < chunk.getWidth(); i++) {
            for (int j = 0; j < chunk.getHeight(); j++) {
                marioLevelModel.setBlock(x + i, y + j, blocks[i][j]);
            }
        }

        // Update generation cursor position
        cursorPos += chunk.getWidth();
    }

    public static ArrayList<String> readFileList(String filepath) throws Exception {
        Scanner scanner = new Scanner(new File(filepath));
        ArrayList<String> lvlRows = new ArrayList<String>();
        while (scanner.hasNext()) {
            lvlRows.add(scanner.next());
        }
        scanner.close();
        return lvlRows;
    }

    private ArrayList<Chunk> getUniqueChunks(Chunk level) {
        int colStart = 1;
        int colEnd = 1;
        int lvlWidth = level.getWidth();

        ArrayList<Chunk> uniqueChunks = new ArrayList<>();

        // Loop through the level entirety
        while (colStart < lvlWidth - 1 && colEnd < lvlWidth - 1) {
            // Advance until non-ground column is reached
            while (level.checkGround(colStart)) {
                colStart++;
            }

            colEnd = colStart;

            // Advance until two consecutive ground columns or end of level
            boolean twoGroundColumns = false;
            do {
                colEnd++;

                if (level.checkGround(colEnd) &&
                    level.checkGround(colEnd + 1)) {
                    twoGroundColumns = true;
                }
            } while (colEnd + 2 < lvlWidth && !twoGroundColumns);

            if (colEnd >= lvlWidth) {
                colEnd = lvlWidth - 1;
            }

            // Selected "unique" chunk columns' range: [colStart, colEnd)
            int chunkWidth = colEnd - colStart;

            // Create unique chunk from selection (+/- 1 column on either side)
            char[][] uniqueBlocks = new char[chunkWidth + 2][level.getHeight()];

            // Copy one column to the left
            copyCharArr(level.getColumn(colStart - 1), uniqueBlocks[0]);

            // Copy selected columns [colStart, colEnd)
            for (int i = 0; i < chunkWidth; i++) {
                copyCharArr(level.getColumn(colStart + i), uniqueBlocks[i + 1]);
            }

            // Copy one column to the right
            copyCharArr(level.getColumn(colEnd), uniqueBlocks[chunkWidth + 1]);

            // Add to the list if it does not contain a flag
            Chunk uniqueChunk = new Chunk(uniqueBlocks);
            if (!uniqueChunk.contains(MarioLevelModel.MARIO_EXIT)) {
                uniqueChunks.add(uniqueChunk);
            }
            colStart = colEnd + 2;
        }

        return uniqueChunks;
    }

    /**
     * Creates the Markov chain transition table using Chunks from the input level data
     */
    private void createAutomatedHashmap() {
        try {
            for (int i = 1; i < 1001; i++) {
                Chunk prevChunk = null;
                for (Chunk c : getUniqueChunks(new Chunk(readFileList("levels/notchParam/lvl-" + i + ".txt")))) {
                    // Add this (prev,next) pair to the Markov chain hashmaps
                    if (prevChunk != null) {
                        addChunkPairToHash(prevChunk, c);
                    }
                    prevChunk = c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getGeneratedLevel(MarioLevelModel model, MarioTimer timer) {
        // Store the given model so other methods have access
        this.marioLevelModel = model;

        createAutomatedHashmap();

        Chunk currentChunk = START;

        // Set everything in the map to empty
        model.setRectangle(0, 0, model.getWidth(), model.getHeight(), MarioLevelModel.EMPTY);

        addChunkToMap(currentChunk);

        while (cursorPos < model.getWidth()-8) {
            currentChunk = getNextChunk(currentChunk);
            addChunkToMap(currentChunk);
            System.out.println(currentChunk);
        }

        addChunkToMap(FLAG);

        System.out.println(model.getMap());

        return model.getMap();
    }

    @Override
    public String getGeneratorName() {
        return "JariwalaLuccaGenerator";
    }

    /**
     * Copies a source array of chars into a new given location in memory of
     * the same (or smaller) size
     * @param src The char array to copy
     * @param dest The char array reference to store the copy in
     */
    private void copyCharArr(char[] src, char[] dest) {
        int len = Math.min(src.length, dest.length);
        System.arraycopy(src, 0, dest, 0, len);
    }

}

/**
 * A class for representing chunks
 */
class Chunk {

    // All the blocks of the chunk (i.e. block[row][col])
    private char[][] blocks;

    /**
     * Constructs a Chunk from a 2D array of blocks
     * @param blocks 2D array of block chars (row, col)
     */
    public Chunk(char[][] blocks) {
        this.blocks = blocks;
    }

    /**
     * Constructs a Chunk from an array of strings representing the rows of the Chunk
     * @param rows Rows of blocks as an array of Strings
     */
    public Chunk(String[] rows) {
        initFromRows(rows);
    }

    /**
     * Constructs a Chunk from an ArrayList of strings representing the rows of the Chunk
     * @param rowsList Rowws of blocks as an ArrayList of Strings
     */
    public Chunk(ArrayList<String> rowsList) {
        String[] rowsArray = new String[rowsList.size()];
        rowsList.toArray(rowsArray);
        initFromRows(rowsArray);
    }

    /**
     * Sets blocks according to an array of strings representing the rows of the Chunk
     * @param rows Rows of blocks as an array of Strings
     */
    private void initFromRows(String[] rows) {
        int width = rows[0].length();
        int height = rows.length;

        blocks = new char[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                blocks[x][y] = rows[y].charAt(x);
            }
        }
    }

    /**
     * Returns the width of this Chunk (left-to-right)
     * @return The width of the Chunk
     */
    public int getWidth() {
        return blocks.length;
    }

    /**
     * Returns the height of this Chunk (top-to-bottom)
     * @return The height of the Chunk, or 0 if the Chunk has no width
     */
    public int getHeight() {
        if (getWidth() > 0) {
            return blocks[0].length;
        }

        return 0;
    }

    /**
     * Get the blocks stored in the chunk itself as a 2D array
     * @return A 2D array of chars representing the blocks in the chunk
     */
    char[][] getBlocks() {
        return blocks;
    }

    /**
     * Get a copy of the specified column of blocks in the chunk
     * @param x The x-coordinate of the column
     * @return The column at this x-coordinate, or null
     * if the column does not exist
     */
    char[] getColumn(int x) {
        if (x < 0 || x >= blocks.length) {
            return null;
        }
        return blocks[x];
    }

    /**
     * Checks a given column to see if it contains ground blocks or ground
     * and empty blocks
     * @param x The column of blocks to check
     * @return True if the column contains ONLY ground blocks or only
     * ground AND air blocks, false otherwise
     */
    boolean checkGround(int x) {
        char[] col = getColumn(x);
        boolean foundGround = false;

        // Column out of bounds
        if (col == null) {
            return false;
        }

        // Check one block at a time
        for (int i = 0; i < col.length; i++) {
            // Neither ground nor air
            if (col[i] != 'X' && col[i] != '-') {
                return false;
            }
            if (col[i] == 'X') {
                foundGround = true;
            }
        }

        // Contains only ground and air
        // Only return true if some ground was found
        return foundGround;
    }

    /**
     * Checks to see if this Chunk contains any of the specified block
     * @param blockType Char representing block type to check for
     * @return True if any block in the chunk is of the given type, false otherwise
     */
    boolean contains(char blockType) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (blocks[x][y] == blockType) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != Chunk.class) {
            return false;
        }

        return this.toString().equals(other.toString());
    }

    @Override
    public String toString() {
        String blocksAsStr = "";

        // Write out Chunk row by row, inserting newlines after each
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                blocksAsStr += blocks[x][y];
            }
            blocksAsStr += "\n";
        }

        return blocksAsStr;
    }

}
