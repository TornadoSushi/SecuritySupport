package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.entity.CompanyShaft;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CoalShaftFragment extends Fragment {

    private View view;

    private TextView tvShaftArea;
    private TextView tvShaftSize;
    private TextView tvCanShaft;
    private TextView tvShaftCount;
    private TextView tvShaftHeight;
    private TextView tvShaftLic;
    private TextView tvShaftDeepA;
    private TextView tvShaftDeepB;

    private int companyCode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_baseinfo_shaft, null);

        tvShaftArea = (TextView) view.findViewById(R.id.tvShaftArea);
        tvShaftSize = (TextView) view.findViewById(R.id.tvShaftSize);
        tvCanShaft = (TextView) view.findViewById(R.id.tvCanShaft);
        tvShaftCount = (TextView) view.findViewById(R.id.tvShaftCount);
        tvShaftHeight = (TextView) view.findViewById(R.id.tvShaftHeight);
        tvShaftLic = (TextView) view.findViewById(R.id.tvShaftLic);
        tvShaftDeepA = (TextView) view.findViewById(R.id.tvShaftDeepA);
        tvShaftDeepB = (TextView) view.findViewById(R.id.tvShaftDeepB);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");

        CompanyShaft info = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).findLast(CompanyShaft.class);
        tvShaftArea.setText(info.getShaftArea());
        tvShaftSize.setText(info.getShaftSize());
        tvCanShaft.setText(info.getShaftCan());
        tvShaftCount.setText(info.getShaftCount());
        tvShaftHeight.setText(info.getShaftHeight());
        tvShaftLic.setText(info.getShaftLic());
        tvShaftDeepA.setText(info.getShaftDeepA());
        tvShaftDeepB.setText(info.getShaftDeepB());

    }
}
