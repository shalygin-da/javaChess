package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int position;

    Move(final Board board,
         final Piece movedPiece,
         final int position) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.position = position;
    }

    public static final class OrdMove extends Move {

        public OrdMove(final Board board, final Piece movedPiece, final int position) {
            super(board, movedPiece, position);
        }
    }

    public static final class AtkMove extends Move {

        final Piece attackedPiece;

        public AtkMove(final Board board, final Piece movedPiece, final int position, final Piece attackedPiece) {
            super(board, movedPiece, position);
            this.attackedPiece = attackedPiece;
        }
    }

}
