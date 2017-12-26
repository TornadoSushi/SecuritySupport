package com.infinity.jerry.securitysupport.common.otherstuff.seriousdanger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.SearchbarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.WindowUtils;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.HazardDBController;

import java.util.List;

public class HazardChooseActivity extends SearchbarActivity {

    private TextView mCategoryTV;
    private TextView mItemTV;
    private TextView mThreholdTV;
    private EditText mCountET;

    private HazardDanger mItem;

    private String[] mCategories;

    private static final int REQUEST_CODE_HAZARD_SEARCH = 0x201;

    private static final String KEY = "HazardChooseActivity";
    static SmallActivityCache<HazardDanger> sStoryHashMap = new SmallActivityCache<HazardDanger>();

    public static void launchActivity(Context context, HazardDanger item) {
        Long key = sStoryHashMap.put(item);

        Intent intent = new Intent(context, HazardChooseActivity.class);
        intent.putExtra(HazardChooseActivity.KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected View onCreateChildView() {
        mCategories = getResources().getStringArray(R.array.hazard_categories);
        mItem = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            mItem = sStoryHashMap.get(extras.getLong(KEY));
        }

        View view = getInflateView(R.layout.activity_hazard_choose);
        mCategoryTV = (TextView) view.findViewById(R.id.hazardChooseCategoryTV);
        mItemTV = (TextView) view.findViewById(R.id.hazardChooseItemTV);
        mCountET = (EditText) view.findViewById(R.id.hazardChooseCountET);
        mThreholdTV = (TextView) view.findViewById(R.id.hazardChooseThreholdTV);

        if (mItem.name != null) {
            initViews();
        }
        return view;
    }

    @Override
    protected boolean beforeSearchStarted(String searchWord) {
        HazardSearchActivity.launchActivityForResult(this, searchWord, REQUEST_CODE_HAZARD_SEARCH);
        return false;
    }

    @Override
    protected void onAsyncSearchStarted(String searchWord) {

    }

    @Override
    protected String centerTitle() {
        return "危险化学品";
    }

    @Override
    protected boolean hasBackOption() {
        return true;
    }

    @Override
    protected View.OnClickListener rightClickListener() {
        return null;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {
        TextView tv = new TextView(this);
        tv.setText("删除");
        tv.setBackgroundResource(R.drawable.main_btn_background);
        int horizonPadding = WindowUtils.dip2px(this, 16);
        int verticalPadding = WindowUtils.dip2px(this, 8);
        tv.setPadding(horizonPadding, verticalPadding, horizonPadding, verticalPadding);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, WindowUtils.sp2px(this, 16));
        ll.addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItem.isDeleteSuccessed = true;
                finish();
            }
        });
    }

    public void onChooseCategoryClicked(View view) {
        showCategoriesDialog();
    }

    public void onChooseItemClicked(View view) {
        showItemsDialog();
    }

    public void onSaveItemClicked(View view) {
        String categoryText = mCategoryTV.getText().toString();
        if (null == categoryText) {
            Toast.makeText(this, "请选择危化品种类", Toast.LENGTH_SHORT).show();
            return;
        }
        String itemText = mItemTV.getText().toString();
        if (null == itemText) {
            Toast.makeText(this, "请选择危化品", Toast.LENGTH_SHORT).show();
            return;
        }
        String countText = mCountET.getText().toString();
        if (null == countText || TextUtils.isEmpty(countText)) {
            Toast.makeText(this, "请填写实际存量", Toast.LENGTH_SHORT).show();
            return;
        } else {
            countText = countText.trim();
            float count = Float.valueOf(countText);
            if (count <= 0) {
                Toast.makeText(this, "实际存量不可为零或负值", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mItem.count = countText;
            }
        }
        mItem.isAddSuccessed = true;
        finish();
    }

    private void initViews() {
        mCategoryTV.setText(mCategories[mItem.categoryId - 1]);
        mItemTV.setText(mItem.name);
        mThreholdTV.setText(mItem.threhold);
        mCountET.setText(mItem.count);
    }

    private void showCategoriesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择危化品种类")
                .setItems(R.array.hazard_categories, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mItem.categoryId = which + 1;
                        mCategoryTV.setText(mCategories[which]);
                    }
                });
        builder.create().show();
    }

    private void showItemsDialog() {
        if (mItem.categoryId <= 0) {
            Toast.makeText(this, "请先选择危化品类别", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final List<HazardDanger> items = HazardDBController.instance().db_getHazardsByCategoryId(mItem.categoryId);
        final String[] sections = new String[items.size()];
        for (int i = 0; i != items.size(); ++i) {
            sections[i] = items.get(i).name;
        }
        builder.setTitle("选择危化品")
                .setItems(sections, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HazardDanger item = items.get(which);
                        mItem.name = item.name;
                        mItem.threhold = item.threhold;
                        mItem.beta = item.beta;

                        mItemTV.setText(mItem.name);
                        mThreholdTV.setText(mItem.threhold);
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && requestCode == REQUEST_CODE_HAZARD_SEARCH) {
            int categoryId = data.getIntExtra("HazardCategoryId", -1);
            if (-1 != categoryId) {
                mItem.categoryId = categoryId;
                mItem.name = data.getStringExtra("HazardName");
                mItem.threhold = data.getStringExtra("HazardThreshold");
                mItem.beta = data.getStringExtra("HazardBeta");

                initViews();
            }
        }
    }
}
