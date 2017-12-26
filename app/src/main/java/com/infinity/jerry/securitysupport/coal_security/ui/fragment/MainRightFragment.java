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
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.accident.AccidentActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.equipment.EquipmentActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.FireDistanceActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.law_rule.LawRuleActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.seriousdanger.HazardActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.standard.StandardActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.weihua.ChemicalsActivity;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZCommonAdapter;
import com.infinity.jerry.securitysupport.common.z_utils.z_adapter.ZViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/11/1.
 */

public class MainRightFragment extends Fragment {

    private View view;

    private ListView listView;

    private List<String> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_right_main, null);
        listView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    public void onResume() {
        super.onResume();
        dataList = new ArrayList<>();
        dataList.add("法规检索");
        dataList.add("标准规范检索");
        dataList.add("防火间距查询");
        dataList.add("危化品查询");
        dataList.add("重大危险源辨别");
        dataList.add("危险事故案例及处理");
        dataList.add("装备使用");
//        dataList.add("安全检查助手");
        initListView();
    }

    private void initListView() {
        listView.setAdapter(new ZCommonAdapter<String>(getActivity(), dataList, R.layout.item_main_list) {
            @Override
            public void convert(ZViewHolder holder, String item, int position) {
                ImageView imageView = holder.getView(R.id.imageView);
                TextView tvDes = holder.getView(R.id.tvDes);
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.ruleoflaw);
                        tvDes.setText("检索标准法律法规库");
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.standard);
                        tvDes.setText("检索标准规范库");
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.firedistance);
                        tvDes.setText("查询各项设备放火距离");
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.chemicals);
                        tvDes.setText("查询化学品,作用、危害、使用");
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.majorhazard);
                        tvDes.setText("辨别危险源手册");
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.accident);
                        tvDes.setText("查询往期事故及解决方式");
                        break;
                    case 6:
                        imageView.setImageResource(R.drawable.equipment);
                        tvDes.setText("各项装备使用指南");
                        break;
                    case 7:
                        imageView.setImageResource(R.drawable.securityassitant);
                        tvDes.setText("煤炭安全检查项目展示");
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
                        intent.setClass(MainRightFragment.this.getActivity(), LawRuleActivity.class);
                        break;
                    case 1:
                        intent.setClass(MainRightFragment.this.getActivity(), StandardActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainRightFragment.this.getActivity(), FireDistanceActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainRightFragment.this.getActivity(), ChemicalsActivity.class);
                        break;
                    case 4:
                        intent.setClass(MainRightFragment.this.getActivity(), HazardActivity.class);
                        break;
                    case 5:
                        intent.setClass(MainRightFragment.this.getActivity(), AccidentActivity.class);
                        break;
                    case 6:
                        intent.setClass(MainRightFragment.this.getActivity(), EquipmentActivity.class);
                        break;

                }
                startActivity(intent);
            }
        });
    }

}
