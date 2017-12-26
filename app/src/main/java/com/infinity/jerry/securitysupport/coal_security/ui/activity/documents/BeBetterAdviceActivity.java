package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.BeBetterAdvice;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.PlanToDocEntity;
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

public class BeBetterAdviceActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_telling)
    EditText edTelling;
    @BindView(R.id.ed_baosong)
    EditText edBaosong;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private int companyCode = -1;
    private int planId = -1;
    private String companyName;
    private String something = "";

    private BeBetterAdvice record;

    @Override
    public int getLayoutId() {
        return R.layout.zdocac_be_better_advice;
    }

    @Override
    public void initData() {

        companyName = getIntent().getStringExtra("companyName");
        companyCode = getIntent().getIntExtra("companyCode", -256);
        planId = getIntent().getIntExtra("planId", -256);
        record = DataSupport.where("planId = ?", String.valueOf(planId)).findLast(BeBetterAdvice.class);

        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edName.setText(record.getName());
            edTelling.setText(record.getTelling());
            edBaosong.setText(record.getBaosong());
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
    public void initView() {
        titleBar.setTitle("加强和改善安全监督管理建议书");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(BeBetterAdviceActivity.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
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
                Toasty.success(BeBetterAdviceActivity.this, "完成").show();
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
//        加强和改善安全监督管理建议书
        String inspItem = edInspItem.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String tell = edTelling.getText().toString().trim();
        String baosong = edBaosong.getText().toString().trim();

        if (record == null) {
            BeBetterAdvice entity = new BeBetterAdvice();
            entity.setPlanId(planId);
            entity.setInspItem(inspItem);
            entity.setName(name);
            entity.setTelling(tell);
            entity.setBaosong(baosong);
            entity.save();

            //第一次
            PlanToDocEntity entityX = new PlanToDocEntity();
            entityX.setPlanId(planId);
            entityX.setDocName("加强和改善安全监督管理建议书");
            entityX.setDocType(DocsConstant.BETTER_ADVICE);
            entityX.save();
        } else {
            record.setPlanId(planId);
            record.setInspItem(inspItem);
            record.setName(name);
            record.setTelling(tell);
            record.setBaosong(baosong);
            record.update(record.getId());
        }
    }

    private void printAndSave(boolean isPrint) {
        save();
        String name = edName.getText().toString().trim();
        String tell = edTelling.getText().toString().trim();
        String baosong = edBaosong.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        params.put("${something}", something);
        params.put("${name}", name);
        params.put("${me}", tell);
        params.put("${baosong}", baosong);
        params.put("${linex}", DocMultiHelper.SOMETHING_SHIT);

        CoalDocUtils.entryPrinterShare(this, "加强和改善安全监督管理建议书", "JiaQiangHeGaiShanAnQuanJianDuGuanLiJianYiShu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });
    }
}
