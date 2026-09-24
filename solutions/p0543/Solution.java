package p0543;

import common.TreeNode;

class Solution {
    private int best = 0; // longest path (in edges) seen anywhere

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return best;
    }

    // returns the height of the subtree in nodes
    private int height(TreeNode node) {
        if (node == null) return 0;
        int l = height(node.left), r = height(node.right);
        best = Math.max(best, l + r); // path that bends at this node
        return 1 + Math.max(l, r);
    }
}
