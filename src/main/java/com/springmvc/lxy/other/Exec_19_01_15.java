package com.springmvc.lxy.other;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-16
 **/
public class Exec_19_01_15 {

    public static void main(String[] args) {

    }

    /**
     * 如：8：1000，1 不相邻就可以
     * 7：111，1相邻了，不行。
     * <p>
     * 这个方法有一个巧妙的用处就是，可以直接拿到一个数字n中，1的个数，每次和mask进行 & 操作就行了。
     * 用一个status记录状态
     * while的条件是 n > 0
     *
     * @param n
     * @return
     */
    public static boolean hasAlternatingBits(int n) {
        int mask = 1;
        int status = n & mask;
        n = n >> 1;
        while (n > 0) {
            if (status == (n & mask)) {
                return false;
            }
            status = n & mask;
            n = n >> 1;
        }
        return true;
    }

    /**
     * 树的层序遍历，输出每一层的平均值
     * 其实python有一个更好的层序遍历的方法，就是bfs
     *
     *         average_list = []
     *         next_level_nodes = [root]
     *         while next_level_nodes:
     *             //每次循环，都是遍历一层
     *             temp_sum, temp_count = 0, 0
     *
     *             //用 下一层 的list，赋值 current_level_nodes，然后遍历下一层。
     *             current_level_nodes = next_level_nodes
     *
     *             //用 next_level_nodes 去收集下下层
     *             next_level_nodes = []
     *
     *             //只要无脑遍历 current_level_nodes 即可
     *             //利用了 next_level_nodes 这个来保存下一层的数量
     *
     *             for node in current_level_nodes:
     *                 temp_sum += node.val
     *                 temp_count += 1
     *                 if node.left:
     *                     next_level_nodes.append(node.left)
     *                 if node.right:
     *                     next_level_nodes.append(node.right)
     *             average_list.append(temp_sum / temp_count)
     *         return average_list
     *
     * @param root
     * @return
     */
    public static List<Double> averageOfLevels(TreeNode root) {
        TreeNode dummy = new TreeNode(-1);
        Queue<TreeNode> queue = new LinkedList<>();
        ((LinkedList<TreeNode>) queue).add(root);
        ((LinkedList<TreeNode>) queue).add(dummy);
        List<Double> list = new ArrayList<>();
        double sum = 0;
        double count = 0;
        while(!queue.isEmpty()){
            while (!queue.isEmpty()) {
                TreeNode poll = queue.poll();
                if (poll == dummy) {
                    break;
                }
                sum += poll.val;
                count ++;
                if (poll.left != null) {
                    ((LinkedList<TreeNode>) queue).add(poll.left);
                }
                if (poll.right != null) {
                    ((LinkedList<TreeNode>) queue).add(poll.right);
                }
            }
            double res = sum / count;
            list.add(res);
            sum = 0;
            count = 0;
            if(!queue.isEmpty()){
                ((LinkedList<TreeNode>) queue).add(dummy);
            }
        }
        return list;
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
