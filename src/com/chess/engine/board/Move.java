package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int dest;

    Move(final Board board,
         final Piece movedPiece,
         final int dest) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.dest = dest;
    }

    public int getDest() {
        return this.dest;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public abstract Board execute();

    public static final class OrdMove extends Move {

        public OrdMove(final Board board, final Piece movedPiece, final int dest) {
            super(board, movedPiece, dest);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getAlivePieces()){
                //todo hashcode & equals in Piece
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
    }

    public static final class AtkMove extends Move {

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

}
