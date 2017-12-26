package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jerry on 2017/11/14.
 */

public class InfoFragment extends Fragment {

    Unbinder unbinder;
    private View view;

    @BindView(R.id.tv_baseInfo)
    TextView tvBaseInfo;
    @BindView(R.id.tvPropInfo)
    TextView tvPropInfo;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ComLeftFragment leftFragment;
    private ComRightFragment rightFragment;

    private int companyCode;

    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_company_details, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode", -256);
        Bundle bundle = new Bundle();
        bundle.putInt("companyCode", companyCode);
        leftFragment = new ComLeftFragment();
        leftFragment.setArguments(bundle);
        rightFragment = new ComRightFragment();
        rightFragment.setArguments(bundle);
        fragmentList = new ArrayList<>();
        fragmentList.add(leftFragment);
        fragmentList.add(rightFragment);
        initView();
    }

    private void initView() {
        viewPager.setAdapter(new ZViewPagerAdapter(getChildFragmentManager(), fragmentList));
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
                        tvBaseInfo.setTextColor(ContextCompat.getColor(InfoFragment.this.getActivity(), R.color.color_main));
                        break;
                    case 1:
                        tvPropInfo.setTextColor(ContextCompat.getColor(InfoFragment.this.getActivity(), R.color.color_main));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setTvZero() {
        tvBaseInfo.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        tvPropInfo.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
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

