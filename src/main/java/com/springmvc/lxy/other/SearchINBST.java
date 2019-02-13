package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-12-08
 **/
public class SearchINBST {
    private static final Logger log = LoggerFactory.getLogger(SortArrayByPartition2.class);

    public static void main(String[] args) {
        SearchINBST client = new SearchINBST();

        log.info("\n***** 2018-12-08...no1，在bst中搜索：");
        log.info("\n***** 2018-12-08...result：{}");

        log.info("\n***** 2018-12-08...no2，smallestRangeI：");
        int[] A = {1, 3, 6};
        int K = 3;
        int i = client.smallestRangeI(A, K);
        log.info("\n***** 2018-12-08...result：{}", i);

        log.info("\n***** 2018-12-08...no3，maxDepth：");

        log.info("\n***** 2018-12-08...no4，transpose：");
        int[][] arr = {{1, 2, 3}, {4, 5, 6}};
        int[][] transpose = client.transpose(arr);
        log.info("\n***** 2018-12-08...no4，result：{}", JSON.toJSONString(transpose));

        log.info("\n***** 2018-12-08...no5，middleNode：");

        log.info("\n***** 2018-12-08...no6，subdomainVisits：");
        String[] arr2 = {"9001 discuss.leetcode.com", "9001 leetcode.com", "9001 com"};
        List<String> strings = client.subdomainVisits(arr2);
        log.info("\n***** 2018-12-08...no6，subdomainVisits：{}",strings);

        log.info("\n***** 2018-12-08...no7，reverseWords：");
        String str = "Let's take LeetCode contest";
        String s = client.reverseWords(str);
        log.info("\n***** 2018-12-08...no7 ends，reverseWords：{}",s);

        log.info("\n***** 2018-12-08...no8【开始】，shortestToChar",s);
        String S = "loveleetcode";
        char C = 'e';
        int[] ints = client.shortestToChar2(S, C);
        log.info("\n***** 2018-12-08...no8【结束】，shortestToChar：{}",JSON.toJSONString(ints));

        log.info("\n***** 2018-12-08...no9【开始】，findComplement",s);/**/
        int num = 5;
        int result = client.findComplement(num);
        log.info("\n***** 2018-12-08...no9【结束】，findComplement：{}",result);
    }

    /**
     * 非常棒的一个算法：
     * 先获取num对应的二进制数:从RIGHTMOST创建一个N位为1的位掩码。我们可以使用体面的Java内置函数Integer.highestOneBit来获得LEFTMOST位1，左移1，然后减1。
     * 对输入的数字可以取反 num = ~num
     * or num和mask ^
     * or ~num和mask &
     * @param num
     * @return
     */
    public int findComplement(int num) {
        int mask = (Integer.highestOneBit(num) << 1) - 1;
        return num ^ mask;
    }

    /**
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }

        if (val > root.val) {
            return searchBST(root.left, val);
        }
        return searchBST(root.right, val);
    }

    /**
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST2(TreeNode root, int val) {
        while (root != null && root.val != val) {
            root = val < root.val ? root.left : root.right;
        }
        return null;
    }

    public int smallestRangeI(int[] A, int K) {
        if (A.length == 1) {
            return 0;
        }
        int max = A[0];
        int min = A[0];
        for (int i : A) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }

        if (max - min > 2 * K) {
            return max - min - 2 * K;
        }
        return 0;
    }

    /**
     * 欣赏一个写的更简洁的版本
     *
     * @param A
     * @param K
     * @return
     */
    public int smallestRangeI2(int[] A, int K) {
        int mx = A[0], mn = A[0];
        for (int a : A) {
            mx = Math.max(mx, a);
            mn = Math.min(mn, a);
        }
        return Math.max(0, mx - mn - 2 * K);
    }

    /**
     * 求深度：递归方式
     *
     * @param root
     * @return
     */
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        if (root.children == null || root.children.size() == 0) {
            return 1;
        }
        int maxLength = 1;
        for (Node child : root.children) {
            maxLength = Math.max(maxLength, maxDepth(child) + 1);
        }
        return maxLength;
    }

    /**
     * 求深度：非递归方式
     * 巧妙的利用了queue的size，作为一个边界值进行计算。把每个从queue中poll出来的，继续再把其子节点放进去
     *
     * @param root
     * @return
     */
    public int maxDepth2(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int dept = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node poll = queue.poll();
                for (Node child : poll.children) {
                    queue.offer(child);
                }
            }
            dept++;
        }
        return dept;
    }

    public int[][] transpose(int[][] A) {
        if (A == null) {
            return null;
        }
        int[][] result = new int[A[0].length][A.length];
        for (int i = 0; i < A[0].length; i++) {
            int[] temp = new int[A.length];
            for (int i1 = 0; i1 < A.length; i1++) {
                temp[i1] = A[i1][i];
            }
            result[i] = temp;
        }
        return result;
    }

    /**
     * 更简洁的这种方式！
     *
     * @param A
     * @return
     */
    public int[][] transpose2(int[][] A) {
        int[][] result = new int[A[0].length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                result[j][i] = A[i][j];
            }
        }
        return result;
    }


    /**
     * 一串节点的中间节点：快慢节点
     * 只用快的节点计算就行了。两者同时出发。
     *
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode middleNode = head;
        ListNode fastNode = head;
        while (fastNode != null && fastNode.next != null) {
            middleNode = middleNode.next;
            fastNode = fastNode.next == null ? null : fastNode.next.next;
        }
        return middleNode;
    }

    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> map = new HashMap<>();
        for (String cpdomain : cpdomains) {
            String[] strings = cpdomain.split(" ");
            Integer n = Integer.valueOf(strings[0]);
            String s = strings[1];
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == '.') {
                    String d = s.substring(i + 1);
                    map.put(d, map.getOrDefault(d, 0) + n);
                }
            }
            map.put(s, map.getOrDefault(s, 0) + n);
        }
        List<String> res = new ArrayList();
        for (String d : map.keySet()) {
            res.add(map.get(d) + " " + d);
        }
        return res;

    }

    public String reverseWords(String s) {
        String[] split = s.split(" ");
        String result = "";
        for (int i = 0; i < split.length; i++) {
            String str = split[i];
            char[] chars = str.toCharArray();
            for (int i1 = 0; i1 < chars.length/2; i1++) {
                char temp = chars[i1];
                chars[i1] = chars[chars.length - i1-1];
                chars[chars.length - i1 - 1] = temp;
            }
            result += new String(chars) + " ";
        }
        return result.trim();
    }

    public String reverseString(String s) {
        return new StringBuffer(s).reverse().toString();
    }

    /**
     * 首尾交换的方式
     *
     * @param s
     * @return
     */
    public String reverseString2(String s) {
        char[] word = s.toCharArray();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            char temp = word[i];
            word[i] = word[j];
            word[j] = temp;
            i++;
            j--;
        }
        return new String(word);
    }

    /**
     * 很奇怪的递归的方式
     *
     * @param s
     * @return
     */
    public String reverseString3(String s) {
        int length = s.length();
        if (length <= 1) return s;
        String leftStr = s.substring(0, length / 2);
        String rightStr = s.substring(length / 2, length);
        return reverseString3(rightStr) + reverseString3(leftStr);
    }

    public int[] shortestToChar(String S, char C) {
        int recentCindex = S.indexOf(C);
        int[] result = new int[S.length()];
        for (int i = 0; i < S.toCharArray().length; i++) {
            if (S.toCharArray()[i] == C) {
                recentCindex = i;
                result[i] = 0;
                continue;
            }
            if (i < recentCindex) {
                result[i] = recentCindex - i;
                continue;
            }
            int i1 = S.indexOf(C, i);
            if (i1 < 0) {
                result[i] = Math.abs(i - recentCindex);
            }else{
                result[i] = Math.min(Math.abs(i - recentCindex), Math.abs(i - i1));
            }
        }
        return result;
    }

    /**
     * 非常棒的一种想法：有点动态规划的意思。
     * 第一遍过滤，直接放置是0的
     * 第二遍过滤，从前往后，利用前一个值来过滤
     * 第三遍过滤，从后往前，利用后一个值来过滤
     *
     * @param S
     * @param C
     * @return
     */
    public int[] shortestToChar2(String S, char C) {
        int n = S.length();
        int[] res = new int[n];
        for (int i = 0; i < n; ++i) res[i] = S.charAt(i) == C ? 0 : n;
        for (int i = 1; i < n; ++i) res[i] = Math.min(res[i], res[i - 1] + 1);
        for (int i = n - 2; i >= 0; --i) res[i] = Math.min(res[i], res[i + 1] + 1);
        return res;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static class Node {
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
}
