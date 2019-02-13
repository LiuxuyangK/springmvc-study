package com.springmvc.lxy.other;

/**
 * 描述:
 * 拿货币数量最少
 *
 * <p>
 *
 * @author: harry
 * @date: 2018-09-28
 **/
public class DP1 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 8, 9};
        int aim = 7;
        int count = exchangeMoney(arr, aim);
        System.out.println(count);

        int aim2 = 17;
        int count2 = exchangeMoney(arr, aim2);
        System.out.println(count2);

        int aim3 = 25;
        int count3 = exchangeMoney(arr, aim3);
        System.out.println(count3);
    }

    private static int exchangeMoney(int[] arr, int aim) {
        if (arr.length <= 0 || aim == 0) {
            return 0;
        }

        //制造一个二维数组
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < aim + 1; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //初始化数组边界值，第一列都是0，拿0元自然是不需要任何货币，所以都是0
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 0;
        }

        //初始化边界值2，第一行是0，意味着只用第一种货币，能如何凑够想要的钱，
        //        因为第一种货币默认就是最小的
        for (int i = 1; i < aim + 1; i++) {
            if (i - arr[0] >= 0 && dp[0][i - arr[0]] != Integer.MAX_VALUE) {
                dp[0][i] = dp[0][i - arr[0]] + 1;
            }
        }

        //开始按照递推公式走，从第i = 1行，第i = 1列走
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < aim + 1; j++) {
                int remain = j - arr[i];
                int up = dp[i - 1][j];
                int left = Integer.MAX_VALUE;
                if (remain >= 0 && dp[i][remain] != Integer.MAX_VALUE) {
                    left = dp[i][remain] + 1;
                }
                dp[i][j] = Integer.min(up, left);
            }
        }

        return dp[arr.length - 1][aim] == Integer.MAX_VALUE ? -1 :dp[arr.length - 1][aim];
    }
}
