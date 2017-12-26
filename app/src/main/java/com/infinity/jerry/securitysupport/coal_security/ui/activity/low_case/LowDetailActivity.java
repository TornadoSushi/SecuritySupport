package com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case.casehelper.CaseStepHelper;
import com.infinity.jerry.securitysupport.common.base.BaseActivity;
import com.infinity.jerry.securitysupport.common.entity.CaseDocHelper;
import com.infinity.jerry.securitysupport.common.entity.LowCaseMedia;
import com.infinity.jerry.securitysupport.common.entity.LowCaseRecord;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.CoalDocUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZDialogUtils;
import com.infinity.jerry.securitysupport.common.z_utils.z_widget.ZTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jerry on 2017/11/27.
 */

public class LowDetailActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    ZTitleBar titleBar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.addDoc)
    TextView tvAddDoc;
    @BindView(R.id.edDescri)
    EditText edDescri;

    private int position;

    private String titleName;

    private ZCommonAdapter<CaseDocHelper> adapter;

    private List<CaseDocHelper> docHelperList;

    private List<CaseStepHelper.DocsSurface> docsSurfaces;

    private LowCaseRecord record;

    @Override
    public int getLayoutId() {
        return R.layout.activity_low_details;
    }

    @Override
    public void initData() {
        record = (LowCaseRecord) getIntent().getBundleExtra("recordBundle").getSerializable("recordEntity");
        position = getIntent().getIntExtra("position", -256);
        titleName = getIntent().getStringExtra("title");
        docsSurfaces = CaseStepHelper.getListData().get(position).getDocsSurfaces();
        docHelperList = new ArrayList<>();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        titleBar.setTitle(titleName);
        tvAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nameList = new String[docsSurfaces.size()];
                for (int i = 0; i < docsSurfaces.size(); i++) {
                    nameList[i] = docsSurfaces.get(i).getDocName() + " " + (docsSurfaces.get(i).isHasTo() ? "必选" : "可选");
                }
                ZDialogUtils.showDialog2(LowDetailActivity.this, nameList, "文书选择", new CallBack1() {
                    @Override
                    public void callBack(Object object) {
                        int which = (int) object;
                        CaseStepHelper.DocsSurface item = docsSurfaces.get(which);
                        CaseDocHelper helper = new CaseDocHelper();
                        helper.setDocName(item.getDocName());
                        helper.setDocType(item.getDocType());
                        docHelperList.add(helper);//加入数据
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        titleBar.setTitleMode(ZTitleBar.Companion.getMODE_TEXT());
        titleBar.setTvPlusText("完成");
        titleBar.setOnTextModeListener(new ZTitleBar.OnTextModeListener() {
            @Override
            public void onClickTextMode() {
                ZDialogUtils.showDialog(LowDetailActivity.this, "您确定要保存此项操作", new CallBack0() {
                    @Override
                    public void callBack() {//完成
                        for (CaseDocHelper entity : docHelperList) {
                            LowCaseMedia item = new LowCaseMedia();
                            item.setCaseId(record.getId());
                            item.setDescri(edDescri.getText().toString());
                            item.setStepId(position);
                            item.setDocType(entity.getDocType());
                            item.save();
                        }
                        Intent intent = new Intent();
                        intent.putExtra("caseStepPosition", position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });
        adapter = new ZCommonAdapter<CaseDocHelper>(this, docHelperList, R.layout.item_single_text) {
            @Override
            public void convert(ZViewHolder holder, CaseDocHelper item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(item.getDocName());
                TextView tvPlus = holder.getView(R.id.tvPlus);
                tvPlus.setVisibility(View.VISIBLE);
                tvPlus.setText("查看");
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CaseDocHelper helper = docHelperList.get(position);
                String[] docNameAndFileByType = CoalDocUtils.getDocNameAndFileByType(helper.getDocType());
                CoalDocUtils.entryPrinterShare(LowDetailActivity.this, docNameAndFileByType[0], docNameAndFileByType[1], new HashMap<String, String>(), new CallBack1() {
                    @Override
                    public void callBack(Object object) {
                        Intent intent = (Intent) object;
                        startActivity(intent);
                    }
                });
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ZDialogUtils.showDialog(LowDetailActivity.this, "您确定要删除这个文书吗?", new CallBack0() {
                    @Override
                    public void callBack() {
                        docHelperList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
    }

}
