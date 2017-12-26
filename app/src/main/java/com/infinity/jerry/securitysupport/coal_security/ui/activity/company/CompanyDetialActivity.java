package com.infinity.jerry.securitysupport.coal_security.ui.activity.company;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.ComLeftFragment;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.ComRightFragment;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewPagerAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jerry on 2017/11/13.
 */

public class CompanyDetialActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.tv_baseInfo)
    TextView tvBaseInfo;
    @BindView(R.id.tvPropInfo)
    TextView tvPropInfo;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ComLeftFragment leftFragment;
    private ComRightFragment rightFragment;

    private List<Fragment> fragmentList;

    private int companyCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_details;
    }

    @Override
    public void initData() {
        companyCode = getIntent().getIntExtra("companyCode", -256);
        Bundle bundle = new Bundle();
        bundle.putInt("companyCode", companyCode);
        leftFragment = new ComLeftFragment();
        leftFragment.setArguments(bundle);
        rightFragment = new ComRightFragment();
        rightFragment.setArguments(bundle);
        fragmentList = new ArrayList<>();
        fragmentList.add(leftFragment);
        fragmentList.add(rightFragment);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("煤炭企业详情");
        viewPager.setAdapter(new ZViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTvZero();
                switch (position) {
                    case 0:
                        tvBaseInfo.setTextColor(ContextCompat.getColor(CompanyDetialActivity.this, R.color.color_main));
                        break;
                    case 1:
                        tvPropInfo.setTextColor(ContextCompat.getColor(CompanyDetialActivity.this, R.color.color_main));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTvZero() {
        tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        tvPropInfo.setTextColor(ContextCompat.getColor(this, R.color.color_white));
    }


    @OnClick({R.id.tv_baseInfo, R.id.tvPropInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_baseInfo:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvPropInfo:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
