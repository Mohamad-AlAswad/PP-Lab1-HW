import junit.framework.TestCase;


public class FibonacciMemoTest extends TestCase {

    final int n = 1_000;

    public void testFiboPP() {
        long start = System.currentTimeMillis();
        FibonacciMemo fib = new FibonacciMemo(n);
        int res = fib.compute();
        long end = System.currentTimeMillis() - start;
        System.out.printf("Fibonacci for %d is %d, and parallel execution took %d ms\n", n, res, end);
    }

    public void testFiboSeq() {
        long start = System.currentTimeMillis();
        FibonacciMemo fib = new FibonacciMemo(n);
        int res = fib.computeSeq();
        long end = System.currentTimeMillis() - start;
        System.out.printf("Fibonacci for %d is %d, and sequential execution took %d ms\n", n, res, end);
    }
}
