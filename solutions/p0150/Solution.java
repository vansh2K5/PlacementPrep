package p0150;

import java.util.*;

class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String t : tokens) {
            switch (t) {
                case "+" -> stack.push(stack.pop() + stack.pop());
                case "*" -> stack.push(stack.pop() * stack.pop());
                case "-" -> { int b = stack.pop(), a = stack.pop(); stack.push(a - b); }
                case "/" -> { int b = stack.pop(), a = stack.pop(); stack.push(a / b); }
                default -> stack.push(Integer.parseInt(t));
            }
        }
        return stack.pop();
    }
}
