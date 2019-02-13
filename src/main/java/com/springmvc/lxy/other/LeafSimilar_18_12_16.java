package com.springmvc.lxy.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-12-16
 **/
public class LeafSimilar_18_12_16 {

    private static final Logger log = LoggerFactory.getLogger(SortArrayByPartition2.class);

    public static void main(String[] args) {

        LeafSimilar_18_12_16 client = new LeafSimilar_18_12_16();

        log.info("\n***** 2018-12-16...no1【开始】，leafSimilar");
        log.info("\n***** 2018-12-16...no1【结束】，leafSimilar：{}");

        log.info("\n***** 2018-12-16...no2【开始】，numSpecialEquivGroups");
        String[] A = {"abc","acb","bac","bca","cab","cba"};
        String[] A2 = {"a","b","c","a","c","c"};
        String[] A3 = {"aa","bb","ab","ba"};
        int i = client.numSpecialEquivGroups(A3);
        log.info("\n***** 2018-12-16...no2【结束】，numSpecialEquivGroups：{}",i);
    }

    /**
     * 一个字符串，下标是奇数、偶数的可以调换，调换完了只要值一样，就算同一个
     * @param A
     * @return
     */
    public int numSpecialEquivGroups(String[] A) {
        if (A == null) {
            return 0;
        }
        if (A.length == 0 || A.length == 1) {
            return 1;
        }

        Set<String> set = new HashSet<>();
        for (String s : A) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);

//            set.add();
        }
        return set.size();
    }

    /**
     * 题目：872。判断两棵树叶子是不是相同的
     * 要点：利用了树的深入搜索，递归性质
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 != null && root2 == null || root1 == null && root2 != null) {
            return false;
        }

        return preOrderTraverse(root1).equals(preOrderTraverse(root2));
    }

    public String preOrderTraverse(TreeNode node) {
        if (node == null) {
            return "";
        }
        if (node.left == null && node.right == null) {
            return node.val + "";
        }
        return preOrderTraverse(node.left) + preOrderTraverse(node.right);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
