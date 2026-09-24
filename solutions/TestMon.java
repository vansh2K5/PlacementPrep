import common.Check;
import java.util.*;

public class TestMon {
    public static void main(String[] a) throws Exception {
        Check.eq("1", new int[]{0, 1}, T.call("p0001", "twoSum", new int[]{2, 7, 11, 15}, 9));
        Check.eq("1b", new int[]{1, 2}, T.call("p0001", "twoSum", new int[]{3, 2, 4}, 6));
        Check.eq("1c", new int[]{0, 1}, T.call("p0001", "twoSum", new int[]{3, 3}, 6));
        Check.eq("217", true, T.call("p0217", "containsDuplicate", new int[]{1, 2, 3, 1}));
        Check.eq("217b", false, T.call("p0217", "containsDuplicate", new int[]{1, 2, 3, 4}));
        Check.eq("242", true, T.call("p0242", "isAnagram", "anagram", "nagaram"));
        Check.eq("242b", false, T.call("p0242", "isAnagram", "rat", "car"));
        @SuppressWarnings("unchecked")
        List<List<String>> g = (List<List<String>>) T.call("p0049", "groupAnagrams", (Object) new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        Check.sameGroups("49", List.of(List.of("bat"), List.of("nat", "tan"), List.of("ate", "eat", "tea")), g);
        int[] top = (int[]) T.call("p0347", "topKFrequent", new int[]{1, 1, 1, 2, 2, 3}, 2);
        Arrays.sort(top);
        Check.eq("347", new int[]{1, 2}, top);
        Check.eq("347b", new int[]{1}, T.call("p0347", "topKFrequent", new int[]{1}, 1));
        Check.eq("238", new int[]{24, 12, 8, 6}, T.call("p0238", "productExceptSelf", new int[]{1, 2, 3, 4}));
        Check.eq("238b", new int[]{0, 0, 9, 0, 0}, T.call("p0238", "productExceptSelf", new int[]{-1, 1, 0, -3, 3}));
        Check.eq("128", 4, T.call("p0128", "longestConsecutive", new int[]{100, 4, 200, 1, 3, 2}));
        Check.eq("128b", 9, T.call("p0128", "longestConsecutive", new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        Check.eq("128c", 0, T.call("p0128", "longestConsecutive", new int[]{}));
        char[][] valid = {
            "53..7....".toCharArray(), "6..195...".toCharArray(), ".98....6.".toCharArray(),
            "8...6...3".toCharArray(), "4..8.3..1".toCharArray(), "7...2...6".toCharArray(),
            ".6....28.".toCharArray(), "...419..5".toCharArray(), "....8..79".toCharArray()};
        Check.eq("36", true, T.call("p0036", "isValidSudoku", (Object) valid));
        char[][] bad = new char[9][];
        for (int i = 0; i < 9; i++) bad[i] = valid[i].clone();
        bad[0][0] = '8';
        Check.eq("36b", false, T.call("p0036", "isValidSudoku", (Object) bad));
        Check.eq("53", 6, T.call("p0053", "maxSubArray", new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        Check.eq("53b", -1, T.call("p0053", "maxSubArray", new int[]{-3, -1, -2}));
        Check.eq("560", 2, T.call("p0560", "subarraySum", new int[]{1, 1, 1}, 2));
        Check.eq("560b", 2, T.call("p0560", "subarraySum", new int[]{1, 2, 3}, 3));
        Check.eq("560c", 3, T.call("p0560", "subarraySum", new int[]{1, -1, 0}, 0));
        Check.eq("56", new int[][]{{1, 6}, {8, 10}, {15, 18}}, T.call("p0056", "merge", (Object) new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}));
        Check.eq("56b", new int[][]{{1, 5}}, T.call("p0056", "merge", (Object) new int[][]{{1, 4}, {4, 5}}));
        Check.eq("41", 3, T.call("p0041", "firstMissingPositive", new int[]{1, 2, 0}));
        Check.eq("41b", 2, T.call("p0041", "firstMissingPositive", new int[]{3, 4, -1, 1}));
        Check.eq("41c", 1, T.call("p0041", "firstMissingPositive", new int[]{7, 8, 9, 11, 12}));
        Check.eq("41d", 2, T.call("p0041", "firstMissingPositive", new int[]{1, 1}));
        T.done();
    }
}
