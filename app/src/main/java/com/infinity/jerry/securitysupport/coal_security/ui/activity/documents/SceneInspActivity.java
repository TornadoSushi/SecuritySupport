package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.SceneInspEntity;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntity;
import com.infinity.jerry.securitysupport.common.entity.PlanToDocEntity;
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
 * Created by jerry on 2017/9/26.
 * private String inspPerson;
 * private String timeStamp;
 * private String line;
 * private String mining_lic;
 * private String mas_safty_certi;
 * private String mas_certi;
 * private String satfy_lic;
 * private String coal_lic;
 * <p>
 * private String secondPerson;
 * private String inspContent;
 * private String inspAdvice;
 */

public class SceneInspActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.edPlace)
    EditText edPlace;
    @BindView(R.id.edTime)
    EditText edTime;
    @BindView(R.id.edLine)
    EditText edLine;
    @BindView(R.id.ed_mining_lic)
    EditText edMiningLic;
    @BindView(R.id.ed_masSafty_lic)
    EditText edMasSaftyLic;
    @BindView(R.id.ed_mas_certi)
    EditText edMasCerti;
    @BindView(R.id.ed_safty_lic)
    EditText edSaftyLic;
    @BindView(R.id.ed_coal_lic)
    EditText edCoalLic;
    @BindView(R.id.ed_comp)
    EditText edComp;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ed_advice)
    EditText edAdvice;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private String companyName;
    private int companyCode;
    private int planId;

    private SceneInspEntity record;
    private String content;

    private String something;
    private CompanyCoalEntity company;

    private void save() {
        String inSpItem = edInspItem.getText().toString().trim();
        String place = edPlace.getText().toString().trim();
        String time = edTime.getText().toString().trim();
        String line = edLine.getText().toString().trim();
        String miningLic = edMiningLic.getText().toString().trim();
        String masSaftyCerti = edMasSaftyLic.getText().toString().trim();
        String masCerti = edMasCerti.getText().toString().trim();
        String saftyLic = edSaftyLic.getText().toString().trim();
        String coalLic = edCoalLic.getText().toString().trim();
        String secondPersom = edComp.getText().toString().trim();
        String content = edContent.getText().toString().trim();
        String advice = edAdvice.getText().toString().trim();

        if (record == null) {
            SceneInspEntity recordX = new SceneInspEntity();
            recordX.setPlanId(planId);
            recordX.setInspItem(inSpItem);
            recordX.setPlace(place);
            recordX.setTimeStamp(System.currentTimeMillis());
            recordX.setLine(line);
            recordX.setMining_lic(miningLic);
            recordX.setMas_safty_certi(masSaftyCerti);
            recordX.setMas_certi(masCerti);
            recordX.setSatfy_lic(saftyLic);
            recordX.setCoal_lic(coalLic);
            recordX.setSecondPerson(secondPersom);
            recordX.setInspContent(content);
            recordX.setInspAdvice(advice);
            recordX.save();
            //第一次
            PlanToDocEntity entity = new PlanToDocEntity();
            entity.setPlanId(planId);
            entity.setDocName("现场检查记录");
            entity.setDocType(DocsConstant.SCENE_INSP);
            entity.save();
        } else {
            record.setPlanId(planId);
            record.setInspItem(inSpItem);
            record.setPlace(place);
            record.setTimeStamp(System.currentTimeMillis());
            record.setLine(line);
            record.setMining_lic(miningLic);
            record.setMas_safty_certi(masSaftyCerti);
            record.setMas_certi(masCerti);
            record.setSatfy_lic(saftyLic);
            record.setCoal_lic(coalLic);
            record.setSecondPerson(secondPersom);
            record.setInspContent(content);
            record.setInspAdvice(advice);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.zdoc_secne_insp;
    }

    @Override
    public void initData() {
        content = getIntent().getStringExtra("ai_content");
        if (content == null) {
            content = "";
        }
        edContent.setText(content);
        companyName = getIntent().getStringExtra("companyName");
        companyCode = getIntent().getIntExtra("companyCode", -256);
        planId = getIntent().getIntExtra("planId", -256);
        record = DataSupport.where("planId = ?", String.valueOf(planId)).findLast(SceneInspEntity.class);
        CompanyBaseInfo baseInfo = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);
        company = DataSupport.where("companyCode = ?", String.valueOf(companyCode)).findLast(CompanyCoalEntity.class);
        something = baseInfo.getSaftyLicCode();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onBackPressed() {
        ZDialogUtils.showDialog(SceneInspActivity.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
            @Override
            public void callBack() {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        titleBar.setTitle("现场检查记录(新)");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(SceneInspActivity.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
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
                Toasty.success(SceneInspActivity.this, "完成").show();
            }
        });

        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edTime.setText(DateUtil.getCurrentDataYMDHMS());
            edPlace.setText(record.getPlace());
            edLine.setText(record.getLine());
            edMiningLic.setText(record.getMining_lic());
            edMasSaftyLic.setText(record.getMas_safty_certi());
            edMasCerti.setText(record.getMas_certi());
            edSaftyLic.setText(record.getSatfy_lic());
            edCoalLic.setText(record.getCoal_lic());
            edComp.setText(record.getSecondPerson());
            edContent.setText(record.getInspContent());
            edAdvice.setText(record.getInspAdvice());
        } else {
            CompanyBaseInfo baseInfo = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);
            edInspItem.setText(companyName);
            edTime.setText(DateUtil.getCurrentDataYMDHMS());
            edMiningLic.setText(baseInfo.getMiningLicCode());
            edMasSaftyLic.setText(baseInfo.getMasCertiCode());
            edMasCerti.setText(baseInfo.getMasCertiCode());
            edSaftyLic.setText(baseInfo.getSaftyLicCode());
            edCoalLic.setText(baseInfo.getCoalLicence());
        }
        tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printAndSave(true);
            }
        });
    }

    private void printAndSave(boolean isPrint) {
        String inSpItem = edInspItem.getText().toString().trim();
        String place = edPlace.getText().toString().trim();
        String line = edLine.getText().toString().trim();
        String time = edTime.getText().toString().trim();
        String miningLic = edMiningLic.getText().toString().trim();
        String masSaftyCerti = edMasSaftyLic.getText().toString().trim();
        String masCerti = edMasCerti.getText().toString().trim();
        String saftyLic = edSaftyLic.getText().toString().trim();
        String coalLic = edCoalLic.getText().toString().trim();
        String secondPersom = edComp.getText().toString().trim();
        String content = edContent.getText().toString().trim();
        String advice = edAdvice.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();
        DocMultiHelper.setPageEmptyInfo(params);//清空所有信息

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        DocMultiHelper.setSliptContent(params, content, 190);

        params.put("${something}", something);

        params.put("${address}", line);

        params.put("${inspItem}", inSpItem);

        params.put("${name}", company.getLeaglPerson());

        params.put("${place}", place);

        params.put("${phone}", company.getCoalPhone());

        params.put("${line}", line);

        int a1 = 18;
        StringBuilder inspS1 = new StringBuilder(inSpItem);
        for (int f = 0; f < a1 - inSpItem.length(); f++) {
            inspS1.append("\t");
        }
        params.put("${check}", inspS1.toString());

        String newTime = year + " 日 " + month + " 月 " + day + " 日 ";

        int a9 = 30;
        StringBuilder timeS9 = new StringBuilder(newTime);
        for (int i = 0; i < a9 - newTime.length(); i++) {
            timeS9.append("\t");
        }
        params.put("${time}", timeS9.toString());

        int a2 = 22;
        StringBuilder lineS2 = new StringBuilder(line);
        for (int f = 0; f < a2 - inSpItem.length(); f++) {
            lineS2.append("\t");
        }
        params.put("${line}", lineS2.toString());

        int a3 = 26;
        StringBuilder miniS3 = new StringBuilder(miningLic);
        for (int i = 0; i < a3 - miniS3.length() / 2; i++) {
            miniS3.append("\t");
        }
        params.put("${mining_lic}", miniS3.toString());

        int a4 = 25;
        StringBuilder masS4 = new StringBuilder(masSaftyCerti);
        for (int i = 0; i < a4 - masS4.length() / 2; i++) {
            masS4.append("\t");
        }
        params.put("${mas_safty_certi}", masS4.toString());

        int a5 = 26;
        StringBuilder mascS5 = new StringBuilder(masCerti);
        for (int i = 0; i < a5 - mascS5.length() / 2; i++) {
            mascS5.append("\t");
        }
        params.put("${mas_certi}", mascS5.toString());

        int a6 = 24;
        StringBuilder safS6 = new StringBuilder(saftyLic);
        for (int i = 0; i < a6 - safS6.length() / 2 - 3; i++) {
            safS6.append("\t");
        }
        params.put("${safty_lic}", safS6.toString());

        int a7 = 32;
        StringBuilder coalS7 = new StringBuilder(coalLic);
        for (int i = 0; i < a7 - coalS7.length(); i++) {
            coalS7.append("\t");
        }
        params.put("${coal_lic}", coalS7.toString());

        int a8 = 25;
        StringBuilder spS8 = new StringBuilder(secondPersom);
        for (int i = 0; i < a8 - spS8.length(); i++) {
            spS8.append("\t");
        }
        params.put("${secondPerson}", secondPersom);
        params.put("${advice}", advice);

        CoalDocUtils.entryPrinterShare(this, "现场检查记录", "XianChangJianChaBiLu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });
    }
}
