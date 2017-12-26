package com.infinity.jerry.securitysupport.common.z_utils.z_widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by jerry on 2017/11/13.
 */

public class ZOneLineText extends LinearLayout {

    private LayoutInflater inflater;
    public ZOneLineText(Context context) {
        super(context);
        initView(context);
    }

    public ZOneLineText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ZOneLineText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
    }
}
