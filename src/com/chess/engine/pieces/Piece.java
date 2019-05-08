package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece {

    protected final int position;
    protected final Team pieceTeam;

    Piece(final int position, final Team team) {
        this.pieceTeam = team;
        this.position = position;
    }

    public Team getPieceTeam() {
        return this.pieceTeam;
    }

    public abstract List<Move> calcMoves(final Board board);

}
