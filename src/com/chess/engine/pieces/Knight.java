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

public class Knight extends Piece {

    private final static int[] POTENTIAL_MOVE_COORDS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Team team, final int position) {
        super(PieceType.KNIGHT, position, team, hashCode);
    }

    @Override
    public Collection<Move> calcMoves(final Board board) {

        List<Move> moves = new ArrayList<>();

        for (final int currentPotentialMove : POTENTIAL_MOVE_COORDS) {
            final int potentialDest = this.position + currentPotentialMove;
            if (BoardUtils.isValidCoord(potentialDest) ) {
                if(isFirstColumnExclusion(this.position, currentPotentialMove) ||
                        isSecondColumnExclusion(this.position, currentPotentialMove) ||
                        isSeventhColumnExclusion(this.position, currentPotentialMove) ||
                        isEighthColumnExclusion(this.position, currentPotentialMove)){
                    continue;
                }
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
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getTeam(), move.getDest());
    }

    private static boolean isFirstColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.FIRST_COLUMN[position] && (potentialMove == -17 || potentialMove == -10 || potentialMove == 6 || potentialMove == 15);
    }

    private static boolean isSecondColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.SECOND_COLUMN[position] && (potentialMove == -10 || potentialMove == 6);
    }

    private static boolean isSeventhColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.SEVENTH_COLUMN[position] && (potentialMove == 10 || potentialMove == -6);
    }

    private static boolean isEighthColumnExclusion(final int position, final int potentialMove) {
        return BoardUtils.EIGHTH_COLUMN[position] && (potentialMove == 17 || potentialMove == 10 || potentialMove == -6 || potentialMove == -15);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
