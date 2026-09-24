package p0417;

import java.util.*;

class Solution {
    private int rows, cols;
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        rows = heights.length;
        cols = heights[0].length;
        boolean[][] pac = new boolean[rows][cols], atl = new boolean[rows][cols];
        // flow "uphill" from each ocean's border
        for (int r = 0; r < rows; r++) {
            dfs(heights, pac, r, 0);
            dfs(heights, atl, r, cols - 1);
        }
        for (int c = 0; c < cols; c++) {
            dfs(heights, pac, 0, c);
            dfs(heights, atl, rows - 1, c);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (pac[r][c] && atl[r][c]) res.add(List.of(r, c));
            }
        }
        return res;
    }

    private void dfs(int[][] h, boolean[][] seen, int r, int c) {
        if (seen[r][c]) return;
        seen[r][c] = true;
        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) continue;
            if (h[nr][nc] >= h[r][c]) dfs(h, seen, nr, nc); // water could flow from (nr,nc) down to here
        }
    }
}
