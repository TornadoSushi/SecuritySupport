package com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_check;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jerry on 2017/12/12.
 */

public class CheckSynActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ListView listView;

    private List<PlanRecord> recordList;
    private ZCommonAdapter<PlanRecord> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_syn_check_1;
    }

    @Override
    public void initData() {
        recordList = DataSupport.where("isSyn != 1 OR isSyn ISNULL AND isFinish = 1").find(PlanRecord.class);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("检查同步");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
//        titleBar.setTvPlusText("同步记录");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                Intent intent = new Intent(CheckSynActivity.this, CheckSynHistoryActvitiy.class);
                startActivity(intent);
            }
        });
        adapter = new ZCommonAdapter<PlanRecord>(this, recordList, R.layout.item_syn_check) {
            @Override
            public void convert(ZViewHolder holder, PlanRecord item, int position) {
                TextView tvCompany = holder.getView(R.id.tvCompany);
                tvCompany.setText(item.getCompanyName());
                TextView tvType = holder.getView(R.id.tvType);
                tvType.setText(ZUtils.getStrFromPlanType(item.getPlanType()));
                TextView tvPlanName = holder.getView(R.id.tvPlanName);
                tvPlanName.setText(item.getPlanName());
                TextView tvTime = holder.getView(R.id.tvTime);
                String endTime = (item.getEndTime() == null ? "" : item.getEndTime());
                tvTime.setText(item.getStartTime() + "至" + endTime);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

}
