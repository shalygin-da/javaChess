package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class King extends Piece {

    private final static int[] POTENTIAL_MOVE_COORDS = {-9, -8, -7, -1, 1, 7, 8, 9,};

    public King(final Team team, final int position) {
        super(PieceType.KING, position, team, hashCode);
    }

    @Override
    public Collection<Move> calcMoves(Board board) {
        final List<Move> moves = new ArrayList<>();
        for (final int currentPotentialMove : POTENTIAL_MOVE_COORDS) {
            final int potentialDest = this.position + currentPotentialMove;

            if (isFirstColumnExclusion(position, currentPotentialMove) || isEighthColumnExclusion(position, currentPotentialMove))
                continue;

            if (BoardUtils.isValidCoord(potentialDest)) {
                final Tile potentialDestTile = board.getTile(potentialDest);
                if (!potentialDestTile.isOccupied()) {
                    moves.add(new OrdMove(board, this, potentialDest));
                } else {
                    final Piece pieceAtDest = potentialDestTile.getPiece();
                    final Team team = pieceAtDest.getTeam();
                    if (this.team != team) {
                        moves.add(new AtkMove(board, this, potentialDest, pieceAtDest));
                    }

                }
            }
        }
        return moves;
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getMovedPiece().getTeam(), move.getDest());
    }

    private static boolean isFirstColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.FIRST_COLUMN[position] && (potentialMove == -9 || potentialMove == -1 || potentialMove == 7);
    }

    private static boolean isEighthColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.EIGHTH_COLUMN[position] && (potentialMove == 9 || potentialMove == 1 || potentialMove == -7);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}