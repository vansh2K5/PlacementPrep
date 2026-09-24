package p0070;

class Solution {
    public int climbStairs(int n) {
        int oneBack = 1, twoBack = 1; // ways to reach step i-1 and i-2 (step 0 and "step -1")
        for (int i = 2; i <= n; i++) {
            int cur = oneBack + twoBack; // last move was 1 step or 2 steps
            twoBack = oneBack;
            oneBack = cur;
        }
        return oneBack;
    }
}
