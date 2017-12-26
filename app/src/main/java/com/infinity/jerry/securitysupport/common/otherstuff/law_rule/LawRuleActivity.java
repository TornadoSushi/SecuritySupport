package com.infinity.jerry.securitysupport.common.otherstuff.law_rule;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.SearchbarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.StringActionRowAdapter;

/**
 * Created by edwardliu on 15/11/23.
 */
public class LawRuleActivity extends SearchbarActivity {

    private LinearLayout mHistoryLL;
    private ListView mHistoryLV;
    private TextView mClearTV;

    private StringActionRowAdapter mAdapter;

    @Override
    protected String centerTitle() {
        return "法律检索";
    }

    @Override
    protected boolean hasBackOption() {
        return true;
    }

    @Override
    protected View.OnClickListener rightClickListener() {
        return null;
    }

    @Override
    protected View onCreateChildView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_law_rule, null);
        mHistoryLL = (LinearLayout) view.findViewById(R.id.lawRuleHistoryLL);
        mHistoryLV = (ListView) view.findViewById(R.id.lawRuleHistoryLV);
        mClearTV = (TextView) view.findViewById(R.id.lawRuleClearTV);

        mAdapter = new StringActionRowAdapter(this);
        mHistoryLV.setAdapter(mAdapter);

        mHistoryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LawRuleSearchActivity.launchActivity(LawRuleActivity.this, (String) mAdapter.getItem(position));
            }
        });

        mClearTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SecurityLawReference.getInstance().clearLawRuleHistories();
                mAdapter.clearItems();
                mAdapter.notifyDataSetChanged();
                mHistoryLL.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
////        Set<String> histories = SecurityLawReference.getInstance().getLawRuleHistories();
//        if (null != histories && !histories.isEmpty()) {
//            mHistoryLL.setVisibility(View.VISIBLE);
//            mAdapter.setItems(histories);
//            mAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    protected boolean beforeSearchStarted(String searchWord) {
        clearSearch();
        LawRuleSearchActivity.launchActivity(this, searchWord);
        return false;
    }

    @Override
    protected void onAsyncSearchStarted(String searchWord) {
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {
    }
}
