package common;

import java.util.*;

public final class Check {
    public static int passed = 0, failed = 0;

    public static void eq(String name, Object expected, Object actual) {
        boolean ok = Objects.deepEquals(expected, actual);
        if (ok) passed++; else failed++;
        if (!ok) System.out.println("FAIL " + name + ": expected " + show(expected) + " but got " + show(actual));
    }

    /** Compare collections of collections ignoring order at both levels. */
    public static <T extends Comparable<T>> void sameGroups(String name, List<List<T>> expected, List<List<T>> actual) {
        eq(name, normalize(expected), normalize(actual));
    }

    private static <T extends Comparable<T>> List<List<T>> normalize(List<List<T>> in) {
        List<List<T>> out = new ArrayList<>();
        for (List<T> g : in) { List<T> c = new ArrayList<>(g); Collections.sort(c); out.add(c); }
        out.sort(Comparator.comparing(Object::toString));
        return out;
    }

    private static String show(Object o) {
        if (o instanceof int[] a) return Arrays.toString(a);
        if (o instanceof int[][] a) return Arrays.deepToString(a);
        return String.valueOf(o);
    }
}
