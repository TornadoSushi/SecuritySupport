package com.infinity.jerry.securitysupport.common.otherstuff.weihua;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;

import java.util.HashMap;
import java.util.List;

/**
 */
public class ChemicalFeatureActivity extends TitlebarActivity implements View.OnClickListener {

    private HashMap<Integer, String> lhParams;
    private HashMap<Integer, String> jkParams;

    @Override
    protected String centerTitle() {
        return "危化品查询";
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
        lhParams = new HashMap<>();
        jkParams = new HashMap<>();

        View view = LayoutInflater.from(this).inflate(R.layout.activity_chemical_feature, null);
        GridView lhGV = (GridView) view.findViewById(R.id.chemicalFeatureLHGV);
        FeatureAdapter lhAdapter = new FeatureAdapter(ChemicalFeatureHelper.LH);
        lhGV.setAdapter(lhAdapter);
        lhGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final List<String> features = ChemicalFeatureHelper.getFeatures(true, position);
                showFeatureDialog(features, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tv = (TextView) view;
                        if (which != features.size()) {
                            tv.setBackgroundResource(R.drawable.main_btn_background);
                            tv.setText(features.get(which));
                            lhParams.put(position, features.get(which));
                        } else {
                            tv.setBackgroundResource(R.drawable.main_btn_background);
                            tv.setText(ChemicalFeatureHelper.LH[position]);
                            lhParams.remove(position);
                        }
                    }
                });
            }
        });

        GridView jkGV = (GridView) view.findViewById(R.id.chemicalFeatureJKGV);
        FeatureAdapter jkAdapter = new FeatureAdapter(ChemicalFeatureHelper.JK);
        jkGV.setAdapter(jkAdapter);
        jkGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final List<String> features = ChemicalFeatureHelper.getFeatures(false, position);
                showFeatureDialog(features, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tv = (TextView) view;
                        if (which != features.size()) {
                            tv.setBackgroundResource(R.drawable.main_btn_background);
                            tv.setText(features.get(which));
                            jkParams.put(position, features.get(which));
                        } else {
                            tv.setBackgroundResource(R.drawable.main_btn_background);
                            tv.setText(ChemicalFeatureHelper.JK[position]);
                            jkParams.remove(position);
                        }
                    }
                });
            }
        });

        TextView searchTV = (TextView) view.findViewById(R.id.chemicalFeatureSearchTV);
        searchTV.setOnClickListener(this);
        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {
    }

    @Override
    public void onClick(View v) {
        ChemicalSearchResultActivity.launchActivity(this,
                ChemicalFeatureHelper.generateChemicalQuerySql(lhParams, jkParams));
    }

    private class FeatureAdapter extends BaseAdapter {

        private String[] items;

        FeatureAdapter(String[] items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.view_feature_btn, parent, false);
            }
            ((TextView) convertView).setText(items[position]);
            return convertView;
        }
    }

    private void showFeatureDialog(final List<String> features, final DialogInterface.OnClickListener listener) {
        String[] names = new String[features.size() + 1];
        for (int i = 0; i != features.size(); ++i) {
            names[i] = features.get(i);
        }
        names[features.size()] = "清空";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择特性")
                .setItems(names, listener);
        builder.create().show();
    }
}
