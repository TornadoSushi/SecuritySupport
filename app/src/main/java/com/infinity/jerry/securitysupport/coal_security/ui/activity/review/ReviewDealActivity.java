package com.infinity.jerry.securitysupport.coal_security.ui.activity.review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.ReviewAdviceActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review.BeBetterAdviceActivityR;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review.CancelWorkOrderActivityR;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review.CloseCoalActivityR;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review.SceneDealActivityR;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review.TempCancelLicActivityR;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.ReviewRecord;
import com.infinity.jerry.securitysupport.common.entity.ReviewToDocEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZMesuredListView;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/24.
 */

public class ReviewDealActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.edAdvice)
    EditText edAdvice;
    @BindView(R.id.tvAddDoc)
    TextView tvAddDoc;
    @BindView(R.id.listView)
    ZMesuredListView listView;
    @BindView(R.id.tvFinish)
    TextView tvFinish;

    private int isPassReview = 0;

    private CheckItemRecord record;

    private ReviewRecord reviewRecord;

    private List<ReviewToDocEntity> docList;
    private ZCommonAdapter<ReviewToDocEntity> adapter;

    //type 0 ,1,2,3,4,5,6
    private static final String[] FILES = {"复查意见书",
            "现场处理决定书",
            "撤出作业人员命令书",
            "责令关闭矿井决定书",
            "暂扣或者吊销煤炭生产许可证决定书",
            "加强和改善安全监督管理建议书"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_review_deal;
    }

    @Override
    public void initData() {
        record = (CheckItemRecord) getIntent().getBundleExtra("recordBundle").getSerializable("recordEntity");
        initDocList();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("复查记录");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                Toasty.info(ReviewDealActivity.this, "检查记录").show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        isPassReview = 0;
                        break;
                    case R.id.rb_2:
                        isPassReview = 1;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tvAddDoc, R.id.tvFinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAddDoc://添加文书
                addDoc();
                break;
            case R.id.tvFinish:
                ZDialogUtils.showDialog(this, "您确定要完成此次复查", new CallBack0() {
                    @Override
                    public void callBack() {
                        reviewRecord = new ReviewRecord();
                        reviewRecord.setAdvice(edAdvice.getText().toString());
                        reviewRecord.setExcuteTime(DateUtil.getCurrentDataYMDHMS());
                        reviewRecord.setPlanId(record.getPlanId());
                        reviewRecord.setSubItemId(record.getSubItemId());
                        reviewRecord.save();
                        record.setIsReview(isPassReview);
                        record.update(record.getId());
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                break;
        }
    }

    private void addDoc() {
        ZDialogUtils.showDialog2(this, FILES, "文书类型选择", new CallBack1() {
            @Override
            public void callBack(Object object) {
                int position = (int) object;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putSerializable("itemEntity", record);
                intent.putExtra("itemBundle", bundle);
                switch (position) {
                    case 0://复查意见书
                        if (isGoesNext(DocsConstant.REVIEW_ADVICE)) {
                            intent.setClass(ReviewDealActivity.this, ReviewAdviceActivity.class);
                            startActivityForResult(intent, 0);
                        }
                        break;
                    case 1:
                        if (isGoesNext(DocsConstant.SCENE_DEAL)) {
                            intent.setClass(ReviewDealActivity.this, SceneDealActivityR.class);
                            startActivityForResult(intent, 1);
                        }
                        break;
                    case 2:
                        if (isGoesNext(DocsConstant.ORDER_CANCELWORK)) {
                            intent.setClass(ReviewDealActivity.this, CancelWorkOrderActivityR.class);
                            startActivityForResult(intent, 2);
                        }
                        break;
                    case 3:
                        if (isGoesNext(DocsConstant.CLOSE_COAL)) {
                            intent.setClass(ReviewDealActivity.this, CloseCoalActivityR.class);
                            startActivityForResult(intent, 3);
                        }
                        break;
                    case 4:
                        if (isGoesNext(DocsConstant.TEMP_CANCEL_LIC)) {
                            intent.setClass(ReviewDealActivity.this, TempCancelLicActivityR.class);
                            startActivityForResult(intent, 4);
                        }
                        break;
                    case 5:
                        if (isGoesNext(DocsConstant.BETTER_ADVICE)) {
                            intent.setClass(ReviewDealActivity.this, BeBetterAdviceActivityR.class);
                            startActivityForResult(intent, 5);
                        }
                        break;
                }
            }
        });
    }

    private void initDocList() {
        docList = DataSupport.where("planId = ? AND subItemId = ?", String.valueOf(record.getPlanId()), String.valueOf(record.getSubItemId())).find(ReviewToDocEntity.class);
        adapter = new ZCommonAdapter<ReviewToDocEntity>(this, docList, R.layout.item_single_text) {
            @Override
            public void convert(ZViewHolder holder, ReviewToDocEntity item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getDocName());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReviewToDocEntity item = docList.get(position);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemEntity", record);
                intent.putExtra("itemBundle", bundle);
                switch (item.getDocType()) {
                    case DocsConstant.REVIEW_ADVICE:
                        intent.setClass(ReviewDealActivity.this, ReviewAdviceActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                    case DocsConstant.SCENE_DEAL:
                        intent.setClass(ReviewDealActivity.this, SceneDealActivityR.class);
                        startActivityForResult(intent, 1);
                        break;
                    case DocsConstant.ORDER_CANCELWORK:
                        intent.setClass(ReviewDealActivity.this, CancelWorkOrderActivityR.class);
                        startActivityForResult(intent, 2);
                        break;
                    case DocsConstant.CLOSE_COAL:
                        intent.setClass(ReviewDealActivity.this, CloseCoalActivityR.class);
                        startActivityForResult(intent, 3);
                        break;
                    case DocsConstant.TEMP_CANCEL_LIC:
                        intent.setClass(ReviewDealActivity.this, TempCancelLicActivityR.class);
                        startActivityForResult(intent, 4);
                        break;
                    case DocsConstant.BETTER_ADVICE:
                        intent.setClass(ReviewDealActivity.this, BeBetterAdviceActivityR.class);
                        startActivityForResult(intent, 5);
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            initDocList();
        }
    }

    private boolean isGoesNext(int typeId) {
        for (ReviewToDocEntity item : docList) {
            if (item.getDocType() == typeId) {
                Toasty.error(this, "您已经添加过该文书了").show();
                return false;
            }
        }
        return true;
    }
}


