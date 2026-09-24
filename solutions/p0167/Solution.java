package p0167;

class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int sum = numbers[l] + numbers[r];
            if (sum == target) return new int[]{l + 1, r + 1}; // 1-indexed
            if (sum < target) l++;  // need bigger
            else r--;               // need smaller
        }
        return new int[0];
    }
}
