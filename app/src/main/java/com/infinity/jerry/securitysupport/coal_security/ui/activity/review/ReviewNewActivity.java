package com.infinity.jerry.securitysupport.coal_security.ui.activity.review;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.InfoFragment;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.ReviewFragment;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jerry on 2017/11/14.
 */

public class ReviewNewActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_check)
    TextView tvCheck;

    private FragmentManager fragmentManager;

    private InfoFragment infoFragment;
    private ReviewFragment reviewFragment;

    private boolean isAddThisFragment = false;

    private int companyCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_review_coal;
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        companyCode = getIntent().getIntExtra("companyCode", -256);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_NONE());
        titleBar.setTitle("复查监管");
        addFirstFragment();
    }

    private void addFirstFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("companyCode", companyCode);
        infoFragment.setArguments(bundle);
        transaction.add(R.id.container, infoFragment);
        transaction.commit();
        tvInfo.setTextColor(getResources().getColor(R.color.color_main));
    }

    private void changeFragment(Fragment fragment, TextView tvCurrent) {
        setTvZero();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isAddThisFragment) {
            transaction.add(R.id.container, fragment);
            isAddThisFragment = false;
        }
        hideAllFragment();
        transaction.show(fragment);
        transaction.commit();
        tvCurrent.setTextColor(getResources().getColor(R.color.color_main));
    }

    private void hideAllFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (infoFragment != null) {
            transaction.hide(infoFragment);
        }
        if (reviewFragment != null) {
            transaction.hide(reviewFragment);
        }


        transaction.commit();
    }

    @OnClick({R.id.tv_info, R.id.tv_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_info:
                if (infoFragment == null) {
                    infoFragment = new InfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("companyCode", companyCode);
//                    bundle.putInt("planId", planId);
                    infoFragment.setArguments(bundle);
                    isAddThisFragment = true;
                }
                changeFragment(infoFragment, tvInfo);
                break;
            case R.id.tv_check:
                if (reviewFragment == null) {
                    reviewFragment = new ReviewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("companyCode", companyCode);
                    reviewFragment.setArguments(bundle);
                    isAddThisFragment = true;
                }
                changeFragment(reviewFragment, tvCheck);
                break;

        }
    }

    private void setTvZero() {
        tvInfo.setTextColor(getResources().getColor(R.color.color_white));
        tvCheck.setTextColor(getResources().getColor(R.color.color_white));
    }
}
