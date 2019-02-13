package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-14
 **/
public class Exec_19_01_14 {


    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 2, 3, 3};
        distributeCandies(arr);

        int[][] arr2 = {{1, 2}, {3, 4}};
        int[][] arr3 = {{1}, {2}, {3}, {4}};
        int[][] ints = matrixReshape(arr3, 2, 2);
        System.out.println(JSONObject.toJSONString(ints));




        Node node2 = new Node(2, null);
        Node node4 = new Node(4, null);
        Node node5 = new Node(5, null);
        Node node6 = new Node(6, null);

        Node node3 = new Node(3, Arrays.asList(node5, node6));

        Node node1 = new Node(1, Arrays.asList(node3,node2,node4));

        List<List<Integer>> lists = levelOrder(node1);
        System.out.println(JSONObject.toJSONString(lists));
    }

    /**
     * 树的层序遍历
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<Node> queue = new LinkedList<>();
        ((LinkedList<Node>) queue).add(root);
        Node dummy = new Node();
        ((LinkedList<Node>) queue).add(dummy);
        List<List<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            while (true) {
                Node poll = queue.poll();
                if (poll == dummy) {
                    break;
                }
                list.add(poll.val);
                if (poll.children != null) {
                    for (Node child : poll.children) {
                        ((LinkedList<Node>) queue).add(child);
                    }
                }
            }
            res.add(list);
            if(!queue.isEmpty()){
                ((LinkedList<Node>) queue).add(dummy);

            }
        }
        return res;
    }

    /**
     * 这个排序写的很漂亮
     * 1.   进来的两个字符串 s1,s2，先拆开（就拆前两个）
     * 2.   取第二个的第一个字母，判断是否是数字
     * 3.   如果两个都不是数字，按照第一个排名
     * 4.   如果两个有一个是数字，数字放到后面
     * <p>
     * 其实另一个js的想法也很好：
     * 弄两个数组，一个存放字母的，一个存放数字的
     * letter的比较，数字的不管
     * 最后拼接。
     *
     * @param logs
     * @return
     */
    public String[] reorderLogFiles(String[] logs) {
        Comparator<String> myComp = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] split1 = s1.split(" ", 2);
                String[] split2 = s2.split(" ", 2);
                boolean isDigit1 = Character.isDigit(split1[1].charAt(0));
                boolean isDigit2 = Character.isDigit(split2[1].charAt(0));
                if (!isDigit1 && !isDigit2) {
                    int comp = split1[1].compareTo(split2[1]);
                    if (comp != 0)
                        return comp;
                    return split1[0].compareTo(split2[0]);
                }

                //如果isDigit1 是数字，那么看isDigit2 是不是，如果是，说明一样的，就按照进来的顺序
                //如果不是，那么isDigit2 是前面的。
                //如果isDigit1 不是数字，那么没必要排序了。
                return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
            }
        };
        Arrays.sort(logs, myComp);
        return logs;
    }

    /**
     * 这个算法比较笨
     * 网上有一个python的方法：提前先把所有的数组放到 line 里面，这个方法真的很好！
     * <p>
     * line = []
     * for num in nums:
     * for n in num:
     * line.append(n)
     * if r*c != len(line):
     * return nums
     * <p>
     * matrix = []
     * for i in range(0, len(line), c):
     * matrix.append(line[i:i+c])
     * return matrix
     *
     * @param nums
     * @param r
     * @param c
     * @return
     */
    public static int[][] matrixReshape(int[][] nums, int r, int c) {
        if (r == 0 || c == 0) {
            return nums;
        }

        if (nums.length * nums[0].length != r * c) {
            return nums;
        }

        int[][] res = new int[r][c];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                int cur = i * nums[i].length + j;
                int row = cur / c;
                int col = cur % c;
                res[row][col] = nums[i][j];
            }
        }
        return res;
    }

    /**
     * 使用了异或，只用一次变量，而且只有一个变量，就可以获取到唯一的数字
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }
        return result;
    }

    public static int distributeCandies(int[] candies) {
        Set<Integer> kinds = new HashSet<>();
        for (int candy : candies) kinds.add(candy);
        return kinds.size() >= candies.length / 2 ? candies.length / 2 : kinds.size();
    }


    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                list.add("FizzBuzz");
            } else if (i % 3 == 0) {
                list.add("Fizz");
            } else if (i % 5 == 0) {
                list.add("Buzz");
            } else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left) + 1, maxDepth(root.right) + 1);
    }

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    ;

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
