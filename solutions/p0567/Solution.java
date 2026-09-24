package p0567;

import java.util.*;

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int n = s1.length();
        if (n > s2.length()) return false;
        int[] need = new int[26], window = new int[26];
        for (int i = 0; i < n; i++) {
            need[s1.charAt(i) - 'a']++;
            window[s2.charAt(i) - 'a']++;
        }
        if (Arrays.equals(need, window)) return true;
        for (int i = n; i < s2.length(); i++) {
            window[s2.charAt(i) - 'a']++;       // add new right char
            window[s2.charAt(i - n) - 'a']--;   // drop old left char
            if (Arrays.equals(need, window)) return true;
        }
        return false;
    }
}
