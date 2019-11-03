package levelGenerators.JariwalaLuccaGenerator;

import engine.core.MarioLevelGenerator;
import engine.core.MarioLevelModel;
import engine.core.MarioTimer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LevelGenerator implements MarioLevelGenerator {

    private double HI_GROUND_CHANCE = 0.0;
    private double PLAT_CHANCE = 0.1;
    private double GAP_CHANCE = 0.2;
    private double HILL_CHANCE = 0.1;
    private double LOWPIPE_CHANCE = 0.2;
    private double HIGHPIPE_CHANCE = 0.2;
    private double LOWPIPEPLANT_CHANCE = 0.1;
    private double HIGHPIPEPLANT_CHANCE = 0.1;
    private double GOOBA1_CHANCE = 0.2;
    private double GOOBA2_CHANCE = 0.2;
    private double KOOPA1_CHANCE = 0.2;
    private double KOOPA2_CHANCE = 0.2;
    private double KOOPA3_CHANCE = 0.2;
    private double MIX1_CHANCE = 0.2;
    private double MIX2_CHANCE = 0.2;
    private double EMPTY_CHANCE = 0.2;
    private double RAMP_CHANCE = 0.1;
    private double START_CHANCE = 0.0;


    HashMap<Chunk, HashMap> transitionMaps = new HashMap<>();



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
            "XXXX",
            "XXXX"
    });

    private final String PLAT = "" +
            "-SSSSS-" + "\n" +
            "-------" + "\n" +
            "-------" + "\n" +
            "-------" + "\n" +
            "-------";

    private final String GAP = "---";

    private final String HILL = "" +
            "----#--#----" + "\n" +
            "---##--##---" + "\n" +
            "--###--###--" + "\n" +
            "-####--####-" + "\n" +
            "#####--#####" + "\n" +
            "XXXXX--XXXXX" + "\n" +
            "XXXXX--XXXXX";

    private final String FLAG = "" +
            "-----------" + "\n" +
            "-F---------" + "\n" +
            "-#---------" + "\n" +
            "XXXXXXXXXXXX" + "\n" +
            "XXXXXXXXXXXX";

    private final String START = "" +
            "---" + "\n" +
            "---" + "\n" +
            "-M-" + "\n" +
            "XXX" + "\n" +
            "XXX";

    private final String LOWPIPE = "" +
            "----" + "\n" +
            "-tt-" + "\n" +
            "-tt-" + "\n" +
            "XXXX" + "\n" +
            "XXXX";

    private final String HIGHPIPE = "" +
            "-tt-" + "\n" +
            "-tt-" + "\n" +
            "-tt-" + "\n" +
            "XXXX" + "\n" +
            "XXXX";

    private final String LOWPIPEPLANT = "" +
            "----" + "\n" +
            "-TT-" + "\n" +
            "-TT-" + "\n" +
            "XXXX" + "\n" +
            "XXXX";

    private final String HIGHPIPEPLANT = "" +
            "-TT-" + "\n" +
            "-TT-" + "\n" +
            "-TT-" + "\n" +
            "XXXX" + "\n" +
            "XXXX";

    private final String GOOMBA1 = "" +
            "-------" + "\n" +
            "-------" + "\n" +
            "-g--g--" + "\n" +
            "XXXXXXX" + "\n" +
            "XXXXXXX";

    private final String GOOMBA2 = "" +
            "--SSS--" + "\n" +
            "-------" + "\n" +
            "-g-g-g-" + "\n" +
            "XXXXXXX" + "\n" +
            "XXXXXXX";

    private final String KOOPA1 = "" +
            "-------" + "\n" +
            "-------" + "\n" +
            "-r-g-g-" + "\n" +
            "XXXXXXX" + "\n" +
            "XXXXXXX";

    private final String KOOPA2 = "" +
            "-------" + "\n" +
            "-------" + "\n" +
            "--k-k--" + "\n" +
            "XXXXXXX" + "\n" +
            "XXXXXXX";

    private final String KOOPA3 = "" +
            "---R---" + "\n" +
            "--SSS--" + "\n" +
            "-------" + "\n" +
            "XXXXXXX" + "\n" +
            "XXXXXXX";

    private final String MIX1 = "" +
            "---!--R---" + "\n" +
            "-SS---SS--" + "\n" +
            "-----r---o" + "\n" +
            "XXXXXXXXXX" + "\n" +
            "XXXXXXXXXX";

    private final String MIX2 = "" +
            "----------" + "\n" +
            "-oo------!" + "\n" +
            "--g-%%--g-" + "\n" +
            "XXX-||-XXX" + "\n" +
            "XXX-||-XXX";

    private final String EMPTY = "" +
            "----------" + "\n" +
            "----------" + "\n" +
            "----------" + "\n" +
            "XXXXXXXXXX" + "\n" +
            "XXXXXXXXXX";

    private final String RAMP = "" +
            "--------##" + "\n" +
            "-------###" + "\n" +
            "------####" + "\n" +
            "XXXXXXXXXX" + "\n" +
            "XXXXXXXXXX";

    private final String[] LEVEL_CHUNKS = {
            HI_GROUND,
            PLAT,
            GAP,
            HILL,
            LOWPIPE,
            HIGHPIPE,
            LOWPIPEPLANT,
            HIGHPIPEPLANT,
            GOOMBA1,
            GOOMBA2,
            KOOPA1,
            KOOPA2,
            KOOPA3,
            MIX1,
            MIX2,
            EMPTY,
            RAMP,
            START
    };

    public void createHash() {
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
        cursorPos = x;
    }

    /*
    public static String[][] readFile(String filename) throws Exception {
        BufferedReader fileInput = new BufferedReader(new FileReader("lvl-1.txt"));
        fileInput.readLine().length();


        String[][] s = new String[fileInput.readLine().length()][5];
        String line;
        while ((line = fileInput.readLine()) != null) {

        }
        fileInput.close();
        return s;
    }
*/
    public static ArrayList<String> readFileList(String filepath) throws Exception {
        Scanner scanner = new Scanner(new File(filepath));
        ArrayList<String> lvlRows = new ArrayList<String>();
        while (scanner.hasNext()) {
            lvlRows.add(scanner.next());
        }
        scanner.close();
        return lvlRows;
    }

    @Override
    public String getGeneratedLevel(MarioLevelModel model, MarioTimer timer) {
        createHash();

        // Store the given model so other methods have access
        this.marioLevelModel = model;
        Chunk currentChunk = START;

        // Set everything in the map to empty
        model.setRectangle(0, 0, model.getWidth(), model.getHeight(), MarioLevelModel.EMPTY);

        addChunkToMap(currentChunk);

        while (cursorPos < model.getWidth()-8) {
            currentChunk = getNextChunk(currentChunk);
            addChunkToMap(currentChunk);
        }

        addChunkToMap(FLAG);

        System.out.println(model.getMap());

        return model.getMap();
    }

    @Override
    public String getGeneratorName() {
        return "JariwalaLuccaGenerator";
    }

}

/**
 * A class for representing chunks
 */
class Chunk {

    // All the blocks of the chunk (i.e. block[row][col])
    private char[][] blocks;

    public Chunk(char[][] blocks) {
        this.blocks = blocks;
    }

    public Chunk(String[] rows) {
        int width = rows[0].length();
        int height = rows.length;

        blocks = new char[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                blocks[x][y] = rows[y].charAt(x);
            }
        }
    }

    public Chunk(ArrayList<String> rows) {
        new Chunk((String[])(rows.toArray()));
    }

    public int getWidth() {
        return blocks.length;
    }

    public int getHeight() {
        return blocks[0].length;
    }

    /**
     * Get the blocks stored in the chunk itself as a 2D array
     * @return A 2D array of chars representing the blocks in the chunk
     */
    public char[][] getBlocks() {
        return blocks;
    }

    /**
     * Get a specified column of blocks in the chunk
     * @param x The x-coordinate of the column
     * @return The column at this x-coordinate, or null
     * if the column does not exist
     */
    public char[] getColumn(int x) {
        if (x < 0 || x >= blocks.length) {
            return null;
        }
        return blocks[x];
    }

    @Override
    public int hashCode() {

        return toString().hashCode();
    }

    @Override
    public String toString() {
        String blocksAsStr = "";

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                blocksAsStr += blocks[x][y];
            }
            blocksAsStr += "\n";
        }

        return blocksAsStr;
    }

}
