package com.infinity.jerry.securitysupport.common.otherstuff;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.infinity.jerry.securitysupport.R;


/**
 */
public abstract class SearchbarListActivity extends SearchbarActivity {

    protected ListView mResultLV;
    protected LinearLayout mNoResultLL;

    @Override
    protected View onCreateChildView() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_searchbar_list, null);
        mResultLV = (ListView) view.findViewById(R.id.searchResultLV);
        mNoResultLL = (LinearLayout) view.findViewById(R.id.searchNoResultLL);

        onViewCreated();
        return view;
    }

    protected abstract void onViewCreated();
}
