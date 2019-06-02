package com.chess.engine.player;


import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class WhitePlayer extends Player{


    public WhitePlayer(final Board board, final Collection<Move> whiteStartMoves, final Collection<Move> blackStartMoves) {
        super(board, whiteStartMoves, blackStartMoves);
    }

    @Override
    public Collection<Piece> getAlivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Team getTeam() {
        return Team.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
