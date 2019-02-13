package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * 描述: 动态规划
 * <p>
 *
 * @author: harry
 * @date: 2019-01-10
 **/
public class Exec_19_01_10 {

    public static void main(String[] args) {


        int[] arr1 = {2};
        int[] arr2 = {3, 4};
        int[] arr3 = {6, 5, 7};
        int[] arr4 = {4, 1, 8, 3};
        int[][] arr = {arr1, arr2, arr3, arr4};
        int i = calcTriangle(arr);
        System.out.println("calcTriangle:" + JSONObject.toJSONString(arr) + ",result: " + i);

        int[] wt = {4, 1, 2, 2};
        int[] vt = {3000, 1500, 2000, 2000};
        int m = 4;
        int i1 = calcPack2(m, vt, wt);
        System.out.println("calcPack2:" + ",result: " + i1);

        //背包问题，允许重复
        int i2 = calcPack2CanRepeat(m, vt, wt);
        System.out.println("calcPack2CanRepeat:" + ",result: " + i2);

        //计算最长公共子序列  LCS
        String a = "BDCABA";
        String b = "ABCABDAB";
        int i3 = calcLCS(a,b);
        System.out.println("calcLCS:" + ",result: " + i3);

        //计算最长公共子串
        int i4 = calcCommonSubStr(a,b);
        System.out.println("calcCommonSubStr:" + ",result: " + i4);
    }

    /**
     * 计算数学三角形
     * dp[i][j] 表示以arr[i][j]结束的，最小值是多少
     * dp[i][j] = Math.max(tri[i][j] + dp[i-1][lower ... upper])
     * 其中：lower 和 upper 要注意判断一下
     *
     * @param triangle
     * @return
     */
    public static int calcTriangle(int[][] triangle) {
        int[][] dp = new int[triangle.length][triangle[triangle.length - 1].length];
        dp[0][0] = triangle[0][0];
        for (int i = 1; i < triangle.length; i++) {
            for (int j = 0; j < i + 1; j++) {
                int lower = Math.max(j - 1, 0);
                int upper = Math.min(j, triangle[i - 1].length - 1);
                int cur = Integer.MAX_VALUE;
                for (int k = lower; k <= upper; k++) {
                    cur = Math.min(cur, dp[i - 1][k]);
                }
                dp[i][j] = cur + triangle[i][j];
            }
        }

        return Arrays.stream(dp[triangle.length - 1]).min().getAsInt();
    }

    /**
     * 背包问题2：要求价值最大【不允许重复拿】
     * https://blog.csdn.net/a909301740/article/details/79979717
     * <p>
     * dp[x][y] 表示前 x 件物品，在不超过重量 y 的时候的最大价值
     * dp[i][j] = max(dp[i-1][y],dp[i-1][y-w[i]] + v[i])
     * <p>
     * （其实可以考虑，把行、列都 + 1，多出 一个0来）
     * 初始情况一：对于第0列，它的含义是背包的容量为0。此时物品的价值呢？没有。因此，第一列都填入0。
     * 初始情况二：对于第0行，它的含义是屋内没有物品。那么没有任何物品的背包里的价值多少呢？还是没有！所有都是0。
     * <p>
     * 要注意判断的一点就是：
     * 如果当前背包的容量 >= 当前物品的重量的时候，才可以进行判断，否则是装不下这个物品的。只能是采取 dp[i-1][y]
     *
     * @param m 容量
     * @param v 物品的价值
     * @param w 物品的重量
     * @return
     */
    public static int calcPack2(int m, int[] v, int[] w) {
        int[][] dp = new int[w.length + 1][m + 1];
        //初始化，第一行都是0
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }

        //第一列都是0
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (j >= w[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i - 1]] + v[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[w.length][m];
    }


    /**
     * 如果可以重复，问题就比较简单了，针对每个容量，取一个最大价值
     * dp[i] 表示 在重量不超过i时的最大值
     *
     * @param m
     * @param v
     * @param w
     * @return
     */
    public static int calcPack2CanRepeat(int m, int[] v, int[] w) {
        int[] dp = new int[m + 1];
        //初始化，第一行都是0
        dp[0] = 0;

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < w.length; j++) {
                if (i >= w[j]) {
                    dp[i] = Math.max(dp[i], dp[i - w[j]] + v[j]);
                }
            }
        }
        return dp[m];
    }

    /**
     * LSC：最长公共子串问题
     * https://blog.csdn.net/qq_31881469/article/details/77892324
     * dp[i][j] 表示，截止到 xi,yi时，X 和 Y的最长公共子序列 LCS 的长度。
     *
     * @return
     */
    public static int calcLCS(String str1,String str2){
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        //初始化，第一行都是0
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }

        //第一列都是0
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }

    /**
     * 计算公共子串
     * dp[i][j] 表示，截止到 xi,yi时，X 和 Y的最长公共子串的长度。
     * @param str1
     * @param str2
     * @return
     */
    public static int calcCommonSubStr(String str1,String str2){
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        //初始化，第一行都是0
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }

        //第一列都是0
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }

        int max = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                max = Math.max(max,dp[i][j]);
            }
        }
        return max;
    }
}
