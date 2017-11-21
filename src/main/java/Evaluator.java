public class Evaluator implements OthelloEvaluator {

    // corner score
    private final static int C = 100;

    // adjacent corner score
    private final static int AC = -5;

    // diagonal  corner score
    private final static int DC = -10;

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
        int moves = position.getMoves().size();
        int movesLeft = position.movesLeft();
        int stableBricks =  stableBricks(position);
        int staticEval = staticEval(position);
        Wrapp wrapp = countBricks(position);
        int movesLeftMultiplier = 0;
        int staticEvalMultiplier = 1;

        if(movesLeft < 10) {
            movesLeftMultiplier = 2;
            staticEvalMultiplier = 0;
        }
//        else if (movesLeft < 18) {
//            movesLeftMultiplier = 1;
//        }


        int ret;
        if(wrapp.opponent == 0)
            ret = Integer.MAX_VALUE - 2;
        else if(moves == 0)
            ret = -10000;
        else
            ret = staticEval * staticEvalMultiplier + moves * 2 + wrapp.player * movesLeftMultiplier + stableBricks * 10;


        //ret = wrapp.player - wrapp.opponent;
        return position.playerToMove ? ret : -ret;

    }

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

    class Wrapp {
        public int opponent = 0;
        public int player = 0;
    }

    private Wrapp countBricks(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        char opponent = position.playerToMove ? 'B' : 'W';
        Wrapp wrapp = new Wrapp();
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


    private int potentialMobility(OthelloPosition position) {
        char opponentPlayer = position.playerToMove ? 'B' : 'W';
        int sum = 0;

        for (int r = 1; r < OthelloPosition.BOARD_SIZE + 1; r++) {
            for (int c = 1; c < OthelloPosition.BOARD_SIZE + 1; c++) {
                if (position.board[r][c] == opponentPlayer) {


                    for (int i = r - 1; i <= r + 1; i++) {
                        for (int j = c - 1; j <= c + 1; j++) {

                            if (i < 1 || i > OthelloPosition.BOARD_SIZE ||
                                    j < 1 || j > OthelloPosition.BOARD_SIZE)
                                continue;

                            if (position.board[i][j] == 'E') {
                                sum++;
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }

    private int stableBricks(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';

        int sum = 0;
        for (int i = 1; i < 8 + 1; i += 7) {
            for (int j = 1; j < 8 + 1; j++) {
                // System.err.println(i + " " + j + " " + 0 + " " + 1);
                if (position.board[i][j] == player) {
                    if (isStableEdgePath(position, i, j, new Direction(0,1)) ||
                            isStableEdgePath(position, i, j, new Direction(0,-1))) {
                        sum++;

                    }
                }
            }
        }

      //  System.err.println();
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
        //System.err.println();
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

}
