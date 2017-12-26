package com.infinity.jerry.securitysupport.common.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/14.
 */

public class TestActivity extends Activity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.checkId)
    TextView checkId;
    @BindView(R.id.baseinfo)
    TextView baseinfo;
    @BindView(R.id.produce)
    TextView produce;
    @BindView(R.id.safty)
    TextView safty;
    @BindView(R.id.door)
    TextView door;
    @BindView(R.id.shaft)
    TextView shaft;
    @BindView(R.id.six)
    TextView six;
    @BindView(R.id.checkItem)
    TextView checkitem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        ButterKnife.bind(this);
        titleBar.setTitle("同步数据");
    }
    @OnClick({R.id.userInfo,R.id.company, R.id.checkId, R.id.baseinfo, R.id.produce, R.id.safty, R.id.door, R.id.shaft, R.id.six, R.id.checkItem})
    public void onViewClicked(View view) {
        if (false) {
            Toasty.info(this, "已经关闭同步数据接口").show();
            return;
        }
        switch (view.getId()) {
            case R.id.userInfo:
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.checkItem:
                Toasty.info(this, "已经同步").show();
                PresenterModel.synCheckItem();
                break;
            case R.id.company:
                Toasty.info(this, "已经同步").show();
                PresenterModel.synCompany();
                break;
            case R.id.baseinfo:
                PresenterModel.synBaseIfo();
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.produce:
                PresenterModel.synProInfo();
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.safty:
                PresenterModel.synSafty();
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.door:
                PresenterModel.synDoor();
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.shaft:
                PresenterModel.synShaft();
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.six:
                PresenterModel.synSix();
                Toasty.info(this, "已经同步").show();
                break;
            case R.id.checkId:
                break;
        }
    }

}
