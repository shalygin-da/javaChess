package com.chess.engine.player;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {


    public BlackPlayer(final Board board, final Collection<Move> blackStartMoves, final Collection<Move> whiteStartMoves) {
        super(board, blackStartMoves, whiteStartMoves);
    }

    @Override
    public Collection<Piece> getAlivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Team getTeam() {
        return Team.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
