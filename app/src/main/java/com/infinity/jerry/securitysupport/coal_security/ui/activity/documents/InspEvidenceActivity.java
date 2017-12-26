package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.InspEvidence;
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

public class InspEvidenceActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.ed_location)
    EditText edLocation;
    @BindView(R.id.ed_insp_content)
    EditText edInspContent;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.rb_man)
    RadioButton rbMela;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.ed_age)
    EditText edAge;
    @BindView(R.id.ed_work_place)
    EditText edWorkPlace;
    @BindView(R.id.ed_duty)
    EditText edDuty;
    @BindView(R.id.ed_protical)
    EditText edProtical;
    @BindView(R.id.ed_level)
    EditText edLevel;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.ed_invest_man)
    EditText edInvestMan;
    @BindView(R.id.ed_invest_record)
    EditText edInvestRecord;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private String something = "";
    private String gender = "男";

    private InspEvidence record;
    private String companyName;
    private int planId = -1;
    private int companyCode = -1;


    @Override
    public int getLayoutId() {
        return R.layout.zdocac_insp_evidence;
    }

    @Override
    public void initData() {//调查取证笔录
        companyName = getIntent().getStringExtra("companyName");
        companyCode = getIntent().getIntExtra("companyCode", -256);
        planId = getIntent().getIntExtra("planId", -256);
        record = DataSupport.where("planId = ?", String.valueOf(planId)).findLast(InspEvidence.class);

        CompanyBaseInfo baseInfo = DataSupport.where("coalCode = ?", String.valueOf(companyCode)).findLast(CompanyBaseInfo.class);
        if (record != null) {
            edInspItem.setText(record.getInSpItem());
            edLocation.setText(record.getLocation());
            edInspContent.setText(record.getContent());
            edName.setText(record.getName());
            edAge.setText(record.getAge());
            edWorkPlace.setText(record.getWorkPlace());
            edDuty.setText(record.getDuty());
            edProtical.setText(record.getProtical());
            edLevel.setText(record.getLevel());
            edPhone.setText(record.getPhone());
            edAddress.setText(record.getAddress());
            edInvestMan.setText(record.getInvestMan());
            edInvestRecord.setText(record.getInvestRecord());

            if (record.getGender().equals("男")) {
                rbMela.setChecked(true);
                gender = "男";
            } else {
                rbWoman.setChecked(true);
                gender = "女";
            }
        } else {
            edInspItem.setText(companyName);
        }
        something = baseInfo.getCoalLicence();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("调查取证笔录");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(InspEvidenceActivity.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
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
                Toasty.success(InspEvidenceActivity.this, "完成").show();
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_man:
                        gender = "男";
                        break;
                    case R.id.rb_woman:
                        gender = "女";
                        break;
                }
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
        String location = edLocation.getText().toString().trim();
        String content = edInspContent.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String age = edAge.getText().toString().trim();
        //gender
        String gender = this.gender;
        String workPlace = edWorkPlace.getText().toString().trim();
        String duty = edDuty.getText().toString().trim();
        String protical = edProtical.getText().toString().trim();
        String level = edLevel.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String investMan = edInvestMan.getText().toString().trim();
        String investRecord = edInvestRecord.getText().toString().trim();

        if (record == null) {
            InspEvidence entity = new InspEvidence();
            entity.setPlanId(planId);
            entity.setInSpItem(inspItem);
            entity.setLocation(location);
            entity.setContent(content);
            entity.setName(name);
            entity.setAge(age);
            entity.setGender(gender);
            entity.setWorkPlace(workPlace);
            entity.setDuty(duty);
            entity.setProtical(protical);
            entity.setLevel(level);
            entity.setPhone(phone);
            entity.setAddress(address);
            entity.setInvestMan(investMan);
            entity.setInvestRecord(investRecord);
            entity.save();
            //第一次
            PlanToDocEntity entityX = new PlanToDocEntity();
            entityX.setPlanId(planId);
            entityX.setDocName("调查取证笔录");
            entityX.setDocType(DocsConstant.EVIDENCE_INSP);
            entityX.save();
        } else {
            record.setInSpItem(inspItem);
            record.setLocation(location);
            record.setContent(content);
            record.setName(name);
            record.setAge(age);
            record.setGender(gender);
            record.setWorkPlace(workPlace);
            record.setDuty(duty);
            record.setProtical(protical);
            record.setLevel(level);
            record.setPhone(phone);
            record.setAddress(address);
            record.setInvestMan(investMan);
            record.setInvestRecord(investRecord);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();
    }


    private void printAndSave(boolean isPrint) {
        String inspItem = edInspItem.getText().toString().trim();
        String location = edLocation.getText().toString().trim();
        String content = edInspContent.getText().toString().trim();
        String name = edName.getText().toString().trim();
        String age = edAge.getText().toString().trim();
        //gender
        String workPlace = edWorkPlace.getText().toString().trim();
        String duty = edDuty.getText().toString().trim();
        String protical = edProtical.getText().toString().trim();
        String level = edLevel.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String investMan = edInvestMan.getText().toString().trim();
        String investRecord = edInvestRecord.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();
        DocMultiHelper.setPageEmptyInfo(params);//清空所有信息

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        DocMultiHelper.setSliptContent(params, content, 350);

        int a1 = 28;
        StringBuilder loBuilder = new StringBuilder(location);
        for (int i = 0; i < a1 - loBuilder.length(); i++) {
            loBuilder.append("\t");
        }
        params.put("${location}", loBuilder.toString());
        params.put("${month}", month);

        params.put("${name}", name);
        params.put("${gender}", gender);
        params.put("${age}", age);

        params.put("${company}", workPlace);
        params.put("${duty}", duty);
        params.put("${protical}", protical);

        params.put("${LEVEL}", level);

        params.put("${phone_num}", phone);
        params.put("${address}", address);

        params.put("${invest_man}", investMan);
        params.put("${record_man}", investRecord);

        CoalDocUtils.entryPrinterShare(this, "调查取证笔录", "DiaoChaQuZhengBiLu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });

    }
}



