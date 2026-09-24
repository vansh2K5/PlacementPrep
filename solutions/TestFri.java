import common.*;
import java.lang.reflect.*;
import java.util.*;

public class TestFri {
    static String S(Object o) { return TreeNode.str((TreeNode) o); }

    static TreeNode find(TreeNode r, int v) {
        if (r == null) return null;
        if (r.val == v) return r;
        TreeNode l = find(r.left, v);
        return l != null ? l : find(r.right, v);
    }

    public static void main(String[] a) throws Exception {
        Check.eq("226", "[4,7,2,9,6,3,1]", S(T.call("p0226", "invertTree", TreeNode.of(4, 2, 7, 1, 3, 6, 9))));
        Check.eq("226b", "[]", S(T.call("p0226", "invertTree", (Object) null)));
        Check.eq("104", 3, T.call("p0104", "maxDepth", TreeNode.of(3, 9, 20, null, null, 15, 7)));
        Check.eq("104b", 0, T.call("p0104", "maxDepth", (Object) null));
        Check.eq("543", 3, T.call("p0543", "diameterOfBinaryTree", TreeNode.of(1, 2, 3, 4, 5)));
        Check.eq("543b", 1, T.call("p0543", "diameterOfBinaryTree", TreeNode.of(1, 2)));
        Check.eq("543c", 4, T.call("p0543", "diameterOfBinaryTree", TreeNode.of(1, null, 2, 3, 4, 5, null, null, 6)));
        Check.eq("100", true, T.call("p0100", "isSameTree", TreeNode.of(1, 2, 3), TreeNode.of(1, 2, 3)));
        Check.eq("100b", false, T.call("p0100", "isSameTree", TreeNode.of(1, 2), TreeNode.of(1, null, 2)));
        Check.eq("102", List.of(List.of(3), List.of(9, 20), List.of(15, 7)), T.call("p0102", "levelOrder", TreeNode.of(3, 9, 20, null, null, 15, 7)));
        Check.eq("102b", List.of(), T.call("p0102", "levelOrder", (Object) null));
        Check.eq("199", List.of(1, 3, 4), T.call("p0199", "rightSideView", TreeNode.of(1, 2, 3, null, 5, null, 4)));
        Check.eq("199b", List.of(1, 3, 4, 5), T.call("p0199", "rightSideView", TreeNode.of(1, 2, 3, 4, null, null, null, 5)));
        Check.eq("98", true, T.call("p0098", "isValidBST", TreeNode.of(2, 1, 3)));
        Check.eq("98b", false, T.call("p0098", "isValidBST", TreeNode.of(5, 1, 4, null, null, 3, 6)));
        Check.eq("98c", false, T.call("p0098", "isValidBST", TreeNode.of(5, 4, 6, null, null, 3, 7)));
        Check.eq("98d", true, T.call("p0098", "isValidBST", TreeNode.of(2147483647)));
        Check.eq("98e", false, T.call("p0098", "isValidBST", TreeNode.of(2, 2, 2)));
        TreeNode bst = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        Check.eq("235", 6, ((TreeNode) T.call("p0235", "lowestCommonAncestor", bst, find(bst, 2), find(bst, 8))).val);
        Check.eq("235b", 2, ((TreeNode) T.call("p0235", "lowestCommonAncestor", bst, find(bst, 2), find(bst, 4))).val);
        Check.eq("230", 1, T.call("p0230", "kthSmallest", TreeNode.of(3, 1, 4, null, 2), 1));
        Check.eq("230b", 3, T.call("p0230", "kthSmallest", TreeNode.of(5, 3, 6, 2, 4, null, null, 1), 3));
        Check.eq("105", "[3,9,20,null,null,15,7]", S(T.call("p0105", "buildTree", new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7})));
        Check.eq("105b", "[-1]", S(T.call("p0105", "buildTree", new int[]{-1}, new int[]{-1})));
        Check.eq("105c", "[1,2,null,3]", S(T.call("p0105", "buildTree", new int[]{1, 2, 3}, new int[]{3, 2, 1})));
        Check.eq("215", 5, T.call("p0215", "findKthLargest", new int[]{3, 2, 1, 5, 6, 4}, 2));
        Check.eq("215b", 4, T.call("p0215", "findKthLargest", new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
        Class<?> c = Class.forName("p0295.MedianFinder");
        Constructor<?> k = c.getDeclaredConstructor(); k.setAccessible(true);
        Object mf = k.newInstance();
        Method add = c.getDeclaredMethod("addNum", int.class), med = c.getDeclaredMethod("findMedian");
        add.setAccessible(true); med.setAccessible(true);
        add.invoke(mf, 1); add.invoke(mf, 2);
        Check.eq("295a", 1.5, med.invoke(mf));
        add.invoke(mf, 3);
        Check.eq("295b", 2.0, med.invoke(mf));
        Object mf2 = k.newInstance();
        add.invoke(mf2, Integer.MAX_VALUE); add.invoke(mf2, Integer.MAX_VALUE);
        Check.eq("295c", (double) Integer.MAX_VALUE, med.invoke(mf2));
        Object mf3 = k.newInstance();
        for (int x : new int[]{5, 15, 1, 3}) add.invoke(mf3, x);
        Check.eq("295d", 4.0, med.invoke(mf3));
        T.done();
    }
}
