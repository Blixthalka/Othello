import java.util.LinkedList;
import java.util.concurrent.*;

public class OthelloMain {


    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Wrong arguments.");
            return;
        }

        long timeConstraint = Long.decode(args[1]) * 1000 - 300;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        LinkedList<OthelloActionWrapper> actions = new LinkedList<>();
        OthelloPosition position = new OthelloPosition(args[0]);
        AlphaBetaAlgorithm alg = new AlphaBetaAlgorithm(10, new Evaluator());

        try {
            for (int depth = 4; depth <= 60 ; depth++) {
                long start = System.currentTimeMillis();
                alg = new AlphaBetaAlgorithm(depth, new Evaluator());
                actions.addFirst(executor.submit(new Task(alg, position)).get(timeConstraint, TimeUnit.MILLISECONDS));
                timeConstraint -= (System.currentTimeMillis() - start);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
            // Just want to exit the search.
        } finally {
            executor.shutdownNow();
            alg.exit();
        }

        if (actions.size() == 0) {
            new OthelloAction("pass").print();
            return;
        }

        for(int i = 0; i < actions.size(); i++) {
            OthelloActionWrapper wrapper = actions.get(i);

            if(!wrapper.interupted) {
                wrapper.action.print();
                break;
            }
        }

    }
}

class Task implements Callable<OthelloActionWrapper> {
    private AlphaBetaAlgorithm alg;
    private OthelloPosition position;

    public Task(AlphaBetaAlgorithm alg, OthelloPosition position) {
        this.alg = alg;
        this.position = position;
    }

    @Override
    public OthelloActionWrapper call() throws Exception {
        OthelloActionWrapper wrapper = new OthelloActionWrapper();
        try {
            wrapper.action = alg.evaluate(position);
        } catch (ExitException e) {
            wrapper.interupted = true;
        }
        return wrapper;
    }
}

class OthelloActionWrapper {
    public OthelloAction action;
    public boolean interupted = false;
}
