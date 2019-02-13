package com.springmvc.lxy.other;

import java.util.Arrays;
import java.util.Stack;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-02-01
 **/
public class Exec_19_02_01 {

    public static void main(String[] args) {
        Exec_19_02_01 client = new Exec_19_02_01();

        int[] arr = {7, 1, 5, 3, 6, 4};
        System.out.println(client.maxProfit2(arr));

        int[] arr2 = {3, 3};
        System.out.println(client.containsDuplicate(arr2));
        ;

        int[] arr3 = {5, 5, 5, 10, 20};
        System.out.println(client.lemonadeChange(arr3));
        ;
    }

    private int sum = 0;
    /**
     * bst变成更大树：
     * 非常漂亮的一个解法！
     * 观察到，其实每次都是把最右面的子树值，加到root，和left上，然后把root加到left上，搞定。
     * 所以每次先遍历right，然后累加到root上以后，再把root，累加到sum 上。
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

    /**
     * 上面是递归的招数，接下来要改成迭代的招数。
     * @param root
     * @return
     */
    public TreeNode convertBST2(TreeNode root) {

        return null;
    }

    /**
     * 找零钱问题：买东西的零钱只有5，10，20，那就好办了
     *
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int fives = 0;
        int tens = 0;
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                fives++;
            } else if (bills[i] == 10) {
                if (fives == 0) {
                    return false;
                }
                fives--;
                tens++;
            } else {
                if (tens == 0) {
                    if (fives < 3) {
                        return false;
                    }
                    fives -= 3;
                } else {
                    if (fives == 0) {
                        return false;
                    }
                    tens--;
                    fives--;
                }
            }
        }
        return true;
    }

    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 看t是不是s的 anagram：由颠倒字母顺序而构成的字[短语]
     * 思路：
     * 1.   对两个s、t进行排序，然后比较，最简单
     * 2.   用一个int[26]table，然后s进行++，t进行--，最后结果应该是，数组里面的值，都是0
     * 3.   搞一个hashmap<char,integer>
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    /**
     * 上一个递归解法，很难。但是包含了递归的一些精髓。
     * 这个解法理解起来要容易很多。
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int i = 0;
        int max = 0;
        while (i < prices.length - 1) {
            //首先，找一个低谷
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            int vally = prices[i];

            //第二，找一个高峰
            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            int peak = prices[i];
            max = max + (peak - vally);
        }
        return max;
    }

    /**
     * 比较有趣的一个题：
     * 数组prices 表示 第i天的股票是 prices[i] 的钱
     * 如果要获取最大的收益，应该怎么买卖？
     * <p>
     * 刚开始第一道，就是这么难的题目。
     * 这是一个常规解法，其实还是有一种动态规划的意味在其中。
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        return calcMaxProfit(prices, 0);
    }

    private int calcMaxProfit(int[] prices, int start) {
        if (start >= prices.length) {
            return 0;
        }
        int max = 0;

        //循环遍历数组中的每个数字
        for (int i = start; i < prices.length; i++) {
            //买当前数字i，对后续数字进行一个买卖，记录每次不同的利润
            int maxProfit = 0;

            //循环遍历后面的每一个数字
            for (int j = i + 1; j < prices.length; j++) {

                //如果后面的数字比前面的数字更大
                if (prices[j] > prices[i]) {

                    //进行计算从当前这个数字开始，能得到的利润maxProfit
                    //maxProfit = 从当前的后面的后面（j+1 = i+ 2）那个数字开始的利润 + （后面那个数字 - 当前数字）
                    //j+1是一个核心，因为1，后面是5，但是不从5开始，因为5，只能是卖，最快只能是从3开始
                    int profit = calcMaxProfit(prices, j + 1) + prices[j] - prices[i];

                    //由于从当前第i个数字开始，以后每个比i大的数字，都会计算一遍利润
                    //取最大的利润
                    maxProfit = Math.max(maxProfit, profit);
                }
            }

            //每个i数字，所能产生的利润，到这里，maxProfit 已经代表了每个数字，所能产生的最大利润maxProfit
            //更新到max上，取这一波数字 0,1,2...i，所能产生的最大
            max = Math.max(max, maxProfit);
        }
        return max;
    }


    int calc(int[] pieces, int start) {
        int max = 0;
        if (start >= pieces.length) {
            return 0;
        }
        for (int i = start; i < pieces.length; i++) {
            int maxProfit = 0;
            for (int j = i + 1; j < pieces.length; j++) {
                if (pieces[i] < pieces[j]) {
                    int profit = calcMaxProfit(pieces, j + 1) + pieces[j] - pieces[i];
                    maxProfit = Math.max(maxProfit, profit);
                }
            }
            max = Math.max(maxProfit, max);
        }
        return max;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
