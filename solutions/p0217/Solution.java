package p0217;

import java.util.*;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (!seen.add(x)) return true; // add() returns false if x was already there
        }
        return false;
    }
}
