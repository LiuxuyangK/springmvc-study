package com.springmvc.lxy.other;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-02-14
 **/
public class Exec_19_02_14 {
    TreeSet<Integer> set = new TreeSet<>();
    int min = Integer.MAX_VALUE;

    Integer prev = null;

    public static void main(String[] args) {

    }


    /**
     * 获得一棵树中，节点差值最小的
     * 这个是 DSF 的方式进行查询
     * @param root
     * @return
     */
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return min;
        }
        if(!set.isEmpty()){
            if (set.floor(root.val) != null) {
                min = Math.min(min, root.val - set.floor(root.val));
            }
            if (set.ceiling(root.val) != null) {
                min = Math.min(min,  set.ceiling(root.val) - root.val);
            }
        }
        set.add(root.val);
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);

        return min;
    }


    /**
     * 这个是用中序遍历的方式
     *
     * @param root
     * @return
     */
    public int getMinimumDifference2(TreeNode root) {
        if (root == null) {
            return min;
        }

        //访问左
        getMinimumDifference2(root.left);

        //如果前一个不为空，计算min
        if (prev != null) {
            min = Math.min(min, root.val - prev);
        }

        //设置前一个的值
        prev = root.val;

        //访问右
        getMinimumDifference2(root.right);

        return min;
    }
}
