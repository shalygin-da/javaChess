package com.chess.engine.player;


import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    protected Collection<Move> calcKingCastles(final Collection<Move> playerLegals,
                                               final Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getTile(5).isOccupied() && !this.board.getTile(6).isOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if (Player.calcAtksOnTile(5, opponentsLegals).isEmpty() &&
                        Player.calcAtksOnTile(6, opponentsLegals).isEmpty())
                    if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) kingCastles
                            .add(new Move.KingSideCastleMove(this.board, this.playerKing, 6, (Rook) rookTile.getPiece(), rookTile.getTileCoord(), 5, true));
            }
            if (!this.board.getTile(1).isOccupied() &&
                    !this.board.getTile(2).isOccupied() &&
                    !this.board.getTile(3).isOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        Player.calcAtksOnTile(2, opponentsLegals).isEmpty() &&
                        Player.calcAtksOnTile(3, opponentsLegals).isEmpty()) kingCastles
                        .add(new Move.KingSideCastleMove(this.board, this.playerKing, 2, (Rook) rookTile.getPiece(), rookTile.getTileCoord(), 3, true));
            }
        }
        return kingCastles;
    }
}
