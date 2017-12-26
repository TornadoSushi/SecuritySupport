/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.customview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import com.infinity.jerry.securitysupport.R
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.SetSwipeLayoutParam

/**
 * Created by jerry on 2017/6/13.
 */
class Z_SwipeListView : RelativeLayout {
    var inflater: LayoutInflater? = null
    var imError: ImageView? = null
    var listView: ListView? = null
    var swipeLayout: SwipeRefreshLayout? = null
    var zOnRefreshListener: Z_OnRefreshListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        inflater = LayoutInflater.from(context)
        val view = inflater!!.inflate(R.layout.z_swipe_listview, this, true)
        imError = view.findViewById(R.id.imError)
        listView = view.findViewById(R.id.listView)
        swipeLayout = view.findViewById(R.id.swipeLayout_listView)
        SetSwipeLayoutParam.setParams(swipeLayout!!)
        imError!!.visibility = View.GONE
        swipeLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            zOnRefreshListener?.onRefresh()
        })
    }

    fun getSwipeListView(): ListView? {
        return this.listView
    }

    fun requestNoData() {
        swipeLayout!!.isRefreshing = false
        listView!!.visibility = View.GONE
        imError!!.visibility = View.VISIBLE
        imError!!.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.tms_icon_nodata))
    }

    fun requestError() {
        swipeLayout!!.isRefreshing = false
        listView!!.visibility = View.GONE
        imError!!.visibility = View.VISIBLE
        imError!!.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.tms_icon_fail))
    }

    fun showListView() {
        swipeLayout!!.isRefreshing = false
        imError!!.visibility = View.GONE
        listView!!.visibility = View.VISIBLE
    }

    fun setZ_OnRefreshlistener(listener: Z_SwipeListView.Z_OnRefreshListener) {
        this.zOnRefreshListener = listener
    }

    interface Z_OnRefreshListener {
        fun onRefresh()
    }


}