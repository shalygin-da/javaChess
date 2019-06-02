package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int dest;

    public static final Move NULL_MOVE = new NullMove();

    Move(final Board board,
         final Piece movedPiece,
         final int dest) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.dest = dest;
    }

    public int getCurrentCoord() {
        return this.movedPiece.getPosition();
    }

    public int getDest() {
        return this.dest;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.currentPlayer().getAlivePieces()){
            if (!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.currentPlayer().getOpponent().getAlivePieces()){
            builder.setPiece(piece);
        }
        //move the needed piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMover(this.board.currentPlayer().getOpponent().getTeam());
        return builder.build();
    }

    public static final class OrdMove extends Move {

        public OrdMove(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

    }

    public static class AtkMove extends Move {

        final Piece attackedPiece;

        public AtkMove(final Board board, final Piece movedPiece, final int dest, final Piece attackedPiece) {
            super(board, movedPiece, dest);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class PawnMove extends Move {

        public PawnMove(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

    }

    public static final class PawnAtkMove extends AtkMove {

        public PawnAtkMove(final Board board, final Piece movedPiece, final int dest, final Piece attackedPiece) {
            super(board, movedPiece, dest, attackedPiece);
        }

    }

    public static final class PawnEnPassantMove extends AtkMove {

        public PawnEnPassantMove(final Board board, final Piece movedPiece, final int dest, final Piece attackedPiece) {
            super(board, movedPiece, dest, attackedPiece);
        }

    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

    }

    static abstract class CastleMove extends Move {

        public CastleMove(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

    }

    static abstract class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

    }

    static abstract class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

    }

    static class NullMove extends Move {

        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("null move cannot be executed");
        }

    }

    public static class MoveFactory {

        private MoveFactory() {
            throw new RuntimeException("This class is not instantiable");
        }

        public static Move createMove(final Board board, final int currentCoord, final int dest) {
            for (final Move move: board.getAllLegalMoves())
            if (move.getCurrentCoord() == currentCoord && move.getDest() == dest) return move;
            return NULL_MOVE;
        }
    }

}
