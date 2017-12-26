package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class WindowUtils {

    private static int sWidth = 0;
    private static int sHeight = 0;
    private static float sRatio = 0;

    public static int getScreenWidth(Context context) {
        if (sWidth == 0) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics m = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(m);
            sWidth = m.widthPixels;
        }
        return sWidth;
    }

    public static int getScreenHeight(Context context) {
        if (sHeight == 0) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics m = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(m);
            sHeight = m.heightPixels;
        }
        return sHeight;
    }

    public static float getScreenRatio(Context context) {
        if (sRatio == 0) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics m = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(m);
            sRatio = getScreenHeight(context) * 1.0f / getScreenWidth(context);
        }
        return sRatio;
    }

    public static int getDrawableResource(Context context, String resourceName) {
        Resources res = context.getResources();
        float ratio = getScreenRatio(context);
        if (ratio >= 1.7f) {
            resourceName += "_720";
        } else if (ratio > 1.5f && ratio < 1.7) {
            resourceName += "_800";
        } else {
            resourceName += "_960";
        }
        return res.getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}