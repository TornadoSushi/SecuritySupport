package com.infinity.jerry.securitysupport.common.otherstuff.weihua;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.ExpanableRowBuilder;
import com.infinity.jerry.securitysupport.common.otherstuff.ExpandableRowView;

import java.util.ArrayList;

/**
 * Created by edwardliu on 15/12/30.
 */
public class ChemicalExpandableRowView extends ExpandableRowView {
    public ChemicalExpandableRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ExpanableRowBuilder builder() {
        return new ChemicalRowViewBuilder();
    }

    private class ChemicalRowViewBuilder implements ExpanableRowBuilder {

        private String[] names;
        private ArrayList<String> contents;

        @Override
        public int mainViewResourceId() {
            return R.layout.view_expanable_titleview;
        }

        @Override
        public void onTitleViewCreated(View view) {
        }

        @Override
        public int titleViewResourceId() {
            return R.id.expanableTitleTV;
        }

        @Override
        public int expandImageResourceId() {
            return R.id.expanableTitleIV;
        }

        @Override
        public void setChildCount(int count) {

        }

        @Override
        public View createExpandChildView(Intent data, int index) {
            if (null == contents) {
                names = data.getStringArrayExtra(ChemicalDetailActivity.CHEMICAL_DETAIL_NAMES_ARRAY);
                contents = data.getStringArrayListExtra(ChemicalDetailActivity.CHEMICAL_DETAIL_CONTENTS_LIST);
            }
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_chemical_infoview, null);
            ((TextView) view.findViewById(R.id.chemicalInfoTV)).setText(names[index]);
            ((TextView) view.findViewById(R.id.chemicalInfoVTV)).setText(contents.get(index));
            return view;
        }

        @Override
        public void onChildViewsCreated() {
        }

        @Override
        public void recycle() {
        }
    }
}
