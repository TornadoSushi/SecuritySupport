package com.infinity.jerry.securitysupport.common.z_utils.z_widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ZExpandListView extends ListView {

    public ZExpandListView(Context context) {
        super(context);
    }

    public ZExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}