package com.infinity.jerry.securitysupport.coal_security.ui.activity.review;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.TempNamePara;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZSearchBar;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jerry on 2017/11/14.
 */

public class ReviewActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.searchBar)
    ZSearchBar searchBar;
    @BindView(R.id.listView)
    ListView listView;

    private int tempCode = 1231242424;


    private ZCommonAdapter<TempNamePara> adapter;
    private List<CheckItemRecord> itemRecords;

    private List<TempNamePara> tempList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_review;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_NONE());
        titleBar.setTitle("复查监管");
        searchBar.setOnSearchListener(new ZSearchBar.ZOnSearchListener() {
            @Override
            public void zOnSearch(String string) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListView();
    }

    private void initListView() {
        TempNamePara tempNamePara = null;
        int tempCode = 124543323;
        itemRecords = DataSupport.where("checkResult > 0 AND isReview = 0").order("companyCode").find(CheckItemRecord.class);
        tempList = new ArrayList<>();
        for (CheckItemRecord item : itemRecords) {
            if (item.getCompanyCode() != tempCode) {
                tempCode = item.getCompanyCode();
                tempNamePara = new TempNamePara();
                tempNamePara.setName(item.getCompanyName());
                tempNamePara.setCount(1);
                tempNamePara.setCompanyCode(item.getCompanyCode());
                tempList.add(tempNamePara);
            } else {
                tempNamePara.setCount(tempNamePara.getCount() + 1);
            }
        }
        adapter = new ZCommonAdapter<TempNamePara>(this, tempList, R.layout.item_single_text) {
            @Override
            public void convert(ZViewHolder holder, TempNamePara item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getName());
                TextView tvPlus = holder.getView(R.id.tvPlus);
                tvPlus.setVisibility(View.VISIBLE);
                tvPlus.setText(item.getCount() + "项隐患");
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TempNamePara tempNamePara = tempList.get(position);
                int companyCode = tempNamePara.getCompanyCode();
                Intent intent = new Intent(ReviewActivity.this, ReviewNewActivity.class);
                intent.putExtra("companyCode", companyCode);
                startActivity(intent);
            }
        });
    }


}
