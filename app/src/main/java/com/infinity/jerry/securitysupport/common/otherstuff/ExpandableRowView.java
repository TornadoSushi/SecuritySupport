package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;


/**
 * Created by edwardliu on 15/12/24.
 */
public abstract class ExpandableRowView extends LinearLayout {

    private TextView mTitleTV;
    private ImageView mTitleIV;

    private LayoutInflater mInflater;
    private boolean isExpanded = false;
    private String mTitle;

    protected ExpanableRowBuilder mBuilder;

    public ExpandableRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        setOrientation(LinearLayout.VERTICAL);
        mBuilder = builder();
        initView();
    }

    protected abstract ExpanableRowBuilder builder();

    protected void recycle() {
        mBuilder.recycle();
    }

    private void initView() {
        View view = mInflater.inflate(mBuilder.mainViewResourceId(), null);
        mBuilder.onTitleViewCreated(view);
        mTitleTV = (TextView) view.findViewById(mBuilder.titleViewResourceId());
        mTitleIV = (ImageView) view.findViewById(mBuilder.expandImageResourceId());
        addView(view);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                mTitleIV.setImageResource(isExpanded ? R.drawable.expand_in : R.drawable.expand_out);
                if (getChildCount() > 1) {
                    getChildAt(1).setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            }
        });
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public String getTitle() {
        return mTitle;
    }

    public void initResources(String title, Intent data, int size) {
        mTitle = title;
        mTitleTV.setText(title);

        if (size > 0) {
            LinearLayout ll = new LinearLayout(getContext());
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setVisibility(View.GONE);
            addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            mBuilder.setChildCount(size);
            for (int i = 0; i != size; ++i) {
                ll.addView(mBuilder.createExpandChildView(data, i));
                if (i != size - 1) {
                    View divider = new View(getContext());
                    divider.setBackgroundResource(R.color.rowdivider);
                    ll.addView(divider, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                }
            }
            mBuilder.onChildViewsCreated();
        }
    }
}
