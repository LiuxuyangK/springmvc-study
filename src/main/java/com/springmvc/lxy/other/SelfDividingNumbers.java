package com.springmvc.lxy.other;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-11-18
 **/
public class SelfDividingNumbers {

    public static void main(String[] args) {
        SelfDividingNumbers client = new SelfDividingNumbers();

        System.out.println("no1...");
        int left = 1;
        int right = 22;
        List<Integer> integers = client.selfDividingNumbers(left, right);
        System.out.println(JSONObject.toJSONString(integers));

        System.out.println("\nno2...");
        int[] arr = {1, 4, 3, 2};
        int sum = client.arrayPairSum(arr);
        System.out.println(sum);


        System.out.println("\nno2...快速排序");
        int[] arr2 = {1, 4, 3, 2};
        client.quickSort(arr2,0,arr2.length-1);
        System.out.println(JSONObject.toJSONString(arr2));
    }

    /**
     * 非常简单的思路：判断每一个数字的每一位
     * 实现的比较不错
     *
     * @param left
     * @param right
     * @return
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new ArrayList<>();
        for (int i = left; i <= right; i++)
            if (dividingNumber(i)) res.add(i);
        return res;
    }

    boolean dividingNumber(int num) {
        for (int n = num; n > 0; n = n / 10) {
            if (n % 10 == 0 || num % (n % 10) != 0) {
                return false;
            }
        }
        return true;
    }

    public int adjustArr(int[] nums, int left, int right) {
        int temp = nums[left];
        while (left < right) {
            while(left < right && nums[right] > temp){
                right--;
            }
            if (left < right) {
                nums[left] = nums[right];
                left ++;
            }

            while(left < right && nums[left] < temp){
                left++;
            }
            if (left < right) {
                nums[right] = nums[left];
                right --;
            }
        }
        nums[left] = temp;
        return left;
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int i = adjustArr(nums, left, right);
            quickSort(nums, left, i - 1);
            quickSort(nums, i + 1, right);
        }
    }

    public int arrayPairSum(int[] nums) {
        //jdk 工具类
//        Arrays.sort(nums);

        //冒泡排序
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[i] > nums[j]) {
//                    int temp = nums[i];
//                    nums[i] = nums[j];
//                    nums[j] = temp;
//                }
//            }
//        }

        quickSort(nums,0,nums.length - 1);
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }
        return sum;
    }
}
