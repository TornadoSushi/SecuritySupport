package com.infinity.jerry.securitysupport.common.z_utils.z_widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.infinity.jerry.securitysupport.R

/**
 * Created by jerry on 2017/11/13.
 */
class ZTitleBar :RelativeLayout {

    var inflater: LayoutInflater? = null
    var tvTitle: TextView? = null
    var imBack: ImageView? = null
    var tvPlus: TextView? = null
    var imPlus: ImageView? = null

    val mode_text:Int = 100;

    var textlistener: OnTextModeListener? = null
    var imageListener: OnImageModeListener? = null

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
        val view = inflater!!.inflate(R.layout.z_titlebar, this, true)
        tvTitle = view.findViewById(R.id.tvTitle)
        imBack = view.findViewById(R.id.imBack)
        tvPlus = view.findViewById(R.id.tv_plus)
        imPlus = view.findViewById(R.id.im_plus)

        imBack!!.setOnClickListener({
            (context as Activity).finish()
        })

        tvPlus!!.setOnClickListener({
            textlistener?.onClickTextMode()
        })

        imPlus!!.setOnClickListener({
            imageListener?.onClickImageModel()
        })
        setTitleMode(MODE_NONE)
    }



    fun setTitle(content: String) {
        tvTitle!!.text = content
    }

    fun noBack() {
        imBack!!.visibility = View.GONE
    }

    fun setTitleMode(mode: Int) {
        when (mode) {
            MODE_IMAGE -> {
                imPlus?.visibility = View.VISIBLE
                tvPlus?.visibility = View.GONE
            }
            MODE_TEXT -> {
                tvPlus?.visibility = View.VISIBLE
                imPlus?.visibility = View.GONE
            }
            MODE_NONE -> {
                imPlus?.visibility = View.GONE
                tvPlus?.visibility = View.GONE
            }
        }
    }



    fun setTvPlusText(string: String) {
        tvPlus?.setText(string)
    }

    fun setImPlusDrawable(drawable: Drawable) {
        imPlus?.setImageDrawable(drawable)
    }

    interface OnTextModeListener {
        fun onClickTextMode()
    }

    interface OnImageModeListener {
        fun onClickImageModel()
    }

    fun setOnTextModeListener(listener: OnTextModeListener) {
        textlistener = listener
    }

    fun setOnImageModeListener(listener: OnImageModeListener) {
        imageListener = listener
    }

    companion object {
        val MODE_IMAGE: Int = 200
        val MODE_TEXT: Int = 100
        val MODE_NONE: Int = 150
    }
}