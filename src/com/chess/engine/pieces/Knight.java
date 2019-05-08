package com.chess.engine.pieces;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private final static int[] POTENTIAL_MOVE_COORDS = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int position, Team pieceTeam) {
        super(position, pieceTeam);
    }

    @Override
    public List<Move> calcMoves(Board board) {

        int potentialDest;
        List<Move> moves = new ArrayList<>();

        for (final int currentPotentialMove : POTENTIAL_MOVE_COORDS) {

            potentialDest = this.position + currentPotentialMove;

            if (true /* isValidCoord */) {

                final Tile potentialDestTile = board.getTile(potentialDest);

                if (!potentialDestTile.isOccupied()) {
                    moves.add(new Move());
                } else {
                    final Piece pieceAtDest = potentialDestTile.getPiece();
                    final Team pieceTeam = pieceAtDest.getPieceTeam();

                    if (this.pieceTeam != pieceTeam) {
                        moves.add(new Move());
                    }
                }
            }
        }
        return moves;

    }
}
