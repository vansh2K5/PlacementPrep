package p0011;

class Solution {
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1, best = 0;
        while (l < r) {
            int h = Math.min(height[l], height[r]);
            best = Math.max(best, h * (r - l));
            // the shorter wall limits the area, so move it
            if (height[l] < height[r]) l++;
            else r--;
        }
        return best;
    }
}
