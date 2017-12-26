package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jerry on 2017/12/12.
 */

public class CheckHistoryFragment extends Fragment {

    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    private View view;

    private List<PlanRecord> recordList;
    private ZCommonAdapter<PlanRecord> adapter;

    private int companyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check_history, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
    }

    private void initView() {
        recordList = DataSupport.where("companyCode = ? AND isFinish = 1", String.valueOf(companyCode)).order("id DESC").find(PlanRecord.class);
        adapter = new ZCommonAdapter<PlanRecord>(getActivity(), recordList, R.layout.item_syn_check) {
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
                TextView tvSynBtn = holder.getView(R.id.tvSynBtn);
                tvSynBtn.setVisibility(View.GONE);
            }
        };
        listView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
