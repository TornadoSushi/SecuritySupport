package com.infinity.jerry.securitysupport.coal_security.ui.activity.company;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.CoalConstant;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.check.CheckNewActivity;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntity;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.DateUtil;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZSearchBar;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jerry on 2017/11/13.
 */

public class CompanyListActivityX extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.searchBar)
    ZSearchBar searchBar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tvEast)
    TextView tvEast;
    @BindView(R.id.tvMiddle)
    TextView tvMiddle;
    @BindView(R.id.tvSouth)
    TextView tvSouth;
    @BindView(R.id.tvOther)
    TextView tvOther;

    private ZCommonAdapter<CompanyCoalEntity> adapter;
    private List<CompanyCoalEntity> companyList;

    private int startType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_main;
    }

    @Override
    public void initData() {
        startType = getIntent().getIntExtra("startType", -256);
        companyList = DataSupport.findAll(CompanyCoalEntity.class);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle(getString(R.string.coal_company));
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_NONE());
        searchBar.setSearchHint(getString(R.string.enter_company_name));
        searchBar.setOnSearchListener(new ZSearchBar.ZOnSearchListener() {
            @Override
            public void zOnSearch(String string) {
                companyList.clear();
                searchBar.setSearchHint("请输入公司名字");
                setTvTextColor(null);
                companyList = DataSupport.where("companyName LIKE ?", "%" + string + "%").find(CompanyCoalEntity.class);
                initListView(companyList);
            }
        });
        if (companyList != null) {
            initListView(companyList);
        }
    }

    private void initListView(final List<CompanyCoalEntity> companyList) {
        adapter = new ZCommonAdapter<CompanyCoalEntity>(this, companyList, R.layout.item_single_text) {
            @Override
            public void convert(ZViewHolder holder, CompanyCoalEntity item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getCompanyName());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ZDialogUtils.showDialog(CompanyListActivityX.this,
                        startType == 2 ? "您确定开始一次临时安全检查" : "您确定开始一次临时卫生职业检查", new CallBack0() {
                            @Override
                            public void callBack() {
                                Intent intent = new Intent(CompanyListActivityX.this, CheckNewActivity.class);
                                PlanRecord record = new PlanRecord();
                                switch (startType) {
                                    case 2:
                                        record.setPlanName(getString(R.string.temp_plan));
                                        record.setPlanType(2);
                                        break;
                                    case 3:
                                        record.setPlanName(getString(R.string.temp_plan));
                                        record.setPlanType(3);
                                        break;
                                }
                                record.setExcutePerson1(ZUtils.getExcutePerson());
                                record.setStartTime(DateUtil.getCurrentDataYMDHMS());
                                record.setIsStart(1);
                                record.setIsFinish(0);
                                record.setCompanyCode(companyList.get(position).getCompanyCode());
                                record.setCompanyName(companyList.get(position).getCompanyName());
                                record.save();
                                intent.putExtra("planId", record.getId());
                                intent.putExtra("startType", startType);//很重要
                                startActivity(intent);
                            }
                        });
            }
        });
    }


    @OnClick({R.id.tvEast, R.id.tvMiddle, R.id.tvSouth, R.id.tvOther})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvEast:
                setTvTextColor(tvEast);
                searchArea(CoalConstant.AREA_EAST);
                break;
            case R.id.tvMiddle:
                setTvTextColor(tvMiddle);
                searchArea(CoalConstant.AREA_MIDDLE);
                break;
            case R.id.tvSouth:
                setTvTextColor(tvSouth);
                searchArea(CoalConstant.AREA_SOUTH);
                break;
            case R.id.tvOther:
                setTvTextColor(tvOther);
                searchArea(CoalConstant.AREA_QIANJIANG);
                break;
        }
    }

    private void searchArea(int type) {
        companyList.clear();
        searchBar.setSearchHint("筛选: " + ZUtils.getAreaByType(type));
        companyList = DataSupport.where("companyLocation = ?", ZUtils.getAreaByType(type)).find(CompanyCoalEntity.class);
        initListView(companyList);
    }

    private void setTvTextColor(TextView tvChoosed) {
        tvEast.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        tvMiddle.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        tvSouth.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        tvOther.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        if (tvChoosed != null) {
            tvChoosed.setTextColor(ContextCompat.getColor(this, R.color.appMainColor));
        }
    }
}
