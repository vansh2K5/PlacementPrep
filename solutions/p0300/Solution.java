package p0300;

class Solution {
    public int lengthOfLIS(int[] nums) {
        // tails[i] = smallest possible tail of an increasing subsequence of length i+1
        int[] tails = new int[nums.length];
        int len = 0;
        for (int x : nums) {
            int lo = 0, hi = len;            // first index with tails[idx] >= x
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < x) lo = mid + 1;
                else hi = mid;
            }
            tails[lo] = x;                   // extend (lo == len) or make a tail smaller
            if (lo == len) len++;
        }
        return len;
    }
}
