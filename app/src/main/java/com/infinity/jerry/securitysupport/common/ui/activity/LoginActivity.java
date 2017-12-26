package com.infinity.jerry.securitysupport.common.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.MainActivity;
import com.infinity.jerry.securitysupport.common.app.CommonApplication;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntity;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntityNew;
import com.infinity.jerry.securitysupport.common.entity.User;
import com.infinity.jerry.securitysupport.common.test.PresenterModel;
import com.infinity.jerry.securitysupport.common.test.UserHelper;
import com.infinity.jerry.securitysupport.common.z_utils.constant.BaseCallBack;
import com.infinity.jerry.securitysupport.common.z_utils.constant.BaseOkHttpClient;
import com.infinity.jerry.securitysupport.common.z_utils.constant.ConstantPool;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZShrPrefencs;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;


/**
 * Created by jerry on 2017/11/13.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ll_imei)
    LinearLayout llImei;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    @BindView(R.id.rd_anjian)
    RadioButton rdAnjian;
    @BindView(R.id.rd_meijian)
    RadioButton rdMeijian;
    @BindView(R.id.rgbtn)
    RadioGroup rgbtn;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.getData)
    AppCompatButton getData;

    private TelephonyManager tm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        String[] nameAndPwd = ZShrPrefencs.getInstance().getNameAndPwd();
        if (nameAndPwd.length == 3) {
            edName.setText(nameAndPwd[0]);
            edPwd.setText(nameAndPwd[1]);
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        rgbtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_anjian:
                        CommonApplication.isMeijian = false;
                        break;
                    case R.id.rd_meijian:
                        CommonApplication.isMeijian = true;
                        break;
                }
            }
        });
    }

    @SuppressLint("HardwareIds")
    @OnClick({R.id.ll_imei, R.id.tv_login, R.id.getData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_imei:
                tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                Toasty.info(this, "获取IMEI 号码").show();
                break;
            case R.id.tv_login:
                doLogin();
                break;
            case R.id.getData:
                BaseOkHttpClient.newBuilder()
                        .get()
                        .url(ConstantPool.URL)
                        .build().enqueue(new BaseCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        Gson gson = new Gson();
                        CompanyCoalEntityNew entity = gson.fromJson(o.toString(),CompanyCoalEntityNew.class);
                        List<CompanyCoalEntityNew.DataBean> entities = entity.getData();

                        Log.e("TAGTAGTAGTAG",entities.toString());

                        List<CompanyCoalEntity> entityList = new ArrayList<>();
                        for(int i=0;i<entities.size();i++){
                            CompanyCoalEntity coalEntity = new CompanyCoalEntity();
                            coalEntity.setId(entities.get(i).getId());
                            coalEntity.setCoalCell(entities.get(i).getCoal_cell());
                            coalEntity.setCoalPhone(entities.get(i).getCoal_phone());
                            coalEntity.setCompanyArea(entities.get(i).getCompany_area());
                            coalEntity.setCompanyName(entities.get(i).getCompany_name());
                            coalEntity.setCoalUrl(entities.get(i).getCoal_url());
                            coalEntity.setCompanyLocation(entities.get(i).getCompany_location());
                            coalEntity.setCompanyCode(entities.get(i).getCompany_code());
                            coalEntity.setCompanyState(entities.get(i).getCompany_state());
                            coalEntity.setDirector(entities.get(i).getDirector());
                            coalEntity.setLeaglPerson(entities.get(i).getLeagl_person());
                            entityList.add(coalEntity);
                        }
                        DataSupport.saveAll(entityList);
                    }

                    @Override
                    public void onError(int code) {
                        Log.e("TAG", "onError   synData ");
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("TAG", "onFailure    synData " + e.getMessage());
                    }
                });
//                PresenterModel.synData();
                break;
        }
    }

    private void doLogin() {
        String userName = edName.getText().toString();
        String pwd = edPwd.getText().toString();

        if (userName.equals("")) {
            Toasty.info(this, "用户名不能为空").show();
            return;
        }

        if (pwd.equals("")) {
            Toasty.info(this, "密码不能为空").show();
            return;
        }

        String realName = "";
        List<User> userList = UserHelper.getUserList();
        boolean isLogin = false;
        for (User user : userList) {
            if (userName.equals(user.getUsername()) && pwd.equals(user.getPassword())) {
                isLogin = true;
                realName = user.getName();
                break;
            }
        }
        if (isLogin) {
            ZShrPrefencs.getInstance().setNameAndPwd(userName, pwd, realName);
            Intent intent = new Intent(this, MainActivity.class);
            Toasty.success(this, "欢迎使用煤监执法系统,尊敬的" + realName + "同志").show();
            startActivity(intent);
        } else {
            Toasty.error(this, "用户名或密码错误").show();
        }
    }

}
