/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import com.google.gson.Gson;

/**
 * Created by root on 2/17/17.
 */

public class ZGsonUtils {

    private static ZGsonUtils zGsonUtils = null;
    private Gson gson;

    private ZGsonUtils() {
        gson = new Gson();
    }

    public static ZGsonUtils getInstance() {
        if (zGsonUtils == null) {
            zGsonUtils = new ZGsonUtils();
        }

        return zGsonUtils;
    }

    public String getJsonString(Object o) {
        return gson.toJson(o);
    }
}
