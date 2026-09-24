package p0739;

import java.util.*;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // indices of days still waiting for a warmer day
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int j = stack.pop();
                res[j] = i - j; // day i is the first warmer day after j
            }
            stack.push(i);
        }
        return res; // days left on the stack stay 0
    }
}
