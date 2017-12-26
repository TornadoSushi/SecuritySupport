package com.infinity.jerry.securitysupport.coal_security.ui.activity.check;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CoalCheckItem;
import com.infinity.jerry.securitysupport.common.entity.PlanCheckedItem;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZMesuredListView;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/16.
 */

public class CheckSubItemActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ZMesuredListView listView;
    @BindView(R.id.tvFinish)
    TextView tvFinish;

    private int superItemId;
    private int planId;

    private List<CoalCheckItem> subItemList;
    private List<PlanCheckedItem> checkedItems;

    private ZCommonAdapter<CoalCheckItem> adapter;

    private final int REQUEST_FOR_CHECK_DETAIL = 1;
    private int originalSize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_check;
    }

    @Override
    public void initData() {
        planId = getIntent().getIntExtra("planId", -256);
        superItemId = getIntent().getIntExtra("superItemId", -256);
        subItemList = DataSupport.where("parent = ?", String.valueOf(superItemId)).find(CoalCheckItem.class);
        originalSize = subItemList.size();
        checkedItems = DataSupport.where("planId = ? AND superId = ? AND type = 1", String.valueOf(planId), String.valueOf(superItemId)).find(PlanCheckedItem.class);
        if (!checkedItems.isEmpty()) {
            for (CoalCheckItem subItems : subItemList) {
                for (PlanCheckedItem checkedItem : checkedItems) {
                    if (subItems.getId() == checkedItem.getSubId()) {
                        subItems.setC15("已完成");
                    }
                }
            }
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("检查小项");
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(CheckSubItemActivity.this, "您确定要执行无隐患方式完成所有检查吗", new CallBack0() {
                    @Override
                    public void callBack() {
                        Intent intent = new Intent();
                        intent.putExtra("superItemId", superItemId);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });

        adapter = new ZCommonAdapter<CoalCheckItem>(this, subItemList, R.layout.item_single_text) {
            @Override
            public void convert(ZViewHolder holder, CoalCheckItem item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getCheckinItem());
                TextView tvPlus = holder.getView(R.id.tvPlus);
                tvPlus.setVisibility(View.VISIBLE);
                tvPlus.setText(item.getC15() == null || item.getC15().equals("") ? getString(R.string.not_finish) : item.getC15());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//开始检查
                if (!subItemList.get(position).getC15().equals("已完成")) {
                    Intent intent = new Intent(CheckSubItemActivity.this, CheckDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subItem", subItemList.get(position));
                    intent.putExtra("subBundle", bundle);
                    intent.putExtra("planId", planId);
                    startActivityForResult(intent, REQUEST_FOR_CHECK_DETAIL);
                } else {
                    Toasty.info(CheckSubItemActivity.this, "您已经完成了这项检查").show();
                }

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ZDialogUtils.showDialog(CheckSubItemActivity.this, "您确定要以无隐患方式完成此项检查吗?", new CallBack0() {
                    @Override
                    public void callBack() {
                        subItemList.get(position).setC15("已完成");
                        PlanCheckedItem item = new PlanCheckedItem();
                        item.setPlanId(planId);
                        item.setSuperId(superItemId);
                        item.setSubId(subItemList.get(position).getId());
                        item.setType(1);
                        item.save();
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FOR_CHECK_DETAIL && resultCode == RESULT_OK) {
            int finish_id = data.getIntExtra("finish_id", -256);
            for (CoalCheckItem coalCheckItem : subItemList) {
                if (coalCheckItem.getId() == finish_id) {
                    coalCheckItem.setC15("已完成");
                    PlanCheckedItem item = new PlanCheckedItem();
                    item.setPlanId(planId);
                    item.setSuperId(superItemId);
                    item.setSubId(coalCheckItem.getId());
                    item.setType(1);
                    item.save();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        int i = 0;
        for (CoalCheckItem item : subItemList) {
            if (item.getC15() != null) {
                i++;
            }
        }
        if (i == originalSize) {
            Intent intent = new Intent();
            intent.putExtra("finish_id", superItemId);
            setResult(RESULT_OK, intent);
            this.finish();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
