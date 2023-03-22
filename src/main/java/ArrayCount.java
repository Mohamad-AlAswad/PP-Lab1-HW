import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ArrayCount extends RecursiveAction {
    final int[] arr;
    final int lo;
    final int hi;
    final int x;
    public long result;

    public ArrayCount(int[] arr, int lo, int hi, int x) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
        this.x = x;
    }

    public long computeSeq() {
        for (int i = lo; i <= hi; ++i) {
            if (arr[i] == x)
                result++;
        }
        return result;
    }

    @Override
    protected void compute() {
        if (hi - lo > 1_000_000) {
            int mid = (lo + hi) / 2;
            ArrayCount left = new ArrayCount(arr, lo, mid, x);
            ArrayCount right = new ArrayCount(arr, mid + 1, hi, x);
            left.fork();
            right.compute();
            left.join();
            result = left.result + right.result;
        } else {
            result = this.computeSeq();
        }
    }

    public void computeStream() {
        result = Arrays.stream(arr).asLongStream().parallel().filter(e -> e == x).count();
    }
}
