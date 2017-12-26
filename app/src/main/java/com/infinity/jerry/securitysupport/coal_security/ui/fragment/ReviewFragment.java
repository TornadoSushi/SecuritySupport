package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.review.ReviewDealActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by jerry on 2017/11/24.
 */

public class ReviewFragment extends Fragment {

    private View view;

    private ListView listView;

    private int companyCode;

    private List<CheckItemRecord> recordList;
    private ZCommonAdapter<CheckItemRecord> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_review_list, null);
        listView = (ListView) view.findViewById(R.id.listView);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        initListView();
    }

    private final int REQUEST_REVIEW = 3;

    private void initListView() {
        recordList = DataSupport.where("checkResult > 0 AND companyCode = ? AND isReview = 0" , String.valueOf(companyCode)).find(CheckItemRecord.class);
        adapter = new ZCommonAdapter<CheckItemRecord>(getActivity(), recordList, R.layout.item_review_details) {
            @Override
            public void convert(ZViewHolder holder, CheckItemRecord item, int position) {
                TextView tvSuperName = holder.getView(R.id.superName);
                tvSuperName.setText("检查类别 " + ZUtils.getSuperItem(item.getSuperItemId()));
                TextView tvSubName = holder.getView(R.id.subName);
                tvSubName.setText("检查项 " + item.getSubItemName());
                TextView tvLevel = holder.getView(R.id.level);
                tvLevel.setText("隐患级别 " + (item.getCheckResult() == 1 ? "一般隐患" : "重大隐患"));
                TextView tvDescri = holder.getView(R.id.desri);
                tvDescri.setText("隐患描述 " + (item.getDangerDescri() == null ? "" : item.getDangerDescri()));
                TextView tvLow = holder.getView(R.id.lowPunish);
                tvLow.setText("行政处罚 " + (item.getLowPunish() == 0 ? "无" : item.getLowPunish() == 1 ? "简易程序" : "一般程序"));
                TextView tvPerson = holder.getView(R.id.person);
                tvPerson.setText("执行人 " + item.getExcutePerson()==null?"":item.getExcutePerson());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckItemRecord checkItemRecord = recordList.get(position);
                Intent intent = new Intent(ReviewFragment.this.getActivity(), ReviewDealActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recordEntity", checkItemRecord);
                intent.putExtra("recordBundle", bundle);
                startActivityForResult(intent, REQUEST_REVIEW);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        initListView();
    }
}
