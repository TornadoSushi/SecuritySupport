package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;


/**
 * Created by IceLi on 2016-01-06.
 */
public class PropSelecterView extends PropWidget {
    public PropSelecterView(Context context, String caption, String content, int propID, boolean isMainbuilding) {
        super(context, caption, content, propID, isMainbuilding);
    }

    protected void initView() {
        View view = mInflater.inflate(R.layout.view_prop_select_item, null);
        LinearLayout ll = (LinearLayout)view.findViewById(R.id.firedistancePropLL);
        int paddingPx = convertDIP2PX(10);
        ll.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        TextView captionTV = (TextView)view.findViewById(R.id.firedistancePropCaptionTV);
        captionTV.setText(mCaption);

        TextView contentTV = (TextView)view.findViewById(R.id.firedistancePropContentTV);
        if (null != mContent && !mContent.isEmpty())
        {
            contentTV.setText(mContent);
        }
        mEditView = contentTV;
        this.addView(view);

        mImageView =  (ImageView)view.findViewById(R.id.firedistancePropCommentIV);

        View divider = new View(getContext());
        divider.setBackgroundResource(R.drawable.divider_line);
        this.addView(divider);

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String strComments;
                if (mIsMainBuildingProp)
                {
                    strComments = FireDistDataMgr.getInst().getMainBuildingPropItemByID(mPropID).comment;
                }
                else
                {
                    strComments = FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(mPropID).comment;
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setTitle("注释")
                        .setMessage(strComments);

                builder.create().show();

            }
        });
    }

    @Override
    public CharSequence getEditValue()
    {
        return ((TextView)mEditView).getText();
    }

    @Override
    public void setEditValue(String strContent)
    {
        if (strContent.isEmpty())
        {
            ((TextView) (mEditView)).setText(strContent);
            ((TextView) (mEditView)).setHint("请选择（可为空）");
        }
        else {
            ((TextView) (mEditView)).setText(strContent);
        }
    }
}
