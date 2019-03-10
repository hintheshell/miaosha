package com.imooc.access;

import com.imooc.miaosha.domain.MiaoshaUser;

public class UserContext {

    //通过ThreadLocal保存
    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }

}
