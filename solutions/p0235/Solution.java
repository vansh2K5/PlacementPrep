package p0235;

import common.TreeNode;

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        while (cur != null) {
            if (p.val < cur.val && q.val < cur.val) cur = cur.left;        // both on the left
            else if (p.val > cur.val && q.val > cur.val) cur = cur.right;  // both on the right
            else return cur;                                                // they split here
        }
        return null;
    }
}
