import java.util.List;

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
    public OthelloAction evaluate(OthelloPosition position) throws ExitException {
        OthelloAction bestAction = new OthelloAction("pass");
        int bestScore = position.playerToMove ? Integer.MIN_VALUE : Integer.MAX_VALUE;


        try {
           //System.err.println(depth);
            for (OthelloAction action : position.getMoves()) {

                int v = alphaBeta(position.makeMove(action), this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                //System.err.println("Score: " + v + " (" + action.row + "," + action.column + ")" +  " best: " + bestScore + " (" + bestAction.row + "," + bestAction.column + ")");
                if (position.playerToMove) {
                    if (v > bestScore) {
                        bestAction = action;
                        bestScore = v;
                    }
                } else {
                    if (v < bestScore) {
                    //    System.err.println("New best score.");
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

    private int alphaBeta(OthelloPosition pos, int depth, int a, int b) throws IllegalMoveException, ExitException {
        // System.out.println("depth = [" + depth + "], a = [" + a + "], b = [" + b + "]");
        if (exit) throw new ExitException();

        if (depth == 0) {
            //System.err.println(evaluator.evaluate(pos));
            return evaluator.evaluate(pos);
        }

        List<OthelloAction> actions = pos.getMoves();

        if (actions.isEmpty()) {
            return evaluator.evaluate(pos);
        }

        if (pos.playerToMove) {
            // white is always maximising player
            int v = Integer.MIN_VALUE;
            for (OthelloAction action : actions) {
                v = Math.max(v, alphaBeta(pos.makeMove(action), depth - 1, a, b));
                a = Math.max(v, a);
                if (b <= a) {
                    break;
                }

                if (exit) throw new ExitException();
            }
            return v;
        } else {
            // black is always minimising player
            int v = Integer.MAX_VALUE;
            for (OthelloAction action : actions) {
                v = Math.min(v, alphaBeta(pos.makeMove(action), depth - 1, a, b));
                b = Math.min(v, b);
                if (b <= a) {
                    break;
                }
                if (exit) throw new ExitException();
            }
            return v;
        }
    }
}
