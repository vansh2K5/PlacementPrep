package p0215;

import java.util.*;

class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // keeps the k largest seen so far
        for (int x : nums) {
            minHeap.offer(x);
            if (minHeap.size() > k) minHeap.poll(); // drop the smallest
        }
        return minHeap.peek(); // smallest of the k largest = kth largest
    }
}
