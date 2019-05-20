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
        //Let's throw an IllegalArgumentException if n is beyond the range
        //of an integer in Java, since int arrays can't be initialised with long as size
        if (n > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Input number too long!");
        }

        int[] sequenceLengths = new int[((int) n) + 1];
        for (int i = 1; i <= (int) n; i++) {
            long currentN = i;
            int count = 0;
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
}
