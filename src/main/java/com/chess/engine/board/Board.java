package com.chess.engine.board;

import com.chess.engine.Team;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.*;

public class Board {

    private final List<Tile> board;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(final Builder builder) {
        this.board = createBoard(builder);

        this.whitePieces = calcAlivePieces(this.board, Team.WHITE);
        this.blackPieces = calcAlivePieces(this.board, Team.BLACK);

        final Collection<Move> whiteStartMoves = calcMoves(this.whitePieces);
        final Collection<Move> blackStartMoves = calcMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStartMoves, blackStartMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStartMoves, blackStartMoves);
        this.currentPlayer = builder.nextMover.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileInfo = this.board.get(i).toString();
            builder.append(String.format("%3s", tileInfo));
            if ((i + 1) % BoardUtils.NUM_TILE_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Collection<Piece> getWhitePieces() { return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    private Collection<Move> calcMoves(final Collection<Piece> pieces) {
        final List<Move> moves = new ArrayList<>();
        for(final Piece piece : pieces) {
            moves.addAll(piece.calcMoves(this));
        }
        return moves;
    }

    private static Collection<Piece> calcAlivePieces(final List<Tile> board, final Team team) {
        List<Piece> alivePieces = new ArrayList<>();
        for (final Tile tile : board) {
            if (tile.isOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getTeam() == team) {
                    alivePieces.add(piece);
                }
            }
        }
        return alivePieces;
    }


    private List<Tile> createBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        List<Tile> tileList = new ArrayList<Tile>();
        for (int i = 0; i< BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.create(i, builder.config.get(i));
            tileList.add(tiles[i]);
        }
        return tileList;
    }

    public static Board createStandartBoard() {
        final Builder builder = new Builder();
        builder.setPiece(new Rook(Team.BLACK, 0));
        builder.setPiece(new Knight(Team.BLACK, 1));
        builder.setPiece(new Bishop(Team.BLACK, 2));
        builder.setPiece(new Queen(Team.BLACK, 3));
        builder.setPiece(new King(Team.BLACK, 4));
        builder.setPiece(new Bishop(Team.BLACK, 5));
        builder.setPiece(new Knight(Team.BLACK, 6));
        builder.setPiece(new Rook(Team.BLACK, 7));
        builder.setPiece(new Pawn(Team.BLACK, 8));
        builder.setPiece(new Pawn(Team.BLACK, 9));
        builder.setPiece(new Pawn(Team.BLACK, 10));
        builder.setPiece(new Pawn(Team.BLACK, 11));
        builder.setPiece(new Pawn(Team.BLACK, 12));
        builder.setPiece(new Pawn(Team.BLACK, 13));
        builder.setPiece(new Pawn(Team.BLACK, 14));
        builder.setPiece(new Pawn(Team.BLACK, 15));

        builder.setPiece(new Pawn(Team.WHITE, 48));
        builder.setPiece(new Pawn(Team.WHITE, 49));
        builder.setPiece(new Pawn(Team.WHITE, 50));
        builder.setPiece(new Pawn(Team.WHITE, 51));
        builder.setPiece(new Pawn(Team.WHITE, 52));
        builder.setPiece(new Pawn(Team.WHITE, 53));
        builder.setPiece(new Pawn(Team.WHITE, 54));
        builder.setPiece(new Pawn(Team.WHITE, 55));
        builder.setPiece(new Rook(Team.WHITE, 56));
        builder.setPiece(new Knight(Team.WHITE, 57));
        builder.setPiece(new Bishop(Team.WHITE, 58));
        builder.setPiece(new King(Team.WHITE, 59));
        builder.setPiece(new Queen(Team.WHITE, 60));
        builder.setPiece(new Bishop(Team.WHITE, 61));
        builder.setPiece(new Knight(Team.WHITE, 62));
        builder.setPiece(new Rook(Team.WHITE, 63));

        builder.setMover(Team.WHITE);

        return builder.build();
    }

    public Tile getTile(final int tileCoords) {
        return board.get(tileCoords);
    }

    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public Player blackPlayer() {
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Iterable<Move> getAllLegalMoves() {
        return this.whitePlayer.getMoves();
    }

    public static class Builder {

        Map<Integer, Piece> config;
        Team nextMover;

        public Builder() {
            this.config = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.config.put(piece.getPosition(), piece);
            return this;
        }

        public Builder setMover(final Team nextMover) {
            this.nextMover = nextMover;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
