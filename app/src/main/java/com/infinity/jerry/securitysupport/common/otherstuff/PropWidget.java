package com.infinity.jerry.securitysupport.common.otherstuff;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.infinity.jerry.securitysupport.R;


/**
 * Created by IceLi on 2016-01-07.
 */
public class PropWidget extends LinearLayout {
    protected LayoutInflater mInflater;
    protected String mCaption = null;
    protected String mContent = null;
    protected int mPropID = 0;
    protected View mEditView = null;
    protected ImageView mImageView = null;
    protected Context mContext;
    protected boolean mIsMainBuildingProp;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public PropWidget(Context context, String caption, String content, int propID, boolean isMainBuildingProp) {
        super(context);
        mContext = context;
        mPropID = propID;
        mCaption = caption;
        mContent = content;
        mInflater = LayoutInflater.from(context);
        mIsMainBuildingProp = isMainBuildingProp;

        setGravity(Gravity.CENTER_VERTICAL);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        this.setBackgroundResource(R.color.color_white);
        setOrientation(VERTICAL);

        initView();
    }

    void initView() {}

    public void setCommentIconVisible(Boolean isShowIcon)
    {
        if (null == mImageView) return;

        mImageView.setVisibility(isShowIcon?VISIBLE:INVISIBLE);
    }

    public void setCommentOnClickListener(OnClickListener listener)
    {
        if (null == mImageView) return;
        mImageView.setOnClickListener(listener);
    }

   public void setContentOnClickListener(OnClickListener listener)
    {
        if (null != mEditView)
        {
            mEditView.setOnClickListener(listener);
        }
    }

    public void setEditValue(String strContent)
    {

    }

    public CharSequence getEditValue()
    {
        return "";
    }

    public void setCommentIconOnClickListener(OnClickListener listener)
    {
        if (null != mImageView)
        {
            mImageView.setOnClickListener(listener);
        }
    }

    public View getEdit() { return mEditView; }

    public int getPropID()
    {
        return mPropID;
    }

    protected int convertDIP2PX(int dip)
    {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int paddingPx = (int)(scale * dip + 0.5);
        return paddingPx;
    }

}
