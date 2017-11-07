import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.*;

public class OthelloMain {


    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Wrong arguments");
            return;
        }

        long timeConstraint = Long.decode(args[1]) * 1000 - 300;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        OthelloAction action = new OthelloAction("pass");
        OthelloPosition position = new OthelloPosition(args[0]);
        int depth;
        AlphaBetaAlgorithm alg = new AlphaBetaAlgorithm(1, new Evaluator());

        try {
            for (depth = 2; depth < Integer.MAX_VALUE; depth++) {
                long start = System.currentTimeMillis();
                alg = new AlphaBetaAlgorithm(depth, new Evaluator());
                action = executor.submit(new Task(alg, position)).get(timeConstraint, TimeUnit.MILLISECONDS);
                timeConstraint -= (System.currentTimeMillis() - start);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            executor.shutdownNow();
            alg.exit();
        }

        action.print();
    }
}

class Task implements Callable<OthelloAction> {
    private AlphaBetaAlgorithm alg;
    private OthelloPosition position;

    public Task(AlphaBetaAlgorithm alg, OthelloPosition position) {
        this.alg = alg;
        this.position = position;
    }

    @Override
    public OthelloAction call() throws Exception {
        return alg.evaluate(position);
    }
}
