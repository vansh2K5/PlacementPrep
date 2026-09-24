package p0295;

import java.util.*;

class MedianFinder {
    private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder()); // max-heap: smaller half
    private final PriorityQueue<Integer> high = new PriorityQueue<>();                        // min-heap: larger half

    public void addNum(int num) {
        low.offer(num);
        high.offer(low.poll());                 // move the largest of the small half up
        if (high.size() > low.size()) {
            low.offer(high.poll());             // keep low the same size or one bigger
        }
    }

    public double findMedian() {
        if (low.size() > high.size()) return low.peek();
        return (low.peek() + (long) high.peek()) / 2.0;
    }
}
