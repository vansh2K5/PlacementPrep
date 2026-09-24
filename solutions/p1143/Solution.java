package p1143;

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1]; // dp[i][j] = LCS of text1[0..i) and text2[0..j)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;              // use the matching char
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // drop one char
                }
            }
        }
        return dp[m][n];
    }
}
