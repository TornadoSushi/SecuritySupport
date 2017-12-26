package com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_check;

import android.widget.ListView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import butterknife.BindView;

/**
 * Created by jerry on 2017/12/12.
 */

public class CheckSynHistoryActvitiy extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_syn_check_history;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("检查记录同步历史");
    }


}
