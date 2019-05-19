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
}
