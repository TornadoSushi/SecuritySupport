package com.infinity.jerry.securitysupport.coal_security.ui.activity.check;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.CoalCheckItem;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.BitmapUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ImgPath;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

/**
 * Created by edwardliu on 16/1/13.
 */
public class CheckDetailActivity extends BaseActivity {

    private LinearLayout mHealthLL;
    private TextView mContentTV;
    private TextView mRequireTV;
    private TextView mWayTV;
    private TextView mBehaviorTV;
    private TextView mTypeTV;
    private TextView mResultTV;
    private TextView mRecifyTV;

    private TextView mReferTv;

    private EditText mDescriptionET;
    private EditText mPunishET;
    private EditText mAdviceET;

    private ImageView mEvidenceIV;

    private EditText edCurPunish;
    private RadioGroup rgPunish;
    private RadioButton rbNoPunish;
    private RadioButton rbEasyPunish;
    private RadioButton rbHardPunish;
    private EditText edLowPunish;
    private EditText edCurPunAdvice;

    private String mPhotoPath;
    private boolean mHasDanger;
    private Uri mPhotoUri;
    private static final int IMAGE_REQUEST_CODE = 1011;
    private static final int IMAGE_PICK_CODE = 1012;

    private int punishState = 0;
    private final int NO_PUNISH = 0;
    private final int EASY_PUNISH = 1;
    private final int HARD_PUNISH = 2;

    private ZTitleBar titleBar;
    private CoalCheckItem checkItem;
    private int planId;
    private int companyCode;
    private String companyName;
    private PlanRecord currentPlan;

    private CheckItemRecord itemRecord;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_estimatexx;
    }

    @Override
    public void initData() {
        itemRecord = new CheckItemRecord();
        planId = getIntent().getIntExtra("planId", -256);
        checkItem = (CoalCheckItem) getIntent().getBundleExtra("subBundle").getSerializable("subItem");
        currentPlan = DataSupport.where("id = ?", String.valueOf(planId)).findLast(PlanRecord.class);
        companyCode = currentPlan.getCompanyCode();
        companyName= currentPlan.getCompanyName();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成此项");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                finishCommit();
            }
        });
        edCurPunish = findViewById(R.id.cur_punish);
        rgPunish = findViewById(R.id.rg_punishment);
        rbNoPunish = findViewById(R.id.rb_no_punish);
        rbEasyPunish = findViewById(R.id.rb_easy_punish);
        rbHardPunish = findViewById(R.id.rb_hard_punish);
        edLowPunish = findViewById(R.id.low_punish);
        edCurPunAdvice = findViewById(R.id.cur_punish_advice);
        mHealthLL = (LinearLayout) findViewById(R.id.checkEstimateWSLL);
        mContentTV = (TextView) findViewById(R.id.checkEstimateContentTV);
        mRequireTV = (TextView) findViewById(R.id.checkEstimateRequireTV);
        mWayTV = (TextView) findViewById(R.id.checkEstimateWayTV);
        mBehaviorTV = (TextView) findViewById(R.id.checkEstimateBehaviorTV);
        mTypeTV = (TextView) findViewById(R.id.checkEstimateTypeTV);
        mResultTV = (TextView) findViewById(R.id.checkEstimateResultTV);
        mRecifyTV = (TextView) findViewById(R.id.checkEstimateFunctionTV);
        mDescriptionET = (EditText) findViewById(R.id.checkEstimateDescripET);
        mPunishET = (EditText) findViewById(R.id.checkEstimatePunishET);
        mAdviceET = (EditText) findViewById(R.id.checkEstimateAdviceTV);
        mEvidenceIV = (ImageView) findViewById(R.id.checkEstimateEvidenceIVX);
        mReferTv = (TextView) findViewById(R.id.checkEstimateReferenceTV);
        rgPunish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_no_punish:
                        punishState = NO_PUNISH;
                        break;
                    case R.id.rb_easy_punish:
                        punishState = EASY_PUNISH;
                        break;
                    case R.id.rb_hard_punish:
                        punishState = HARD_PUNISH;
                        break;
                }
            }
        });
        mContentTV.setText(checkItem.getCheckinContent());
        mRequireTV.setText(checkItem.getCheckinRequest());
        mReferTv.setText(checkItem.getCheckinReference());
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void finishCommit() {
        itemRecord.setExcutePerson(ZUtils.getExcutePerson());
        itemRecord.setExcuteTime(DateUtil.getCurrentDataYMDHMS());
        itemRecord.setPlanId(planId);
        itemRecord.setSuperItemId(Integer.valueOf(checkItem.getParent()));
        itemRecord.setSubItemId(checkItem.getId());
        itemRecord.setSubItemName(checkItem.getCheckinItem());
        itemRecord.setCompanyCode(companyCode);
        itemRecord.setCompanyName(companyName);
        itemRecord.setIsReview(0);
        String result = mResultTV.getText().toString();
        if (result.isEmpty()) {
            Toasty.error(this, "请选择检查结果").show();
            return;
        }
//        "无隐患", "一般", "重大" 0 1 2
        if (result.equals("无隐患")) {
            itemRecord.setCheckResult(0);
        } else if (result.equals("一般")) {
            itemRecord.setCheckResult(1);
        } else if (result.equals("重大")) {
            itemRecord.setCheckResult(2);
        }

        String advice = edCurPunAdvice.getText().toString().trim();
        if (!advice.isEmpty()) {
            itemRecord.setSceneAdvice(advice);
        }

        String dangerDes = mDescriptionET.getText().toString().trim();
        if (!dangerDes.isEmpty()) {
            itemRecord.setDangerDescri(dangerDes);
        }

        String curPunish = edCurPunish.getText().toString().trim();
        String forcePunish = edLowPunish.getText().toString().trim();
        if (!curPunish.isEmpty()) {
            itemRecord.setSceneMoney(curPunish);
        }
        switch (punishState) {
            case NO_PUNISH:
                itemRecord.setLowPunish(0);
                break;
            case EASY_PUNISH:
                itemRecord.setLowPunish(1);
                break;
            case HARD_PUNISH:
                itemRecord.setLowPunish(2);
                break;
        }
        if (!forcePunish.isEmpty()) {
            itemRecord.setLowForce(forcePunish);
        }
        itemRecord.setIsFinished(1);

        ZDialogUtils.showDialog(this, "请确定每一项检查数据，点击'确定'，完成检查", new CallBack0() {
            @Override
            public void callBack() {
                itemRecord.save();
                Intent intent = new Intent();
                intent.putExtra("finish_id",itemRecord.getSubItemId());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void onCheckResultClicked(View view) {
        showResultsDialog();
    }

    public void onCheckRectifyClicked(View view) {
        showRectifiesDialog();
    }

    public void onCheckBasisClicked(View view) {
        Intent intent = new Intent(this, CheckDetailActivity.class);
        startActivity(intent);
    }

    public void onCheckChooseEvidenceClicked(View view) {
        showEvidencesDialog();
    }

    private void showResultsDialog() {
        ZDialogUtils.showDialog2(this, new String[]{"无隐患", "一般", "重大"}, "选择结果", new CallBack1() {
            @Override
            public void callBack(Object object) {
                switch ((int) object) {
                    case 0:
                        mResultTV.setText("无隐患");
                        itemRecord.setCheckResult(0);
                        break;
                    case 1:
                        mResultTV.setText("一般");
                        itemRecord.setCheckResult(1);
                        break;
                    case 2:
                        mResultTV.setText("重大");
                        itemRecord.setCheckResult(2);
                        break;
                }
                if ((int) object != 0) {
                    mHasDanger = true;
                    mDescriptionET.setEnabled(true);
                    mPunishET.setEnabled(true);
                    mAdviceET.setEnabled(true);
                    edCurPunAdvice.setEnabled(true);
                } else {
                    edCurPunAdvice.setEnabled(false);
                    mHasDanger = false;
                    mDescriptionET.setEnabled(false);
                    mPunishET.setEnabled(false);
                    mAdviceET.setEnabled(false);
                }
            }
        });
    }

    private void showRectifiesDialog() {
        if (!mHasDanger) {
            return;
        }

        ZDialogUtils.showDialog2(this, new String[]{"立即整改", "限期整改"}, "选择方式", new CallBack1() {
            @Override
            public void callBack(Object object) {
                mRecifyTV.setText(object + "");
            }
        });
    }

    private void showEvidencesDialog() {
        if (!mHasDanger) {
            return;
        }
        ZDialogUtils.showDialog2(this, new String[]{"拍照", "相册"}, "选择图片", new CallBack1() {
            @Override
            public void callBack(Object object) {
                if ((int) object == 0) {
                    takePhoto();
                } else {
                    choosePhoto();
                }
            }
        });
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoUri = getOutputMediaFile();
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, IMAGE_PICK_CODE);
        }
    }

    public Uri getOutputMediaFile() {
        if (Environment.getExternalStorageState() != null) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".SecurityLaw");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("ProfileEditActivity", "failed to create directory");
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "PTO_" + timeStamp + ".jpg");
            return Uri.fromFile(mediaFile);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            String selectedImagePath = ImgPath.getPath(CheckDetailActivity.this,
                    selectedImageUri);

            if (selectedImagePath == null || selectedImagePath.isEmpty()) {
                AlertDialog alDailog = new AlertDialog.Builder(
                        CheckDetailActivity.this).setTitle("Image Invalid")
                        .setMessage("Please Select Valid Image").create();
                alDailog.show();
                return;
            }
            loadFromPath(selectedImagePath);
        } else if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            loadFromPath(mPhotoUri.getPath());
        }
    }

    private void loadFromPath(String selectedImagePath) {
        try {
            if (selectedImagePath == null) {
                return;
            }
            mPhotoPath = selectedImagePath;
            Bitmap bitmap = BitmapUtils.getImage(selectedImagePath, 16, 8, 4, 100, 60);
            mEvidenceIV.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}