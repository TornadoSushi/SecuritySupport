package com.infinity.jerry.securitysupport.coal_security.ui.activity.plans;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.company.CompanyListActivity;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/13.
 */

public class PlanAddActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.llStartTime)
    LinearLayout llStartTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.llEndTime)
    LinearLayout llEndTime;
    @BindView(R.id.tvPlanType)
    TextView tvPlanType;
    @BindView(R.id.llPlanType)
    LinearLayout llPlanType;
    @BindView(R.id.tvCom)
    TextView tvCom;
    @BindView(R.id.llChooseCom)
    LinearLayout llChooseCom;
    @BindView(R.id.tvEnsure)
    TextView tvEnsure;
    @BindView(R.id.tvClear)
    TextView tvClear;
    @BindView(R.id.edPlan)
    EditText edPlan;
    @BindView(R.id.tvAddLine)
    TextView addLine;
    @BindView(R.id.tvRestart)
    TextView tvRestart;

    private PlanRecord planEntity;

    private final int REQUEST_GIVEME_ACOMPANY = 66;

    private String companyName = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_add;
    }

    @Override
    public void initData() {
        planEntity = new PlanRecord();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle(getString(R.string.add_plan));
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_NONE());
    }


    @OnClick({R.id.llStartTime, R.id.llEndTime, R.id.llPlanType, R.id.llChooseCom, R.id.tvEnsure, R.id.tvClear, R.id.tvAddLine, R.id.tvRestart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llStartTime:
                launchDatePickerDialog(true);
                break;
            case R.id.llEndTime:
                launchDatePickerDialog(false);
                break;
            case R.id.llPlanType:
                showPlanTypesDialog();
                break;
            case R.id.llChooseCom:
                Intent intent = new Intent(this, CompanyListActivity.class);
                intent.putExtra("randomPlan", new Random().nextInt(50));
                startActivityForResult(intent, REQUEST_GIVEME_ACOMPANY);
                break;
            case R.id.tvEnsure://提交
                ensureAddPlan();
                break;
            case R.id.tvClear://清除
                ZDialogUtils.showDialog(this, "您确定要清除 检查方案对所有内容吗?", new CallBack0() {
                    @Override
                    public void callBack() {
                        schemeClear();
                    }
                });
                break;
            case R.id.tvAddLine://换行
                String scheme = edPlan.getText().toString().trim();
                edPlan.setText(scheme + "\n " + (lines++) + ":");
                edPlan.setSelection(edPlan.getText().toString().length());
                break;
            case R.id.tvRestart:
                ZDialogUtils.showDialog(this, "您确定要初始化检查方案吗?", new CallBack0() {
                    @Override
                    public void callBack() {
                        schemeRestart();
                    }
                });
                break;
        }
    }

    private void schemeClear() {
        edPlan.setText("");
        lines = 1;
    }

    private void schemeRestart() {
        edPlan.setText("现将对 " + companyName + " 企业进行如下检查方案:\n 1: ");
        lines = 2;
        edPlan.setSelection(edPlan.getText().toString().length());
    }

    private int lines = 2;

    private void ensureAddPlan() {
        String planName = edName.getText().toString();
        String starTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        String type = tvPlanType.getText().toString();
        if (planName.isEmpty()) {
            Toasty.error(this, "未填写计划名字").show();
            return;
        }
        if (starTime.isEmpty()) {
            Toasty.error(this, "未填写开始时间").show();
            return;
        }
        if (endTime.isEmpty()) {
            Toasty.error(this, "未填写结束时间").show();
            return;
        }
        if (TextUtils.isEmpty(tvCom.getText()) || planEntity.getCompanyCode() == 0) {
            Toasty.error(this, "未选择受检企业").show();
            return;
        }
        if (type.isEmpty()) {
            Toasty.error(this, "未选择检查类型").show();
            return;
        }
        planEntity.setIsStart(0);
        planEntity.setIsFinish(0);
        planEntity.setExcutePerson1(ZUtils.getExcutePerson());
        planEntity.setPlanName(edName.getText().toString());
        planEntity.setPlanScheme(edPlan.getText().toString().trim());
        planEntity.save();
        Log.e("TAG", planEntity.toString());
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GIVEME_ACOMPANY && resultCode == RESULT_OK) {
            String companyName = data.getStringExtra("companyName");
            int companyCode = data.getIntExtra("companyCode", -256);
            this.companyName = companyName;
            planEntity.setCompanyName(companyName);
            planEntity.setCompanyCode(companyCode);
            tvCom.setText(companyName);
            schemeRestart();
        }
    }

    private void launchDatePickerDialog(final boolean isStart) {
        Calendar c = Calendar.getInstance();
        Dialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                StringBuilder builder = new StringBuilder("");
                builder.append(arg1);
                builder.append("-");
                int month = arg2 + 1;
                if (month < 10) {
                    builder.append("0");
                }
                builder.append(month);
                builder.append("-");
                if (arg3 < 10) {
                    builder.append("0");
                }
                builder.append(arg3);
                String date = builder.toString();
                if (isStart) {
                    planEntity.setStartTime(date);
                    tvStartTime.setText(date);
                } else {
                    planEntity.setEndTime(date);
                    tvEndTime.setText(date);
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dateDialog.setTitle("请选择日期");
        dateDialog.show();
    }

    private void showPlanTypesDialog() {
        final String[] types = {getString(R.string.full_safty_check), getString(R.string.full_health_check)
                , getString(R.string.temp_safty_check), getString(R.string.temp_health_check)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择检查类型")
                .setItems(types, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        planEntity.setPlanType(which);
                        tvPlanType.setText(types[which]);
                    }
                });
        builder.create().show();
    }
}
