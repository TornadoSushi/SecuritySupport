package com.infinity.jerry.securitysupport.common.otherstuff.law_rule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;


/**
 * Created by edwardliu on 16/1/11.
 */
public class LawRuleDetailActivity extends TitlebarActivity {

    private static final String KEY = "LawRuleDetailActivity";
    static SmallActivityCache<LawInfo> sStoryHashMap = new SmallActivityCache<LawInfo>();

    public static void launchActivity(Context context, LawInfo item) {
        Long key = sStoryHashMap.put(item);

        Intent intent = new Intent(context, LawRuleDetailActivity.class);
        intent.putExtra(LawRuleDetailActivity.KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        return "法规详情";
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
    protected View onCreateView() {
        LawInfo item = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            item = sStoryHashMap.get(extras.getLong(KEY));
        }
        if (null == item) {
            finish();
        }

        View view = getInflateView(R.layout.activity_lawrule_detail);
        TextView titleTV = (TextView) view.findViewById(R.id.lawRuleDetailTitleTV);
        TextView contentTV = (TextView) view.findViewById(R.id.lawRuleDetailContentTV);
        titleTV.setText(item.clause);
        contentTV.setText(item.content);
        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }
}
