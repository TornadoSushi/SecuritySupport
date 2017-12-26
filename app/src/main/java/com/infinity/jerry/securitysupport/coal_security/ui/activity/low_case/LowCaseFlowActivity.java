package com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CaseHelper;
import com.infinity.jerry.securitysupport.common.entity.LowCaseRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/27.
 */

public class LowCaseFlowActivity extends BaseActivity {
    @BindView(R.id.gridView)
    GridView gridView;

    private LowCaseRecord record;
    private int caseStep = 0;

    private List<CaseHelper> stepList;
    private ZCommonAdapter<CaseHelper> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_low_flow;
    }

    @Override
    public void initData() {
        record = (LowCaseRecord) getIntent().getBundleExtra("recordBundle").getSerializable("recordEntity");
        if (record != null) {
            caseStep = record.getCaseStep();
        }
        stepList = ZUtils.getCaseStepList();

        for (int i = 0; i < caseStep; i++) {
            stepList.get(i).setPass(true);
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new ZCommonAdapter<CaseHelper>(this, stepList, R.layout.item_case_step) {
            @Override
            public void convert(ZViewHolder holder, CaseHelper item, int position) {
                CardView cardView = holder.getView(R.id.cardView);
                TextView tvName = holder.getView(R.id.stepName);
                TextView tvState = holder.getView(R.id.stepState);
                tvName.setText(item.getStepName());
                if (item.isPass()) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(LowCaseFlowActivity.this, R.color.nice_drakGray));
                    tvState.setText("已完成");
                } else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(LowCaseFlowActivity.this, R.color.color_white));
                    tvState.setText("未完成");
                }
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (stepList.get(position).isPass()) {
                    Toasty.info(LowCaseFlowActivity.this, "案件已经完成此环节,请前往电脑版查看").show();
                } else {
                    Toasty.info(LowCaseFlowActivity.this, "进入activity").show();
                    Intent intent = new Intent(LowCaseFlowActivity.this, LowDetailActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("title", stepList.get(position).getStepName());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recordEntity", record);
                    intent.putExtra("recordBundle", bundle);
                    startActivityForResult(intent, REQUEST_FOR_STEP);
                }
            }
        });
    }

    private final int REQUEST_FOR_STEP = 99;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int position = data.getIntExtra("caseStepPosition", -256);
            for (int i = 0; i < caseStep; i++) {
                stepList.get(i).setPass(true);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
