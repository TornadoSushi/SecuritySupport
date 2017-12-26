package com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.LowCaseRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jerry on 2017/11/26.
 */

public class LowMainActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ListView listView;

    private List<LowCaseRecord> records;
    private ZCommonAdapter<LowCaseRecord> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_low;
    }

    @Override
    public void initData() {
        titleBar.setTitle("案件办理");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("新建案件");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                Intent intent = new Intent(LowMainActivity.this, LowAddActivity.class);
                startActivity(intent);
            }
        });
        records = DataSupport.where("isFinish != 1").find(LowCaseRecord.class);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new ZCommonAdapter<LowCaseRecord>(this, records, R.layout.item_low_main) {
            @Override
            public void convert(ZViewHolder holder, LowCaseRecord item, int position) {
                TextView tvCompany = holder.getView(R.id.companyName);
                tvCompany.setText("立案公司: " + item.getCompanyName());
                TextView tvStep = holder.getView(R.id.tvStep);
                tvStep.setText("案件阶段: " + ZUtils.getLowCaseByStep(item.getCaseStep()));
                TextView tvTime = holder.getView(R.id.tvTime);
                tvTime.setText("立案时间: " + item.getExcuteTime());

            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LowCaseRecord record = new LowCaseRecord();
                Intent intent = new Intent(LowMainActivity.this, LowCaseFlowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recordEntity", record);
                intent.putExtra("recordBundle", bundle);
                startActivity(intent);
            }
        });
    }
}
