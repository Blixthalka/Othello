public class Evaluator implements OthelloEvaluator {

    private  int adjacentCorner[] = {2, 2, 1, 2, 2, 1, /* top    left  corner */
                                     7, 1, 7, 2, 8, 2, /* top    right corner */
                                     1, 7, 2, 7, 2, 8, /* bottom left  corner */
                                     8, 7, 7, 7, 7, 8  /* bottom right corner */};

    @Override
    public int evaluate(OthelloPosition position) {
        int moves = position.getMoves().size();
        int adjacentCorner = countAdjacentToCorners(position);
        int bricks = countBricks(position);
        int corners = countCorners(position);
        return moves * 2 - (3 * adjacentCorner) + (6 * corners) + bricks;
    }

    private int countCorners(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        int sum = 0;
        if(position.board[1][1] == player)
            sum++;
        if(position.board[1][8] == player)
            sum++;
        if(position.board[8][1] == player)
            sum++;
        if(position.board[8][8] == player)
            sum++;
        return sum;
    }

    private int countBricks(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        int sum = 0;
        for (int i = 1; i < 8 + 1; i++) {
            for (int j = 1; j < 8 + 1; j++) {
                if(position.board[i][j] == player)
                    sum++;
            }
        }
        return sum;
    }


    private int countAdjacentToCorners(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        int sum = 0;


        for (int i = 0; i < 12; i += 2) {

            if(position.board[adjacentCorner[i]][adjacentCorner[i+1]] == player)
                sum++;
        }

        return sum;
    }


}
