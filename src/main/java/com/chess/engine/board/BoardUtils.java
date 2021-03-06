package com.chess.engine.board;

import java.util.Map;

public class BoardUtils {

    private static final String[] ALGEBRAIC_NOTATION = initAlgNotation();

    private static String[] initAlgNotation() {
        return null;
    }

    private static final Map<String, Integer> POSITION_TO_COORD = initPosToCoord();

    private static Map<String, Integer> initPosToCoord() {
        return null;
    }

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

    public static final boolean[] EIGHTH_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND_RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);


    public static final int NUM_TILES = 64;
    public static final int NUM_TILE_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("An attempt of initiating BoardUtils was made, however, it cannot be initiated.");
    }
    public static boolean isValidCoord(int coord) {
        return coord >= 0 && coord < 64;
    }

    public static int getCoordAtPos(int position) {
        return POSITION_TO_COORD.get(position);
    }

    public static String getPosAtCoord(int coord) {
        return ALGEBRAIC_NOTATION[coord];
    }
}
