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

public class Bishop extends Piece {

    private final static int[] POTENTIAL_MOVE_VECTOR_COORDS = {-9, -7, 7, 9};

    public Bishop(final Team team, final int position) {
        super(position, team);
    }

    @Override
    public Collection<Move> calcMoves(Board board) {
        final List<Move> moves = new ArrayList<>();
        for (final int currentPotentialMove : POTENTIAL_MOVE_VECTOR_COORDS) {
            int potentialDest = this.position;
            while (BoardUtils.isValidCoord(potentialDest)) {
                if (isFirstColumnExclusion(potentialDest, currentPotentialMove) ||
                        isEighthColumnExclusion(potentialDest, currentPotentialMove)) break;
                potentialDest += currentPotentialMove;
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
                        break;
                    }
                }
            }
        }
        return moves;
    }

    private static boolean isFirstColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.FIRST_COLUMN[position] && (potentialMove == -9 || potentialMove == 7);
    }

    private static boolean isEighthColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.EIGHTH_COLUMN[position] && (potentialMove == 9 || potentialMove == -7);
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

}