package com.infinity.jerry.securitysupport.common.z_utils.z_widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;

/**
 * Created by jerry on 2017/11/13.
 */

public class ZSearchBar extends LinearLayout {

    private LayoutInflater inflater;
    private EditText edSearch;
    private TextView tvSearch;

    private ZOnSearchListener listener;

    public ZSearchBar(Context context) {
        super(context);
        initView(context);
    }

    public ZSearchBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ZSearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.z_search_bar, this, true);
        edSearch = view.findViewById(R.id.edSearch);
        tvSearch = view.findViewById(R.id.tvSearch);

        tvSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.zOnSearch(edSearch.getText().toString().trim());
                }
            }
        });
    }

    public interface ZOnSearchListener {

        void zOnSearch(String string);
    }

    public void setSearchHint(String hint) {
        edSearch.setHint(hint);
    }

    public String getSearchString() {
        return edSearch.getText().toString().trim();
    }

    public void setOnSearchListener(ZOnSearchListener listener) {
        this.listener = listener;
    }

}
