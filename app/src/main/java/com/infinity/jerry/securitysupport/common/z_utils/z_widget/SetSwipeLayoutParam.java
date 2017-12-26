/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_widget;

import android.support.v4.widget.SwipeRefreshLayout;

import com.infinity.jerry.securitysupport.R;


/**
 * Created by root on 3/7/17.
 */

public class SetSwipeLayoutParam {

    public static void setParams(SwipeRefreshLayout swipeLayout) {
        swipeLayout.setColorSchemeResources(R.color.color_white);
        //进度圈大小，只有两个值，DEFAULT、LARGE
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //进度圈的背景颜色
        swipeLayout.setProgressBackgroundColor(R.color.color_main);
        //进度圈位置
        swipeLayout.setPadding(0, 0, 0, 0);
        swipeLayout.setProgressViewOffset(true, 20, 20);
        swipeLayout.setDistanceToTriggerSync(80);
        //实现下拉滚动效果，100是下拉的位置
        swipeLayout.setProgressViewEndTarget(true, 100);

    }
}
