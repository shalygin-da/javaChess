package com.chess.engine.board;

public class BoardUtils {

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];
        do {
            column[columnNumber] = true;
            columnNumber += 8;
        } while (columnNumber <64);
        return column;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % NUM_TILE_PER_ROW != 0);
        return row;
    }
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);



    public static final int NUM_TILES = 64;
    public static final int NUM_TILE_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("An attempt of initiating BoardUtils was made, however, it cannot be initiated.");
    }
    public static boolean isValidCoord(int coord) {
        return coord >= 0 && coord < 64;
    }
}
