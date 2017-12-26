package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.ReViewAdviceDoc;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.ReviewToDocEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.CoalDocUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DocMultiHelper;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jerry on 2017/9/28.
 */

public class ReviewAdviceActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.edTime)
    EditText edTime;
    @BindView(R.id.edAdvice)
    EditText edAdvice;
    @BindView(R.id.ed_invest_name)
    EditText edInvestName;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private String something = "";

    private CheckItemRecord checkItem;
    private int planId;
    private int companyCode;
    private int subItemId;
    private ReViewAdviceDoc record;

    @Override
    public int getLayoutId() {
        return R.layout.zdocac_review_advice;
    }

    @Override
    public void initData() {
        titleBar.setTitle("复查意见书");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                save();
            }
        });

        checkItem = (CheckItemRecord) getIntent().getBundleExtra("itemBundle").getSerializable("itemEntity");
        planId = checkItem.getPlanId();
        subItemId = checkItem.getSubItemId();
        companyCode = checkItem.getCompanyCode();

        record = DataSupport.where("type = 1 AND planId = ? AND subItemId = ?", String.valueOf(planId), String.valueOf(subItemId)).findLast(ReViewAdviceDoc.class);
        CompanyBaseInfo baseInfo = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);
        something = baseInfo.getCoalLicence();

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edTime.setText(DateUtil.getCurrentDataYMDHMS());
            edAdvice.setText(record.getAdvice());
            edInvestName.setText(record.getUserName());
        } else {
            edInspItem.setText(checkItem.getCompanyName());
            edTime.setText(DateUtil.getCurrentDataYMDHMS());
        }
    }

    private void save() {
        String inspItem = edInspItem.getText().toString().trim();
        String advice = edAdvice.getText().toString().trim();
        String name = edInvestName.getText().toString().trim();

        if (record == null) {
            ReViewAdviceDoc entity = new ReViewAdviceDoc();
            entity.setInspItem(inspItem);
            entity.setType(1);
            entity.setAdvice(advice);
            entity.setUserName(name);
            entity.setPlanId(planId);
            entity.setSubItemId(subItemId);
            entity.save();

            ReviewToDocEntity plan = new ReviewToDocEntity();
            plan.setDocName("复查建议书");
            plan.setDocType(DocsConstant.REVIEW_ADVICE);
            plan.setDocId(entity.getId());
            plan.setSubItemId(subItemId);
            plan.setPlanId(planId);
            plan.save();

        } else {
            record.setInspItem(inspItem);
            record.setType(1);
            record.setAdvice(advice);
            record.setPlanId(planId);
            record.setSubItemId(subItemId);
            record.setUserName(name);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();
    }

    private void print() {
        String advice = edAdvice.getText().toString().trim();
        String name = edInvestName.getText().toString().trim();
        String time = edTime.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        params.put("${something}", something);
        params.put("${name}", name);
        params.put("${content}", advice);
        params.put("${year}", year);
        params.put("${month}", month);
        params.put("${day}", day);
        params.put("${linex}", DocMultiHelper.SOMETHING_SHIT);

        CoalDocUtils.entryPrinterShare(this, "复查意见书", "FuChaJianYiShu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.tvPrint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvPrint:
                print();
                break;
        }
    }
}
