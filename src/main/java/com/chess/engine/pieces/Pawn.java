package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Pawn extends Piece {

    private static final int[] POTENTIAL_MOVE_COORDS = {8, 16, 7, 9};

    public Pawn(final Team team, final int position) {
        super(PieceType.PAWN, position, team);
    }

    @Override
    public Collection<Move> calcMoves(final Board board) {

        final List<Move> moves = new ArrayList<>();

        for (final int currentPotentialMove : POTENTIAL_MOVE_COORDS) {

            final int potentialDest = this.position + (this.team.getDirection() * currentPotentialMove);
            if (!BoardUtils.isValidCoord(potentialDest)) continue;
            if (currentPotentialMove == 8 && !board.getTile(potentialDest).isOccupied()) { //todo promotions
                moves.add(new Move.OrdMove(board, this, potentialDest));
            } else if (this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.position] && this.getTeam().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.position] && this.getTeam().isWhite())) {
                final int behindPotentialDest = this.position + (this.getTeam().getDirection() * 8);
                if (!board.getTile(behindPotentialDest).isOccupied() && !board.getTile(potentialDest).isOccupied()) {
                    moves.add(new Move.OrdMove(board, this, behindPotentialDest));
                }
            } else if (currentPotentialMove == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.position] && this.team.isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[this.position] && this.team.isBlack()))) {
                if (board.getTile(potentialDest).isOccupied()) {
                    final Piece pieceOnDest = board.getTile(potentialDest).getPiece();
                    if (this.team != pieceOnDest.getTeam()) {
                        // TODO pawn atk
                        moves.add(new Move.AtkMove(board, this, potentialDest, pieceOnDest));
                    }
                }
            } else if (currentPotentialMove == 9 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.position] && this.team.isBlack()) ||
                            (BoardUtils.FIRST_COLUMN[this.position] && this.team.isWhite()))) {
                if (board.getTile(potentialDest).isOccupied()) {
                    final Piece pieceOnDest = board.getTile(potentialDest).getPiece();
                    if (this.team != pieceOnDest.getTeam()) {
                        // TODO pawn atk
                        moves.add(new Move.AtkMove(board, this, potentialDest, pieceOnDest));
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getTeam(), move.getDest());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}

