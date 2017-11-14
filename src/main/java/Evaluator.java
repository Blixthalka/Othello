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
        int potMoves = potentialMobility(position);
        int moves = position.getMoves().size();
        int movesLeft = position.movesLeft();
        int movesLeftMultiplier = 0;

        if(movesLeft < 18)
            movesLeftMultiplier = 1;
        else if (movesLeft < 10)
            movesLeftMultiplier = 5;


        int bricks = position.movesLeft() < 14 ? countBricks(position) : 0;


        int ret = staticEval(position) + potMoves + moves + bricks * movesLeftMultiplier;

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



    private int countBricks(OthelloPosition position) {
        char player = position.playerToMove ? 'W' : 'B';
        int sum = 0;
        for (int i = 1; i < 8 + 1; i++) {
            for (int j = 1; j < 8 + 1; j++) {
                if (position.board[i][j] == player)
                    sum++;
            }
        }
        return sum;
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
        for (int i = 1; i < 8 + 1; i++) {
            for (int j = 1; j < 8 + 1; j++) {
                if (position.board[i][j] == player) {
                    
                }
            }
        }
        return sum;

    }

}
