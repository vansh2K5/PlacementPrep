package p0105;

import common.TreeNode;
import java.util.*;

class Solution {
    private int preIdx = 0;
    private final Map<Integer, Integer> inPos = new HashMap<>(); // value -> index in inorder

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) inPos.put(inorder[i], i);
        return build(preorder, 0, inorder.length - 1);
    }

    // build the subtree whose inorder values are inorder[lo..hi]
    private TreeNode build(int[] preorder, int lo, int hi) {
        if (lo > hi) return null;
        int rootVal = preorder[preIdx++];       // preorder: root comes first
        TreeNode root = new TreeNode(rootVal);
        int mid = inPos.get(rootVal);           // splits inorder into left | root | right
        root.left = build(preorder, lo, mid - 1);
        root.right = build(preorder, mid + 1, hi);
        return root;
    }
}
