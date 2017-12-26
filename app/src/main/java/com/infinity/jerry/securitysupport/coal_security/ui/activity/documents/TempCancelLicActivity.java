package com.infinity.jerry.securitysupport.coal_security.ui.activity.documents;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.entity.TempCancelLic;
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

public class TempCancelLicActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edInspItem)
    EditText edInspItem;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_mine)
    EditText edMine;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.ed_postCode)
    EditText edPostCode;
    @BindView(R.id.ed_contact)
    EditText edContact;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.tvPrint)
    TextView tvPrint;

    private int companyCode = -1;
    private int planId = -1;
    private String something = "";
    private String companyName;
    private TempCancelLic record;

    @Override
    public int getLayoutId() {
        return R.layout.zdocac_temp_cancel_lic;
    }

    @Override
    public void initData() {
        companyName = getIntent().getStringExtra("companyName");
        companyCode = getIntent().getIntExtra("companyCode", -256);
        planId = getIntent().getIntExtra("planId", -256);
        record = DataSupport.where("planId = ?", String.valueOf(planId)).findLast(TempCancelLic.class);

        if (record != null) {
            edInspItem.setText(record.getInspItem());
            edContent.setText(record.getContent());
            edName.setText(record.getName());
            //告知信息
            edMine.setText(record.getMine());
            edAddress.setText(record.getAddress());
            edPostCode.setText(record.getPostCode());
            edContact.setText(record.getContact());
            edPhone.setText(record.getPhone());
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
        titleBar.setTitle("暂扣或者吊销煤炭生产许可证决定书");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.getImBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZDialogUtils.showDialog(TempCancelLicActivity.this, "您还未保存修改，如需保存，请点击右上'完成'", new CallBack0() {
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
                Toasty.success(TempCancelLicActivity.this, "完成").show();
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
        String name = edName.getText().toString().trim();
        String content = edContent.getText().toString().trim();
        String mine = edMine.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String postCode = edPostCode.getText().toString().trim();
        String contact = edContact.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();

        if (record == null) {
            TempCancelLic entity = new TempCancelLic();
            entity.setPlanId(planId);
            entity.setInspItem(inspItem);
            entity.setName(name);
            entity.setContent(content);
            entity.setMine(mine);
            entity.setAddress(address);
            entity.setPostCode(postCode);
            entity.setContact(contact);
            entity.setPhone(phone);
            entity.save();

            //第一次
            PlanToDocEntity entityX = new PlanToDocEntity();
            entityX.setPlanId(planId);
            entityX.setDocName("暂扣或者吊销煤炭生产许可证决定书");
            entityX.setDocType(DocsConstant.TEMP_CANCEL_LIC);
            entityX.save();
        } else {
            record.setPlanId(planId);
            record.setInspItem(inspItem);
            record.setName(name);
            record.setContent(content);
            record.setMine(mine);
            record.setAddress(address);
            record.setPostCode(postCode);
            record.setContact(contact);
            record.setPhone(phone);
            record.update(record.getId());
        }
        setResult(RESULT_OK);
        finish();


    }

    private void printAndSave(boolean isPrint) {
        save();
        String name = edName.getText().toString().trim();
        String content = edContent.getText().toString().trim();
        String company = edInspItem.getText().toString().trim();

        String mine = edMine.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String postCode = edPostCode.getText().toString().trim();
        String contact = edContact.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();

        HashMap<String, String> params = new HashMap<String, String>();

        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dates = year + "/" + month + "/" + day;

        params.put("${something}", something);
        params.put("${linex}", DocMultiHelper.SOMETHING_SHIT);
        params.put("${name}", name);
        params.put("${checkContent}", content);
        params.put("${mine}", mine);
        params.put("${address}", address);
        params.put("${postCode}", postCode);
        params.put("${contact}", contact);
        params.put("${phone}", phone);

        CoalDocUtils.entryPrinterShare(this, "暂扣或者吊销煤炭生产许可证决定书", "ZanKouDiaoXiaoMeiTanLicShu.doc", params, new CallBack1() {
            @Override
            public void callBack(Object object) {
                Intent intent = (Intent) object;
                startActivity(intent);
            }
        });

    }
}
