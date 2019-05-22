package collatz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    static long collatz_1(long n) {
        //Check for values lesser than 1 or values that might push the result
        //out of the max range of long type.
        if (n < 1 || n > (Long.MAX_VALUE - 1) / 3) {
            throw new IllegalArgumentException("Input value out of range!");
        }

        if (n == 1) {
            return 1;
        } else if (n % 2 == 0) {
            return n / 2;
        } else {
            return n * 3 + 1;
        }
    }

    static List<Long> sequenceOf(final long n) {
        //Check for values lesser than 1 or values that might push the result
        //out of the max range of long type.
        if (n < 1 || n > (Long.MAX_VALUE - 1) / 3) {
            throw new IllegalArgumentException("Input value out of range!");
        }

        List<Long> result = new ArrayList<>();
        result.add(n);
        if (n == 1) {
            return result;
        }
        long currentN = n;
        while (currentN > 1) {
            currentN = collatz_1(currentN);
            result.add(currentN);
        }
        return result;
    }

    static int lengthOfSequence(long n) {
        return sequenceOf(n).size();
    }

    static long largestValueInSequence(long n) {
        List<Long> results = sequenceOf(n);
        Collections.sort(results);
        return results.get(results.size() - 1);
    }

    static List<Pair<Long, Integer>> equalLengthTwins(final long lo, final long hi) {
        //Make sure lo is actually lower than hi
        if (lo > hi) {
            throw new IllegalArgumentException("Incorrect input order!");
        }
        //Check for values lesser than 1 or values that might push the result
        //out of the max range of long type.
        if (lo < 1 || lo > 40960000) {
            throw new IllegalArgumentException("Input value out of range!");
        }
        if (hi > 40960000) {
            throw new IllegalArgumentException("Input value out of range!");
        }

        int[] sequenceLengths = memoizedComputeSequenceLengths(hi + 1);

        List<Pair<Long, Integer>> result = new ArrayList<>();
        for (int i = (int) lo; i <= (int) hi; i++) {
            if (sequenceLengths[i] == sequenceLengths[i + 1]) {
                Pair<Long, Integer> pair = new Pair<>((long) i, sequenceLengths[i]);
                result.add(pair);
            }
        }
        return result;
    }

    static List<Pair<Long, Long>> equalMaxValueTwins(final long lo, final long hi) {
        //Make sure lo is actually lower than hi
        if (lo > hi) {
            throw new IllegalArgumentException("Incorrect input order!");
        }
        //Check for values lesser than 1 or values that might push the result
        //out of the max range of long type.
        if (lo < 1 || lo > 40960000) {
            throw new IllegalArgumentException("Input value out of range!");
        }
        if (hi > 40960000) {
            throw new IllegalArgumentException("Input value out of range!");
        }

        long[] maxValues = new long[(int) (hi - lo) + 2];

        for (int i = 0; i <= hi - lo + 1; i++) {
            maxValues[i] = largestValueInSequence(lo + i);
        }

        List<Pair<Long, Long>> result = new ArrayList<>();
        for (int i = 0; i <= hi - lo; i++) {
            if (maxValues[i] == maxValues[i + 1]) {
                Pair<Long, Long> pair = new Pair<>(lo + i, maxValues[i + 1]);
                result.add(pair);
            }
        }
        return result;
    }

    static int[] occurrences(final long n, int counts) {
        //Check for values lesser than 1 or values that might result in
        //running out of heap space
        if (n < 1 || n > 10000000) {
            throw new IllegalArgumentException("Input value out of range!");
        }
        if (counts < 1) {
            throw new IllegalArgumentException("Input value out of range!");
        }

        int[] occurrences = new int[counts + 1];
        int count = 0;

        for (long i = 1; i <= n; i++) {
            List<Long> sequence = sequenceOf(i);
            for (Long item : sequence) {
                if (item <= counts) {
                    occurrences[item.intValue()]++;
                }
            }
        }
        System.out.println(count);
        return occurrences;
    }
}
