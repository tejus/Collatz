package collatz;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Ignore("No need to run a twenty seconds long test each time")
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

        System.out.println("Testing collatz_1() with inputs " + inputOne + " " + inputEven + " " + inputOdd);
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
            fail("Exception not thrown for negative input in collatz_1()!");
        } catch (IllegalArgumentException e) {
            System.out.println("collatz_1() test passed for negative input");
        }

        try {
            long resultZero = collatz_1(inputZero);
            fail("Exception not thrown for input of zero in collatz_1()!");
        } catch (IllegalArgumentException e) {
            System.out.println("collatz_1() test passed for input of zero");
        }
        try {
            long resultLarge = collatz_1(inputLarge);
            fail("Exception not thrown for large input in collatz_1()!");
        } catch (Exception e) {
            System.out.println("collatz_1() test passed for large input");
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

        System.out.println("Testing sequenceOf() with random input " + inputRandom);
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
            fail("Exception not thrown for negative input in sequenceOf()!");
        } catch (IllegalArgumentException e) {
            System.out.println("sequenceOf() test passed for negative input");
        }

        try {
            List<Long> resultZero = sequenceOf(inputZero);
            fail("Exception not thrown for input of zero in sequenceOf()!");
        } catch (IllegalArgumentException e) {
            System.out.println("sequenceOf() test passed for input of zero");
        }

        try {
            List<Long> resultLarge = sequenceOf(inputLarge);
            fail("Exception not thrown for large input in sequenceOf()!");
        } catch (Exception e) {
            System.out.println("sequenceOf() test passed for large input");
        }
    }

    @Test
    public void lengthOfSequenceShouldReturnValidLength() {
        Random random = new Random();
        long inputOne = 1;
        long inputTwo = 2;
        long inputThree = 3;
        long inputRandom = abs(random.nextInt()) + 2;

        assert lengthOfSequence(inputOne) == 1;
        assert lengthOfSequence(inputTwo) == 2;
        assert lengthOfSequence(inputThree) == 8;
        assert lengthOfSequence(inputRandom) > 0;
    }

    @Test
    public void lengthOfSequenceWithInvalidInputShouldThrowException() {
        Random random = new Random();
        long inputNegative = -(abs(random.nextInt()) + 2);
        long inputZero = 0;
        long inputLarge = (Long.MAX_VALUE - 1) / 3 + 1;

        try {
            int resultNegative = lengthOfSequence(inputNegative);
            fail("Exception not thrown for negative input in lengthOfSequence()!");
        } catch (IllegalArgumentException e) {
            System.out.println("lengthOfSequence() test passed for negative input");
        }

        try {
            int resultZero = lengthOfSequence(inputZero);
            fail("Exception not thrown for input of zero in lengthOfSequence()!");
        } catch (IllegalArgumentException e) {
            System.out.println("lengthOfSequence() test passed for input of zero");
        }

        try {
            int resultLarge = lengthOfSequence(inputLarge);
            fail("Exception not thrown for large input in lengthOfSequence()!");
        } catch (Exception e) {
            System.out.println("lengthOfSequence() test passed for large input");
        }
    }

    @Test
    public void largestValueInSequenceShouldReturnValidValue() {
        long inputHundred = 100;
        long largestValueInHundred = 100;
        long inputPrime = 104723;
        long largestValueInPrime = 1006504;

        assert largestValueInSequence(inputHundred) == largestValueInHundred;
        assert largestValueInSequence(inputPrime) == largestValueInPrime;
    }

    @Test
    public void largestValueInSequenceWithInvalidInputShouldThrowException() {
        Random random = new Random();
        long inputNegative = -(abs(random.nextInt()) + 2);
        long inputZero = 0;
        long inputLarge = (Long.MAX_VALUE - 1) / 3 + 1;

        try {
            long resultNegative = largestValueInSequence(inputNegative);
            fail("Exception not thrown for negative input in sequenceOf()!");
        } catch (IllegalArgumentException e) {
            System.out.println("sequenceOf() test passed for negative input");
        }

        try {
            long resultZero = largestValueInSequence(inputZero);
            fail("Exception not thrown for input of zero in sequenceOf()!");
        } catch (IllegalArgumentException e) {
            System.out.println("sequenceOf() test passed for input of zero");
        }

        try {
            long resultLarge = largestValueInSequence(inputLarge);
            fail("Exception not thrown for large input in sequenceOf()!");
        } catch (Exception e) {
            System.out.println("sequenceOf() test passed for large input");
        }
    }

    @Test
    public void equalLengthTwinsOf28To30ShouldReturnTwoItems() {
        long lo = 28;
        long hi = 30;

        List<Pair<Long, Integer>> resultOne = equalLengthTwins(lo, lo);
        List<Pair<Long, Integer>> resultTwo = equalLengthTwins(lo, hi);

        assert resultOne != null;
        assert resultTwo != null;
        assert resultOne.size() == 1;
        assert resultTwo.size() == 2;
        assert resultOne.contains(new Pair<>(28L, 19));
        assert resultTwo.contains(new Pair<>(28L, 19));
        assert resultTwo.contains(new Pair<>(29L, 19));
    }

    @Test
    public void equalLengthTwinsWithAnyInvalidInputShouldThrowException() {
        Random random = new Random();
        long inputNegative = -(abs(random.nextInt()) + 2);
        long inputZero = 0;
        long inputValid = 40960000;
        long inputLarge = (Long.MAX_VALUE - 1) / 3;
        long inputLarger = (Long.MAX_VALUE - 1) / 3 + 1;

        List<Long> inputs = new ArrayList<>(Arrays.asList(inputNegative, inputZero, inputValid, inputLarger));

        for (int i = 0; i < inputs.size(); i++) {
            for (int j = 0; j < inputs.size(); j++) {
                try {
                    List<Pair<Long, Integer>> result = equalLengthTwins(inputs.get(i), inputs.get(j));
                    if (i != 2 && j != 2)
                        fail("Exception not thrown for inputs in lengthOfSequence()!");
                } catch (IllegalArgumentException e) {
                    System.out.println("lengthOfSequence() test passed for invalid inputs "
                            + inputs.get(i) + " and " + inputs.get(j));
                }
            }
        }

        try {
            List<Pair<Long, Integer>> result = equalLengthTwins(inputValid, inputLarge);
            fail("Exception not thrown for inputs in lengthOfSequence()!");
        } catch (IllegalArgumentException e) {
            System.out.println("lengthOfSequence() test passed for large hi input");
        }

        long inputValidLo = 5;
        try {
            List<Pair<Long, Integer>> result = equalLengthTwins(inputValid, inputValidLo);
            fail("Exception not thrown for inputs in lengthOfSequence()!");
        } catch (IllegalArgumentException e) {
            System.out.println("lengthOfSequence() test passed for incorrect order of inputs");
        }
    }

    @Test
    public void equalMaxValueTwinsOf5And6ShouldReturn5And16() {
        long lo = 5;
        long hi = 6;

        List<Pair<Long, Long>> result = equalMaxValueTwins(lo, hi);

        assert result != null;
        assert result.size() == 1;
        assert result.contains(new Pair<>(5L, 16L));
    }

    @Test
    public void equalMaxValueTwinsWithInvalidInputShouldThrowException() {
        Random random = new Random();
        long inputNegative = -(abs(random.nextInt()) + 2);
        long inputZero = 0;
        long inputValid = 40960000;
        long inputLarge = (Long.MAX_VALUE - 1) / 3;

        List<Long> inputs = new ArrayList<>(Arrays.asList(inputNegative, inputZero, inputValid, inputLarge));

        for (int i = 0; i < inputs.size(); i++) {
            for (int j = 0; j < inputs.size(); j++) {
                try {
                    List<Pair<Long, Long>> result = equalMaxValueTwins(inputs.get(i), inputs.get(j));
                    if (i != 2 && j != 2)
                        fail("Exception not thrown for inputs in lengthOfSequence()!");
                } catch (IllegalArgumentException e) {
                    System.out.println("lengthOfSequence() test passed for invalid inputs "
                            + inputs.get(i) + " and " + inputs.get(j));
                }
            }

            long inputValidLo = 5;
            try {
                List<Pair<Long, Long>> result = equalMaxValueTwins(inputValid, inputValidLo);
                fail("Exception not thrown for inputs in lengthOfSequence()!");
            } catch (IllegalArgumentException e) {
                System.out.println("lengthOfSequence() test passed for incorrect order of inputs");
            }
        }
    }
}
