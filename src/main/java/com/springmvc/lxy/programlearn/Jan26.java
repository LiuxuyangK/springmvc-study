package com.springmvc.lxy.programlearn;

/**
 * 描述：
 * <p>
 * <p>
 * harryliu
 * 2019/1/27
 */
class Student {
    int grade;
    double score;
    User parent;
}


class User {
    char sex;
    float height;
    float weight;
    String home;
    String position;
    String name;

    public void drink() {
        System.out.println("喝水。。。");
    }

    public void eat() {
        System.out.println("吃饭。。。");
    }

    public void eatToomuch() {
        System.out.println("吃了很多很多。。。");
        this.weight++;
        this.height += 2;
    }

    public  void love(User user){
        System.out.println(this.name + "和谁love：" + user.name);

    }
}


public class Jan26 {
    public static void main(String[] args) {
//        test1();

//        whiletest();
        User user2 = new User();
        user2.sex = '女';
        user2.name = "雪琴";
        user2.position = "会计";
        user2.home = "安徽芜湖";
        user2.weight = 95F;
        user2.height = 168F;

        user2.drink();
        System.out.println(user2.weight);
        user2.eatToomuch();
        System.out.println(user2.weight);


        User user3 = new User();
        user3.name = "刘旭阳";

        user2.love(user3);

        Student ab = new Student();
        ab.grade = 3;
        ab.parent = user2;
        ab.score = 88.888D;
        System.out.println();
    }

    private static void test1() {
        byte y = 2;
        short s = 9;
        int a = 100;
        long o = 987654321L;
        float c = 1.23456F;
        double b = 1.576856788D;

        boolean g = true;
        boolean f = false;
        boolean p = 4 > 5;
        boolean aa = g == f;

        boolean ab = g && f;
        boolean ac = p || g;

        char qw = '阳';

        char qa = 'x';
//        System.out.println(qw);
//
//
        String er = "liuxuyangshizhu";
//        System.out.println(y + "---" + er);


        int[] arr = {1, 2, 3, 4, 5};
        String[] k = {"xuxueqin", "liuxuyang", "pi", "wo"};
        for (int t = 0; t < 4; t = t + 1) {
            System.out.println(arr[t]);

            for (int u = 0; u < 3; u = u + 1) {
                System.out.println(k[u]);
            }
        }

        //    double [] liu={wo,shi,xu,xue,qin};
    }


}
