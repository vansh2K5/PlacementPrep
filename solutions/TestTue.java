import common.Check;
import java.util.*;

public class TestTue {
    public static void main(String[] a) throws Exception {
        Check.eq("125", true, T.call("p0125", "isPalindrome", "A man, a plan, a canal: Panama"));
        Check.eq("125b", false, T.call("p0125", "isPalindrome", "race a car"));
        Check.eq("125c", true, T.call("p0125", "isPalindrome", " "));
        Check.eq("125d", false, T.call("p0125", "isPalindrome", "0P"));
        Check.eq("121", 5, T.call("p0121", "maxProfit", new int[]{7, 1, 5, 3, 6, 4}));
        Check.eq("121b", 0, T.call("p0121", "maxProfit", new int[]{7, 6, 4, 3, 1}));
        Check.eq("704", 4, T.call("p0704", "search", new int[]{-1, 0, 3, 5, 9, 12}, 9));
        Check.eq("704b", -1, T.call("p0704", "search", new int[]{-1, 0, 3, 5, 9, 12}, 2));
        Check.eq("167", new int[]{1, 2}, T.call("p0167", "twoSum", new int[]{2, 7, 11, 15}, 9));
        Check.eq("167b", new int[]{1, 3}, T.call("p0167", "twoSum", new int[]{2, 3, 4}, 6));
        Check.eq("167c", new int[]{1, 2}, T.call("p0167", "twoSum", new int[]{-1, 0}, -1));
        @SuppressWarnings("unchecked") List<List<Integer>> t = (List<List<Integer>>) T.call("p0015", "threeSum", (Object) new int[]{-1, 0, 1, 2, -1, -4});
        Check.sameGroups("15", List.of(List.of(-1, -1, 2), List.of(-1, 0, 1)), t);
        @SuppressWarnings("unchecked") List<List<Integer>> t2 = (List<List<Integer>>) T.call("p0015", "threeSum", (Object) new int[]{0, 0, 0, 0});
        Check.sameGroups("15b", List.of(List.of(0, 0, 0)), t2);
        @SuppressWarnings("unchecked") List<List<Integer>> t3 = (List<List<Integer>>) T.call("p0015", "threeSum", (Object) new int[]{-2, 0, 0, 2, 2});
        Check.sameGroups("15c", List.of(List.of(-2, 0, 2)), t3);
        Check.eq("11", 49, T.call("p0011", "maxArea", new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        Check.eq("11b", 1, T.call("p0011", "maxArea", new int[]{1, 1}));
        Check.eq("3", 3, T.call("p0003", "lengthOfLongestSubstring", "abcabcbb"));
        Check.eq("3b", 1, T.call("p0003", "lengthOfLongestSubstring", "bbbbb"));
        Check.eq("3c", 3, T.call("p0003", "lengthOfLongestSubstring", "pwwkew"));
        Check.eq("3d", 0, T.call("p0003", "lengthOfLongestSubstring", ""));
        Check.eq("3e", 2, T.call("p0003", "lengthOfLongestSubstring", "abba"));
        Check.eq("424", 4, T.call("p0424", "characterReplacement", "ABAB", 2));
        Check.eq("424b", 4, T.call("p0424", "characterReplacement", "AABABBA", 1));
        Check.eq("567", true, T.call("p0567", "checkInclusion", "ab", "eidbaooo"));
        Check.eq("567b", false, T.call("p0567", "checkInclusion", "ab", "eidboaoo"));
        Check.eq("567c", false, T.call("p0567", "checkInclusion", "abc", "ab"));
        Check.eq("33", 4, T.call("p0033", "search", new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        Check.eq("33b", -1, T.call("p0033", "search", new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        Check.eq("33c", -1, T.call("p0033", "search", new int[]{1}, 0));
        Check.eq("33d", 1, T.call("p0033", "search", new int[]{3, 1}, 1));
        Check.eq("875", 4, T.call("p0875", "minEatingSpeed", new int[]{3, 6, 7, 11}, 8));
        Check.eq("875b", 30, T.call("p0875", "minEatingSpeed", new int[]{30, 11, 23, 4, 20}, 5));
        Check.eq("875c", 23, T.call("p0875", "minEatingSpeed", new int[]{30, 11, 23, 4, 20}, 6));
        Check.eq("875d", 4, T.call("p0875", "minEatingSpeed", new int[]{1000000000, 1000000000}, 1000000000 / 3 * 2 + 1));
        Check.eq("42", 6, T.call("p0042", "trap", new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        Check.eq("42b", 9, T.call("p0042", "trap", new int[]{4, 2, 0, 3, 2, 5}));
        T.done();
    }
}
