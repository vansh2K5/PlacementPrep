package p0347;

import java.util.*;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) freq.merge(x, 1, Integer::sum);

        // bucket[f] = numbers that appear exactly f times
        List<Integer>[] bucket = new List[nums.length + 1];
        for (var e : freq.entrySet()) {
            int f = e.getValue();
            if (bucket[f] == null) bucket[f] = new ArrayList<>();
            bucket[f].add(e.getKey());
        }

        int[] res = new int[k];
        int idx = 0;
        for (int f = nums.length; f >= 1 && idx < k; f--) {
            if (bucket[f] == null) continue;
            for (int x : bucket[f]) {
                if (idx == k) break;
                res[idx++] = x;
            }
        }
        return res;
    }
}
