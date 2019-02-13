package com.springmvc.lxy.other;

import java.util.*;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-21
 **/
public class Exec_19_01_21 {

    private static final int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103};

    public static void main(String[] args) {
        int[] arr = {1, 1, 0, 1, 1, 1};
        int maxConsecutiveOnes = findMaxConsecutiveOnes(arr);
        System.out.println(maxConsecutiveOnes);

        int i = addDigits(38);
        System.out.println(i);

        char theDifference = findTheDifference("a", "aa");

        int[] arr1 = {4, 3, 2, 7, 8, 2, 3, 1};
        findDisappearedNumbers(arr1);
    }

    /**
     * 很难，思路不容易想！
     *
     * 00110011 有6个子串，0和1是相等的 => "0011", "01", "1100", "10", "0011", "01"
     *
     * 1.用一个prevLen去表示上一个连续相同的是多少。
     * 2.如果有不一样的，那么立刻到else中，prev记录了前一种连续的是多少个
     * 3.并且最后的判断，如果prevLen >= curLen，那么就表示上一种连续的更长，比当前的长，仍然可以容纳下当前的这个
     *
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int prevRunLength = 0, curRunLength = 1, res = 0;
        for (int i=1;i<s.length();i++) {
            if (s.charAt(i) == s.charAt(i-1)) curRunLength++;
            else {
                prevRunLength = curRunLength;
                curRunLength = 1;
            }
            if (prevRunLength >= curRunLength) res++;
        }
        return res;
    }

    /**
     * 两种方法都特别好，想法太棒了！
     * 这个方法不如上面的方法思路好
     *
     * @param s
     * @return
     */
    public int countBinarySubstrings2(String s) {
        int cur = 1, pre = 0, res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) cur++;
            else {
                res += Math.min(cur, pre);
                pre = cur;
                cur = 1;
            }
        }
        return res + Math.min(cur, pre);
    }
    /**
     * 求交集
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        return null;
    }

    /**
     * 识别大写字母用的对不对
     * 如果都是大写字母，就对
     * 如果一个都没有，就不对
     * 如果只有一个大写字母，而且它是开头，就对。
     *
     * 优雅的实现！
     * @param word
     * @return
     */
    public boolean detectCapitalUse(String word) {
        int cnt = 0;
        for(char c: word.toCharArray()) if('Z' - c >= 0) cnt++;
        return ((cnt==0 || cnt==word.length()) || (cnt==1 && 'Z' - word.charAt(0)>=0));
    }

    /**
     * 找到 1 <= nums[i] <= nums.length
     * Input:[4,3,2,7,8,2,3,1]
     *
     * Output:[5,6]
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        for (int num : nums) {
            int temp = nums[num - 1];
            while (temp != num) {
                nums[num - 1] = num;
                num = temp;
                temp = nums[temp - 1];
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num-1 != i) {
                res.add(i+1);
            }
        }

        return res;
    }

    /**
     * 我用了一种很蠢的方法，遍历
     *
     * @param s
     * @param t
     * @return
     */
    public static char findTheDifference(String s, String t) {
        char[] array = s.toCharArray();
        List<Character> set = new ArrayList<>();
        for (char c : array) {
            set.add(c);
        }

        char[] array1 = t.toCharArray();
        List<Character> set1 = new ArrayList<>();
        for (char c : array1) {
            set1.add(c);
        }

        for (char c : array) {
            set1.remove((Character) c);
        }

        return set1.get(0);
    }

    /**
     * 用亦或，真的是太帅了
     * 数组逆置：com.springmvc.lxy.other.UniqueMorseRepresentations#flipAndInvertImage1(int[][])
     * 找到数组中唯一的那个数字：com.springmvc.lxy.other.Exec_19_01_14#singleNumber(int[])
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference1(String s, String t) {
        int n = t.length();
        char c = t.charAt(n - 1);
        for (int i = 0; i < n - 1; ++i) {
            c ^= s.charAt(i);
            c ^= t.charAt(i);
        }
        return c;
    }

    /**
     * 这个是bfs（广度优先）的方法
     *
     * @param employees
     * @param id
     * @return
     */
    public int getImportance2(List<Employee> employees, int id) {
        int total = 0;
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }
        Queue<Employee> queue = new LinkedList<>();
        queue.offer(map.get(id));
        while (!queue.isEmpty()) {
            Employee current = queue.poll();
            total += current.importance;
            for (int subordinate : current.subordinates) {
                queue.offer(map.get(subordinate));
            }
        }
        return total;
    }

    /**
     * 我这个是 dfs （深度优先）的方法
     *
     * @param employees
     * @param id
     * @return
     */
    public int getImportance(List<Employee> employees, int id) {
        int count = 0;
        List<Integer> subs = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.id == id) {
                count += employee.importance;
                subs.addAll(employee.subordinates);
            }
        }
        for (Integer sub : subs) {
            count += getImportance(employees, sub);
        }
        return count;
    }

    /**
     * 这个是非常棒的一个解法！
     * 将licensePlate，计算一个值，类似于hashCode 一样的东西，计算的方式是用一个质数的数组，进行计算的。
     *
     * @param licensePlate
     * @param words
     * @return
     */
    public String shortestCompletingWord(String licensePlate, String[] words) {
        long charProduct = getCharProduct(licensePlate.toLowerCase());
        String shortest = "aaaaaaaaaaaaaaaaaaaa"; // 16 a's
        for (String word : words)
            if (word.length() < shortest.length() && getCharProduct(word) % charProduct == 0)
                shortest = word;
        return shortest;
    }

    private long getCharProduct(String plate) {
        long product = 1L;
        for (char c : plate.toCharArray()) {
            int index = c - 'a';
            if (0 <= index && index <= 25)
                product *= primes[index];
        }
        return product;
    }

    public static int addDigits(int num) {
        if (num >= 10) {
            int sum = 0;
            while (num > 0) {
                int code = num % 10;
                sum += code;
                num = num / 10;
            }
            return addDigits(sum);
        }
        return num;
    }

    /**
     * 移动掉所有的0，而且还要保留原先的顺序
     * 看到一个非常棒的答案！用一个下标做循环：遇到非0的数，就加
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index++] = num;
            }
        }
        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    /**
     * 数连续的1，这个 很简单
     *
     * @param nums
     * @return
     */
    public static int findMaxConsecutiveOnes(int[] nums) {

        int count = 0;
        int max = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            } else {
                max = Math.max(max, count);
                count = 0;
            }
        }
        return Math.max(max, count);
    }


    public static class Employee {
        // It's the unique id of each node;
        // unique id of this employee
        public int id;
        // the importance value of this employee
        public int importance;
        // the id of direct subordinates
        public List<Integer> subordinates;
    }

    ;
}
