package com.springmvc.lxy.exec;

/**
 * 描述: java 练习
 * <p>
 *
 * @author: harry
 * @date: 2018-12-31
 **/
public class Exec1 {

    public static void main(String[] args) {
        System.out.println();

        //整型变量
        int a = 1;
        int b = 1;
        int c = a + b;

        System.out.println("整型变量：" + c);

        //浮点类型变量
        double a1 = 1.5;
        double b1 = 1.5;
        double c1 = a1 + b1;

        System.out.println("浮点类型变量：" + c1);

        //字符串类型变量
        String a11 = "hello ";
        String b11 = "world";
        String c11 = a11 + b11;

        System.out.println("字符串变量：" + c11);


        //布尔类型变量
        boolean a111 = true;
        boolean b111 = false;
        boolean c111 = a111 & b111;

        System.out.println("布尔类型变量：" + c111);


    }

}
