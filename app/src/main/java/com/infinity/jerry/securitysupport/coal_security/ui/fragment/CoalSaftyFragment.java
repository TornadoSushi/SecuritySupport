package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.entity.CompanySafty;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CoalSaftyFragment extends Fragment {

    private View view;

    private TextView tvGasPump;
    private TextView tvGasMoniter;
    private TextView tvGasMoniterIsNet;
    private TextView tvHasManLocation;
    private TextView tvMainFanNum;
    private TextView tvMainFanElecNum;
    private TextView tvMainFanRatePower;
    private TextView tvSubFanNum;
    private TextView tvSubfanElecNum;
    private TextView tvSubFanRatePower;
    private TextView tvRatedBlast;
    private TextView tvRatedPressure;
    private TextView tvDOWMethod;
    private TextView tvDOWNum;
    private TextView tvDOWCount;
    private TextView tvDOWPower;
    private TextView tvMaxDOW;
    private TextView tvDOWRadius;
    private TextView tvHasGasPump;
    private TextView tvGasPumpMethod;
    private TextView tvPumpNum;
    private TextView tvPumpSize;
    private TextView tvHasTube;
    private TextView tvTubeNum;
    private TextView tvHasN2;
    private TextView tvN2Type;
    private TextView tvN2Num;
    private TextView tvHasInjection;
    private TextView tvInjectionType;
    private TextView tvInjectionNum;
    private TextView tvInjectForday;
    private TextView tvOtherFireSYS;
    private TextView tvHasMoniter;
    private TextView tvMonitorNum;
    private TextView tvMonitorCount;
    private TextView tvGasSensorCount;
    private TextView tvCOSensorCount;
    private TextView tvWindSensorCount;
    private TextView tvTempSensorCount;
    private TextView tvOnOffSensorCount;
    private TextView tvOtherSensorCount;
    private TextView tvSSProducedPlace;
    private TextView tvSSProducedTime;
    private TextView tvSSInstallTime;
    private TextView tvUnderElecSYS;
    private TextView tvElecMethod;
    private TextView tvTransformerRate;
    private TextView tvMainLoop;
    private TextView tvMainLoopType;
    private TextView tvSubLoop;
    private TextView tvSubLoopType;
    private TextView tvUnderCoalElecSys;
    private TextView tvIntoPressuer;
    private TextView tvIntoMainCable;
    private TextView tvBlowSys;
    private TextView tvBlowSysNum;

    private int companyCode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_baseinfo_safty, null);

        tvGasPump = (TextView) view.findViewById(R.id.tvGasPump);
        tvGasMoniter = (TextView) view.findViewById(R.id.tvGasMoniter);
        tvGasMoniterIsNet = (TextView) view.findViewById(R.id.tvGasMoniterIsNet);
        tvHasManLocation = (TextView) view.findViewById(R.id.tvHasManLocation);
        tvMainFanNum = (TextView) view.findViewById(R.id.tvMainFanNum);
        tvMainFanElecNum = (TextView) view.findViewById(R.id.tvMainFanElecNum);
        tvMainFanRatePower = (TextView) view.findViewById(R.id.tvMainFanRatePower);
        tvSubFanNum = (TextView) view.findViewById(R.id.tvSubFanNum);
        tvSubfanElecNum = (TextView) view.findViewById(R.id.tvSubfanElecNum);
        tvSubFanRatePower = (TextView) view.findViewById(R.id.tvSubFanRatePower);
        tvRatedBlast = (TextView) view.findViewById(R.id.tvRatedBlast);
        tvRatedPressure = (TextView) view.findViewById(R.id.tvRatedPressure);
        tvDOWMethod = (TextView) view.findViewById(R.id.tvDOWMethod);
        tvDOWNum = (TextView) view.findViewById(R.id.tvDOWNum);
        tvDOWCount = (TextView) view.findViewById(R.id.tvDOWCount);
        tvDOWPower = (TextView) view.findViewById(R.id.tvDOWPower);
        tvMaxDOW = (TextView) view.findViewById(R.id.tvMaxDOW);
        tvDOWRadius = (TextView) view.findViewById(R.id.tvDOWRadius);
        tvHasGasPump = (TextView) view.findViewById(R.id.tvHasGasPump);
        tvGasPumpMethod = (TextView) view.findViewById(R.id.tvGasPumpMethod);
        tvPumpNum = (TextView) view.findViewById(R.id.tvPumpNum);
        tvPumpSize = (TextView) view.findViewById(R.id.tvPumpSize);
        tvHasTube = (TextView) view.findViewById(R.id.tvHasTube);
        tvTubeNum = (TextView) view.findViewById(R.id.tvTubeNum);
        tvHasN2 = (TextView) view.findViewById(R.id.tvHasN2);
        tvN2Type = (TextView) view.findViewById(R.id.tvN2Type);
        tvN2Num = (TextView) view.findViewById(R.id.tvN2Num);
        tvHasInjection = (TextView) view.findViewById(R.id.tvHasInjection);
        tvInjectionType = (TextView) view.findViewById(R.id.tvInjectionType);
        tvInjectionNum = (TextView) view.findViewById(R.id.tvInjectionNum);
        tvInjectForday = (TextView) view.findViewById(R.id.tvInjectForday);
        tvOtherFireSYS = (TextView) view.findViewById(R.id.tvOtherFireSYS);
        tvHasMoniter = (TextView) view.findViewById(R.id.tvHasMoniter);
        tvMonitorNum = (TextView) view.findViewById(R.id.tvMonitorNum);
        tvMonitorCount = (TextView) view.findViewById(R.id.tvMonitorCount);
        tvGasSensorCount = (TextView) view.findViewById(R.id.tvGasSensorCount);
        tvCOSensorCount = (TextView) view.findViewById(R.id.tvCOSensorCount);
        tvWindSensorCount = (TextView) view.findViewById(R.id.tvWindSensorCount);
        tvTempSensorCount = (TextView) view.findViewById(R.id.tvTempSensorCount);
        tvOnOffSensorCount = (TextView) view.findViewById(R.id.tvOnOffSensorCount);
        tvOtherSensorCount = (TextView) view.findViewById(R.id.tvOtherSensorCount);
        tvSSProducedPlace = (TextView) view.findViewById(R.id.tvSSProducedPlace);
        tvSSProducedTime = (TextView) view.findViewById(R.id.tvSSProducedTime);
        tvSSInstallTime = (TextView) view.findViewById(R.id.tvSSInstallTime);
        tvUnderElecSYS = (TextView) view.findViewById(R.id.tvUnderElecSYS);
        tvElecMethod = (TextView) view.findViewById(R.id.tvElecMethod);
        tvTransformerRate = (TextView) view.findViewById(R.id.tvTransformerRate);
        tvMainLoop = (TextView) view.findViewById(R.id.tvMainLoop);
        tvMainLoopType = (TextView) view.findViewById(R.id.tvMainLoopType);
        tvSubLoop = (TextView) view.findViewById(R.id.tvSubLoop);
        tvSubLoopType = (TextView) view.findViewById(R.id.tvSubLoopType);
        tvUnderCoalElecSys = (TextView) view.findViewById(R.id.tvUnderCoalElecSys);
        tvIntoPressuer = (TextView) view.findViewById(R.id.tvIntoPressuer);
        tvIntoMainCable = (TextView) view.findViewById(R.id.tvIntoMainCable);
        tvBlowSys = (TextView) view.findViewById(R.id.tvBlowSys);
        tvBlowSysNum = (TextView) view.findViewById(R.id.tvBlowSysNum);
        initData();
        return view;
    }

    private void initData() {

        companyCode = getArguments().getInt("companyCode");
        CompanySafty info = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).findLast(CompanySafty.class);

        tvGasPump.setText(info.getGasPump());
        tvGasMoniter.setText(info.getGasMonitor());
        tvGasMoniterIsNet.setText(info.getGasIsNet());
        tvHasManLocation.setText(info.getManLocation());
        tvMainFanNum.setText(info.getMainFanNum());
        tvMainFanElecNum.setText(info.getMainFanElcNum());
        tvMainFanRatePower.setText(info.getMainFanRatePower());
        tvSubFanNum.setText(info.getSubFanNum());
        tvSubfanElecNum.setText(info.getSubFanElcNum());
        tvSubFanRatePower.setText(info.getSubFanRatePower());
        tvRatedBlast.setText(info.getRatedBlast());
        tvRatedPressure.setText(info.getRatedPressure());
        tvDOWMethod.setText(info.getDowMethod());
        tvDOWNum.setText(info.getDowNum());
        tvDOWCount.setText(info.getDowCount());
        tvDOWPower.setText(info.getDowPower());
        tvMaxDOW.setText(info.getDowMax());
        tvDOWRadius.setText(info.getDowRadius());
        tvHasGasPump.setText(info.getHasGasPump());
        tvGasPumpMethod.setText(info.getGasPumpMethod());
        tvPumpNum.setText(info.getPumpNum());
        tvPumpSize.setText(info.getPumpSize());
        tvHasTube.setText(info.getHasTube());
        tvTubeNum.setText(info.getTubeNum());
        tvHasN2.setText(info.getHasN2());
        tvN2Type.setText(info.getN2Type());
        tvN2Num.setText(info.getN2Num());
        tvHasInjection.setText(info.getHasInjection());
        tvInjectionType.setText(info.getInjectionType());
        tvInjectionNum.setText(info.getInjectionNum());
        tvInjectForday.setText(info.getInjectionForday());
        tvOtherFireSYS.setText(info.getOtherFireSys());
        tvHasMoniter.setText(info.getHasMonitor());
        tvMonitorNum.setText(info.getMonitorNum());
        tvMonitorCount.setText(info.getMonitorCount());
        tvGasSensorCount.setText(info.getGasSensorCount());
        tvCOSensorCount.setText(info.getCoSensorCount());
        tvWindSensorCount.setText(info.getWindSensorCount());
        tvTempSensorCount.setText(info.getTempSensorCount());
        tvOnOffSensorCount.setText(info.getOnoffSensorCount());
        tvOtherSensorCount.setText(info.getOtherSensorCount());
        tvSSProducedPlace.setText(info.getSsProducePlace());
        tvSSProducedTime.setText(info.getSsProduceTime());
        tvSSInstallTime.setText(info.getSsInstallTime());
        tvUnderElecSYS.setText(info.getUnderElcSys());
        tvElecMethod.setText(info.getUnderElcMethod());
        tvTransformerRate.setText(info.getTransformerPower());
        tvMainLoop.setText(info.getMainLoop());
        tvMainLoopType.setText(info.getMainLoopType());
        tvSubLoop.setText(info.getSubLoop());
        tvSubLoopType.setText(info.getSubLoopType());
        tvUnderCoalElecSys.setText(info.getUndercoalElcSys());
        tvIntoPressuer.setText(info.getIntoMainPressure());
        tvIntoMainCable.setText(info.getIntoMainCable());
        tvBlowSys.setText(info.getWindSys());
        tvBlowSysNum.setText(info.getWindSysType());
    }
}
