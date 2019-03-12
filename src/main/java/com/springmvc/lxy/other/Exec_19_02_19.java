package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSONObject;

/**
 * 描述：
 * <p>
 * <p>
 * harryliu
 * 2019/2/19
 */
public class Exec_19_02_19 {

    public static void main(String[] args) {
        Exec_19_02_19 client = new Exec_19_02_19();
        int[] A = {-10, 1, 5, 7};
        int[] ints = client.sortedSquares(A);
        System.out.println(JSONObject.toJSONString(ints));
    }

    /**
     * 给定一个非降序的数组A，要求返回一个数组，新数组中每个值是原来数组中每个值的平方
     * 新数组的要求还是非降序
     * <p>
     * 1.最土的办法，先求出新数组的每个值，然后对数组做一次排序
     * 2.官网推荐的一种办法，先求比0小的
     * 3.这个解法更好，设置两个变量分别存储一前一 后的下标。先比较绝对值，哪个大，就让前面的++，后面的--
     * @param A
     * @return
     */
    public int[] sortedSquares(int[] A) {
        int[] res = new int[A.length];
        for (int d = A.length - 1, i = d, u = 0; i >= 0; i--) {
            if (-A[u] > A[d]) {
                res[i] = A[u] * A[u++];
            } else {
                res[i] = A[d] * A[d--];
            }
        }
        return res;
    }
}
