package com.infinity.jerry.securitysupport.common.otherstuff;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;


public class PropEditView extends  PropWidget{
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public PropEditView(Context context, String caption, String content, int propID, boolean isMainbuilding) {
        super(context, caption, content, propID, isMainbuilding);
    }

    @Override
    protected void initView() {
        View view = mInflater.inflate(R.layout.view_prop_edit_item, null);
        LinearLayout ll = (LinearLayout)view.findViewById(R.id.firedistanceEditPropLL);
        int paddingValue = convertDIP2PX(10);
        ll.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);

        TextView captionTV = (TextView)view.findViewById(R.id.firedistancePropEditCaptionTV);
        captionTV.setText(mCaption);

        mEditView = view.findViewById(R.id.firedistancePropEditContentET);
        this.addView(view);

        View divider = new View(getContext());
        divider.setBackgroundResource(R.drawable.divider_line);
        this.addView(divider);
    }

    @Override
    public CharSequence getEditValue()
    {
        return ((EditText)mEditView).getText();
    }

    @Override
    public void setEditValue(String strContent)
    {
        ((EditText)(mEditView)).setText(strContent);
    }
}
