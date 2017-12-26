package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.company.CompanyListActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.company.CompanyListActivityX;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case.LowMainActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.plans.PlanActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.review.ReviewActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_check.CheckSynActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_lawcase.LawCaseActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_review.ReviewSynActivity;
import com.infinity.jerry.securitysupport.common.entity.LowCaseRecord;
import com.infinity.jerry.securitysupport.common.entity.PlanRecord;
import com.infinity.jerry.securitysupport.common.entity.ReviewRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZMesuredListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/11/1.
 */

public class MainLeftFragment extends Fragment {

    private View view;

    private TextView tvSyn1;
    private TextView tvSyn2;
    private TextView tvSyn3;

    private LinearLayout llCheck;
    private LinearLayout llReview;
    private LinearLayout llCase;

    private ScrollView scrollView;

    private ZMesuredListView listView;

    private List<String> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_main, null);
        llCheck = view.findViewById(R.id.llSynCheck);
        llReview = view.findViewById(R.id.llSynReview);
        llCase = view.findViewById(R.id.llSynLaw);
        tvSyn1 = view.findViewById(R.id.tvSyn1);
        tvSyn2 = view.findViewById(R.id.tvSyn2);
        tvSyn3 = view.findViewById(R.id.tvSyn3);
        scrollView = view.findViewById(R.id.scrollView);
        listView = view.findViewById(R.id.listView);
        dataList = new ArrayList<>();
        dataList.add("煤炭企业管理");
        dataList.add("执法计划");
        dataList.add("安全检查");
        dataList.add("职业卫生检查");
        dataList.add("整改复查");
        dataList.add("案件办理");
        listView.setAdapter(new ZCommonAdapter<String>(getActivity(), dataList, R.layout.item_main_list) {
            @Override
            public void convert(ZViewHolder holder, String item, int position) {
                ImageView imageView = holder.getView(R.id.imageView);
                TextView tvDes = holder.getView(R.id.tvDes);
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.company);
                        tvDes.setText("查询煤炭公司详细信息");
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.plan);
                        tvDes.setText("管理、查询或添加执法计划");
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.security);
                        tvDes.setText("进行一次临时的安全检查");
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.health);
                        tvDes.setText("进行一次临时的卫生检查计划");
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.review);
                        tvDes.setText("对隐患项进行复查");
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.casehandle);
                        tvDes.setText("查询执法进度、企业执法管理");
                        break;
                }
                TextView tvContent = holder.getView(R.id.tvContent);
                tvContent.setText(item);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainLeftFragment.this.getActivity(), CompanyListActivity.class);
                        break;
                    case 1:
                        intent.setClass(MainLeftFragment.this.getActivity(), PlanActivity.class);
                        break;
                    case 2:
                        intent.putExtra("startType", 2);//安全检查
                        intent.setClass(MainLeftFragment.this.getActivity(), CompanyListActivityX.class);
                        break;
                    case 3:
                        intent.putExtra("startType", 3);//卫生检查
                        intent.setClass(MainLeftFragment.this.getActivity(), CompanyListActivityX.class);
                        break;
                    case 4:
                        intent.setClass(MainLeftFragment.this.getActivity(), ReviewActivity.class);
                        break;
                    case 5:
                        intent.setClass(MainLeftFragment.this.getActivity(), LowMainActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        int planCount = DataSupport.where("isSyn != 1 OR isSyn ISNULL AND isFinish = 1").count(PlanRecord.class);
        int reviewCount = DataSupport.where("isSyn != 1 OR isSyn ISNULL").count(ReviewRecord.class);
        int caseCount = DataSupport.where("isSyn != 1 OR isSyn ISNULL").count(LowCaseRecord.class);
        tvSyn1.setText(String.valueOf(planCount));
        tvSyn2.setText(String.valueOf(reviewCount));
        tvSyn3.setText(String.valueOf(caseCount));

        llCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLeftFragment.this.getActivity(), CheckSynActivity.class);
                startActivity(intent);
            }
        });

        llReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLeftFragment.this.getActivity(), ReviewSynActivity.class);
                startActivity(intent);
            }
        });

        llCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLeftFragment.this.getActivity(), LawCaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
