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

    @Override
    protected Collection<Move> calcKingCastles(final Collection<Move> playerLegals,
                                               final Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getTile(61).isOccupied() && !this.board.getTile(62).isOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if (Player.calcAtksOnTile(61, opponentsLegals).isEmpty() &&
                        Player.calcAtksOnTile(62, opponentsLegals).isEmpty())
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) kingCastles
                        .add(new Move.KingSideCastleMove(this.board, this.playerKing, 62, (Rook) rookTile.getPiece(), rookTile.getTileCoord(), 61, true));
            }
            if (!this.board.getTile(57).isOccupied() &&
                    !this.board.getTile(58).isOccupied() &&
                    !this.board.getTile(59).isOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        Player.calcAtksOnTile(58, opponentsLegals).isEmpty() &&
                        Player.calcAtksOnTile(59, opponentsLegals).isEmpty())
                    kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) rookTile.getPiece(), rookTile.getTileCoord(), 59, true));
            }
        }
        return kingCastles;
    }
}
