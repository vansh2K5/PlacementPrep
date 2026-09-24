package p0051;

import java.util.*;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');
        boolean[] cols = new boolean[n];
        boolean[] diag = new boolean[2 * n - 1];     // r - c + n - 1 is constant on "\" diagonals
        boolean[] anti = new boolean[2 * n - 1];     // r + c is constant on "/" diagonals
        place(0, n, board, cols, diag, anti, res);
        return res;
    }

    private void place(int r, int n, char[][] board, boolean[] cols, boolean[] diag, boolean[] anti,
                       List<List<String>> res) {
        if (r == n) {
            List<String> sol = new ArrayList<>();
            for (char[] row : board) sol.add(new String(row));
            res.add(sol);
            return;
        }
        for (int c = 0; c < n; c++) {
            int d = r - c + n - 1, a = r + c;
            if (cols[c] || diag[d] || anti[a]) continue; // attacked
            board[r][c] = 'Q';
            cols[c] = diag[d] = anti[a] = true;
            place(r + 1, n, board, cols, diag, anti, res);
            board[r][c] = '.';
            cols[c] = diag[d] = anti[a] = false;
        }
    }
}
