package p0053;

class Solution {
    public int maxSubArray(int[] nums) {
        int cur = nums[0];  // best sum of a subarray ending here
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]); // extend, or start fresh
            best = Math.max(best, cur);
        }
        return best;
    }
}
