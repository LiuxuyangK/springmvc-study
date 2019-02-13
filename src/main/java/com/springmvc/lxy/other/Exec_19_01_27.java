package com.springmvc.lxy.other;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-27
 **/
public class Exec_19_01_27 {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;

        ListNode listNode = reverseList2(listNode1);
        System.out.println();
    }

    /**
     * 用前序遍历的方式，拿到一个str
     *
     * @param t
     * @return
     */
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }
        String result = t.val + "";
        String left = tree2str(t.left);
        String right = tree2str(t.right);
        if (left == "" && right == "") return result;
        if (left == "") return result + "()" + "(" + right + ")";
        if (right == "") return result + "(" + left + ")";
        return result + "(" + left + ")" + "(" + right + ")";
    }

    /**
     * 获取 次数超过 nums.length 的数字
     * 【这个答案神了。。。】
     * 和com.springmvc.lxy.other.Exec_19_01_21#countBinarySubstrings(java.lang.String) 很像，可以参照一下。
     *
     * @param num
     * @return
     */
    public int majorityElement(int[] num) {
        int major = num[0], count = 1;
        for (int i = 1; i < num.length; i++) {
            if (count == 0) {
                count++;
                major = num[i];
            } else if (major == num[i]) {
                count++;
            } else count--;

        }
        return major;
    }

    // Sorting
    public int majorityElement1(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    // Hashtable
    public int majorityElement2(int[] nums) {
        Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
        //Hashtable<Integer, Integer> myMap = new Hashtable<Integer, Integer>();
        int ret = 0;
        for (int num : nums) {
            if (!myMap.containsKey(num))
                myMap.put(num, 1);
            else
                myMap.put(num, myMap.get(num) + 1);
            if (myMap.get(num) > nums.length / 2) {
                ret = num;
                break;
            }
        }
        return ret;
    }

    /**
     * 一个链表是 4,1,5,9。只给一个节点node，让你删除。。。
     * 牛逼。。。
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 计算 a+b ，但是不能用 +
     * https://leetcode.com/problems/sum-of-two-integers/discuss/84278/A-summary%3A-how-to-use-bit-manipulation-to-solve-problems-easily-and-efficiently
     *
     * @param a
     * @param b
     * @return
     */
    public int getSum(int a, int b) {
        if (a == b) {
            return 2 * a;
        }
        return (a * a - b * b) / (a - b);
    }

    /**
     * 思路：还是用一个dfs 去搜索，搜索 (k - currentNode.val) 的值
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return dfs(root, set, k);
    }

    private boolean dfs(TreeNode root, Set<Integer> set, int k) {
        if (root == null) {
            return false;
        }
        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return dfs(root.left, set, k) || dfs(root.right, set, k);
    }


    /**
     * 递归版本：
     * 1.每次都用head.next进行迭代，如果已经到了末尾的一个，那就直接return
     * 2.递归调用的返回值，是原来链表的最后一个值，这个值不动它
     * 3.每次递归调用回来的处理：先拿出当前head.next，然后把当前head，缀到head.next 的 next上面
     * 4.把head.next = null，因为head.next已经没用了，而且head也已经缀到了最后一个上面。
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode listNode = reverseList(head.next);
        ListNode next = head.next;
        next.next = head;
        head.next = null;
        return listNode;
    }

    public static ListNode reverseList2(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        ListNode prev = null;
        ListNode next = null;

        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int x) {
            val = x;
        }
    }
}
