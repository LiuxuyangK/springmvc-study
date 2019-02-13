package com.springmvc.lxy.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-01
 **/
public class IsToeplitzMatrix_19_01_01 {
    private static final Logger log = LoggerFactory.getLogger(IsToeplitzMatrix_19_01_01.class);

    public static void main(String[] args) {
        log.info("***** 2018-01-01...no1【开始】，判断矩阵的一个题目：isToeplitzMatrix");

        log.info("***** 2018-01-01...no2【开始】，树的中序遍历的一个题目：isToeplitzMatrix");

        log.info("***** 2018-01-01...no3【开始】，栈的一个题目：isToeplitzMatrix");
        String[] arr = {
            "-27276",
            "D",
            "+",
            "+",
            "C",
            "D",
            "+",
            "-12178",
            "+"
        };
//        calPoints(arr);


        int[][] arr2 = {{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}};
        int i = islandPerimeter(arr2);
        System.out.println(i);

        binaryGap(22);

    }

    public static int binaryGap(int N) {
        String s = Integer.toBinaryString(N);
        char[] array = s.toCharArray();
        int preIndex = -1;
        int dis = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '0') {
                continue;
            }
            if (array[i] == '1') {
                if (preIndex == -1) {
                    preIndex = i;
                    continue;
                }
                dis = Math.max(dis, i - preIndex);
                preIndex = i;
            }
        }
        return dis;
    }

    public int peakIndexInMountainArray(int[] A) {
        boolean small = false;
        for (int i = 1; i < A.length - 1; i++) {
            if (A[i] < A[i - 1]) {
                if (small) {
                    return -1;
                }
            } else {
                small = true;
            }
        }

        return 0;
    }

    public int[] diStringMatch(String S) {
        int n = S.length(), left = 0, right = n;
        int[] res = new int[n + 1];
        for (int i = 0; i < n; ++i)
            res[i] = S.charAt(i) == 'I' ? left++ : right--;
        res[n] = left;
        return res;
    }

    public int repeatedNTimes(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : A) {
            Integer res = map.get(i);
            if (res == null) {
                map.put(i, 1);
            } else {
                map.put(i, res + 1);
            }
        }
        int count = A.length / 2;
        for (Integer integer : map.keySet()) {
            Integer times = map.get(integer);
            if (times == count) {
                return integer;
            }
        }
        return 0;
    }

    /**
     * 岛屿问题，很简单
     *
     * @param grid
     * @return
     */
    public static int islandPerimeter(int[][] grid) {
        int count = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    continue;

                }
                count += 4;
                //上
                if (i > 0) {
                    if (grid[i - 1][j] == 1) {
                        count -= 1;
                    }
                }

                //下
                if (i < grid.length - 1) {
                    if (grid[i + 1][j] == 1) {
                        count -= 1;
                    }
                }

                //左
                if (j > 0) {
                    if (grid[i][j - 1] == 1) {
                        count -= 1;
                    }
                }

                //右
                if (j < grid[i].length - 1) {
                    if (grid[i][j + 1] == 1) {
                        count -= 1;
                    }
                }
            }
        }
        return count;
    }

    /**
     * 修剪二叉树，二叉树是排好序的。要求在[L,R]之间
     * 思路：递归解法，
     *
     * @param root
     * @param L
     * @param R
     * @return
     */
    public TreeNode trimBSTRec(TreeNode root, int L, int R) {

        //结束条件
        if (root == null) {
            return null;
        }

        //如果root的值，比最小的还小，那就看root的right就行了。
        //为什么要返回呢？因为后面的代码是针对该节点的值，在[L,R]范围内的调整，
        // 进入了这个if判断，就不应该继续走下面的代码了，所以要return
        if (root.val < L) {
            return trimBST(root.right, L, R);
        }

        //如果root的值，比最大的还大，那就看root的left就行了。
        if (root.val > R) {
            return trimBST(root.left, L, R);
        }

        root.left = trimBST(root.left, L, R);
        root.right = trimBST(root.right, L, R);
        return root;
    }

    /**
     * 修剪二叉树，二叉树是排好序的。要求在[L,R]之间
     *
     * @param root
     * @param L
     * @param R
     * @return
     */
    public TreeNode trimBST(TreeNode root, int L, int R) {

        //去掉不在 [L,R] 之间的节点
        while (root.val < L || root.val > R) {
            if (root.val < L) {
                root = root.right;
            }
            if (root.val > R) {
                root = root.left;
            }
        }

        //去掉左节点上面不符合条件的一些点，
        // 因为即使root在[L,R]之间，也不一定保证，root.left或者root.right 在[L,R]之间
        // 这个循环，就是要去掉比L还小的一些节点。如果root.left比L小，那么就取 比root.left 大的，但是最接近的那个，
        // 就是root.left.right 去代替 root.left
        TreeNode dummy = root;
        while (dummy != null) {
            while (dummy.left != null && dummy.left.val < L) {
                //把比dummy.left最接近的一个大的节点，赋值给 dummy.left
                dummy.left = dummy.left.right;
            }

            //再次循环下去，走到dummy.left，判断是否
            dummy = dummy.left;
        }

        //去掉右节点上面不符合条件的一些点，
        dummy = root;
        while (dummy != null) {
            while (dummy.right != null && dummy.right.val > R) {
                dummy.right = dummy.right.left;
            }

            dummy = dummy.right;
        }
        return root;
    }

    public static String[] uncommonFromSentences(String A, String B) {
        String[] split1 = A.split(" ");
        String[] split2 = B.split(" ");

        Map<String, Integer> map = new HashMap<>();
        for (String s : split1) {
            Integer integer = map.get(s);
            if (integer == null) {
                map.put(s, 1);
            } else {
                map.put(s, integer + 1);
            }
        }

        for (String s : split2) {
            Integer integer = map.get(s);
            if (integer == null) {
                map.put(s, 1);
            } else {
                map.put(s, integer + 1);
            }
        }

        List<String> result = new ArrayList<>();
        for (String s : map.keySet()) {
            Integer count = map.get(s);
            if (count == 1) {
                result.add(s);
            }
        }
        return result.toArray(new String[0]);
    }

    public static int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        for (String op : ops) {
            if (op.equals("C")) {
                stack.pop();
            } else if (op.equals("D")) {
                stack.push(2 * stack.peek());
            } else if (op.equals("+")) {
                int sum = stack.get(stack.size() - 1) + stack.get(stack.size() - 2);
                stack.push(sum);
            } else {
                stack.push(Integer.valueOf(op));
            }
        }

        Integer sum = stack.stream().mapToInt(i -> i).sum();

        // Or
        // sum = stack.parallelStream().reduce(0, Integer::sum);
        return sum;
    }


    /**
     * 判断矩阵的一个题目：
     * [
     * [1,2,3],
     * [4,1,2],
     * [5,4,1]
     * ]
     * 这个节点："5";"4,4";"1,1,1";"2,2";"3"
     *
     * @param matrix
     * @return
     */
    public static boolean isToeplitzMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                if (matrix[i][j] != matrix[i + 1][j + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 这个root是排好序的 左 < 根 < 右
     * 要输出的结果是：一颗歪脖子树
     * left 是null的，只是通过right相连起来。
     *
     * @param root
     * @return
     */
    public static TreeNode increasingBST(TreeNode root) {
        //用来当做返回的新的头结点
        TreeNode head = null;

        //访问的上一个节点，因为需要把上一个节点的right设置成当前访问的节点，需要保持一个状态
        TreeNode pre = null;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {

            //老规矩，访问到最左节点
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            //除了while以后，node现在是空了

            // 出栈，拿到一个node，要把它挂到上一个节点的right上
            node = stack.pop();

            //head只用一次
            if (head == null) {
                head = node;
            }

            //需要保持状态的来了
            if (pre != null) {

                //要把它挂到上一个节点的right上
                pre.right = node;
            }

            //继承和发展
            pre = node;

            //使命执行结束，可以把left设置成null了，因为后面拿出来的一个节点，它的left 已经被访问过了
            pre.left = null;

            //先坐后右，right可以迟到，但不可以缺席
            node = node.right;
        }
        return head;
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
