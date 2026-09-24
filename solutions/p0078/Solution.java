package p0078;

import java.util.*;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> res) {
        res.add(new ArrayList<>(path));          // every path is a valid subset
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);                   // choose
            backtrack(nums, i + 1, path, res);   // explore
            path.remove(path.size() - 1);        // un-choose
        }
    }
}
