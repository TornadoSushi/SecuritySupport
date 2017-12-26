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
 * Created by jerry on 2017/11/13.
 */

public class ComRightFragment extends Fragment {

    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.tv_safty)
    TextView tvSafty;
    @BindView(R.id.tv_door)
    TextView tvDoor;
    @BindView(R.id.tv_shaft)
    TextView tvShaft;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    private View view;

    private List<Fragment> fragmentList;

    private CoalProFragment coalProFragment;
    private CoalSaftyFragment coalSaftyFragment;
    private CoalDoorFragment coalDoorFragment;
    private CoalShaftFragment coalShaftFragment;
    private CoalSixFragment coalSixFragment;
    private int companyCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_right_detail, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("companyCode", companyCode);
        coalProFragment = new CoalProFragment();
        coalProFragment.setArguments(bundle);
        coalSaftyFragment = new CoalSaftyFragment();
        coalSaftyFragment.setArguments(bundle);
        coalDoorFragment = new CoalDoorFragment();
        coalDoorFragment.setArguments(bundle);
        coalShaftFragment = new CoalShaftFragment();
        coalShaftFragment.setArguments(bundle);
        coalSixFragment = new CoalSixFragment();
        coalSixFragment.setArguments(bundle);

        fragmentList.add(coalProFragment);
        fragmentList.add(coalSaftyFragment);
        fragmentList.add(coalDoorFragment);
        fragmentList.add(coalShaftFragment);
        fragmentList.add(coalSixFragment);

        tvPro.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_convert));

        viewPager.setAdapter(new ZViewPagerAdapter(getChildFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTvZero();
                switch (position) {
                    case 0:
                        tvPro.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_convert));
                        break;
                    case 1:
                        tvSafty.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_convert));
                        break;
                    case 2:
                        tvDoor.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_convert));
                        break;
                    case 3:
                        tvShaft.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_convert));
                        break;
                    case 4:
                        tvSix.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_convert));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTvZero() {
        tvPro.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        tvSafty.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        tvDoor.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        tvShaft.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
        tvSix.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_pro, R.id.tv_safty, R.id.tv_door, R.id.tv_shaft, R.id.tv_six})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pro:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_safty:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_door:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_shaft:
                viewPager.setCurrentItem(3);
                break;
            case R.id.tv_six:
                viewPager.setCurrentItem(4);
                break;
        }
    }
}
