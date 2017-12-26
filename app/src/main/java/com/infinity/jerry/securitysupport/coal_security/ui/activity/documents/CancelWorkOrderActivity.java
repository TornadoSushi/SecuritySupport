package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.CancelOrder;
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

public class CancelWorkOrderActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ed_area)
    EditText edArea;
    @BindView(R.id.ed_deal)
    EditText edResult;
    @BindView(R.id.ed_advice)
    EditText edAdvice;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private int companyCode = -1;
    private int planId = -1;
    private String something = "";
    private String companyName;

    private CancelOrder record;

    @Override
    public int getLayoutId() {
        return R.layout.zdocac_cancel_work_order;
    }

    @Override
    public void initData() {
        companyName = getIntent().getStringExtra("companyName");
        companyCode = getIntent().getIntExtra("companyCode", -256);
        planId = getIntent().getIntExtra("planId", -256);
        record = DataSupport.where("planId = ?", String.valueOf(planId)).findLast(CancelOrder.class);

        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edContent.setText(record.getContent());
            edArea.setText(record.getArea());
            edResult.setText(record.getResult());
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
        titleBar.setTitle("撤销作业人员命令书");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(CancelWorkOrderActivity.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
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
                Toasty.success(CancelWorkOrderActivity.this, "完成").show();
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
        String area = edArea.getText().toString().trim();
        String result = edResult.getText().toString().trim();

        if (record == null) {
            CancelOrder entity = new CancelOrder();
            entity.setPlanId(planId);
            entity.setInspItem(inspItem);
            entity.setContent(content);
            entity.setArea(area);
            entity.setResult(result);
            entity.save();
            //第一次
            PlanToDocEntity entityX = new PlanToDocEntity();
            entityX.setPlanId(planId);
            entityX.setDocName("撤销作业人员命令书");
            entityX.setDocType(DocsConstant.ORDER_CANCELWORK);
            entityX.save();
        } else {
            record.setPlanId(planId);
            record.setInspItem(inspItem);
            record.setContent(content);
            record.setArea(area);
            record.setResult(result);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();
    }


    private void printAndSave(boolean isPrint) {
        save();
        String content = edContent.getText().toString().trim();
        String area = edArea.getText().toString().trim();
        String result = edResult.getText().toString().trim();
        String advice = edAdvice.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        params.put("${linex}", DocMultiHelper.SOMETHING_SHIT);
        params.put("${something}", something);
        params.put("${content}", content);
        params.put("${area}", area);
        params.put("${result}", result);
        params.put("${advice}", advice);

        CoalDocUtils.entryPrinterShare(this, "撤销作业人员命令书", "CheChuZuoYeRenYuanMingLingShu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });
    }
}
