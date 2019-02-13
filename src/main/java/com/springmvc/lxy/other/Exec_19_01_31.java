package com.springmvc.lxy.other;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-31
 **/
public class Exec_19_01_31 {

    public static void main(String[] args) {
        titleToNumber("abcd");
    }

    public static int titleToNumber(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = result *26 + s.charAt(i) - 'A' + 1;
        }
        return result;
    }
}
