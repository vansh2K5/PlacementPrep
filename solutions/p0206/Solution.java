package p0206;

import common.ListNode;

class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, cur = head;
        while (cur != null) {
            ListNode next = cur.next; // save the rest of the list
            cur.next = prev;          // flip the arrow
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
