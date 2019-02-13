package com.springmvc.lxy.other;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-16
 **/
public class Exec_19_01_16 {

    public static void main(String[] args) {
        int a = 'Z';
        System.out.println(a);

        System.out.println("1:" + reverseOnlyLetters("a-bC-dEf-ghIj"));

        System.out.println("2:" + reverseOnlyLetters2("a-bC-dEf-ghIj"));

        int[][] arr = {{1, 2}, {3, 4}};
        System.out.println("3:" + surfaceArea(arr));

        String s = "a1b2";
        System.out.println("1:" + letterCasePermutation(s));
        ;
        System.out.println("2:" + letterCasePermutation2(s));
        ;
    }

    /**
     * 判断数组是不是单调的。。。
     * 越做越跑偏了。。。
     *
     * @param A
     * @return
     */
    public boolean isMonotonic(int[] A) {
        if (A.length == 1) {
            return true;
        }
        boolean big = A[1] >= A[0];
        for (int i = 1; i < A.length; i++) {
            if (A[i] == A[i - 1]) {
                continue;
            }
            if (big != A[i] > A[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 这个解答更好一点！
     * 老老实实拿一个变量判断一下，如果是增长，就这样，如果不增长，就这样；
     *
     * @param A
     * @return
     */
    public boolean ismonotonic2(int[] A) {
        boolean increasing = (A[A.length - 1] - A[0]) > 0 ? true : false;
        for (int i = 0; i < A.length - 1; i++) {
            // if equal, we never hit either condition
            if (increasing) {
                if (A[i] > A[i + 1]) return false;
            } else {
                if (A[i] < A[i + 1]) return false;
            }
        }
        return true;
    }

    /**
     * Input: S = "a1b2"
     * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
     * 这个题有点意思：用递归的方式更好做。
     *
     * @param S
     * @return
     */
    public static List<String> letterCasePermutation(String S) {

        List<String> s = new ArrayList<String>();
        if (S == null) {
            return new ArrayList<>();
        }

        util(S.toCharArray(), s, 0);
        return s;
    }

    /**
     * 用递归是最好的一种解决办法！
     *
     * @param c_arr
     * @param s
     * @param index
     */
    public static void util(char[] c_arr, List<String> s, int index) {
        //下标到了最后的末尾，说明这一次遍历已经结束了，添加到list中，并返回
        if (index == c_arr.length) {
            s.add(new String(c_arr));
            return;
        }

        //如果是数字直接就来,index+1
        if (Character.isDigit(c_arr[index])) {
            util(c_arr, s, index + 1);
            return;
        }

        //换成小写来一波
        c_arr[index] = Character.toLowerCase(c_arr[index]);
        util(c_arr, s, index + 1);

        //换成大写来一波
        c_arr[index] = Character.toUpperCase(c_arr[index]);
        util(c_arr, s, index + 1);
    }


    /**
     * 这个方法思路很奇特，但是最后的结果是不可用，因为会有这样的结果
     * a1b2 => [2b1a, 2b1A, 2B1a, 2B1A]
     *
     * @param S
     * @return
     */
    public static List<String> letterCasePermutation2(String S) {

        if (S == null) {
            return new ArrayList<>();
        }

        List<String> s = perm(S, 0);
        return s;
    }

    private static List<String> perm(String s, int i) {
        if (s.equalsIgnoreCase("")) {
            ArrayList<String> objects = new ArrayList<>();
            objects.add("");
            return objects;
        }
        List<String> res = new ArrayList<>();
        String substring = s.substring(i + 1, s.length());
        List<String> ans = perm(substring, 0);
        for (String an : ans) {
            if (Character.isDigit(s.charAt(i))) {
                res.add(an + s.charAt(i));
            } else {
                res.add(an + ("" + s.charAt(i)).toLowerCase());
                res.add(an + ("" + s.charAt(i)).toUpperCase());
            }
        }
        return res;
    }


    /**
     * 给定一个数组，用这个数组里面的数字，凑成三角形三边长，使得面积最大
     *
     * @param A
     * @return
     */
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        int ci = A.length - 1;
        while (ci > 1) {
            int c = A[ci];
            int a = A[ci - 1];
            int b = A[ci - 2];
            if (a + b > c) return a + b + c;
            ci--;
        }
        return 0;
    }

    /**
     * 每次可以拿 1--3个，我先手
     *
     * @param n
     * @return
     */
    public boolean canWinNim(int n) {
        if (n < 4) {
            return true;
        }

        //只要给对方留下一个4，我就赢了
        if (n % 4 == 0) {
            return false;
        }
        return true;
    }

    /**
     * 越坐越偏了。。。
     *
     * @param grid
     * @return
     */
    public static int surfaceArea(int[][] grid) {
        int line = 0;
        for (int i = 0; i < grid.length; i++) {
            line += Arrays.stream(grid[i]).max().getAsInt();
        }

        int zeroCount = 0;
        for (int i = 0; i < grid[0].length; i++) {
            int max = 0;
            for (int j = 0; j < grid.length; j++) {
                max = Math.max(max, grid[j][i]);
                if (grid[j][i] == 0) {
                    zeroCount++;
                }
            }
            line += max;
        }
        line = (line + grid.length * grid[0].length) * 2;
        return line - zeroCount * 2;
    }

    /**
     * 把这道题理解复杂了。。。
     * 其实很简单：如果a、b相等，那肯定不行
     * 如果a、b不等，那么就取大的就行
     *
     * @param a
     * @param b
     * @return
     */
    public int findLUSlength(String a, String b) {
        if (a.equals(b))
            return -1;
        return Math.max(a.length(), b.length());
    }

    /**
     * 类似于这种的：a-bC-dEf-ghIj，除了字母之外，其他的符号不能动：j-Ih-gfE-dCba
     * 所以说，用一个栈去保存字母的顺序，然后pop出来，达到逆转的效果。
     * <p>
     * 有一个快排的答案不错。
     *
     * @param S
     * @return
     */
    public static String reverseOnlyLetters(String S) {
        char[] array = S.toCharArray();
        char[] res = new char[array.length];
        Stack<Character> stack = new Stack<>();
        //先把对应的符号放到位置上，字母先存起来，因为要逆转，所以用栈
        for (int i = 0; i < array.length; i++) {
            if (97 <= array[i] && array[i] <= 122
                || 65 <= array[i] && array[i] <= 90) {
                stack.push(array[i]);
            }
            res[i] = array[i];
        }
        for (int i = 0; i < array.length; i++) {
            if (97 <= array[i] && array[i] <= 122
                || 65 <= array[i] && array[i] <= 90) {
                res[i] = stack.pop();
            }
        }
        return new String(res);
    }

    /**
     * 快排的解决方法，真的非常好
     *
     * @param S
     * @return
     */
    public static String reverseOnlyLetters2(String S) {
        char[] array = S.toCharArray();
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            while (left < right && !isAlphabet(array[left])) {
                left++;
            }
            while (left < right && !isAlphabet(array[right])) {
                right--;
            }
            char c = array[left];
            array[left++] = array[right];
            array[right--] = c;
        }
        return new String(array);
    }

    public static boolean isAlphabet(char c) {
        if (97 <= c && c <= 122 || 65 <= c && c <= 90) {
            return true;
        }
        return false;
    }

    /**
     * 翻转二叉树，我擦完美啊！
     * 翻转左，翻转右，翻转本身
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        invertTree(root.left);
        invertTree(root.right);
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        return root;
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
