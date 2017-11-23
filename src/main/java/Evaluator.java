public class Evaluator implements OthelloEvaluator {

    // corner score
    private final static int C = 85;

    // adjacent corner score
    private final static int AC = 5;

    // diagonal  corner score
    private final static int DC = -5;

    // edge
    private final static int E = 20;

    private int score[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, C, AC, E, E, E, E, AC, C, 0},
            {0, AC, DC, -4, 1, 1, -4, DC, AC, 0},
            {0, E, -4, 2, 2, 2, 2, -4, E, 0},
            {0, E, 1, 2, -3, -3, 2, 1, E, 0},
            {0, E, 1, 2, -3, -3, 2, 1, E, 0},
            {0, E, -4, 2, 2, 2, 2, -4, E, 0},
            {0, AC, DC, -4, 1, 1, -4, DC, AC, 0},
            {0, C, AC, E, E, E, E, AC, C, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};


    @Override
    public int evaluate(OthelloPosition position) {
        int whitePlayerScore;
        int blackPlayerScore;


        if (position.playerToMove) {
            whitePlayerScore = localEvaluate(position);
            position.playerToMove = !position.playerToMove;
            blackPlayerScore = localEvaluate(position);
        } else {
            blackPlayerScore = localEvaluate(position);
            position.playerToMove = !position.playerToMove;
            whitePlayerScore = localEvaluate(position);
        }

        position.playerToMove = !position.playerToMove;
        return whitePlayerScore - blackPlayerScore;
    }

    /**
     * Evaluates the board state.
     * @param position The game state.
     * @return The score of the evaluation.
     */
    public int localEvaluate(OthelloPosition position) {
        int moves = position.getMoves().size();
        int movesLeft = position.movesLeft();
        int stableBricks =  stableBricks(position);
        int staticEval = staticEval(position);
        PlayerWrapper wrapp = countBricks(position);

        int stableBricsMultiplier = 15;
        int bricksMultiplier = 1;
        int staticEvalMultiplier = 2;
        int movesMultiplier = 5;

        if(movesLeft <= 14) {
            bricksMultiplier = 10;
            staticEvalMultiplier = 0;
            stableBricsMultiplier = 5;
            movesMultiplier = 1;
        } else if (movesLeft < 30) {
            bricksMultiplier = 2;
        }

        if(wrapp.opponent == 0)
            return Integer.MAX_VALUE - 2;
        else
           return staticEval    * staticEvalMultiplier +
                   moves        * movesMultiplier +
                   wrapp.player * bricksMultiplier +
                   stableBricks * stableBricsMultiplier;
    }

    /**
     * Evaluates the board, based on the static evaluation matrix.
     */
    private int staticEval(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        int sum = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (position.board[i][j] == player) {
                    sum += score[i][j];
                }
            }
        }
        return sum;
    }



    private PlayerWrapper countBricks(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        char opponent = position.playerToMove ? 'B' : 'W';
        PlayerWrapper wrapp = new PlayerWrapper();
        for (int i = 1; i < 8 + 1; i++) {
            for (int j = 1; j < 8 + 1; j++) {
                if (position.board[i][j] == player)
                    wrapp.player++;
                else if (position.board[i][j] == opponent)
                    wrapp.opponent++;
            }
        }
        return wrapp;
    }


    /**
     * Checks how many stable brick on the edge the current player has.
     * @param position The game state.
     * @return The number of stable edge bricks.
     */
    private int stableBricks(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';

        int sum = 0;
        for (int i = 1; i < 8 + 1; i += 7) {
            for (int j = 1; j < 8 + 1; j++) {
                if (position.board[i][j] == player) {
                    if (isStableEdgePath(position, i, j, new Direction(0,1)) ||
                            isStableEdgePath(position, i, j, new Direction(0,-1))) {
                        sum++;

                    }
                }
            }
        }

        for (int i = 1; i < 8 + 1; i++) {
            for (int j = 1; j < 8 + 1; j += 7) {
                if (position.board[i][j] == player) {
                    if (isStableEdgePath(position, i, j, new Direction(1,0)) ||
                            isStableEdgePath(position, i, j, new Direction(-1,0))) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    private boolean isStableEdgePath(OthelloPosition position, int row, int column, Direction direction) {
        char player = position.playerToMove ? 'W' : 'B';

        do {
            row    += direction.getRowDirection();
            column += direction.getColumnDirection();
        } while (position.board[row][column] == player);

        return isCorner(row, column);
    }

    private boolean isCorner(int row, int column) {
        return row == 8 && column == 1 ||
                row == 8 && column == 8 ||
                row == 1 && column == 8 ||
                row == 1 && column == 1;
    }

    class PlayerWrapper {
        public int opponent = 0;
        public int player = 0;
    }

}
