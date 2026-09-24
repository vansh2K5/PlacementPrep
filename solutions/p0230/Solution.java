package p0230;

import common.TreeNode;
import java.util.*;

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {        // go as far left as possible
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();           // next smallest value
            if (--k == 0) return cur.val;
            cur = cur.right;
        }
        return -1; // k is always valid
    }
}
