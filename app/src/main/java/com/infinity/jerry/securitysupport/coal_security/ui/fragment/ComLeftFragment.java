package com.infinity.jerry.securitysupport.coal_security.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntity;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jerry on 2017/11/13.
 */

public class ComLeftFragment extends Fragment {
    @BindView(R.id.tvComName)
    TextView tvComName;
    @BindView(R.id.tvComBusiLic)
    TextView tvComBusiLic;
    @BindView(R.id.tvCoalLicCode)
    TextView tvCoalLicCode;
    @BindView(R.id.tvMinLicCode)
    TextView tvMinLicCode;
    @BindView(R.id.tvSaftyLicCode)
    TextView tvSaftyLicCode;
    @BindView(R.id.tvComArea)
    TextView tvComArea;
    @BindView(R.id.tvComAddDetail)
    TextView tvComAddDetail;
    @BindView(R.id.tvMonitor)
    TextView tvMonitor;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.tvLegalPerson)
    TextView tvLegalPerson;
    @BindView(R.id.tvComState)
    TextView tvComState;
    @BindView(R.id.tvComUrl)
    TextView tvComUrl;
    @BindView(R.id.tvDirector)
    TextView tvDirector;
    @BindView(R.id.tvDirectorPhone)
    TextView tvDirectorPhone;
    @BindView(R.id.tvComPhone)
    TextView tvComPhone;
    Unbinder unbinder;
    private View view;

    private int companyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_detail, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        CompanyCoalEntity companyCoalEntity = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).findLast(CompanyCoalEntity.class);
        CompanyBaseInfo companyBaseInfos = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);
        tvComName.setText(companyCoalEntity.getCompanyName());
        tvComBusiLic.setText(companyBaseInfos.getBusiLicCode());
        tvCoalLicCode.setText(companyBaseInfos.getCoalLicence());
        tvMinLicCode.setText(companyBaseInfos.getMiningLicCode());
        tvSaftyLicCode.setText(companyBaseInfos.getSaftyLicCode());
        tvComArea.setText(companyCoalEntity.getCompanyArea());
        tvComAddDetail.setText("");
        tvMonitor.setText(companyCoalEntity.getCompanyLocation());
        tvClass.setText("煤炭企业");
        tvLegalPerson.setText(companyCoalEntity.getLeaglPerson());
        tvComState.setText(companyCoalEntity.getCompanyState());
        tvComUrl.setText(companyCoalEntity.getCoalUrl());
        tvDirector.setText(companyCoalEntity.getDirector());
        tvDirectorPhone.setText(companyCoalEntity.getCoalPhone());
        tvComPhone.setText(companyCoalEntity.getCoalCell());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
