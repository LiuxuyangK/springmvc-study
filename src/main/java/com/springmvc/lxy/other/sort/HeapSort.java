package com.springmvc.lxy.other.sort;

import java.util.Arrays;

/**
 * 描述: 堆排序（维基百科的）
 * <p>
 *
 * @author: harry
 * @date: 2018-12-13
 **/
public class HeapSort {

    private int[] arr;

    public HeapSort(int[] arr) {
        this.arr = arr;
    }

    public static void heapSort(int[] arr) {
        int len = arr.length -1;
        for(int i = arr.length/2 - 1; i >=0; i --){ //堆构造
            heapAdjust(arr,i,len);
        }
        while (len >0){
            swap(arr,0,len--);    //将堆顶元素与尾节点交换后，长度减1，尾元素最大
            heapAdjust(arr,0,len);    //再次对堆进行调整
        }
    }

    /**
     *
     * @param arr
     * @param i
     * @param len 最后一个值的下标。数组长度为6，则len是5
     */
    public static  void heapAdjust(int[] arr,int i,int len){
        int left,right,j ;
        while((left = 2*i+1) <= len){    //判断当前父节点有无左节点（即有无孩子节点，left为左节点）
            right = left + 1;  //右节点
            j = left;   //j"指针指向左节点"
            if(right < len && arr[left] < arr[right])    //右节点大于左节点
                j ++;     //当前把"指针"指向右节点
            if(arr[i] < arr[j])    //将父节点与孩子节点交换（如果上面if为真，则arr[j]为右节点，如果为假arr[j]则为左节点）
                swap(arr,i,j);
            else         //说明比孩子节点都大，直接跳出循环语句
                break;
            i = j;
        }
    }
    public static  void swap(int[] arr,int i,int len){
        int temp = arr[i];
        arr[i] = arr[len];
        arr[len] = temp;
    }

    /**
     * 测试用例
     * <p>
     * 输出：
     * [0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9]
     */
    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 3, 0, 8, 6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8, 9, 7, 3, 1, 2, 5, 9, 7, 4, 0, 2, 6};
        int[] arr2 = new int[]{3, 5, 1, 500};
        new HeapSort(arr).heapSort(arr2);
        System.out.println(Arrays.toString(arr2));
    }
}
