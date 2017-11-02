

public class OthelloMain {



    public static void main(String [] args) throws IllegalMoveException {
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();

        OthelloAction action = new OthelloAction(2, 2);
        pos.makeMove(action).illustrate();
        pos.illustrate();
    }
}

