import java.util.HashMap;
import java.util.concurrent.RecursiveTask;


/*
However, besides being a dumb way to compute Fibonacci functions (there is a simple fast linear algorithm that you'd use in practice),
 this is likely to perform poorly because the smallest subtasks are too small to be worthwhile splitting up.
 Instead, as is the case for nearly all fork/join applications,
 you'd pick some minimum granularity size (for example 10 here) for which you always sequentially solve rather than subdividing.
 */

public class FibonacciMemo extends RecursiveTask<Integer> {
    final static HashMap<Integer, Integer> memo = new HashMap<>();
    final int n;
    final int mod = 1_000_000_007;

    public FibonacciMemo(int n) {
        this.n = n;
    }

    public Integer compute() {

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        if (n > 50) {
            FibonacciMemo f1 = new FibonacciMemo(n - 1);
            f1.fork();
            FibonacciMemo f2 = new FibonacciMemo(n - 2);
            memo.put(n, (f2.compute() + f1.join()) % mod);
            return memo.get(n);
        } else {
            return computeSeq();
        }
    }

    public Integer computeSeq() {
        if (n <= 1)
            return n;

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        FibonacciMemo f1 = new FibonacciMemo(n - 1);
        FibonacciMemo f2 = new FibonacciMemo(n - 2);
        memo.put(n, (f1.computeSeq() + f2.computeSeq()) % mod);
        return memo.get(n);
    }

}
