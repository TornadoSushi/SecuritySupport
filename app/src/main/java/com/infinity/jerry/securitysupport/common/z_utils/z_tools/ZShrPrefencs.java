/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.infinity.jerry.securitysupport.common.z_utils.constant.ConstantPool;


public class ZShrPrefencs {

    private static ZShrPrefencs zShrPrefencs = null;
    private Context context;//This is the context from only application context;

    private SharedPreferences userShare;
    private SharedPreferences.Editor userEditor;


    private ZShrPrefencs(Context context) {
        this.context = context;
        userShare = context.getSharedPreferences(ConstantPool.USER_SHARE, Context.MODE_PRIVATE);
        userEditor = userShare.edit();
    }

    public static ZShrPrefencs registApp(Context context) {
        if (zShrPrefencs == null) {
            zShrPrefencs = new ZShrPrefencs(context);
        }
        return zShrPrefencs;
    }

    public static ZShrPrefencs getInstance() {
        return zShrPrefencs;
    }

    public void clearData() {
        userEditor.clear();
        userEditor.apply();
        Log.e("TAG", "SharePreferences 清除所有Token数据");
    }

    //登录成功之后需要修改这两个参数
    public void setNameAndPwd(String name, String pwd,String realName) {
        userEditor.putBoolean("is_login", true);
        userEditor.putString("zUserName", name);
        userEditor.putString("zUserPwd", pwd);
        userEditor.putString("zUserRealName", realName);
        userEditor.apply();
    }

    public String[] getNameAndPwd() {
        String[] arr = {userShare.getString("zUserName", "ZGtt")
                , userShare.getString("zUserPwd", "LoveGtt")
                , userShare.getString("zUserRealName", "NiceGtt")};
        return arr;
    }

}
