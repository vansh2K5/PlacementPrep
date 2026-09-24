package p0039;

import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);                 // lets us stop early
        List<List<Integer>> res = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int[] c, int remaining, int start, List<Integer> path, List<List<Integer>> res) {
        if (remaining == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < c.length; i++) {
            if (c[i] > remaining) break;         // sorted, so everything after is too big too
            path.add(c[i]);
            backtrack(c, remaining - c[i], i, path, res); // i, not i+1: reuse allowed
            path.remove(path.size() - 1);
        }
    }
}
