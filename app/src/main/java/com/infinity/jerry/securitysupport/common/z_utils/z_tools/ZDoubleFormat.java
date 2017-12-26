/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

/**
 * Created by root on 3/17/17.
 */

public class ZDoubleFormat {

    public static String zFormat(String price) {
        StringBuilder builder = new StringBuilder();

        if (price.split("\\.").length == 0) {
            builder.append(price);
        } else {
            builder.append(price.split("\\.")[0]);
        }
        return builder.toString();
    }
}
