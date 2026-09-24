package p0121;

class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE; // cheapest day to buy so far
        int best = 0;
        for (int p : prices) {
            minPrice = Math.min(minPrice, p);
            best = Math.max(best, p - minPrice); // sell today
        }
        return best;
    }
}
