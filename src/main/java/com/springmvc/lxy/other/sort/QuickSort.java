package com.springmvc.lxy.other.sort;

import com.alibaba.fastjson.JSONObject;
import com.springmvc.lxy.other.SelfDividingNumbers;

import java.util.List;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-12-16
 **/
public class QuickSort {
    public static void main(String[] args) {
        QuickSort client = new QuickSort();

        System.out.println("\nno2...快速排序");
        int[] arr2 = {1, 4, 3, 2};
        client.quickSort(arr2,0,arr2.length-1);
        System.out.println(JSONObject.toJSONString(arr2));
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
}
