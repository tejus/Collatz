package collatz;

public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A _1() {
        return first;
    }

    public B _2() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (!(obj instanceof Pair)) return false;
        return this.first == ((Pair) obj)._1()
                && this.second == ((Pair) obj).second;
    }
}
