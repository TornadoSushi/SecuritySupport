package com.infinity.jerry.securitysupport.common.otherstuff.law_rule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.infinity.jerry.securitysupport.common.otherstuff.LawInfoAdapter;
import com.infinity.jerry.securitysupport.common.otherstuff.SearchbarListActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.LawDBController;

import java.util.List;

/**
 * Created by edwardliu on 15/11/28.
 */
public class LawRuleSearchActivity extends SearchbarListActivity {

    private LawInfoAdapter mAdapter;

    private static final String KEY = "LawRuleSearchActivity";
    static SmallActivityCache<String> sStoryHashMap = new SmallActivityCache<String>();

    public static void launchActivity(Context context, String item) {
        Long key = sStoryHashMap.put(item);

        Intent intent = new Intent(context, LawRuleSearchActivity.class);
        intent.putExtra(LawRuleSearchActivity.KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        return "法规反查";
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
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    @Override
    protected void onViewCreated() {
        String keyword = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            keyword = sStoryHashMap.get(extras.getLong(KEY));
        }
        if (null == keyword) {
            finish();
        }
        mAdapter = new LawInfoAdapter(this, keyword);
        mResultLV.setAdapter(mAdapter);
        mResultLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LawRuleDetailActivity.launchActivity(LawRuleSearchActivity.this, (LawInfo) mAdapter.getItem(position));
            }
        });

        startSearch(keyword);
    }

    @Override
    protected boolean beforeSearchStarted(String searchWord) {
        return true;
    }

    @Override
    protected void onAsyncSearchStarted(String searchWord) {
        Log.e("TAG", "11111111");
        mAdapter.setKeyword(searchWord);
        LawDBController instance = LawDBController.instance();
        final List<LawInfo> items = instance.db_getQueriedLawInfos(searchWord);
        if (null != items && !items.isEmpty()) {
//            SecurityLawReference.getInstance().saveLawRuleHistory(searchWord);
            for (LawInfo item : items) {
                int index = item.content.indexOf(searchWord);
                int end = item.content.length();
                int start = 0;
                String prefix = "";
                String endfix = "";
                if (index <= 70) {
                    if (end > (index + 15)) {
                        end = index + 15;
                        endfix = "…";
                    }
                } else {
                    prefix = "…";
                    start = index - 70;
                    end = index + 15;
                    endfix = "…";
                }
                if (end > item.content.length()) {
                    end = item.content.length();
                    endfix = "";
                }
                StringBuilder builder = new StringBuilder(prefix);
                builder.append(item.content.substring(start, end));
                builder.append(endfix);

                item.contentShow = builder.toString().replaceAll("\\s", "");
            }

            postRunnable(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setItems(items);
                    mAdapter.notifyDataSetChanged();

                    mResultLV.setVisibility(View.VISIBLE);
                    mNoResultLL.setVisibility(View.GONE);
                }
            });
        } else {
            postRunnable(new Runnable() {
                @Override
                public void run() {
                    mResultLV.setVisibility(View.GONE);
                    mNoResultLL.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
