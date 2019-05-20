package collatz;

import org.junit.Ignore;
import org.junit.Test;

import static collatz.Collatz.*;

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
}
