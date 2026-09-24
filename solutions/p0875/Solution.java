package p0875;

class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);
        while (lo < hi) {                 // find the smallest speed that works
            int mid = lo + (hi - lo) / 2;
            if (hoursNeeded(piles, mid) <= h) hi = mid; // fast enough: try slower
            else lo = mid + 1;                          // too slow
        }
        return lo;
    }

    private long hoursNeeded(int[] piles, int speed) {
        long hours = 0;
        for (int p : piles) hours += (p + speed - 1) / speed; // ceil(p / speed)
        return hours;
    }
}
