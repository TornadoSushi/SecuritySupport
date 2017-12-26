package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.review;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.CloseCoalDecide;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.ReviewToDocEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.CoalDocUtils;
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

public class CloseCoalActivityR extends BaseActivity {


    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private int companyCode = -1;
    private int planId = -1;
    private String companyName;
    private String something = "";
    private CheckItemRecord checkItem;
    private int subItemId;


    private CloseCoalDecide record;

    @Override
    public int getLayoutId() {
        return R.layout.zdocac_close_coal;
    }

    @Override
    public void initData() {
        checkItem = (CheckItemRecord) getIntent().getBundleExtra("itemBundle").getSerializable("itemEntity");
        planId = checkItem.getPlanId();
        subItemId = checkItem.getSubItemId();
        companyCode = checkItem.getCompanyCode();
        companyName = checkItem.getCompanyName();
        record = DataSupport.where("planId = ? AND type = 1 AND subItemId = ?", String.valueOf(planId), String.valueOf(subItemId)).findLast(CloseCoalDecide.class);

        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edContent.setText(record.getContent());
        } else {
            edInspItem.setText(companyName);
        }
        CompanyBaseInfo baseInfo = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);
        something = baseInfo.getCoalLicence();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {//责令关闭矿井决定书
        titleBar.setTitle("责令关闭矿井决定书");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(CloseCoalActivityR.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
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
                save();
                Toasty.success(CloseCoalActivityR.this, "完成").show();
            }
        });
        tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printAndSave(true);
            }
        });
    }

    private void save() {
        String inspItem = edInspItem.getText().toString().trim();
        String content = edContent.getText().toString().trim();

        if (record == null) {
            CloseCoalDecide entity = new CloseCoalDecide();
            entity.setPlanId(planId);
            entity.setInspItem(inspItem);
            entity.setContent(content);
            entity.save();

            //第一次
            ReviewToDocEntity plan = new ReviewToDocEntity();
            plan.setDocName("责令关闭矿井决定书");
            plan.setDocType(DocsConstant.CLOSE_COAL);
            plan.setDocId(entity.getId());
            plan.setSubItemId(subItemId);
            plan.setPlanId(planId);
            plan.save();
        } else {
            record.setPlanId(planId);
            record.setInspItem(inspItem);
            record.setContent(content);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();
    }


    private void printAndSave(boolean isPrint) {
        save();
        String company = edInspItem.getText().toString().trim();
        String content = edContent.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        params.put("${something}", something);
        params.put("${company}", company);
        params.put("${content}", content);
        params.put("${linex}", DocMultiHelper.SOMETHING_SHIT);

        CoalDocUtils.entryPrinterShare(this, "责令关闭矿井决定书", "ZeLingGuanBiKuangJingJueDingShu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });
    }
}
