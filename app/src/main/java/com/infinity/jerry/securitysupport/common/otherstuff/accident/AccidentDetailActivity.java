package com.infinity.jerry.securitysupport.common.otherstuff.accident;

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
 * Created by edwardliu on 16/2/23.
 */
public class AccidentDetailActivity extends TitlebarActivity {

    private static final String KEY = "AccidentDetailActivity";
    static SmallActivityCache<AccidentDetail> sStoryHashMap = new SmallActivityCache<AccidentDetail>();

    public static void launchActivity(Context context, AccidentDetail item) {
        Long key = sStoryHashMap.put(item);

        Intent intent = new Intent(context, AccidentDetailActivity.class);
        intent.putExtra(AccidentDetailActivity.KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        return "";
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
        AccidentDetail item = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            item = sStoryHashMap.get(extras.getLong(KEY));
        }
        if (null == item) {
            finish();
            return null;
        }
        String title = item.name;
        if (title.contains("（")) {
            int index = title.indexOf("（");
            title = title.substring(0, index);
        }
        mTitleTV.setText(title);

        View view = getInflateView(R.layout.activity_accident_detail);
        TextView reasonTV = (TextView) view.findViewById(R.id.accidentReasonTV);
        TextView solutionTV = (TextView) view.findViewById(R.id.accidentSolutionTV);
        TextView adviceTV = (TextView) view.findViewById(R.id.accidentAdviceTV);
        String reason = item.knowledge.replace("\\n", "\n");
        reasonTV.setText(reason);
        String save = item.save.replace("\\n", "\n");
        solutionTV.setText(save);
        String advice = item.advice.replace("\\n", "\n");
        adviceTV.setText(advice);
        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }
}
