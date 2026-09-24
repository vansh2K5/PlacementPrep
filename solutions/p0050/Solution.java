package p0050;

class Solution {
    public double myPow(double x, int n) {
        long N = n;              // long: -Integer.MIN_VALUE overflows int
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return fastPow(x, N);
    }

    private double fastPow(double x, long n) {
        if (n == 0) return 1.0;
        double half = fastPow(x, n / 2);   // compute once, reuse
        return (n % 2 == 0) ? half * half : half * half * x;
    }
}
