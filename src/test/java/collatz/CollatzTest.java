package collatz;

import org.junit.Ignore;
import org.junit.Test;

public class CollatzTest {

    @Test(expected = IllegalArgumentException.class)
    public void collatzOfZeroShouldThrowIllegalArgumentException() {
        Collatz.collatz(0);
    }

    @Test
    public void collatzOfOneShouldReturnOne() {
        assert Collatz.collatz(1) == 1;
    }

    @Test
    public void collatzOfTwoShouldReturnOne() {
        assert Collatz.collatz(2) == 1;
    }

    @Test
    @Ignore("No need to run a two and a half minute long test each time")
    public void collatzOfBigNumbersShouldReturnOne() {
        //NB: BigNumbers = 163840000 for now.
        for (long i = 3; i <= 163840000; i++) {
            assert Collatz.collatz(i) == 1;
        }
    }

    @Test
    public void sequenceLengthsTillThree() {
        int[] lengths = Collatz.simpleComputeSequenceLengths(3);
        assert lengths.length == 4;
        assert lengths[1] == 1;
        assert lengths[2] == 2;
        assert lengths[3] == 8;
    }

    @Test
    public void memoizedSequenceLengthsTillNine() {
        int[] lengths = Collatz.memoizedComputeSequenceLengths(9);
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
            int[] simple = Collatz.simpleComputeSequenceLengths(i);
            int[] memoized = Collatz.memoizedComputeSequenceLengths(i);
            for (int j = 1; j <= i; j++)
                assert simple[j] == memoized[j];
        }
    }

    @Test
    public void timeDifferenceShouldMatchParameter() {
        long delayDurationRequested = 5 * (long) Math.pow(10, 9);
        long runDuration = Collatz.doTimings(delayDurationRequested);
        //Define maximum variance of 10ms
        long maxVariance = 10 * (long) Math.pow(10, 6);
        System.out.println(runDuration);
        assert Math.abs(delayDurationRequested - runDuration) < maxVariance;
    }
}
