package com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.LowCaseRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jerry on 2017/11/27.
 */

public class LowAddActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ListView listView;

    private List<CheckItemRecord> dangerList;
    private ZCommonAdapter<CheckItemRecord> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_low_add;
    }

    @Override
    public void initData() {
        titleBar.setTitle("建议立案企业");
        dangerList = DataSupport.where("lowPunish = 2").find(CheckItemRecord.class);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        adapter = new ZCommonAdapter<CheckItemRecord>(this,dangerList,R.layout.item_lowcase_advice) {
            @Override
            public void convert(ZViewHolder holder, CheckItemRecord item, int position) {
                TextView tvName = holder.getView(R.id.companyName);
                tvName.setText(item.getCompanyName());
                TextView tvSub = holder.getView(R.id.subItem);
                tvSub.setText("隐患: "+item.getSubItemName());
                TextView tvMakeLow = holder.getView(R.id.makeLow);
                tvMakeLow.setText("建议立案");
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ZDialogUtils.showDialog(LowAddActivity.this, "您确定要将此企业立案调查吗？", new CallBack0() {
                    @Override
                    public void callBack() {
                        CheckItemRecord itemRecord = dangerList.get(position);
                        LowCaseRecord record = new LowCaseRecord();
                        record.setCompanyCode(itemRecord.getCompanyCode());
                        record.setCompanyName(itemRecord.getCompanyName());
                        record.setSuperItemId(itemRecord.getSuperItemId());
                        record.setSuperItemName(ZUtils.getSuperItem(itemRecord.getSuperItemId()));
                        record.setSubItemId(itemRecord.getSubItemId());
                        record.setSubItemName(itemRecord.getSubItemName());
                        record.setCaseStep(0);
                        record.setExcutePerson(ZUtils.getExcutePerson());
                        record.setExcuteTime(DateUtil.getCurrentDataYMDHMS());
                        record.save();
                        Intent intent = new Intent(LowAddActivity.this, LowCaseFlowActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("recordEntity", record);
                        intent.putExtra("recordBundle", bundle);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }


}
