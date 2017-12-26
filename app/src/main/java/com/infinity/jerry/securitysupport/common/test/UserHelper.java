package com.infinity.jerry.securitysupport.common.test;

import com.infinity.jerry.securitysupport.common.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/11/29.
 */


public class UserHelper {

    private static List<User> userList = null;

    public static List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
            User user1 = new User();
            user1.setName("李敏");
            user1.setPassword("lm");
            user1.setUsername("lm");
            userList.add(user1);

            User user2 = new User();
            user2.setName("张继勇");
            user2.setPassword("zjy");
            user2.setUsername("zjy");
            userList.add(user2);

            User user3 = new User();
            user3.setName("李广法");
            user3.setPassword("lgf");
            user3.setUsername("lgf");
            userList.add(user3);

            User user4 = new User();
            user4.setName("刘顺国");
            user4.setPassword("lsg");
            user4.setUsername("lsg");
            userList.add(user4);

            User user5 = new User();
            user5.setName("文世元");
            user5.setPassword("wsy");
            user5.setUsername("wsy");
            userList.add(user5);

            User user6 = new User();
            user6.setName("王智力");
            user6.setPassword("wzl");
            user6.setUsername("wzl");
            userList.add(user6);


            User user7 = new User();
            user7.setName("龚建明");
            user7.setPassword("gjm");
            user7.setUsername("gjm");
            userList.add(user7);

            User user8 = new User();
            user8.setName("任新龙");
            user8.setPassword("rxl");
            user8.setUsername("rxl");
            userList.add(user8);


            User user9 = new User();
            user9.setName("周玉明");
            user9.setPassword("zym");
            user9.setUsername("zym");
            userList.add(user9);

            User user10 = new User();
            user10.setName("刘俊宁");
            user10.setPassword("ljn");
            user10.setUsername("ljn");
            userList.add(user10);


            User user11 = new User();
            user11.setName("米绍成");
            user11.setPassword("msc");
            user11.setUsername("msc");
            userList.add(user11);

            User user12 = new User();
            user12.setName("瞿廷春");
            user12.setPassword("qtc");
            user12.setUsername("qtc");
            userList.add(user12);

            User user13 = new User();
            user13.setName("刘言");
            user13.setPassword("ly");
            user13.setUsername("ly");
            userList.add(user13);

            User user14 = new User();
            user14.setName("杨家寿");
            user14.setPassword("yjs");
            user14.setUsername("yjs");
            userList.add(user14);

            User user15 = new User();
            user15.setName("蔡海刚");
            user15.setPassword("chg");
            user15.setUsername("chg");
            userList.add(user15);

            User user16 = new User();
            user16.setName("周必华");
            user16.setPassword("zbh");
            user16.setUsername("zbh");
            userList.add(user16);

            User user17 = new User();
            user17.setName("邹贵");
            user17.setPassword("zg");
            user17.setUsername("zg");
            userList.add(user17);
        }
        return userList;
    }
}
