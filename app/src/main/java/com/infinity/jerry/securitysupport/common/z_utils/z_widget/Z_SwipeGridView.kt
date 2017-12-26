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
import android.widget.GridView
import android.widget.ImageView
import android.widget.RelativeLayout
import com.infinity.jerry.securitysupport.R
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.SetSwipeLayoutParam

/**
 * Created by jerry on 2017/6/15.
 */
class Z_SwipeGridView : RelativeLayout {

    var inflater: LayoutInflater? = null
    var imError: ImageView? = null
    var gridView: GridView? = null
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
        var view = inflater!!.inflate(R.layout.z_swipe_gridview, this, true)
        imError = view.findViewById(R.id.imError)
        gridView = view.findViewById(R.id.gridView)
        swipeLayout = view.findViewById(R.id.swipeGridLayout)
        SetSwipeLayoutParam.setParams(swipeLayout!!)
        imError!!.visibility = View.GONE
        swipeLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            zOnRefreshListener?.onRefresh()
        }
        )

    }

    fun getSwipeGridView(): GridView? {
        return this.gridView
    }

    fun requestNoData() {
        swipeLayout!!.isRefreshing = false
        gridView!!.visibility = View.GONE
        imError!!.visibility = View.VISIBLE
        imError!!.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.tms_icon_nodata))
    }

    fun requestError() {
        swipeLayout!!.isRefreshing = false
        gridView!!.visibility = View.GONE
        imError!!.visibility = View.VISIBLE
        imError!!.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.tms_icon_fail))
    }

    fun showGridView() {
        swipeLayout!!.isRefreshing = false
        imError!!.visibility = View.GONE
        gridView!!.visibility = View.VISIBLE
    }

    fun setZ_OnRefreshlistener(listener: Z_OnRefreshListener) {
        this.zOnRefreshListener = listener
    }

    interface Z_OnRefreshListener {
        fun onRefresh()
    }
}