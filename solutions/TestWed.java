import common.*;
import java.lang.reflect.*;

public class TestWed {
    static String L(Object o) { return ListNode.str((ListNode) o); }

    static Object make(String cls, Object... args) throws Exception {
        Class<?> c = Class.forName(cls);
        for (Constructor<?> k : c.getDeclaredConstructors()) {
            if (k.getParameterCount() == args.length) { k.setAccessible(true); return k.newInstance(args); }
        }
        throw new NoSuchMethodException(cls);
    }

    static Object inv(Object o, String m, Object... args) throws Exception {
        for (Method x : o.getClass().getDeclaredMethods()) {
            if (x.getName().equals(m) && x.getParameterCount() == args.length) { x.setAccessible(true); return x.invoke(o, args); }
        }
        throw new NoSuchMethodException(m);
    }

    public static void main(String[] a) throws Exception {
        Check.eq("206", "[5,4,3,2,1]", L(T.call("p0206", "reverseList", ListNode.of(1, 2, 3, 4, 5))));
        Check.eq("206b", "[]", L(T.call("p0206", "reverseList", (Object) null)));
        Check.eq("21", "[1,1,2,3,4,4]", L(T.call("p0021", "mergeTwoLists", ListNode.of(1, 2, 4), ListNode.of(1, 3, 4))));
        Check.eq("21b", "[0]", L(T.call("p0021", "mergeTwoLists", null, ListNode.of(0))));
        ListNode cyc = ListNode.of(3, 2, 0, -4);
        cyc.next.next.next.next = cyc.next;
        Check.eq("141", true, T.call("p0141", "hasCycle", cyc));
        Check.eq("141b", false, T.call("p0141", "hasCycle", ListNode.of(1, 2)));
        Check.eq("141c", false, T.call("p0141", "hasCycle", (Object) null));
        Check.eq("20", true, T.call("p0020", "isValid", "()[]{}"));
        Check.eq("20b", false, T.call("p0020", "isValid", "(]"));
        Check.eq("20c", true, T.call("p0020", "isValid", "{[()]}"));
        Check.eq("20d", false, T.call("p0020", "isValid", "(("));
        Check.eq("20e", false, T.call("p0020", "isValid", "]"));
        Object q = make("p0232.MyQueue");
        inv(q, "push", 1); inv(q, "push", 2);
        Check.eq("232a", 1, inv(q, "peek"));
        Check.eq("232b", 1, inv(q, "pop"));
        inv(q, "push", 3);
        Check.eq("232c", 2, inv(q, "pop"));
        Check.eq("232d", false, inv(q, "empty"));
        Check.eq("232e", 3, inv(q, "pop"));
        Check.eq("232f", true, inv(q, "empty"));
        Object ms = make("p0155.MinStack");
        inv(ms, "push", -2); inv(ms, "push", 0); inv(ms, "push", -3);
        Check.eq("155a", -3, inv(ms, "getMin"));
        inv(ms, "pop");
        Check.eq("155b", 0, inv(ms, "top"));
        Check.eq("155c", -2, inv(ms, "getMin"));
        ListNode r1 = ListNode.of(1, 2, 3, 4);
        T.call("p0143", "reorderList", r1);
        Check.eq("143", "[1,4,2,3]", L(r1));
        ListNode r2 = ListNode.of(1, 2, 3, 4, 5);
        T.call("p0143", "reorderList", r2);
        Check.eq("143b", "[1,5,2,4,3]", L(r2));
        ListNode r3 = ListNode.of(1);
        T.call("p0143", "reorderList", r3);
        Check.eq("143c", "[1]", L(r3));
        Check.eq("19", "[1,2,3,5]", L(T.call("p0019", "removeNthFromEnd", ListNode.of(1, 2, 3, 4, 5), 2)));
        Check.eq("19b", "[]", L(T.call("p0019", "removeNthFromEnd", ListNode.of(1), 1)));
        Check.eq("19c", "[2]", L(T.call("p0019", "removeNthFromEnd", ListNode.of(1, 2), 2)));
        Check.eq("2", "[7,0,8]", L(T.call("p0002", "addTwoNumbers", ListNode.of(2, 4, 3), ListNode.of(5, 6, 4))));
        Check.eq("2b", "[8,9,9,9,0,0,0,1]", L(T.call("p0002", "addTwoNumbers", ListNode.of(9, 9, 9, 9, 9, 9, 9), ListNode.of(9, 9, 9, 9))));
        Check.eq("150", 9, T.call("p0150", "evalRPN", (Object) new String[]{"2", "1", "+", "3", "*"}));
        Check.eq("150b", 6, T.call("p0150", "evalRPN", (Object) new String[]{"4", "13", "5", "/", "+"}));
        Check.eq("150c", 22, T.call("p0150", "evalRPN", (Object) new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}));
        Check.eq("739", new int[]{1, 1, 4, 2, 1, 1, 0, 0}, T.call("p0739", "dailyTemperatures", new int[]{73, 74, 75, 71, 69, 72, 76, 73}));
        Check.eq("739b", new int[]{1, 1, 1, 0}, T.call("p0739", "dailyTemperatures", new int[]{30, 40, 50, 60}));
        Object lru = make("p0146.LRUCache", 2);
        inv(lru, "put", 1, 1); inv(lru, "put", 2, 2);
        Check.eq("146a", 1, inv(lru, "get", 1));
        inv(lru, "put", 3, 3);
        Check.eq("146b", -1, inv(lru, "get", 2));
        inv(lru, "put", 4, 4);
        Check.eq("146c", -1, inv(lru, "get", 1));
        Check.eq("146d", 3, inv(lru, "get", 3));
        Check.eq("146e", 4, inv(lru, "get", 4));
        Object lru2 = make("p0146.LRUCache", 2);
        inv(lru2, "put", 2, 1); inv(lru2, "put", 2, 2);
        Check.eq("146f", 2, inv(lru2, "get", 2));
        inv(lru2, "put", 1, 1); inv(lru2, "put", 4, 1);
        Check.eq("146g", -1, inv(lru2, "get", 2));
        T.done();
    }
}
