package gui;

import com.chess.engine.board.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Table {

    public String string(Board board) {
        return "chessPieces/art/" + board.getTile(63).getPiece().getTeam().toString().substring(0, 1) +
                board.getTile(63).getPiece().toString() + ".png";
    }

    @Test
    public void pathFinder() {
        assertEquals("chessPieces/art/WR.png", string(Board.createStandartBoard()));
    }

}
