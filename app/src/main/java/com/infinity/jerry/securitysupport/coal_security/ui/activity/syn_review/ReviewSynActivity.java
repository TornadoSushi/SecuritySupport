package com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_review;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.syn_check.CheckSynHistoryActvitiy;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.ReviewRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jerry on 2017/12/12.
 */

public class ReviewSynActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ListView listView;

    private List<ReviewRecord> recordList;
    private ZCommonAdapter<ReviewRecord> adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_syn_check_1;
    }

    @Override
    public void initData() {
        recordList = DataSupport.where("isSyn != 1 OR isSyn ISNULL").find(ReviewRecord.class);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle("复查同步");
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
//        titleBar.setTvPlusText("同步记录");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                Intent intent = new Intent(ReviewSynActivity.this, CheckSynHistoryActvitiy.class);
                startActivity(intent);
            }
        });
        adapter = new ZCommonAdapter<ReviewRecord>(this, recordList, R.layout.item_syn_review) {
            @Override
            public void convert(ZViewHolder holder, ReviewRecord item, int position) {

                CheckItemRecord entity = DataSupport.where("planId = ? AND subItemId = ?", String.valueOf(item.getPlanId()), String.valueOf(item.getSubItemId())).findLast(CheckItemRecord.class);

                TextView tvCompany = holder.getView(R.id.tvCompany);
                tvCompany.setText(entity.getCompanyName());
                TextView tvExcutePerson = holder.getView(R.id.tvExcutePerson);
                tvExcutePerson.setText(entity.getExcutePerson());

                TextView tvSubItem = holder.getView(R.id.tvSubItem);
                tvSubItem.setText("复查项: " + entity.getSubItemName());

                TextView tvDescri = holder.getView(R.id.tvDescri);
                tvDescri.setText("隐患描述: " + (entity.getDangerDescri() == null ? "" : entity.getDangerDescri()));

                TextView tvAdvice = holder.getView(R.id.tvAdvice);
                tvAdvice.setText("复查建议: " + (item.getAdvice() == null ? "" : item.getAdvice()));

                TextView tvTime = holder.getView(R.id.tvTime);
                tvTime.setText("复查时间: " + item.getExcuteTime());

            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }
}
