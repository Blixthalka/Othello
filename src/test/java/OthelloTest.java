import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class OthelloTest {

    private OthelloPosition pos;

    @Before
    public void setUp() {
        pos = new OthelloPosition();
    }

    @After
    public void tearDown() {
        pos = null;
    }

    @Test
    public void getMovesEmptyBoard() {
        assertEquals(pos.getMoves().size(), 0);
    }


    @Test()
    public void initMoveWhite() throws IllegalMoveException {
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        pos.makeMove(new OthelloAction(5, 3));
    }

    @Test(expected = IllegalMoveException.class)
    public void notAdjacentMove() throws IllegalMoveException {
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        pos.makeMove(new OthelloAction(2, 2));
    }
}
