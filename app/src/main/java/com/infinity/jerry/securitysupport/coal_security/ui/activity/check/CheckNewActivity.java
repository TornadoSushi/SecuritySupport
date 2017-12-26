package com.infinity.jerry.securitysupport.coal_security.ui.activity.check;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.CheckFragment;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.CheckHistoryFragment;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.DocFragment;
import com.infinity.jerry.securitysupport.coal_security.ui.fragment.InfoFragment;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.entity.PlanToDocEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/14.
 */

public class CheckNewActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.tv_doc)
    TextView tvDoc;
    @BindView(R.id.tv_history)
    TextView tvHistory;

    private FragmentManager fragmentManager;

    private InfoFragment infoFragment;
    private CheckFragment checkFragment;
    private DocFragment docFragment;
    private CheckHistoryFragment historyFragment;

    private boolean isAddThisFragment = false;

    private int companyCode;
    private int planId;
    private String companyName;

    private PlanRecord record;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_coal;
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        planId = getIntent().getIntExtra("planId", -256);
        record = DataSupport.where("id = ?", String.valueOf(planId)).findLast(PlanRecord.class);
        companyCode = record.getCompanyCode();
        companyName = record.getCompanyName();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.setTitle("现场检查");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {//完成检查 检查现场文书情况
                ZDialogUtils.showDialog(CheckNewActivity.this, "您确定完成该次检查吗?", new CallBack0() {
                    @Override
                    public void callBack() {
                        List<PlanToDocEntity> pte = DataSupport.where("planId = ?", String.valueOf(record.getId())).find(PlanToDocEntity.class);
                        boolean temp = false;
                        for (PlanToDocEntity item : pte) {
                            if (item.getDocType() == DocsConstant.SCENE_INSP) {
                                temp = true;
                            }
                        }
                        if (temp) {
                            record.setIsFinish(1);
                            record.setEndTime(DateUtil.getCurrentDataYMDHMS());
                            record.update(record.getId());
                            finish();
                        } else {
                            Toasty.warning(CheckNewActivity.this, "请确保至少有'现场检查文书'").show();
                        }
                    }
                });
            }
        });
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(CheckNewActivity.this, "您还未保存修改，如需保存，请点击右上'完成检查'", new CallBack0() {
                    @Override
                    public void callBack() {
                        finish();
                    }
                });
            }
        });
        addFirstFragment();
    }

    private void addFirstFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("companyCode", companyCode);
        bundle.putInt("planId", planId);
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
        if (checkFragment != null) {
            transaction.hide(checkFragment);
        }

        if (docFragment != null) {
            transaction.hide(docFragment);
        }
        if (historyFragment != null) {
            transaction.hide(historyFragment);
        }
        transaction.commit();
    }

    @OnClick({R.id.tv_info, R.id.tv_check, R.id.tv_doc,R.id.tv_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_info:
                if (infoFragment == null) {
                    infoFragment = new InfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("companyCode", companyCode);
                    bundle.putInt("planId", planId);
                    infoFragment.setArguments(bundle);
                    isAddThisFragment = true;
                }
                changeFragment(infoFragment, tvInfo);
                break;
            case R.id.tv_check:
                if (checkFragment == null) {
                    checkFragment = new CheckFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("companyName", companyName);
                    bundle.putInt("companyCode", companyCode);
                    bundle.putInt("planId", planId);
                    checkFragment.setArguments(bundle);
                    isAddThisFragment = true;
                }
                changeFragment(checkFragment, tvCheck);
                break;
            case R.id.tv_doc:
                if (docFragment == null) {
                    docFragment = new DocFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("companyName", companyName);
                    bundle.putInt("companyCode", companyCode);
                    bundle.putInt("planId", planId);
                    docFragment.setArguments(bundle);
                    isAddThisFragment = true;
                }
                changeFragment(docFragment, tvDoc);
                break;
            case R.id.tv_history:
                if(historyFragment == null){
                    historyFragment = new CheckHistoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("companyCode", companyCode);
                    historyFragment.setArguments(bundle);
                    isAddThisFragment = true;
                }
                changeFragment(historyFragment, tvHistory);
                break;
        }
    }

    private void setTvZero() {
        tvInfo.setTextColor(getResources().getColor(R.color.color_white));
        tvCheck.setTextColor(getResources().getColor(R.color.color_white));
        tvDoc.setTextColor(getResources().getColor(R.color.color_white));
        tvHistory.setTextColor(getResources().getColor(R.color.color_white));
    }
}
