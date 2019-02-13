package com.springmvc.lxy.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-12-02
 **/
public class SortArrayByPartition2 {

    private static final Logger log = LoggerFactory.getLogger(SortArrayByPartition2.class);

    public static void main(String[] args) {
        SortArrayByPartition2 client = new SortArrayByPartition2();

        log.info("\n***** 2018-12-02...no1：");
        int[] arr = {4, 5, 2, 7, 7, 8};
        int[] result = client.sortArrayByParityII(arr);
        log.info("\n***** 2018-12-02...result：{}", result);



    }

    /**
     * 把数组分割成：当下标是偶数，那么A[i]就是偶数
     * 思路：类似于快排，走一遍过程。i，j都要时刻注意分别 < n，因为有可能给出的数组符合条件，i，j已经走完了，留下的势必也已经走完。
     *
     * @param A
     * @return
     */
    public int[] sortArrayByParityII(int[] A) {
        int i = 0;
        int j = 1;
        int n = A.length;
        while (i < n && j < n) {
            while (i < n && A[i] % 2 == 0) {
                i += 2;
            }
            while (j < n && A[j] % 2 != 0) {
                j += 2;
            }
            if (i < n && j < n) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        return A;
    }

    public int ping(int t) {
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(t);
        return ts.tailSet(t - 3000).size();
    }

    public int projectionArea(int[][] grid) {
        int sum1 = 0;
        for (int i = 0; i < grid.length; i++) {
            int max = Arrays.stream(grid[i]).max().getAsInt();
            sum1 += max;
        }

        for (int j = 0; j < grid[0].length; j++) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < grid.length; i++) {
                list.add(grid[i][j]);
            }
            Integer max = list.stream().max(Comparator.comparing(Integer::intValue)).get();
            sum1 += max;
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int i1 = 0; i1 < grid[i].length; i1++) {
                list.add(grid[i][i1]);
            }
        }
        long count = list.stream().filter(i -> i > 0).count();
        return sum1 += count;
    }

    /**
     * 上面，我那个计算方法太笨了。算数组就老老实实遍历，相加就行了
     *
     * @param grid
     * @return
     */
    public int projectionArea2(int[][] grid) {
        int res = 0, n = grid.length;
        for (int i = 0, v = 0; i < n; ++i, res += v, v = 0)
            for (int j = 0; j < n; ++j)
                v = Math.max(v, grid[i][j]);
        for (int j = 0, v = 0; j < n; ++j, res += v, v = 0)
            for (int i = 0; i < n; ++i)
                v = Math.max(v, grid[i][j]);
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (grid[i][j] > 0) res++;
        return res;
    }

    /**
     * 前序遍历（递归版本）
     *
     * @param root
     * @return
     */
    public List<Integer> preorder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        preorderAction(root,result);
        return result;
    }

    private void preorderAction(Node root, List<Integer> result) {
        if (root == null) {
            return ;
        }
        result.add(root.val);
        if (root.children != null) {
            for (Node child : root.children) {
                preorderAction(child,result);
            }
        }
    }

    /**
     * 前序遍历：非递归版本（用栈）
     *
     * @param root
     * @return
     */
    public List<Integer> preorder2(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node head = stack.pop();
            if (head.children != null) {
                for (int i = head.children.size() - 1; i > 0; i--) {
                    stack.push(head.children.get(i));
                }
            }
            result.add(head.val);
        }
        return result;
    }

    public List<Integer> postorder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        postorderAction(root,result);
        return result;
    }

    public List<Integer> postorder2(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node pop = stack.pop();
            result.add(pop.val);
            for (Node child : pop.children) {
                stack.push(child);
            }
        }
        Collections.reverse(result);
        return result;
    }

    private void postorderAction(Node root, List<Integer> result) {
        if (root == null) {
            return ;
        }
        if (root.children != null) {
            for (Node child : root.children) {
                postorderAction(child,result);
            }
        }
        result.add(root.val);
    }

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

}
