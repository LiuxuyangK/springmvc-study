package com.springmvc.lxy.service.impl;

import com.springmvc.lxy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-08-31
 **/
@Service
public class UserServiceImple implements UserService {
    @Override
    public String getUser1() {
        System.out.println("use1");
        return "user1";
    }


    public static void main(String[] args) {
        //replace和replaceAll，都不动原来的src，只返回一个新的string
//        String src = new String("ab43a2c43d");
//        System.out.println(src.replace("3", "f") + "---" + src);
//        System.out.println(src.replace('3', 'f') + "---" + src);
//        System.out.println(src.replaceAll("\\d", "f") + "---" + src);
//        System.out.println(src.replaceAll("a", "f") + "---" + src);
//        System.out.println(src.replaceFirst("\\d", " f") + "---" + src);
//        System.out.println(src.replaceFirst("4", "h") + "---" + src);

        UserServiceImple userServiceImple = new UserServiceImple();
        String hello = userServiceImple.toLowerCase("Hello");
        System.out.println(hello);
    }

    public int numUniqueEmails(String[] emails) {
        Set<String> set = new HashSet<>();
        for (String email : emails) {
            String[] strings = email.split("@");
            String local = strings[0];
            String domain = strings[1];

            local = local.replace(".", "");
            local = getRidOfPlus(local);
            set.add(local + "@" + domain);
        }
        return set.size();
    }

    public String toLowerCase(String str) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((int) c <= 90 && (int) c >= 65) {
                chars[i] = (char) ((int) c + 32);
            }
        }
        return new String(chars);
    }

    private String getRidOfPlus(String local) {
        if (local.contains("+")) {
            int index = local.indexOf("+");
            String pre = local.substring(0, index);
            local = pre;
            return getRidOfPlus(local);
        }
        return local;
    }
}
