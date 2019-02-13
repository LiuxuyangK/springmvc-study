package com.springmvc.lxy.other;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

/**
 * 描述:
 * <p>
 *
 * @author: harry
 * @date: 2019-01-20
 **/
public class Exec_19_01_20 {

    public static void main(String[] args) {

    }

    /**
     * 给定一组数，给出这组数能凑出最大三角形的周长
     * 先对A进行排序，然后按照 两边之和大于第三边的方式取，最后返回 a + b + c
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
     * https://leetcode.com/problems/largest-triangle-area/discuss/122711/C%2B%2BJavaPython-Solution-with-Explanation-and-Prove
     *
     * 这个证明过程很好！
     * 解题的过程就比较简单了：
     * 三重循环，然后不断计算面积
     *
     * prove2 没有看明白：
     * S = 1/2|AB * AC|
     * 其实是：三角形的面积，等于 AB 向量 * AC向量的模，再取一半
     * @param p
     * @return
     */
    public double largestTriangleArea(int[][] p) {
        double res = 0;
        for (int[] i: p)
            for (int[] j: p)
                for (int[] k: p)
                    res = Math.max(res, 0.5 * Math.abs(i[0] * j[1] + j[0] * k[1] + k[0] * i[1]- j[0] * i[1] - k[0] * j[1] - i[0] * k[1]));
        return res;
    }

    /**
     * 没明白这是什么意思。。。
     *
     * 1.看了一眼A,B数组的范围就知道，时间复杂度一定不能太高。我们分析一下可以看出，交换之后A,B数组的和都应该是相同的，也就是两个数组和的平均数。
     *
     * 2.所以对数组A进行遍历，那么对于每个数字，得到除去该数值以外其他数字的和；使用平均数减去这个和，看这个结果是否在数组B中，如果在返回即可。
     *
     * 3.也就是说，通过上面的方法，我们使得数组A的和等于平均数，这种情况下，数组B的和也为平均数。
     *
     * @param A
     * @param B
     * @return
     */
    public int[] fairCandySwap(int[] A, int[] B) {
        int dif = (IntStream.of(A).sum() - IntStream.of(B).sum()) / 2;
        HashSet<Integer> S = new HashSet<>();
        for (int a : A) S.add(a);
        for (int b : B) if (S.contains(b + dif)) return new int[] {b + dif, b};
        return new int[0];
    }
}
