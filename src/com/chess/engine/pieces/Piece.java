package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int position;
    protected final Team team;
    protected final boolean isFirstMove;

    Piece(final int position, final Team team) {
        this.team = team;
        this.position = position;
        //todo first move
        this.isFirstMove = false;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Team getTeam() {
        return this.team;
    }

    public abstract Collection<Move> calcMoves(final Board board);

    public int getPosition() {
        return this.position;
    }

    public enum PieceType {

        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return pieceName;
        }
    }
}
