package com.chess.engine.player;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> moves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> moves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !Player.calcAtksOnTile(this.playerKing.getPosition(), opponentMoves).isEmpty();
        this.moves = moves;
    }

    private static Collection<Move> calcAtksOnTile(int position, Collection<Move> opponentMoves) {

        final List<Move> atkMoves = new ArrayList<>();
        for (final Move move : opponentMoves) {
            if (position == move.getDest()) {
                atkMoves.add(move);
            }
        }
        return atkMoves;
    }

    public Collection<Move> getMoves() {
        return this.moves;
    }

    private King establishKing() {
        for (final Piece piece : getAlivePieces()) {
            if (piece.getPieceType().isKing()) return (King) piece;
        }
        throw new RuntimeException("No King was detected on one of the sides");
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    private boolean canEscape() {
        for (final Move move : this.moves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getStatus().isDone()) return true;
        }
        return false;
    }

    public boolean isMovePossible(final Move move) {
        return this.moves.contains(move);
    }
    
    public boolean isInCheck() {
        return this.isInCheck;
    }
    
    public boolean isInCheckMate() {
        return this.isInCheck && !canEscape();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !canEscape();
    }
    
    public boolean isCastled() {
        return false; // TODO: 29.05.2019  
    }

    public MoveTransition makeMove(Move move) {

        if (!isMovePossible(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();
        final Collection<Move> atksOnKing = Player.calcAtksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPosition(),
                transitionBoard.currentPlayer().getMoves());
        if (!atksOnKing.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }
    
    public abstract Collection<Piece> getAlivePieces();
    public abstract Team getTeam();
    public abstract Player getOpponent();

}
