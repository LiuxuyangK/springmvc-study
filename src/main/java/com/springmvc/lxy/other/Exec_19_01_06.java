package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 描述: 动态规划
 * <p>
 *
 * @author: harry
 * @date: 2019-01-06
 **/
public class Exec_19_01_06 {

    public static void main(String[] args) {
        //切钢管
        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int n = 4;
        int cut = cut2(p, n);
        System.out.println(cut);

        int cut3 = cut3(p, n);
        System.out.println(cut3);

        int cut4 = cut4(p, n);
        System.out.println(cut4);

        //选任务，获得收益最大
        int[] t = {5, 1, 8, 4, 6, 3, 2, 4};
        int[] prev = {-1, -1, -1, 0, -1, 1, 2, 4};
        int res = doTask(t, prev);
        System.out.println(res);

        int[][] jobs = {{1, 4, 5},
            {3, 5, 1},
            {0, 6, 8},
            {4, 7, 4},
            {3, 8, 6},
            {5, 9, 3},
            {6, 10, 2},
            {8, 11, 4}};
        int jobRes2 = doTask2(jobs);
        System.out.println(jobRes2);

        //计算不相邻的数字
        int[] arr = {1, 2, 4, 1, 7, 8, 3};
        int i = calcNotXianglin(arr);
        System.out.println(i);

        //取钱，递归
        int[] arr2 = {3, 34, 4, 12, 5, 2};
        boolean money = getMoneyRec(arr2, arr2.length - 1, 9);
        System.out.println(money);
        boolean money2 = getMoneyRec(arr2, arr2.length - 1, 10);
        System.out.println(money2);
        boolean money3 = getMoneyRec(arr2, arr2.length - 1, 11);
        System.out.println(money3);
        boolean money4 = getMoneyRec(arr2, arr2.length - 1, 12);
        System.out.println(money4);
        boolean money5 = getMoneyRec(arr2, arr2.length - 1, 13);
        System.out.println(JSONObject.toJSONString(arr2) + " = 13，result：" + money5);

        int[] arr3 = {1, 2, 5, 8, 9};
        boolean money6 = getMoneyRec(arr3, arr3.length - 1, 25);
        System.out.println(JSONObject.toJSONString(arr3) + " = 25，result：" + money6);


        //取钱，非递归
        boolean money7 = getMoney(arr3, 25);
        System.out.println("getMoney：" + JSONObject.toJSONString(arr3) + " = 25，result：" + money7);

        //重复拿钞票问题
        int[] coins = {1, 5};
        int sum = 17;
        int num = getMoneyWithLeastNum(coins, sum);
        System.out.println("getMoneyWithLeastNum:" + num);
    }

    /**
     * 取货币的方法。
     * 递推公式：也是取和不取。
     * dp[i] = Math.max(dp[i-1],s - arr[i],dp[i - 1],s)
     * <p>
     * 这是递归的方法，注意方法的结束出口
     *
     * @param arr
     * @param i
     * @param s
     * @return
     */
    private static boolean getMoneyRec(int[] arr, int i, int s) {
        if (s == 0) {
            return true;
        } else if (i == 0) {
            return arr[i] == s;
        } else if (arr[i] > s) {
            return getMoneyRec(arr, i - 1, s);
        }
        boolean selected = getMoneyRec(arr, i - 1, s - arr[i]);
        boolean notSelected = getMoneyRec(arr, i - 1, s);
        return selected || notSelected;
    }

    /**
     * 非递归的方法
     * 要用一个二维的数组去设计！
     * 听这个老师讲课简直不要太清晰！
     * dp[i][j] 表示： 用前i个钱币，凑金额为j，是否可行。
     * 递归公式：dp[i][j] = Math.max(dp[i-1][j-i],dp[i-1][j])
     * <p>
     * 1.   首先列出一个二维数组，"竖高"是钱币的下标以及对应的值，"横宽"是把目标值，从0,1,2...n拆解出来。
     * 2.   然后填满第一行，填满第一列。
     * 3.   带入去计算了。
     *
     * @param arr
     * @param s
     * @return
     */
    private static boolean getMoney(int[] arr, int s) {
        int[][] dp = new int[arr.length][s + 1];

        //赋初始值：第一行
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
            if (arr[0] == i) {
                dp[0][i] = 1;
            }
        }

        //第一列
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (arr[i] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    int notSelected = dp[i - 1][j];
                    int selected = dp[i - 1][j - arr[i]];
                    dp[i][j] = notSelected == 1 || selected == 1 ? 1 : 0;
                }
            }
        }

        return dp[arr.length - 1][s] == 1;
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
     * 计算不相邻的数字，4，1，1，9。选择某个数字，就没法选相邻的
     * 这个是非递归的，也可以写一个递归的
     * 递推公式较为简单：
     * dp[i] = Math.max(dp[i - 1],arr[i] + dp[i - 2])
     *
     * @param arr
     * @return
     */
    private static int calcNotXianglin(int[] arr) {
        int[] dp = new int[arr.length];

        //初始化好dp，就可以从i= 2开始递归了
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < dp.length; i++) {
            int notSelected = dp[i - 1];
            int selected = arr[i] + dp[i - 2];

            dp[i] = Math.max(notSelected, selected);
        }
        return dp[dp.length - 1];
    }

    /**
     * 打劫房屋问题，和上面的 计算不相邻是一个问题。但是这个解法是用两个变量保存状态，理解起来不如上一个方法。
     * res2是到目前为止，最大的值
     * res1是到目前为止，上一个最大的值
     * 所以在进入下一个循环的时候，res2 表示上一个最大的，res1 表示上两个最大的
     * 递推公式也就不难理解了：res3 = Math.max(res2, res1 + arr[i]);
     * 循环结束的时候，依次把 res1 = res2,res2 = res3；把值更新掉。
     * @param arr
     * @return
     */
    private static int houseRobber(int[] arr) {
        // write your code here
        int len = arr.length;
        if (len == 0)
            return 0;
        int res1 = 0;
        int res2 = arr[0];
        if (len == 1) {
            return res2;
        }
        for (int i = 1; i < len; i++) {
            int res3 = Math.max(res2, res1 + arr[i]);
            res1 = res2;
            res2 = res3;
        }
        return res2;
    }

    /**
     * 打劫系列：https://www.cnblogs.com/Revenent-Blog/p/7569620.html
     *
     * 成环打劫：0个和n-1个，是连接在一起的，所以取了0，就不能取 n-1
     * 思路和第一种一样，但是要用两个dp数组去维护，算两次
     * @param nums
     * @return
     */
    public static int houseRobber2(int[] nums) {
        // write your code here
        if(nums.length==0||nums.length==2||nums==null){
            return 0;
        }
        if(nums.length==1){
            return nums[0];
        }
        int[] DP_1 = new int[nums.length-1];
        int[] DP_2 = new int[nums.length-1];
        //不打劫最后一所房子则从第一所房子开始打劫
        for(int i=0;i<nums.length-1;i++){
            if(i==0){
                DP_1[i] = nums[0];
            }
            if(i==1){
                DP_1[i] = Math.max(nums[1],DP_1[0]);
            }
            if(i>1){
                DP_1[i] = Math.max(DP_1[i-2]+nums[i],DP_1[i-1]);
            }
        }
        //打劫最后一所房子则从第二所房子开始打劫
        for(int i=1;i<nums.length;i++){
            if(i==1){
                DP_2[i-1] = nums[1];
            }
            if(i==2){
                DP_2[i-1] = Math.max(nums[2],DP_2[0]);
            }
            if(i>2){
                DP_2[i-1] = Math.max(DP_2[i-3]+nums[i],DP_2[i-2]);
            }
        }
        return DP_1[nums.length-2]>DP_2[nums.length-2]?DP_1[nums.length-2]:DP_2[nums.length-2];
    }
    /**
     * 选任务，获得收益最大。
     * 每个任务都会给定时间，如：任务1，1-4点。任务2，3-5点。
     * <p>
     * 我这是最简单的一个实现，人为地把最理想的数据都给出来了prev数组就是在作弊，只用了一个递推公式去解决问题的
     * 如果选这个job的收益：vi + opt[prev[i]]
     * 如果不选这个job的收益：opt[i - 1]
     * opt[i] = Math.max(选择,不选)
     *
     * @param t
     * @param prev
     * @return
     */
    private static int doTask(int[] t, int[] prev) {
        int[] opt = new int[t.length];
        for (int i = 0; i < opt.length; i++) {
            int prevVal = 0;
            if (prev[i] != -1) {
                prevVal = opt[prev[i]];
            }
            int notSelected = 0;
            if (i > 0) {
                notSelected = opt[i - 1];
            }
            opt[i] = Math.max(t[i] + prevVal, notSelected);
        }
        return opt[opt.length - 1];
    }

    /**
     * 这次输入的是一个二维数组，而不是人为计算那些值，但是用的递推公式 还是不变的
     * 这次要手工计算 prev 数据
     *
     * @param jobs
     * @return
     */
    private static int doTask2(int[][] jobs) {
        //先对结束时间排个序
        Arrays.sort(jobs, Comparator.comparing(arr1 -> arr1[1], (a, b) -> a - b));

        //创建dp数据，dp[i]表示总共i个任务的最好值。
        int[] dp = new int[jobs.length];
        // 给dp[0]初始化一个值，可以避免接下来循环的时候，从0循环，还是从1循环的尴尬
        //从0需要判断，0-1 = -1；而从1开始，则不需要尴尬。因为1-1=0，而dp[i]已经有值了
        dp[0] = jobs[0][2];

        for (int i = 1; i < jobs.length; i++) {
            //不选当前这个任务
            dp[i] = dp[i - 1];

            //选择这个任务
            int curVal = jobs[i][2];
            for (int j = i; j >= 0; j--) {
                if (jobs[i][0] >= jobs[j][1]) {
                    curVal += dp[j];
                    break;
                }
            }
            dp[i] = Math.max(dp[i], curVal);
        }
        return dp[dp.length - 1];
    }


    /**
     * 从网上抄来的，切刚断的第一种方法，把每段刚，分成 Math.max(如果不切 , 切了);
     * 切了可以有很多种，不一定是分成两段，可以分成很多，比如说4，可以是 0+4,1+3,2+2,1+1+2...等等
     * 虽然算法里面，写的是把4分成了两端，分别是让i从0-3执行，表示 4 = 0+4,1+3,2+2,3+1，
     * 但是当进入下一个函数的时候，n就会变成3，变成2，变成1，所以还是会把4，拆解成 很多很多零散的东西，
     * 最后算出最大值
     *
     * @param p
     * @param n
     * @return
     */
    public static int cut(int[] p, int n) {
        if (n == 0)
            return 0;
        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            q = Math.max(q, p[i - 1] + cut(p, n - i));
        }
        return q;
    }

    /**
     * 自己写cut
     *
     * @param p
     * @param n
     * @return
     */
    public static int cut2(int[] p, int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 0) {
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            q = Math.max(q, p[n - i - 1] + cut(p, i));
        }
        return q;
    }

    /**
     * 自己写cut，利用了备忘录的方法，将计算的过程存储起来，避免不必要的递归调用
     * 假设n，不会超过 p.length - 1，否则就爆了
     *
     * @param p
     * @param n
     * @return
     */
    public static int cut3(int[] p, int n) {

        //r[i] 表示计算过的 p[i]
        int[] r = new int[p.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = -1;
        }

        return cut3Action(p, n, r);
    }

    private static int cut3Action(int[] p, int n, int[] r) {
        if (r[n] > 0) {
            return r[n];
        }
//        if (n == 1) {
//            return 1;
//        }
        if (n == 0) {
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            q = Math.max(q, p[n - i - 1] + cut3Action(p, i, r));
        }
        r[n] = q;
        return q;
    }

    /**
     * 动态规划的问题，不用递归，同样也是用一个数组，把之前计算过的数据，存储起来
     * 递推公式：ri 是长度为i的时候切割的最大值。ri = r[i-j] + r[j]，j = 0,1,,,i
     *
     * @param p
     * @param n
     * @return
     */

    public static int cut4(int[] p, int n) {

        //r[i] 表示计算过的 p[i]
        int[] r = new int[p.length + 1];

        //先给初始化好
        for (int i = 1; i < r.length; i++) {
            r[i] = p[i - 1];
        }
        for (int i = 1; i < r.length; i++) {
            int q = -1;
            for (int j = 1; j <= i; j++) {
                q = Math.max(q, r[i - j] + r[j]);
            }
            r[i] = q;
        }

        return r[n];
    }
}
