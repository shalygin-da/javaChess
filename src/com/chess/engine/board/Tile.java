package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {


    protected final int tileCoord;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createTiles();

    private static Map<Integer, EmptyTile> createTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return emptyTileMap;
    }

    public static Tile create(final int tileCoord, final Piece piece) {

        return piece != null ? new OccupiedTile(tileCoord, piece) : EMPTY_TILES_CACHE.get(tileCoord);
    }

    private Tile(int tileCoords) {
        this.tileCoord = tileCoords;
    }

    public abstract  boolean isOccupied(); //defined in EmptyTile & OccupiedTile

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int coord) {
            super(coord);
        }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

    }

    public static final class OccupiedTile extends Tile {

        private final Piece piece;

        private OccupiedTile(int coord, Piece piece) {
            super(coord);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return piece;
        }
    }

}
