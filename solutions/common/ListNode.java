package common;

public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode of(int... vals) {
        ListNode dummy = new ListNode(), cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    public static String str(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode n = head; n != null; n = n.next) sb.append(n.val).append(n.next != null ? "," : "");
        return sb.append("]").toString();
    }
}
