package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.SceneDealDecide;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.ReviewToDocEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.CoalDocUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DocMultiHelper;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/9/27.
 */

public class SceneDealActivityR extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.ed_check_time)
    EditText edCheckTime;
    @BindView(R.id.ed_illegal)
    EditText edIllgal;
    @BindView(R.id.ed_advice)
    EditText edAdvice;
    @BindView(R.id.ed_invest_name)
    EditText edInvestName;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private SceneDealDecide record;
    private CheckItemRecord checkItem;

    private String something;
    private String companyName;
    private int planId = -1;
    private int companyCode = -1;
    private int subItemId;

    @Override
    public int getLayoutId() {
        return R.layout.zdocac_secne_deal;
    }

    @Override
    public void initData() {

        checkItem = (CheckItemRecord) getIntent().getBundleExtra("itemBundle").getSerializable("itemEntity");
        planId = checkItem.getPlanId();
        subItemId = checkItem.getSubItemId();
        companyCode = checkItem.getCompanyCode();
        companyName = checkItem.getCompanyName();
        record = DataSupport.where("planId = ? AND type = 1 AND subItemId = ?", String.valueOf(planId), String.valueOf(subItemId)).findLast(SceneDealDecide.class);

        CompanyBaseInfo baseInfo = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);

        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edCheckTime.setText(record.getTime());
            edIllgal.setText(record.getIllgalContent());
            edAdvice.setText(record.getDealContent());
            edInvestName.setText(record.getInvestName());
        } else {
            edInspItem.setText(companyName);
            edCheckTime.setText(DateUtil.getCurrentDataYMDHMS());
        }

        something = baseInfo.getCoalLicence();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("现场处理决定书");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(SceneDealActivityR.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
                    @Override
                    public void callBack() {
                        finish();
                    }
                });
            }
        });
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                saveData();
                Toasty.success(SceneDealActivityR.this, "完成").show();
            }
        });
        tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printAndSave(true);
            }
        });

    }

    private void saveData() {
        String inspItem = edInspItem.getText().toString().trim();
        String time = edCheckTime.getText().toString().trim();
        String illgal = edIllgal.getText().toString().trim();
        String deal = edAdvice.getText().toString().trim();
        String investName = edInvestName.getText().toString().trim();

        if (record == null) {
            SceneDealDecide entity = new SceneDealDecide();
            entity.setPlanId(planId);
            entity.setInspItem(inspItem);
            entity.setTime(time);
            entity.setIllgalContent(illgal);
            entity.setDealContent(deal);
            entity.setInvestName(investName);
            entity.setSubItemId(subItemId);
            entity.setType(1);
            entity.save();

            //第一次
            ReviewToDocEntity plan = new ReviewToDocEntity();
            plan.setDocName("现场处理决定书");
            plan.setDocType(DocsConstant.SCENE_DEAL);
            plan.setDocId(entity.getId());
            plan.setSubItemId(subItemId);
            plan.setPlanId(planId);
            plan.save();
        } else {
            record.setPlanId(planId);
            record.setInspItem(inspItem);
            record.setTime(time);
            record.setIllgalContent(illgal);
            record.setDealContent(deal);
            record.setInvestName(investName);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();
    }


    private void printAndSave(boolean isPrint) {
        saveData();
        String time = edCheckTime.getText().toString().trim();
        String illgal = edIllgal.getText().toString().trim();
        String deal = edAdvice.getText().toString().trim();
        String investName = edInvestName.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("${linex}", DocMultiHelper.SOMETHING_SHIT);

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        params.put("${time}", time);
        params.put("${illegal}", illgal);
        params.put("${advice}", deal);
        params.put("${name}", investName);
        params.put("${something}", something);


        CoalDocUtils.entryPrinterShare(this, "现场处理决定书", "XianChangChuLiJueDingShu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });

    }
}
