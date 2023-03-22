import junit.framework.TestCase;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ArrayCountTest extends TestCase {


    private int[] randomArray(int size, int seed, int mn, int mx) {
        Random rand = new Random(seed);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(mx - mn + 1) + mn;
        }
//         Check  random array
//        Arrays.stream(arr).limit(10).forEach(System.out::println);
        return arr;
    }

    public void testArrayCountSeq() {

        int size = 1_000_000_000;
        int[] arr = randomArray(size, 32, 1, 100_000);
        int x = 2;

        ArrayCount array = new ArrayCount(arr, 0, arr.length - 1, x);
        long start = System.currentTimeMillis();
        long result = array.computeSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Time execution for Random Array of size %d is %d ms. occurrences of '2' is %d\n", size, endTimer, result);
        assertEquals(9849, result);
    }

    public void testArrayCountPP() {

        int size = 1_000_000_000;
        int[] arr = randomArray(size, 32, 1, 100_000);
        int x = 2;

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "7");

        ArrayCount array = new ArrayCount(arr, 0, arr.length - 1, x);
        long start = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(array);
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Time execution for Random Array of size %d is %d ms. occurrences of '2' is %d\n", size, endTimer, array.result);
        assertEquals(9849, array.result);
    }

    public void testArrayCountStream() {

        int size = 1_000_000_000;
        int[] arr = randomArray(size, 32, 1, 100_000);
        int x = 2;

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "7");

        ArrayCount array = new ArrayCount(arr, 0, arr.length - 1, x);
        long start = System.currentTimeMillis();
        array.computeStream();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Stream Time execution for Random Array of size %d is %d ms. occurrences of '2' is %d\n", size, endTimer, array.result);
        assertEquals(9849, array.result);
    }

}
