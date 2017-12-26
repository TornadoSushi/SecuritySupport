package com.infinity.jerry.securitysupport.coal_security.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.MainLeftFragment;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.MainRightFragment;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.test.TestActivity;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewPagerAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.imLeft)
    ImageView imLeft;
    @BindView(R.id.imRight)
    ImageView imRight;

    private MainLeftFragment leftFragment;
    private MainRightFragment rightFragment;

    private List<Fragment> fragList;

    private FragmentManager manager = getSupportFragmentManager();

    @Override
    public int getLayoutId() {
        return R.layout.activity_coal_main;
    }

    @Override
    public void initData() {
        leftFragment = new MainLeftFragment();
        rightFragment = new MainRightFragment();
        fragList = new ArrayList<>();
        fragList.add(leftFragment);
        fragList.add(rightFragment);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTitle(getString(R.string.app_title));
        titleBar.noBack();
        titleBar.setTvPlusText("同步");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        viewPager.setAdapter(new ZViewPagerAdapter(manager, fragList));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
