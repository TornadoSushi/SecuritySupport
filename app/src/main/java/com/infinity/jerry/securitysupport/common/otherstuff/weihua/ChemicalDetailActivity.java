package com.infinity.jerry.securitysupport.common.otherstuff.weihua;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.Chemical;
import com.infinity.jerry.securitysupport.common.otherstuff.ExpandableRowView;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.ChemicalDataController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.IDBResult;

import java.util.ArrayList;
import java.util.List;

public class ChemicalDetailActivity extends TitlebarActivity {

    private Chemical mItem;

    private static final String[] LHTZ = {"相对气度(空气=1)", "透明度", "临界温度", "爆炸上限",
            "ph值", "气味", "自然温度", "临界压力", "沸点", "温度", "比重", "闪点",
            "相对密度(水=1)", "熔点", "外形与形状", "蒸汽幅度", "爆炸下限", "味道", "颜色", "状态", "燃烧热"};
    private static final String[] WXX = {"健康危害", "危险源类别"};
    private static final String[] JJCS = {"食入", "皮肤接触", "眼睛接触", "吸入"};
    private static final String[] XFCS = {"危险特性", "灭火方法"};
    private static final String[] XLCL = {"泄露处理"};
    private static final String[] CZSZ = {"储运注意事项"};
    private static final String[] JCKZ = {"其他", "眼睛防护", "身体防护", "手防护", "健康接触限值", "工程控制", "呼吸系统防护"};
    private static final String[] WDX = {"稳定性和反应活性"};
    private static final String[] DLX = {"rtecs编号", "毒性"};
    private static final String[] YSXX = {"包装类别", "un编号", "iso", "危险货物包装标志", "危险货物编号"};
    private static final String[] CLFA = {"处理方案"};

    private static final String KEY = "ChemicalDetailActivity";
    static SmallActivityCache<Integer> sStoryHashMap = new SmallActivityCache<Integer>();

    public static final String CHEMICAL_DETAIL_NAMES_ARRAY = "sl_chemical_detail_names_array";
    public static final String CHEMICAL_DETAIL_CONTENTS_LIST = "sl_chemical_detail_contents_list";

    public static void launchActivity(Context context, int itemId) {
        Long key = sStoryHashMap.put(itemId);

        Intent intent = new Intent(context, ChemicalDetailActivity.class);
        intent.putExtra(ChemicalDetailActivity.KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        return "危化品详情";
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
    protected View onCreateView() {
        int id = -1;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            id = sStoryHashMap.get(extras.getLong(KEY));
        }
        if (-1 == id) {
            finish();
            return null;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.activity_chemical_detail, null);
        final TextView ctv = (TextView) view.findViewById(R.id.ChemicalDetailCNameTV);
        final TextView etv = (TextView) view.findViewById(R.id.ChemicalDetailENameTV);
        final TextView castv = (TextView) view.findViewById(R.id.ChemicalDetailCASTV);
        final TextView fzstv = (TextView) view.findViewById(R.id.ChemicalDetailFZSTV);
        final ChemicalExpandableRowView lhView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailLHRV);
        final ChemicalExpandableRowView wxView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailWXRV);
        final ChemicalExpandableRowView jjView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailJJRV);
        final ChemicalExpandableRowView xfView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailXFRV);
        final ChemicalExpandableRowView xlView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailXLRV);
        final ChemicalExpandableRowView czView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailCZRV);
        final ChemicalExpandableRowView jzView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailJCRV);
        final ChemicalExpandableRowView wdfyView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailWDFYRV);
        final ChemicalExpandableRowView dlView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailDLRV);
        final ChemicalExpandableRowView ysView = (ChemicalExpandableRowView) view.findViewById(R.id.chemicalDetailYSRV);

        showLoading();
        ChemicalDataController.getController().db_getSearchedChemicals("id =" + "'" + id + "'", new IDBResult.Callback<List<Chemical>>() {
            @Override
            public void onResultCallback(List<Chemical> chemicals) {
                if (null == chemicals || chemicals.isEmpty()) {
                    finish();
                } else {
                    mItem = chemicals.get(0);
                    ctv.setText(mItem.cName);
                    etv.setText(mItem.eName);
                    castv.setText(mItem.casno);
                    fzstv.setText(mItem.fzs);
                    submitWorker(new Runnable() {
                        @Override
                        public void run() {
                            addLH(lhView);
                            addWX(wxView);
                            addJJ(jjView);
                            addXF(xfView);
                            addXL(xlView);
                            addCZ(czView);
                            addJC(jzView);
                            addWDFY(wdfyView);
                            addDL(dlView);
                            addYS(ysView);
                        }
                    });
                    hideLoading();
                }
            }
        });
        return view;
    }

    private void addLH(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.mdair);
        lh.add(mItem.clarity);
        lh.add(mItem.ljwd);
        lh.add(mItem.bzsx);
        lh.add(mItem.ph);
        lh.add(mItem.odor);
        lh.add(mItem.zrwd);
        lh.add(mItem.ljyl);
        lh.add(mItem.fd);
        lh.add(mItem.zqywd);
        lh.add(mItem.bizhong);
        lh.add(mItem.sd);
        lh.add(mItem.mdwater);
        lh.add(mItem.rd);
        lh.add(mItem.wgxz);
        lh.add(mItem.zqmd);
        lh.add(mItem.bzxx);
        lh.add(mItem.taste);
        lh.add(mItem.color);
        lh.add(mItem.state);
        lh.add(mItem.rsr);

        setViewResource(rowView, "理化特征", LHTZ, lh);
    }

    private void addWX(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.jkwh);
        lh.add(mItem.wwxlb);
        setViewResource(rowView, "危险性描述", WXX, lh);
    }

    private void addJJ(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.sr);
        lh.add(mItem.pfjc);
        lh.add(mItem.yjjc);
        lh.add(mItem.xr);
        setViewResource(rowView, "急救措施", JJCS, lh);
    }

    private void addXF(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.wxtx);
        lh.add(mItem.mhff);
        setViewResource(rowView, "消防措施", XFCS, lh);
    }

    private void addXL(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.xlcl);
        setViewResource(rowView, "泄露应急处理", XLCL, lh);
    }

    private void addCZ(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.cyzysx);
        setViewResource(rowView, "操作设置", CZSZ, lh);
    }

    private void addJC(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.qt);
        lh.add(mItem.yjfh);
        lh.add(mItem.stfh);
        lh.add(mItem.sfh);
        lh.add(mItem.jcxzz);
        lh.add(mItem.gckz);
        lh.add(mItem.hxxtfh);
        setViewResource(rowView, "接触控制", JCKZ, lh);
    }

    private void addWDFY(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.wdx);
        setViewResource(rowView, "稳定性和反应活性", WDX, lh);
    }

    private void addDL(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.rtecs);
        lh.add(mItem.dux);
        setViewResource(rowView, "毒理学资料", DLX, lh);
    }

    private void addYS(final ExpandableRowView rowView) {
        final ArrayList<String> lh = new ArrayList<String>();
        lh.add(mItem.bzlb);
        lh.add(mItem.un);
        lh.add(mItem.iso);
        lh.add(mItem.wxhwbzbz);
        lh.add(mItem.wxhwbh);
        setViewResource(rowView, "运输信息", YSXX, lh);
    }

    private void setViewResource(final ExpandableRowView rowView, final String title,
                                 final String[] names, final ArrayList<String> lh) {
        postRunnable(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra(CHEMICAL_DETAIL_NAMES_ARRAY, names);
                intent.putStringArrayListExtra(CHEMICAL_DETAIL_CONTENTS_LIST, lh);
                rowView.initResources(title, intent, lh.size());
            }
        });
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }
}
