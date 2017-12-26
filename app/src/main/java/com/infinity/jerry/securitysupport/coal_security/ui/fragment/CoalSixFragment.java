package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.entity.CompanySixSys;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CoalSixFragment extends Fragment {

    private View view;


    private TextView tvMonitor;
    private TextView tvMonitorFinish;
    private TextView tvWindSaveSys;
    private TextView tvWindSaveSysFinish;
    private TextView tvWaterSaveSys;
    private TextView tvWaterSaveSysFinish;
    private TextView tvNetSys;
    private TextView tvNetSysFinish;
    private TextView tvUnderSaveSys;
    private TextView tvUnderSaveSysFinish;
    private TextView tvUnderLocationSys;
    private TextView tvUnderLocationSysFinish;

    private int companyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_baseinfo_sixsys, null);

        tvMonitor = (TextView) view.findViewById(R.id.tvMonitor);
        tvMonitorFinish = (TextView) view.findViewById(R.id.tvMonitorFinish);
        tvWindSaveSys = (TextView) view.findViewById(R.id.tvWindSaveSys);
        tvWindSaveSysFinish = (TextView) view.findViewById(R.id.tvWindSaveSysFinish);
        tvWaterSaveSys = (TextView) view.findViewById(R.id.tvWaterSaveSys);
        tvWaterSaveSysFinish = (TextView) view.findViewById(R.id.tvWaterSaveSysFinish);
        tvNetSys = (TextView) view.findViewById(R.id.tvNetSys);
        tvNetSysFinish = (TextView) view.findViewById(R.id.tvNetSysFinish);
        tvUnderSaveSys = (TextView) view.findViewById(R.id.tvUnderSaveSys);
        tvUnderSaveSysFinish = (TextView) view.findViewById(R.id.tvUnderSaveSysFinish);
        tvUnderLocationSys = (TextView) view.findViewById(R.id.tvUnderLocationSys);
        tvUnderLocationSysFinish = (TextView) view.findViewById(R.id.tvUnderLocationSysFinish);

        initData();
        return view;

    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        CompanySixSys info = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).findLast(CompanySixSys.class);
        tvMonitor.setText(info.getMonitor());
        tvMonitorFinish.setText(info.getMonitorFinish());
        tvWindSaveSys.setText(info.getWindSave());
        tvWindSaveSysFinish.setText(info.getWindSaveFinish());
        tvWaterSaveSys.setText(info.getWaterSave());
        tvWaterSaveSysFinish.setText(info.getWaterSaveFinish());
        tvNetSys.setText(info.getNetSys());
        tvNetSysFinish.setText(info.getNetSysFinish());
        tvUnderSaveSys.setText(info.getUnderSave());
        tvUnderSaveSysFinish.setText(info.getUnderSaveFinish());
        tvUnderLocationSys.setText(info.getUnderLocation());
        tvUnderLocationSysFinish.setText(info.getUnderLocationFinish());
    }
}
