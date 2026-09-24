package p0198;

class Solution {
    public int rob(int[] nums) {
        int prev2 = 0, prev1 = 0; // best loot up to house i-2 and i-1
        for (int x : nums) {
            int cur = Math.max(prev1, prev2 + x); // skip this house, or rob it
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }
}
