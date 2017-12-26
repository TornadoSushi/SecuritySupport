package com.infinity.jerry.securitysupport.coal_security.ui.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.BeBetterAdviceActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.CancelWorkOrderActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.CloseCoalActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.InspEvidenceActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.SceneDealActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.SceneInspActivity;
import com.infinity.jerry.securitysupport.coal_security.ui.activity.documents.TempCancelLicActivity;
import com.infinity.jerry.securitysupport.common.entity.CheckItemRecord;
import com.infinity.jerry.securitysupport.common.entity.PlanToDocEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.CoalDocUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/14.
 */

public class DocFragment extends Fragment {

    @BindView(R.id.tvAddDoc)
    TextView tvAddDoc;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tvShowDanger)
    TextView tvShowDanger;
    @BindView(R.id.coverFrame)
    RelativeLayout coverFrame;
    @BindView(R.id.listViewX)
    ListView listViewX;
    @BindView(R.id.tvCloseCover)
    TextView tvCloseCover;
    @BindView(R.id.ll_doc)
    LinearLayout llDoc;
    Unbinder unbinder;
    private View view;

    private long duration = 300;
    private int planId;
    private int companyCode;
    private String companyName;

    private String aiContent = "";
    private List<PlanToDocEntity> docList;
    private ZCommonAdapter<PlanToDocEntity> docAdapter;
    private List<CheckItemRecord> dangerList;
    private ZCommonAdapter<CheckItemRecord> itemAdapter;

    private static final String[] FILES = {"现场检查记录(新)", "调查取证笔录", "现场处理措施决定书(新)",
            "撤出作业人员命令书", "责令关闭矿井决定书", "暂扣或者吊销煤炭生产许可证决定书", "加强和改善安全监督管理建议书"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check_doc, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        companyCode = getArguments().getInt("companyCode");
        companyName = getArguments().getString("companyName");
        planId = getArguments().getInt("planId");
        initDocList();
        initDangerList();
        initListView();
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void initListView() {
        //初始化文书
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("companyName", companyName);
                intent.putExtra("companyCode", companyCode);
                intent.putExtra("planId", planId);
                switch (docList.get(position).getDocType()) {
                    case DocsConstant.SCENE_INSP:
                        intent.putExtra("ai_content", aiContent);
                        intent.setClass(DocFragment.this.getActivity(), SceneInspActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                    case DocsConstant.EVIDENCE_INSP:
                        intent.setClass(DocFragment.this.getActivity(), InspEvidenceActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    case DocsConstant.SCENE_DEAL:
                        intent.setClass(DocFragment.this.getActivity(), SceneDealActivity.class);
                        startActivityForResult(intent, 2);
                        break;
                    case DocsConstant.ORDER_CANCELWORK:
                        intent.setClass(DocFragment.this.getActivity(), CancelWorkOrderActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    case DocsConstant.CLOSE_COAL:
                        intent.setClass(DocFragment.this.getActivity(), CloseCoalActivity.class);
                        startActivityForResult(intent, 4);
                        break;
                    case DocsConstant.TEMP_CANCEL_LIC:
                        intent.setClass(DocFragment.this.getActivity(), TempCancelLicActivity.class);
                        startActivityForResult(intent, 5);
                        break;
                    case DocsConstant.BETTER_ADVICE:
                        intent.setClass(DocFragment.this.getActivity(), BeBetterAdviceActivity.class);
                        startActivityForResult(intent, 6);
                        break;
                }
            }
        });

    }

    private void ai_dangerList() {
        if (dangerList != null && dangerList.size() != 0) {
            aiContent = "经查 " + companyName + " 企业，现存在以下隐患";
            for (int i = 0; i < dangerList.size(); i++) {
                CheckItemRecord itemRecord = dangerList.get(i);
                aiContent += (i + 1) + "." + itemRecord.getSubItemName() + "," + itemRecord.getDangerDescri() + " ;";
                if (itemRecord.getSceneAdvice() != null && !itemRecord.getSceneAdvice().trim().equals("")) {
                    aiContent += "现给予 " + itemRecord.getSceneAdvice() + " 的整改建议 ;";
                }
                if (itemRecord.getSceneMoney() != null && !itemRecord.getSceneMoney().trim().equals("")) {
                    aiContent += "给予 " + itemRecord.getSceneMoney() + " 元罚款 ;";
                }
                if (itemRecord.getLowPunish() != 0) {
                    aiContent += "给予 " + (itemRecord.getLowPunish() == 1 ? "简易程序处罚" : "一般程序处罚") + " ;";
                }
                if (itemRecord.getLowForce() != null && !itemRecord.getLowForce().equals("")) {
                    aiContent += "给予 " + itemRecord.getLowForce() + " 的强制措施 ;";
                }
            }
        }else{
            aiContent = "经查 " + companyName + " 企业，所有检查项，全部通过";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tvAddDoc, R.id.tvShowDanger, R.id.coverFrame, R.id.tvCloseCover, R.id.ll_doc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAddDoc:
                showDocDialog();
                break;
            case R.id.tvShowDanger:
                startCondition();
                break;
            case R.id.coverFrame:
                endCondition();
                break;
            case R.id.tvCloseCover:
                endCondition();
                tvCloseCover.setEnabled(false);
                break;
            case R.id.ll_doc:

                break;
        }
    }

    private void showDocDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("文书类型选择")
                .setItems(FILES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("companyName", companyName);
                        intent.putExtra("companyCode", companyCode);
                        intent.putExtra("planId", planId);
                        switch (which) {
                            case 0://现场检查笔录(必选)
                                if (isGoesNext(DocsConstant.SCENE_INSP)) {
                                    intent.putExtra("ai_content", aiContent);
                                    intent.setClass(DocFragment.this.getActivity(), SceneInspActivity.class);
                                    startActivityForResult(intent, 0);
                                }
                                break;
                            case 1://调查取证笔录
                                if (isGoesNext(DocsConstant.EVIDENCE_INSP)) {
                                    intent.setClass(DocFragment.this.getActivity(), InspEvidenceActivity.class);
                                    startActivityForResult(intent, 1);
                                }
                                break;
                            case 2://现场处理决定书
                                if (isGoesNext(DocsConstant.SCENE_DEAL)) {
                                    intent.setClass(DocFragment.this.getActivity(), SceneDealActivity.class);
                                    startActivityForResult(intent, 2);
                                }
                                break;
                            case 3://撤出作业人员命令书
                                if (isGoesNext(DocsConstant.ORDER_CANCELWORK)) {
                                    intent.setClass(DocFragment.this.getActivity(), CancelWorkOrderActivity.class);
                                    startActivityForResult(intent, 3);
                                }
                                break;
                            case 4://责令关闭矿井决定书
                                if (isGoesNext(DocsConstant.CLOSE_COAL)) {
                                    intent.setClass(DocFragment.this.getActivity(), CloseCoalActivity.class);
                                    startActivityForResult(intent, 4);
                                }
                                break;
                            case 5://暂扣或者吊销煤炭生产许可证决定书
                                if (isGoesNext(DocsConstant.TEMP_CANCEL_LIC)) {
                                    intent.setClass(DocFragment.this.getActivity(), TempCancelLicActivity.class);
                                    startActivityForResult(intent, 5);
                                }
                                break;
                            case 6://加强和改善安全监督管理建议书
                                if (isGoesNext(DocsConstant.BETTER_ADVICE)) {
                                    intent.setClass(DocFragment.this.getActivity(), BeBetterAdviceActivity.class);
                                    startActivityForResult(intent, 6);
                                }
                                break;
                        }
                    }
                });
        dialog.create().show();
    }

    private boolean isGoesNext(int typeId) {
        for (PlanToDocEntity item : docList) {
            if (item.getDocType() == typeId) {
                Toasty.error(getActivity(), "您已经添加过该文书了").show();
                return false;
            }
        }
        return true;
    }

    private void startCondition() {
        coverFrame.animate().alpha(0.8f).setDuration(duration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                coverFrame.setEnabled(false);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                coverFrame.setEnabled(true);
                coverFrame.setVisibility(View.VISIBLE);

            }
        });
        llDoc.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.makeInAnimation(getActivity(), false);
        animation.setDuration(duration);
        llDoc.setAnimation(animation);
        coverFrame.setAlpha(0f);

    }

    private void endCondition() {
        coverFrame.animate().alpha(0f).setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        coverFrame.setEnabled(true);
                        coverFrame.setVisibility(View.GONE);
                        tvCloseCover.setEnabled(true);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        coverFrame.setEnabled(false);
                    }
                });
        Animation animation = AnimationUtils.makeOutAnimation(getActivity(), true);
        animation.setDuration(duration);
        llDoc.setAnimation(animation);
        llDoc.setVisibility(View.GONE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (listViewX != null) {
                initDangerList();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            initDocList();
        }
    }

    private void initDocList() {
        docList = DataSupport.where("planId = ? AND docBelong == 0", String.valueOf(planId)).find(PlanToDocEntity.class);
        docAdapter = new ZCommonAdapter<PlanToDocEntity>(this.getActivity(), docList, R.layout.item_single_text) {
            @Override
            public void convert(ZViewHolder holder, PlanToDocEntity item, int position) {
                TextView tvName = holder.getView(R.id.tvName);
                tvName.setText(CoalDocUtils.getDocNameByType(item.getDocType()));
            }
        };
        listView.setAdapter(docAdapter);
    }

    private void initDangerList() {
        dangerList = DataSupport.where("planId = ? AND checkResult > 0", String.valueOf(planId)).find(CheckItemRecord.class);
        itemAdapter = new ZCommonAdapter<CheckItemRecord>(this.getActivity(), dangerList, R.layout.item_danger_list) {
            @Override
            public void convert(ZViewHolder holder, CheckItemRecord item, int position) {
                TextView tvSub = holder.getView(R.id.tvSub);
                tvSub.setText(item.getSubItemName());
                TextView tvLevel = holder.getView(R.id.tvLevel);
                tvLevel.setText(item.getCheckResult() == 1 ? "一般隐患" : "重大隐患");
                TextView tvTime = holder.getView(R.id.tvTime);
                tvTime.setText(item.getExcuteTime());
                TextView tvSuper = holder.getView(R.id.tvSuper);
                tvSuper.setText(item.getSuperItemName());
                TextView tvDescri = holder.getView(R.id.tvDescri);
                tvDescri.setText(item.getDangerDescri());
            }
        };
        listViewX.setAdapter(itemAdapter);
        ai_dangerList();//刷新隐患列表
    }

}
