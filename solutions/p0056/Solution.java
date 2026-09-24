package p0056;

import java.util.*;

class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> res = new ArrayList<>();
        for (int[] in : intervals) {
            if (res.isEmpty() || res.get(res.size() - 1)[1] < in[0]) {
                res.add(new int[]{in[0], in[1]});           // no overlap: new interval
            } else {
                int[] last = res.get(res.size() - 1);
                last[1] = Math.max(last[1], in[1]);        // overlap: stretch the last one
            }
        }
        return res.toArray(new int[0][]);
    }
}
