package collatz;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static collatz.Collatz.*;
import static java.lang.Math.abs;
import static junit.framework.TestCase.fail;

public class CollatzTest {

    @Test(expected = IllegalArgumentException.class)
    public void collatzOfZeroShouldThrowIllegalArgumentException() {
        collatz(0);
    }

    @Test
    public void collatzOfOneShouldReturnOne() {
        assert collatz(1) == 1;
    }

    @Test
    public void collatzOfTwoShouldReturnOne() {
        assert collatz(2) == 1;
    }

    @Test
    @Ignore("No need to run a two and a half minute long test each time")
    public void collatzOfBigNumbersShouldReturnOne() {
        //NB: BigNumbers = 163840000 for now.
        for (long i = 3; i <= 163840000; i++) {
            assert collatz(i) == 1;
        }
    }

    @Test
    public void sequenceLengthsTillThree() {
        int[] lengths = simpleComputeSequenceLengths(3);
        assert lengths.length == 4;
        assert lengths[1] == 1;
        assert lengths[2] == 2;
        assert lengths[3] == 8;
    }

    @Test
    public void memoizedSequenceLengthsTillNine() {
        int[] lengths = memoizedComputeSequenceLengths(9);
        assert lengths.length == 10;
        assert lengths[1] == 1;
        assert lengths[2] == 2;
        assert lengths[3] == 8;
        assert lengths[4] == 3;
        assert lengths[5] == 6;
        assert lengths[6] == 9;
        assert lengths[7] == 17;
        assert lengths[8] == 4;
        assert lengths[9] == 20;
    }

    @Test
    @Ignore("No need to run a fifteen seconds long test each time")
    public void simpleAndMemoizedSequenceLengthsShouldCorrespond() {
        int length = 10000;
        for (int i = 1; i < length; i++) {
            int[] simple = simpleComputeSequenceLengths(i);
            int[] memoized = memoizedComputeSequenceLengths(i);
            for (int j = 1; j <= i; j++)
                assert simple[j] == memoized[j];
        }
    }

    @Test
    public void timeDifferenceShouldBePositive() {
        int length = 40960000;
        assert doTimings(length) > 0;
    }

    @Test(expected = IllegalArgumentException.class)
    public void doTimingsInvalidInputShouldThrowException() {
        long length = Integer.MAX_VALUE;
        assert doTimings(length) > 0;
    }

    @Test
    public void collatz_1ShouldReturnOneStepOfCollatz() {
        Random random = new Random();
        long inputOne = 1;

        long inputEven = (long) abs(random.nextInt()) * 2 + 2;
        long inputOdd = (long) abs(random.nextInt()) * 2 + 1;

        System.out.println("Testing with inputs " + inputOne + " " + inputEven + " " + inputOdd);
        assert collatz_1(inputOne) == 1;
        assert collatz_1(inputEven) == inputEven / 2;
        assert collatz_1(inputOdd) == inputOdd * 3 + 1;
    }

    @Test
    public void collatz_1WithInvalidInputShouldThrowException() {
        Random random = new Random();
        long inputNegative = -(abs(random.nextInt()) + 2);
        long inputZero = 0;
        long inputLarge = (Long.MAX_VALUE - 1) / 3 + 1;

        try {
            long resultNegative = collatz_1(inputNegative);
            fail("Exception not thrown for negative input!");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed for negative input");
        }

        try {
            long resultZero = collatz_1(inputZero);
            fail("Exception not thrown for input of zero!");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed for input of zero");
        }
        try {
            long resultLarge = collatz_1(inputLarge);
            fail("Exception not thrown for large input!");
        } catch (Exception e) {
            System.out.println("Test passed for large input");
        }
    }

    @Test
    public void sequenceOfShouldReturnValidList() {
        Random random = new Random();
        long inputOne = 1;
        long inputKnown = 3;
        long inputRandom = abs(random.nextInt()) + 2;

        List<Long> resultOne = sequenceOf(inputOne);
        List<Long> resultKnown = sequenceOf(inputKnown);
        List<Long> resultRandom = sequenceOf(inputRandom);

        System.out.println("Testing with random input " + inputRandom);
        assert resultOne != null;
        assert resultOne.get(0) == 1;
        assert resultKnown != null;
        assert resultKnown.get(3) == 16;
        assert resultKnown.get(7) == 1;
        assert resultRandom != null;
        assert resultRandom.get(resultRandom.size() - 1) == 1;
    }

    @Test
    public void sequenceOfWithInvalidInputShouldThrowException() {
        Random random = new Random();
        long inputNegative = -(abs(random.nextInt()) + 2);
        long inputZero = 0;
        long inputLarge = (Long.MAX_VALUE - 1) / 3 + 1;

        try {
            List<Long> resultNegative = sequenceOf(inputNegative);
            fail("Exception not thrown for negative input!");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed for negative input");
        }

        try {
            List<Long> resultZero = sequenceOf(inputZero);
            fail("Exception not thrown for input of zero!");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed for input of zero");
        }

        try {
            List<Long> resultLarge = sequenceOf(inputLarge);
            fail("Exception not thrown for large input!");
        } catch (Exception e) {
            System.out.println("Test passed for large input");
        }
    }
}
