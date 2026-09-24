package p0133;

import java.util.*;

class Node {
    public int val;
    public List<Node> neighbors;
    public Node(int val) { this.val = val; neighbors = new ArrayList<>(); }
}

class Solution {
    private final Map<Node, Node> copies = new HashMap<>(); // original -> clone

    public Node cloneGraph(Node node) {
        if (node == null) return null;
        if (copies.containsKey(node)) return copies.get(node); // already cloned (handles cycles)
        Node copy = new Node(node.val);
        copies.put(node, copy);                                // register BEFORE recursing
        for (Node nb : node.neighbors) copy.neighbors.add(cloneGraph(nb));
        return copy;
    }
}
