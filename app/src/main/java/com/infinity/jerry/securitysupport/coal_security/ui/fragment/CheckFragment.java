package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.check.CheckSubItemActivity;
import com.infinity.jerry.securitysupport.common.entity.CoalCheckItem;
import com.infinity.jerry.securitysupport.common.entity.PlanCheckedItem;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZMesuredListView;

import org.litepal.crud.DataSupport;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/14.
 */

public class CheckFragment extends Fragment {

    private View view;
    private ZMesuredListView listView;

    private TextView tvFinish;

    private List<CoalCheckItem> checkItemList;

    private ZCommonAdapter<CoalCheckItem> adapter;

    private int companyCode;
    private String companyName;
    private int planId;

    private List<PlanCheckedItem> superList;

    private final int REQUEST_ALL_SUB = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check, null);
        listView = view.findViewById(R.id.listView);
        tvFinish = view.findViewById(R.id.tvFinish);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        companyName = getArguments().getString("companyName");
        planId = getArguments().getInt("planId");

        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(CheckFragment.this.getActivity(), "您确定要执行无隐患方式完成所有检查吗", new CallBack0() {
                    @Override
                    public void callBack() {
                        PlanCheckedItem item = new PlanCheckedItem();
                        item.setPlanId(planId);
                        item.setSuperId(0);
                        item.setIsAllFinish(256);
                        item.save();
                        for (CoalCheckItem something : checkItemList) {
                            something.setC15("已完成");
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        checkItemList = DataSupport.where("parent IS NULL").find(CoalCheckItem.class);
        superList = DataSupport.where("planId = ? AND isAllFinish = 256", String.valueOf(planId)).find(PlanCheckedItem.class);
        if (superList.isEmpty()) {
            superList = DataSupport.where("planId = ? AND type = 0", String.valueOf(planId)).find(PlanCheckedItem.class);
            if (!superList.isEmpty()) {
                for (CoalCheckItem superItem : checkItemList) {
                    for (PlanCheckedItem finishItem : superList) {
                        if (superItem.getId() == finishItem.getSuperId()) {
                            superItem.setC15("已完成");
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        } else {
            for (CoalCheckItem item : checkItemList) {
                item.setC15("已完成");
            }
            adapter.notifyDataSetChanged();
        }

        adapter = new ZCommonAdapter<CoalCheckItem>(getActivity(), checkItemList, R.layout.item_single_text) {
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!checkItemList.get(position).getC15().isEmpty()) {
                    Toasty.info(CheckFragment.this.getActivity(), "您已经完成该项检查").show();
                    return;
                }
                Intent intent = new Intent(CheckFragment.this.getActivity(), CheckSubItemActivity.class);
                intent.putExtra("planId", planId);
                intent.putExtra("superItemId", checkItemList.get(position).getId());
                startActivityForResult(intent, REQUEST_ALL_SUB);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ZDialogUtils.showDialog(CheckFragment.this.getActivity(), "您确定要以无隐患方式完成此项检查吗?", new CallBack0() {
                    @Override
                    public void callBack() {
                        checkItemList.get(position).setC15("已完成");
                        PlanCheckedItem item = new PlanCheckedItem();
                        item.setPlanId(planId);
                        item.setSuperId(checkItemList.get(position).getId());
                        item.setType(0);
                        item.save();
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ALL_SUB && resultCode == getActivity().RESULT_OK) {
            int superItemId = data.getIntExtra("superItemId", -256);
            if (superItemId != -256) {
                for (CoalCheckItem item : checkItemList) {
                    if (item.getId() == superItemId) {
                        item.setC15("已完成");
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            int finishId = data.getIntExtra("finish_id", -256);
            if (finishId != -256) {
                for (CoalCheckItem item : checkItemList) {
                    if (item.getId() == finishId) {
                        item.setC15("已完成");
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
