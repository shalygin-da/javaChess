package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {

    protected final int position;
    protected final Team ally;

    Piece(final int position, final Team ally) {
        this.ally = ally;
        this.position = position;
    }

    public abstract List<Move> calcMoves(final Board board);

}
