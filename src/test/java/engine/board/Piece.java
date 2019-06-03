package engine.board;

import com.chess.engine.board.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Piece {

    public String toStringString(Board board) {
        return board.getTile(63).getPiece().getTeam().toString() +
                board.getTile(63).getPiece().toString();
    }

    public String pathNameToPieces(Board board){
    return board.getTile(63).getPiece().getTeam().toString().substring(0, 1) + board.getTile(63).getPiece().toString();
    }

    @Test
    public void toStringTester() {
        assertEquals("WR", toStringString(Board.createStandartBoard()));
        assertEquals(pathNameToPieces(Board.createStandartBoard()), toStringString(Board.createStandartBoard()));
    }
}
