package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int dest;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board,
         final Piece movedPiece,
         final int dest, boolean isFirstMove) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.dest = dest;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board,
         final int dest) {
        this.board = board;
        this.movedPiece = null;
        this.dest = dest;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        int ans = 1;
        ans = 31 * ans * this.dest;
        ans = 31 * ans * this.hashCode();
        ans = 31 * ans * this.movedPiece.getPosition();
        return ans;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Move)) return false;
        final Move otherMove = (Move) other;
        return getCurrentCoord() == otherMove.getCurrentCoord() &&
                getDest() == otherMove.getDest() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
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

        public OrdMove(final Board board, final Piece movedPiece, final int dest, final boolean isFirstMove) {
            super(board, movedPiece, dest, isFirstMove);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof OrdMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardUtils.getCoordAtPos(this.dest);
        }

    }

    public static class AtkMove extends Move {

        final Piece attackedPiece;

        public AtkMove(final Board board, final Piece movedPiece, final int dest, final Piece attackedPiece, final boolean isFirstMove) {
            super(board, movedPiece, dest, isFirstMove);
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

        public PawnMove(final Board board, final Piece movedPiece, final int dest, final boolean isFirstMove) {
            super(board, movedPiece, dest, isFirstMove);
        }

    }

    public static final class PawnAtkMove extends AtkMove {

        public PawnAtkMove(final Board board, final Piece movedPiece, final int dest, final Piece attackedPiece) {
            super(board, movedPiece, dest, attackedPiece, false);
        }

    }

    public static final class PawnEnPassantMove extends AtkMove {

        public PawnEnPassantMove(final Board board, final Piece movedPiece, final int dest, final Piece attackedPiece) {
            super(board, movedPiece, dest, attackedPiece, false);
        }

    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board, final Piece movedPiece, final int dest, final boolean isFirstMove) {
            super(board, movedPiece, dest, isFirstMove);
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
                          final int rookdest,
                          final boolean isFirstMove) {
            super(board, movedPiece, dest, isFirstMove);
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
                                  final int rookdest,
                                  final boolean isFirstMove) {
            super(board, movedPiece, dest, rook, rookStart, rookdest, isFirstMove);
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
                                   final int rookdest,
                                   final boolean isFirstMove) {
            super(board, movedPiece, dest, rook, rookStart, rookdest, isFirstMove);
        }

        @Override
        public String toString() {
            return "0-0-0";
        }

    }

    static class NullMove extends Move {

        public NullMove() {
            super(null, null, -1, false);
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
