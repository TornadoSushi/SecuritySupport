/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

/**
 * Created by jerry on 2017/6/29.
 */

public class StringUtils {

    public static String getNullablString(String string) {
        return string == null ? "" : string;
    }

    public static String getZeroableStringForDouble(Double sth) {
        if (sth == null || sth == 0D) {
            return "0";
        }
        return ZDoubleFormat.zFormat(String.valueOf(sth));
    }

    public static String getNullStringForDouble(Double sth) {
        if (sth == null || sth == 0D) {
            return "";
        }
        return String.valueOf(sth);
    }

    public static String getZeroableStringForLong(Long sth) {
        if (sth == null || sth == 0L) {
            return "0";
        }
        return String.valueOf(sth);
    }

    public static double getDoubleNotNull(Double sth) {
        if (sth != null) {
            return sth;
        }
        return 0.0;
    }

}
