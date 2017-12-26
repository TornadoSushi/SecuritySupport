package com.infinity.jerry.securitysupport.common.otherstuff.accident;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.AccidentDataController;

import java.util.ArrayList;
import java.util.List;

public class AccidentActivity extends TitlebarActivity {

    private static final int[] ICON_RES = {R.drawable.acc_coack,
            R.drawable.acc_danger, R.drawable.acc_boom,
            R.drawable.acc_mont, R.drawable.acc_construct};

    @Override
    protected String centerTitle() {
        return "危险事故案例及处理";
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
        GridView gv = (GridView) getInflateView(R.layout.activity_accident_grid);
        List<AccidentCategory> categories = AccidentDataController.getController().db_getAllCategory();
        final AccidentCategoryAdapter adapter = new AccidentCategoryAdapter(categories);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccidentCategory item = (AccidentCategory) adapter.getItem(position);
                Intent intent = new Intent(AccidentActivity.this, AccidentListActivity.class);
                intent.putExtra("SecurityLawAccidentId", item.id);
                intent.putExtra("SecurityLawAccidentName", item.name);
                startActivity(intent);
            }
        });
        return gv;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    private class AccidentCategoryAdapter extends BaseAdapter {

        private List<AccidentCategory> items = new ArrayList<>();

        public AccidentCategoryAdapter(List<AccidentCategory> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(AccidentActivity.this).inflate(R.layout.view_home_item_display, parent, false);
                holder.iconIV = (ImageView) convertView.findViewById(R.id.homeIconIV);
                holder.titleTV = (TextView) convertView.findViewById(R.id.homeTitleTV);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AccidentCategory item = items.get(position);
            if (null != item) {
                holder.iconIV.setImageResource(ICON_RES[item.id - 1]);
                holder.titleTV.setText(item.name);
            }
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView iconIV;
        TextView titleTV;
    }
}
