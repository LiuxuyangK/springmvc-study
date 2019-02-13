package com.springmvc.lxy.other;

/**
 * 描述: 动态规划：钱币问题
 * <p>
 *
 * @author: harry
 * @date: 2019-01-06
 **/
public class Exec_19_01_07 {

    public static void main(String[] args) {
        int[] coins = {1, 5};

        int sum = 17;
        int num = getMoneyWithLeastNum(coins, sum);
        System.out.println("getMoneyWithLeastNum:" + num);

        int[] arr = {5, 3, 4, 8, 6, 7};
        int lis = lis(arr);
        System.out.println(lis);
    }

    /**
     * 允许重复拿，只要拿的数额最少就行
     * min[i] 拿总额是i的钱，最少是几张钞票
     * <p>
     * 递推公式：
     * 1.当前的钱币，肯定要比 要拿的总额，要小
     * 2.如果选了当前钱币，则数量是：min[i - coins[j]] + 1，不选择当前钱币，则数量是：min[i]
     * <p>
     * 从 s = 1，2，3，，，s开始算
     * 如果当前的币面值比要拿的金额数要小，那表示有机会。只是有机会。
     * 进而【遇到一张小的钞票】再判断，是否拿了这张钱币，就能凑够呢？（第一次是这样的思维，因为min[i]刚开始是一个特别大的值）
     * 进而【遇到一张大的钞票】再判断，是否拿了这张钱币，就能比原来更少呢？（后面几次，就是优化的流程了）
     *
     * @param coins
     * @param sum
     * @return
     */
    private static int getMoneyWithLeastNum(int[] coins, int sum) {
        int[] min = new int[sum + 1];
        for (int i = 0; i < min.length; i++) {
            min[i] = Integer.MAX_VALUE - 1;
        }
        min[0] = 0;

        //要拿的总额，从1开始循环。
        for (int i = 1; i <= sum; i++) {

            System.out.println(i);
            //循环试探每一张钱币，看是否满足条件：
            //1.当前的钱币，肯定要比 要拿的总额，要小
            //2.如果选了当前钱币，则数量是：min[i - coins[j]] + 1，不选择当前钱币，则数量是：min[i]
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    int a = min[i - coins[j]] + 1;  //用当前钞票，看看看看是否能凑够、能优化
                    int b = min[i]; //不取当前钞票，但是之前已经有别的钞票，能凑够了，可能就是数量多一点而已。
                    if (a < b) {
                        min[i] = min[i - coins[j]] + 1;
                    }
                }
            }
        }
        return min[sum];
    }

    /**
     * 最长非降序子序列
     * d[i]：以下边i的数字为结束的字序列的长度
     * d[i] = Math.math(d[i-1] +  1,1)
     *
     * @param arr
     * @return
     */
    private static int lis(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int len = 1;
        for (int i = 1; i < arr.length; i++) {
            dp[i] = 1;
            for(int j=0; j<i; ++j){
                if(arr[j]<=arr[i] && dp[j]+1>dp[i]){
                    dp[i] = dp[j] + 1;
                }
            }
            if(dp[i]>len) {
                len = dp[i];
            }
        }
        return len;
    }

}
