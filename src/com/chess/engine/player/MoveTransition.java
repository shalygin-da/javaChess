package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.concurrent.Future;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final  MoveStatus status;

    public MoveTransition(Board transitionBoard, Move move, MoveStatus status) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.status = status;
    }

    public MoveStatus getStatus() {
        return this.status;
    }
}
