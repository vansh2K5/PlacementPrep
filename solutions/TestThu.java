import common.Check;
import java.util.*;

public class TestThu {
    @SuppressWarnings("unchecked")
    static List<List<Integer>> LI(Object o) { return (List<List<Integer>>) o; }

    static List<List<Integer>> li(int[]... rows) {
        List<List<Integer>> out = new ArrayList<>();
        for (int[] r : rows) { List<Integer> l = new ArrayList<>(); for (int x : r) l.add(x); out.add(l); }
        return out;
    }

    public static void main(String[] a) throws Exception {
        Check.eq("509", 0, T.call("p0509", "fib", 0));
        Check.eq("509b", 1, T.call("p0509", "fib", 2));
        Check.eq("509c", 832040, T.call("p0509", "fib", 30));
        Check.eq("50", true, Math.abs((double) T.call("p0050", "myPow", 2.0, 10) - 1024.0) < 1e-9);
        Check.eq("50b", true, Math.abs((double) T.call("p0050", "myPow", 2.1, 3) - 9.261) < 1e-9);
        Check.eq("50c", true, Math.abs((double) T.call("p0050", "myPow", 2.0, -2) - 0.25) < 1e-9);
        Check.eq("50d", true, Math.abs((double) T.call("p0050", "myPow", 1.0, Integer.MIN_VALUE) - 1.0) < 1e-9);
        Check.eq("50e", true, Math.abs((double) T.call("p0050", "myPow", -1.0, Integer.MIN_VALUE) - 1.0) < 1e-9);
        Check.sameGroups("78", li(new int[]{}, new int[]{1}, new int[]{2}, new int[]{3}, new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 3}, new int[]{1, 2, 3}),
            LI(T.call("p0078", "subsets", (Object) new int[]{1, 2, 3})));
        Check.sameGroups("90", li(new int[]{}, new int[]{1}, new int[]{1, 2}, new int[]{1, 2, 2}, new int[]{2}, new int[]{2, 2}),
            LI(T.call("p0090", "subsetsWithDup", (Object) new int[]{1, 2, 2})));
        List<List<Integer>> perms = LI(T.call("p0046", "permute", (Object) new int[]{1, 2, 3}));
        Check.eq("46", 6, perms.size());
        Check.eq("46b", 6, new HashSet<>(perms).size());
        Check.sameGroups("39", li(new int[]{2, 2, 3}, new int[]{7}), LI(T.call("p0039", "combinationSum", new int[]{2, 3, 6, 7}, 7)));
        Check.sameGroups("39b", li(new int[]{2, 2, 2, 2}, new int[]{2, 3, 3}, new int[]{3, 5}), LI(T.call("p0039", "combinationSum", new int[]{2, 3, 5}, 8)));
        Check.sameGroups("40", li(new int[]{1, 1, 6}, new int[]{1, 2, 5}, new int[]{1, 7}, new int[]{2, 6}),
            LI(T.call("p0040", "combinationSum2", new int[]{10, 1, 2, 7, 6, 1, 5}, 8)));
        Check.sameGroups("40b", li(new int[]{1, 2, 2}, new int[]{5}), LI(T.call("p0040", "combinationSum2", new int[]{2, 5, 2, 1, 2}, 5)));
        @SuppressWarnings("unchecked") List<String> lc = (List<String>) T.call("p0017", "letterCombinations", "23");
        Collections.sort(lc);
        Check.eq("17", List.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"), lc);
        Check.eq("17b", List.of(), T.call("p0017", "letterCombinations", ""));
        @SuppressWarnings("unchecked") List<String> gp = new ArrayList<>((List<String>) T.call("p0022", "generateParenthesis", 3));
        Collections.sort(gp);
        Check.eq("22", List.of("((()))", "(()())", "(())()", "()(())", "()()()"), gp);
        char[][] b = {"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        Check.eq("79", true, T.call("p0079", "exist", b, "ABCCED"));
        Check.eq("79b", true, T.call("p0079", "exist", b, "SEE"));
        Check.eq("79c", false, T.call("p0079", "exist", b, "ABCB"));
        Check.eq("79d", "ABCE", new String(b[0]));
        @SuppressWarnings("unchecked") List<List<String>> pp = (List<List<String>>) T.call("p0131", "partition", "aab");
        Check.sameGroups("131", List.of(List.of("a", "a", "b"), List.of("aa", "b")), pp);
        @SuppressWarnings("unchecked") List<List<String>> q4 = (List<List<String>>) T.call("p0051", "solveNQueens", 4);
        Check.eq("51", List.of(List.of(".Q..", "...Q", "Q...", "..Q."), List.of("..Q.", "Q...", "...Q", ".Q..")), q4);
        @SuppressWarnings("unchecked") List<List<String>> q8 = (List<List<String>>) T.call("p0051", "solveNQueens", 8);
        Check.eq("51b", 92, q8.size());
        Check.eq("51c", 1, ((List<?>) T.call("p0051", "solveNQueens", 1)).size());
        T.done();
    }
}
