package p0143;

import common.ListNode;

class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // 1. find the middle
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. reverse the second half
        ListNode prev = null, cur = slow.next;
        slow.next = null; // cut the list in two
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        // 3. weave the two halves together
        ListNode first = head, second = prev;
        while (second != null) {
            ListNode n1 = first.next, n2 = second.next;
            first.next = second;
            second.next = n1;
            first = n1;
            second = n2;
        }
    }
}
