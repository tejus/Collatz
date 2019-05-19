package collatz;

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
}
