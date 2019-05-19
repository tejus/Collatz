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
}
