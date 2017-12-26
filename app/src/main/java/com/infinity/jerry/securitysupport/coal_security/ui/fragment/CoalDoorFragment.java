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
import com.infinity.jerry.securitysupport.common.entity.CompanyDoor;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by jerry on 2017/11/14.
 */

public class CoalDoorFragment extends Fragment {

    private View view;

    private ListView listView;

    private int companyCode;

    private ZCommonAdapter<CompanyDoor> adapter;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_baseinfo_coaldoor, null);
        listView = view.findViewById(R.id.listView);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");

        List<CompanyDoor> companyDoors = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).find(CompanyDoor.class);

        adapter = new ZCommonAdapter<CompanyDoor>(getActivity(), companyDoors, R.layout.item_baseinfo_door) {

            @Override
            public void convert(ZViewHolder holder, CompanyDoor item, int position) {
                TextView doorName = holder.getView(R.id.tvCoalDoorName);
                doorName.setText(item.getDoorName());
                TextView tvX = holder.getView(R.id.tvDoorX);
                tvX.setText(item.getAxisX());
                TextView tvY = holder.getView(R.id.tvDoorY);
                tvY.setText(item.getAxisY());
                TextView tvZ = holder.getView(R.id.tvDoorZ);
                tvZ.setText(item.getAxisZ());
            }

        };
        listView.setAdapter(adapter);

    }
}
