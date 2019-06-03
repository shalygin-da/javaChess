package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int ans = 1;
        ans = prime * ans * this.dest;
        ans = prime * ans * this.hashCode();
        return ans;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Move)) return false;
        final Move otherMove = (Move) other;
        return getDest() == otherMove.getDest() && getMovedPiece().equals(otherMove.getMovedPiece());
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

    public boolean isAtk() {
        return false;
    }

    public boolean isCastling() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
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
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) return true;
            if (!(other instanceof AtkMove)) return false;
            final AtkMove otherAtkMove = (AtkMove) other;
            return super.equals(otherAtkMove) && getAttackedPiece().equals(otherAtkMove.getAttackedPiece());
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAtk() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
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

        @Override
        public Board execute() {

            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getAlivePieces()) {
                if (!this.movedPiece.equals(piece)) builder.setPiece(piece);
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getAlivePieces()) builder.setPiece(piece);
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassant(movedPawn);
            builder.setMover(this.board.currentPlayer().getOpponent().getTeam());
            return builder.build();

        }
    }

    static abstract class CastleMove extends Move {

        protected final Rook rook;
        protected final int rookStart;
        protected final int rookdest;

        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int dest,
                          final Rook rook,
                          final int rookStart,
                          final int rookdest) {
            super(board, movedPiece, dest);
            this.rook = rook;
            this.rookStart = rookStart;
            this.rookdest = rookdest;
        }

        public Rook getRook() {
            return this.rook;
        }

        @Override
        public boolean isCastling() {
            return true;
        }

        @Override
        public Board execute() {

            final Board.Builder builder = new Board.Builder();
            for (final Piece piece: this.board.currentPlayer().getAlivePieces()) {
                if (!this.movedPiece.equals(piece) && !this.rook.equals(piece)) builder.setPiece(piece);
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getAlivePieces()) builder.setPiece(piece);
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.rook.getTeam(), this.rookdest));
            builder.setMover(this.board.currentPlayer().getOpponent().getTeam());
            return builder.build();
        }
    }

    public static class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int dest,
                                  final Rook rook,
                                  final int rookStart,
                                  final int rookdest) {
            super(board, movedPiece, dest, rook, rookStart, rookdest);
        }

        @Override
        public String toString() {
            return "0-0";
        }

    }

    public static class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int dest,
                                   final Rook rook,
                                   final int rookStart,
                                   final int rookdest) {
            super(board, movedPiece, dest, rook, rookStart, rookdest);
        }

        @Override
        public String toString() {
            return "0-0-0";
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
