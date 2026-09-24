import common.Check;
import java.lang.reflect.*;
import java.util.*;

public class TestSat {
    static char[][] grid(String... rows) { char[][] g = new char[rows.length][]; for (int i = 0; i < rows.length; i++) g[i] = rows[i].toCharArray(); return g; }

    public static void main(String[] a) throws Exception {
        Check.eq("70", 2, T.call("p0070", "climbStairs", 2));
        Check.eq("70b", 3, T.call("p0070", "climbStairs", 3));
        Check.eq("70c", 1, T.call("p0070", "climbStairs", 1));
        Check.eq("70d", 1836311903, T.call("p0070", "climbStairs", 45));
        Check.eq("200", 1, T.call("p0200", "numIslands", (Object) grid("11110", "11010", "11000", "00000")));
        Check.eq("200b", 3, T.call("p0200", "numIslands", (Object) grid("11000", "11000", "00100", "00011")));
        Check.eq("198", 4, T.call("p0198", "rob", new int[]{1, 2, 3, 1}));
        Check.eq("198b", 12, T.call("p0198", "rob", new int[]{2, 7, 9, 3, 1}));
        Check.eq("198c", 4, T.call("p0198", "rob", new int[]{2, 1, 1, 2}));
        // Clone graph: 1-2-3-4-1 square
        Class<?> nodeC = Class.forName("p0133.Node");
        Constructor<?> nk = nodeC.getDeclaredConstructor(int.class); nk.setAccessible(true);
        Field valF = nodeC.getField("val"), nbF = nodeC.getField("neighbors");
        valF.setAccessible(true); nbF.setAccessible(true);
        Object[] n = new Object[5];
        for (int i = 1; i <= 4; i++) n[i] = nk.newInstance(i);
        int[][] adj = {{}, {2, 4}, {1, 3}, {2, 4}, {1, 3}};
        for (int i = 1; i <= 4; i++) for (int j : adj[i]) ((List<Object>) (List<?>) nbF.get(n[i])).add(n[j]);
        Object c1 = T.call("p0133", "cloneGraph", n[1]);
        Check.eq("133a", false, c1 == n[1]);
        Check.eq("133b", 1, valF.get(c1));
        List<?> nb = (List<?>) nbF.get(c1);
        Check.eq("133c", 2, valF.get(nb.get(0)));
        Check.eq("133d", false, nb.get(0) == n[2]);
        Object c3 = ((List<?>) nbF.get(nb.get(0))).get(1);
        Check.eq("133e", 3, valF.get(c3));
        Check.eq("133f", true, ((List<?>) nbF.get(((List<?>) nbF.get(c3)).get(1))).get(0) == c1); // 3->4->1 is the same clone of 1
        Check.eq("133g", null, T.call("p0133", "cloneGraph", (Object) null));
        Check.eq("994", 4, T.call("p0994", "orangesRotting", (Object) new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));
        Check.eq("994b", -1, T.call("p0994", "orangesRotting", (Object) new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}));
        Check.eq("994c", 0, T.call("p0994", "orangesRotting", (Object) new int[][]{{0, 2}}));
        Check.eq("994d", 0, T.call("p0994", "orangesRotting", (Object) new int[][]{{0}}));
        Check.eq("322", 3, T.call("p0322", "coinChange", new int[]{1, 2, 5}, 11));
        Check.eq("322b", -1, T.call("p0322", "coinChange", new int[]{2}, 3));
        Check.eq("322c", 0, T.call("p0322", "coinChange", new int[]{1}, 0));
        Check.eq("322d", 2, T.call("p0322", "coinChange", new int[]{1, 3, 4}, 6));
        Check.eq("207", true, T.call("p0207", "canFinish", 2, new int[][]{{1, 0}}));
        Check.eq("207b", false, T.call("p0207", "canFinish", 2, new int[][]{{1, 0}, {0, 1}}));
        int[] ord = (int[]) T.call("p0210", "findOrder", 4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}});
        Check.eq("210", 4, ord.length);
        int[] pos = new int[4]; for (int i = 0; i < 4; i++) pos[ord[i]] = i;
        Check.eq("210b", true, pos[0] < pos[1] && pos[0] < pos[2] && pos[1] < pos[3] && pos[2] < pos[3]);
        Check.eq("210c", new int[0], T.call("p0210", "findOrder", 2, new int[][]{{1, 0}, {0, 1}}));
        Check.eq("210d", new int[]{0}, T.call("p0210", "findOrder", 1, new int[][]{}));
        Check.eq("300", 4, T.call("p0300", "lengthOfLIS", new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        Check.eq("300b", 4, T.call("p0300", "lengthOfLIS", new int[]{0, 1, 0, 3, 2, 3}));
        Check.eq("300c", 1, T.call("p0300", "lengthOfLIS", new int[]{7, 7, 7, 7}));
        @SuppressWarnings("unchecked") List<List<Integer>> pa = (List<List<Integer>>) T.call("p0417", "pacificAtlantic", (Object) new int[][]{
            {1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}});
        Check.eq("417", List.of(List.of(0, 4), List.of(1, 3), List.of(1, 4), List.of(2, 2), List.of(3, 0), List.of(3, 1), List.of(4, 0)), pa);
        Check.eq("417b", List.of(List.of(0, 0)), T.call("p0417", "pacificAtlantic", (Object) new int[][]{{1}}));
        Check.eq("1143", 3, T.call("p1143", "longestCommonSubsequence", "abcde", "ace"));
        Check.eq("1143b", 0, T.call("p1143", "longestCommonSubsequence", "abc", "def"));
        Check.eq("72", 3, T.call("p0072", "minDistance", "horse", "ros"));
        Check.eq("72b", 5, T.call("p0072", "minDistance", "intention", "execution"));
        Check.eq("72c", 3, T.call("p0072", "minDistance", "", "abc"));
        T.done();
    }
}
