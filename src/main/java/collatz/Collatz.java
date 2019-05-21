package collatz;

public class Collatz {

    static int collatz(long number) {
        if (number < 1) {
            throw new IllegalArgumentException("Number needs to be greater than zero!");
        }
        if (number == 1) {
            return 1;
        } else if (number % 2 == 0) {
            return collatz(number / 2);
        } else {
            return collatz(3 * number + 1);
        }
    }

    static int[] simpleComputeSequenceLengths(final long n) {
        int[] sequenceLengths = new int[((int) n) + 1];
        long currentN;
        int count;
        for (int i = 1; i <= (int) n; i++) {
            currentN = i;
            count = 0;
            do {
                count++;
                if (currentN == 1) break;
                else if (currentN % 2 == 0) currentN /= 2;
                else currentN = currentN * 3 + 1;
            } while (true);
            sequenceLengths[i] = count;
        }
        return sequenceLengths;
    }

    static int[] memoizedComputeSequenceLengths(final long n) {
        int[] sequenceLengths = new int[((int) n) + 1];
        long currentN;
        int count;
        for (int i = 1; i <= (int) n; i++) {
            currentN = i;
            count = 0;
            do {
                //Check if the current sequence length has been calculated already
                if (currentN < i) {
                    count += sequenceLengths[(int) currentN];
                    break;
                }
                count++;
                if (currentN == 1) break;
                else if (currentN % 2 == 0) currentN /= 2;
                else currentN = currentN * 3 + 1;
            } while (true);
            sequenceLengths[i] = count;
        }
        return sequenceLengths;
    }

    static long doTimings(long n) {
        //Let's throw an IllegalArgumentException if n is beyond a large value,
        //since large values will throw an OutOfMemoryException. 40960000 works
        //on my system for this purpose.
        int limit = 40960000;
        if (n > limit) {
            throw new IllegalArgumentException("Input number longer than " + limit + " !");
        } else if (n < 1) {
            throw new IllegalArgumentException("Input number needs to be greater than zero!");
        }

        long startTime;
        long endTime;
        long simpleTime;
        long memoizedTime;
        int[] simple;
        int[] memoized;

        System.gc();
        System.out.println("Starting test with simple method");
        startTime = System.nanoTime();
        simple = simpleComputeSequenceLengths(n);
        endTime = System.nanoTime();
        simpleTime = endTime - startTime;
        System.out.println("Simple method took " + simpleTime / Math.pow(10, 6) + "ms to run");

        System.gc();
        System.out.println("Starting test with memoized method");
        startTime = System.nanoTime();
        memoized = memoizedComputeSequenceLengths(n);
        endTime = System.nanoTime();
        memoizedTime = endTime - startTime;
        System.out.println("Memoized method took " + memoizedTime / Math.pow(10, 6) + "ms to run");

        for (int i = 1; i <= n; i++) {
            assert simple[i] == memoized[i];
        }
        return simpleTime - memoizedTime;
    }
}
