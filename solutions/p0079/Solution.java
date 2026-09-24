package p0079;

class Solution {
    public boolean exist(char[][] board, String word) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (dfs(board, word, 0, r, c)) return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] b, String word, int i, int r, int c) {
        if (i == word.length()) return true;
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length || b[r][c] != word.charAt(i)) return false;
        char saved = b[r][c];
        b[r][c] = '#';                        // mark visited
        boolean found = dfs(b, word, i + 1, r + 1, c) || dfs(b, word, i + 1, r - 1, c)
                     || dfs(b, word, i + 1, r, c + 1) || dfs(b, word, i + 1, r, c - 1);
        b[r][c] = saved;                      // restore for other paths
        return found;
    }
}
