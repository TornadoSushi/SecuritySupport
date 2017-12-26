package com.infinity.jerry.securitysupport.common.otherstuff.accident;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.AccidentDataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwardliu on 16/2/23.
 */
public class AccidentListActivity extends TitlebarActivity {

    @Override
    protected String centerTitle() {
        return "";
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
        int parentId = getIntent().getIntExtra("SecurityLawAccidentId", -1);
        String name = getIntent().getStringExtra("SecurityLawAccidentName");
        if (-1 == parentId) {
            finish();
            return null;
        }
        mTitleTV.setText(name);
        ListView lv = (ListView) getInflateView(R.layout.activity_common_list);
        final List<AccidentDetail> items = AccidentDataController.getController().db_getAccidentsWithId(parentId);
        if (null != items) {
            AccidentAdapter adapter = new AccidentAdapter(items);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AccidentDetailActivity.launchActivity(AccidentListActivity.this, items.get(position));
                }
            });
        }
        return lv;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    private class AccidentAdapter extends BaseAdapter {

        private List<AccidentDetail> items = new ArrayList<>();

        public AccidentAdapter(List<AccidentDetail> items) {
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
                convertView = LayoutInflater.from(AccidentListActivity.this).inflate(R.layout.view_string_actionrow, parent, false);
                holder.nameTV = (TextView) convertView.findViewById(R.id.actionRowNameTV);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AccidentDetail item = items.get(position);
            if (null != item) {
                holder.nameTV.setText(item.name);
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView nameTV;
        }
    }
}
