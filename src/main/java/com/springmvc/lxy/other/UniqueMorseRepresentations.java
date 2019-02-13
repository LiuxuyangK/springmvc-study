package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-11-17
 **/
public class UniqueMorseRepresentations {

    public static void main(String[] args) {
        UniqueMorseRepresentations client = new UniqueMorseRepresentations();

        System.out.println("no1...");
        String[] words = {"gin", "zen", "gig", "msg"};
        int i = client.uniqueMorseRepresentations(words);
        System.out.println(i);

        System.out.println("\nno2...");
        int[] arr = {3, 1, 2, 4};
        int[] arr1 = {0, 2};
        int[] arr2 = {0, 1};
        int[] ints = client.sortArrayByParity(arr);
        System.out.println(JSONObject.toJSONString(ints));

        System.out.println("\nno3...");
        String test1 = "UD";
        String test2 = "LL";
        boolean result = client.judgeCircle(test2);
        System.out.println(JSONObject.toJSONString(result));

        System.out.println("\nno4...");
        int x = 1;
        int y = 4;
        int y2 = 3;
        int result4 = client.hammingDistance(x, y2);
        System.out.println(JSONObject.toJSONString(result4));

        System.out.println("\nno5...");
        int[][] arr5_1 = {{1, 1, 0, 0}, {1, 0, 0, 1}, {0, 1, 1, 1}, {1, 0, 1, 0}};
        int[][] arr5_2 = {{1, 1, 0}, {1, 0, 1}, {0, 0, 0}};
        int[][] result5 = client.flipAndInvertImage(arr5_2);
        System.out.println(JSONObject.toJSONString(result5));

        System.out.println("\nno6...");
        TreeNode t1 = new TreeNode(1);
        TreeNode t1_1 = new TreeNode(3);
        TreeNode t1_2 = new TreeNode(2);
        TreeNode t_1_3 = new TreeNode(5);
        t1.left = t1_1;
        t1.right = t1_2;
        t1_1.left = t_1_3;

        TreeNode t2 = new TreeNode(2);
        TreeNode t2_1 = new TreeNode(1);
        TreeNode t2_2 = new TreeNode(3);
        TreeNode t2_3 = new TreeNode(4);
        TreeNode t2_4 = new TreeNode(7);
        t2.left = t2_1;
        t2.right = t2_2;
        t2_1.right = t2_3;
        t2_2.right = t2_4;

        TreeNode tt2 = new TreeNode(1);
        TreeNode result6 = client.mergeTrees(null, tt2);
        System.out.println(JSONObject.toJSONString(result6));
    }


    public int uniqueMorseRepresentations(String[] words) {
        String[] preparedWords = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        int startIndex = 97;
        Set<String> set = new HashSet<>();
        for (String word : words) {
            char[] chars = word.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char aChar : chars) {
                int index = (int) aChar - startIndex;
                String morseWord = preparedWords[index];
                sb.append(morseWord);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    public int[] sortArrayByParity(int[] A) {
        if (A.length == 1) {
            return A;
        }

        int BASE_ODD = 2;
        int i = 0;
        int j = A.length - 1;
        while (i < j) {
            while (i < A.length - 1 && A[i] % BASE_ODD == 0) {
                i++;
            }

            while (j > 0 && A[j] % BASE_ODD != 0) {
                j--;
            }

            if (i < j) {
                //交换
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        return A;
    }

    /**
     * 用这个方法更好，一次遍历，只看准一个条件进行交换
     *
     * @param A
     * @return
     */
    public int[] sortArrayByParity1(int[] A) {
        for (int i = 0, j = 0; j < A.length; j++)
            if (A[j] % 2 == 0) {
                int tmp = A[i];
                A[i++] = A[j];
                A[j] = tmp;
                ;
            }
        return A;
    }

    public boolean judgeCircle(String moves) {

        if (moves.length() == 0) {
            return true;
        }

        if (moves.length() == 1) {
            return false;
        }

        int leftAndRight = 0;
        int upAndDown = 0;
        for (char c : moves.toCharArray()) {
            if (c == 'L') {
                leftAndRight--;
            } else if (c == 'R') {
                leftAndRight++;
            } else if (c == 'U') {
                upAndDown--;
            } else if (c == 'D') {
                upAndDown++;
            }
        }

        if (leftAndRight == 0 && upAndDown == 0) {
            return true;
        }
        return false;
    }

    public int hammingDistance(int x, int y) {
        if (x == y) {
            return 0;
        }

        int res = x ^ y;
        int count = 0;
        while (res > 0) {
            if ((res & 1) > 0) {
                count++;
            }
            res = res >> 1;
        }
        return count;
    }

    /**
     * 这个方法更好用：计算某个数的二进制数中，1的个数
     *
     * @param x
     * @param y
     * @return
     */
    int hammingDistance1(int x, int y) {
        int dist = 0, n = x ^ y;
        while (n > 0) {
            ++dist;
            n &= n - 1;
        }
        return dist;
    }


    public int[][] flipAndInvertImage(int[][] A) {
        for (int[] ints : A) {
            if (ints.length == 1) {
                ints[0] = ints[0] ^ 1;
                continue;
            }

            for (int i = 0, j = ints.length - 1; i <= j; i++, j--) {
                int temp = ints[i];
                ints[i] = (ints[j] ^ 1);
                ints[j] = (temp ^ 1);
            }
        }
        return A;
    }

    /**
     * 这个方法更好，用了一个更加简便的方法，做到数组元素逆置
     *
     * @param A
     * @return
     */
    public int[][] flipAndInvertImage1(int[][] A) {
        int n = A.length;
        for (int[] row : A)

            //数组元素逆置
            for (int i = 0; i * 2 < n; i++)
                if (row[i] == row[n - i - 1])
                    row[i] = row[n - i - 1] ^= 1;
        return A;
    }

    /**
     * 就是一个简单的前序遍历即可
     * 函数刚开头做好判断，每次递归传递左子树或者右子树，为空就传null
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;

        int val = (t1 == null ? 0 : t1.val) + (t2 == null ? 0 : t2.val);
        TreeNode newNode = new TreeNode(val);

        newNode.left = mergeTrees(t1 == null ? null : t1.left, t2 == null ? null : t2.left);
        newNode.right = mergeTrees(t1 == null ? null : t1.right, t2 == null ? null : t2.right);

        return newNode;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}
