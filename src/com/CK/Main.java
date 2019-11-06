package com.CK;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new Solution().calculateMinimumHP(new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}});
    }
}

// Top down
class Solution {

    int[][] mem;

    private int helper(int[][] dungeon, int i, int j) {
        if (i >= dungeon.length || j >= dungeon[0].length)
            return Integer.MAX_VALUE;
        if (mem[i][j] > 0)
            return mem[i][j];
        int health = Integer.MAX_VALUE;
        health = Math.min(health, helper(dungeon, i + 1, j));
        health = Math.min(health, helper(dungeon, i, j + 1));
        health = health == Integer.MAX_VALUE ? 1 : health; // this only happens with i, j is P already
        int ret = health > dungeon[i][j] ? (health - dungeon[i][j]) : 1;
        mem[i][j] = ret;
        return ret;
    }

    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon.length == 0)
            return 0;
        mem = new int[dungeon.length][dungeon[0].length];
        int res = helper(dungeon, 0, 0);
        return res;
    }
}

// Bottom up
class Solution {
    int calculateMinimumHP(int[][] dungeon) {
        int M = dungeon.length;
        int N = dungeon[0].length;
        // hp[i][j] represents the min hp needed at position (i, j)
        // Add dummy row and column at bottom and right side
        int[][] dp = new int[M + 1][N + 1];
        for (int[] row : dp)
            Arrays.fill(row, Integer.MAX_VALUE);
        dp[M][N - 1] = 1;
        dp[M - 1][N] = 1;
        for (int i = M - 1; i >= 0; i--) {
            for (int j = N - 1; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = need <= 0 ? 1 : need;
            }
        }
        return dp[0][0];
    }
}