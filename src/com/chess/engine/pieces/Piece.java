package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int position;
    protected final Team team;

    Piece(final int position, final Team team) {
        this.team = team;
        this.position = position;
    }

    public Team getTeam() {
        return this.team;
    }

    public abstract Collection<Move> calcMoves(final Board board);

}
