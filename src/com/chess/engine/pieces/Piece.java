package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int position;
    protected final Team team;
    protected final boolean isFirstMove;
    protected final PieceType pieceType;
    private final int cashedHashCode;

    Piece(final PieceType pieceType, final int position, final Team team, int hashCode) {
        this.team = team;
        this.pieceType = pieceType;
        this.position = position;
        this.cashedHashCode = computeHashCode();
        //todo first move
        this.isFirstMove = false;

    }

    protected int computeHashCode() {
        int ans = pieceType.hashCode();
        ans = 31 * ans + team.hashCode();
        ans = 31 * ans + position;
        ans = 31 * ans + (isFirstMove ? 1 : 0);
        return ans;
    };

    @Override
    public boolean equals(final Object other) {
       if (this == other) return true;
       if (!(other instanceof Piece)) return false;
       final Piece otherPiece = (Piece) other;
       return position == otherPiece.getPosition() && pieceType == otherPiece.getPieceType() &&
               team == otherPiece.getTeam() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cashedHashCode;
    }


    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Team getTeam() {
        return this.team;
    }

    public abstract Collection<Move> calcMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public int getPosition() {
        return this.position;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return pieceName;
        }

        public abstract boolean isKing();
    }
}
