package com.springmvc.lxy.exec;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-12-31
 **/
public class Exec2 {

    public static void main(String[] args) {
//        ifmethod();
        //12月31日
        int age=30;
        boolean xueqin=false;
        if(age >20){
            System.out.println("cu");
        }else{
            System.out.println("meili" );

        }

    }

    private static void ifmethod() {
        //if

        //tomorrowIsRain ，表示明天是否下雨
        boolean tomorrowIsRain = false;

        if(tomorrowIsRain){
            System.out.println("在家");
        }else{
            System.out.println("出去");
        }

        //买东西，如果价格是50块以内，就买。否则不买。
        int price = 100;
        if (price > 50) {
            System.out.println("不买");
        }else{
            System.out.println("买");
        }

        //更复杂一点判断条件
        //如果今天是周六 && 心情好 && 有菜 ，就做饭
        int today = 6;
        boolean happy = true;
        boolean cai = true;
        if (today == 6 && happy == true && cai == true) {
            System.out.println("做饭");
        }else{
            System.out.println("不做饭");
        }
    }
}
