public class AlphaBetaAlgorithm implements OthelloAlgorithm {
    private int depth;
    private OthelloEvaluator evaluator;
    private volatile boolean exit;

    public AlphaBetaAlgorithm(int depth, OthelloEvaluator evaluator) {
        this.depth = depth;
        this.evaluator = evaluator;
    }

    public void exit() {
        exit = true;
    }

    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void setSearchDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public OthelloAction evaluate(OthelloPosition position) {
        OthelloAction bestAction = new OthelloAction("pass");
        int bestScore = position.playerToMove ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        try {
            for (OthelloAction action : position.getMoves()) {
                int v = alphaBeta(position.makeMove(action), this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);

                if (position.playerToMove) {
                    if (v > bestScore) {
                        bestAction = action;
                        bestScore = v;
                    }
                } else {
                    if(v < bestScore) {
                        bestAction = action;
                        bestScore = v;
                    }
                }
            }

        } catch (IllegalMoveException e) {
            e.printStackTrace();
            return bestAction;
        }

        return bestAction;
    }

    private int alphaBeta(OthelloPosition pos, int depth, int a, int b) throws IllegalMoveException {
        if(exit) return -1;

        if (depth == 0 || pos.isFull()) {
            //System.err.println(evaluator.evaluate(pos));
            return evaluator.evaluate(pos);
        }

        if (pos.playerToMove) {
            // white is always maximising player
            int v = Integer.MIN_VALUE;
            for (OthelloAction action : pos.getMoves()) {
                v = Math.max(v, alphaBeta(pos.makeMove(action), depth - 1, a, b));
                a = Math.max(v, a);
                if (b <= a) break;
                if(exit) return -1;
            }
            return v;
        } else {
            // black is always minimising player
            int v = Integer.MAX_VALUE;
            for (OthelloAction action : pos.getMoves()) {
                v = Math.min(v, alphaBeta(pos.makeMove(action), depth - 1, a, b));
                b = Math.min(v, b);
                if (b <= a) break;
                if(exit) return -1;
            }
            return v;
        }
    }


}
