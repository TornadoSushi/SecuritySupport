package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.entity.CompanyProduct;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CoalProFragment extends Fragment {

    private View view;

    private TextView tvUp;
    private TextView tvTrans;
    private TextView tvGetCoalTech;
    private TextView tvGetCoalBack;
    private TextView tvCoalTime;
    private TextView tvUpTransSystem;
    private TextView tvUpTransMethod;
    private TextView tvTransEquNum;
    private TextView tvPowerSystem;
    private TextView tvPowerMethod;
    private TextView tvTransformerPower;
    private TextView tvMainLoop;
    private TextView tvMainLoopType;
    private TextView tvBackupLoop;
    private TextView tvBackupLoopType;
    private TextView tvDownPowerSup;
    private TextView tvIntoPower;
    private TextView tvIntoMainPower;

    private int companyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_baseinfo_produce, null);
        tvUp = (TextView) view.findViewById(R.id.tvUp);
        tvTrans = (TextView) view.findViewById(R.id.tvTrans);
        tvGetCoalTech = (TextView) view.findViewById(R.id.tvGetCoalTech);
        tvGetCoalBack = (TextView) view.findViewById(R.id.tvGetCoalBack);
        tvCoalTime = (TextView) view.findViewById(R.id.tvCoalTime);
        tvUpTransSystem = (TextView) view.findViewById(R.id.tvUpTransSystem);
        tvUpTransMethod = (TextView) view.findViewById(R.id.tvUpTransMethod);
        tvTransEquNum = (TextView) view.findViewById(R.id.tvTransEquNum);
        tvPowerSystem = (TextView) view.findViewById(R.id.tvPowerSystem);
        tvPowerMethod = (TextView) view.findViewById(R.id.tvPowerMethod);
        tvTransformerPower = (TextView) view.findViewById(R.id.tvTransformerPower);
        tvMainLoop = (TextView) view.findViewById(R.id.tvMainLoop);
        tvMainLoopType = (TextView) view.findViewById(R.id.tvMainLoopType);
        tvBackupLoop = (TextView) view.findViewById(R.id.tvBackupLoop);
        tvBackupLoopType = (TextView) view.findViewById(R.id.tvBackupLoopType);
        tvDownPowerSup = (TextView) view.findViewById(R.id.tvDownPowerSup);
        tvIntoPower = (TextView) view.findViewById(R.id.tvIntoPower);
        tvIntoMainPower = (TextView) view.findViewById(R.id.tvIntoMainPower);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        CompanyProduct info = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).findLast(CompanyProduct.class);
        tvUp.setText(info.getCoalUp());
        tvTrans.setText(info.getUpTransMethod());
        tvGetCoalTech.setText(info.getCoalTech());
        tvGetCoalBack.setText(info.getCoalBackRate());
        tvCoalTime.setText(info.getCoalStartTime());
        tvUpTransSystem.setText(info.getUpTransSys());
        tvUpTransMethod.setText(info.getUpTransMethod());
        tvTransEquNum.setText(info.getTransEquipNum());
        tvPowerSystem.setText(info.getPowerSys());
        tvPowerMethod.setText(info.getDownPowerSupply());
        tvTransformerPower.setText(info.getTransformerPower());
        tvMainLoop.setText(info.getMainLoop());
        tvMainLoopType.setText(info.getMainLoopType());
        tvBackupLoop.setText(info.getBackupLoop());
        tvBackupLoopType.setText(info.getBackupLoopType());
        tvDownPowerSup.setText(info.getDownPowerSupply());
        tvIntoPower.setText(info.getIntoPower());
        tvIntoMainPower.setText(info.getIntoMainPower());
    }
}
