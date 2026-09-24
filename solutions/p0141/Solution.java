package p0141;

import common.ListNode;

class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;          // 1 step
            fast = fast.next.next;     // 2 steps
            if (slow == fast) return true;
        }
        return false;
    }
}
