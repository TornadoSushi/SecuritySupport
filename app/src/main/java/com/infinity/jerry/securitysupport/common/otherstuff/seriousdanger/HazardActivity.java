package com.infinity.jerry.securitysupport.common.otherstuff.seriousdanger;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.WindowUtils;

import java.util.ArrayList;

public class HazardActivity extends TitlebarActivity {

    private ListView mHazardLV;
    private EditText mHazardET;
    private HazardRowAdapter mAdapter;

    private HazardDanger mItem;

    @Override
    protected String centerTitle() {
        return "危险源辨识分级";
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
        View view = getInflateView(R.layout.activity_hazard);
        mHazardLV = (ListView) view.findViewById(R.id.hazardItemsLV);
        mHazardET = (EditText) view.findViewById(R.id.hazardEntranceET);
        mAdapter = new HazardRowAdapter();
        mHazardLV.setAdapter(mAdapter);

        mHazardLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItem = (HazardDanger) mAdapter.getItem(position);
                HazardChooseActivity.launchActivity(HazardActivity.this, mItem);
            }
        });
        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {
        TextView tv = new TextView(this);
        tv.setText("清空");
        tv.setBackgroundResource(R.drawable.main_btn_background);
        int horizonPadding = WindowUtils.dip2px(this, 16);
        int verticalPadding = WindowUtils.dip2px(this, 8);
        tv.setPadding(horizonPadding, verticalPadding, horizonPadding, verticalPadding);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, WindowUtils.sp2px(this, 16));
        ll.addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clearItems();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onAddHazardClick(View view) {
        mItem = new HazardDanger();
        mItem.isAddSuccessed = false;
        mItem.isDeleteSuccessed = false;
        mItem.index = mAdapter.getCount();
        mAdapter.addItem(mItem);
        HazardChooseActivity.launchActivity(this, mItem);
    }

    public void onHazardCalculateClick(View view) {
        int count = mAdapter.getCount();
        String toastMessage = "";
        if (count > 0) {
            float alpha = getAlpha();
            float value = 0.0f;
            for (int i = 0; i != count; ++i) {
                HazardDanger item = (HazardDanger) mAdapter.getItem(i);
                value += Float.valueOf(item.beta) * Float.valueOf(mItem.count) / Float.valueOf(mItem.threhold);
            }
            value *= alpha;
            String level = "四";
            if (value >= 100) {
                level = "一";
            } else if (value >= 50 && value < 100) {
                level = "二";
            } else if (value >= 10 && value < 50) {
                level = "三";
            }
            toastMessage = "危险源级别：" + level + "级重大危险源";
        } else {
            toastMessage = "请先添加危险化学品";
        }
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    private float getAlpha() {
        float alpha = 0.5f;
        String peopleText = mHazardET.getText().toString();
        peopleText = peopleText.trim();
        int people = 0;
        if (!peopleText.isEmpty()) {
            people = Integer.valueOf(peopleText);
        }

        if (people >= 100) {
            alpha = 2.0f;
        } else if (people >= 50 && people < 100) {
            alpha = 1.5f;
        } else if (people >= 30 && people < 50) {
            alpha = 1.2f;
        } else if (people >= 1 && people < 30) {
            alpha = 1.0f;
        }
        return alpha;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mItem) {
            return;
        }
        if (!mItem.isAddSuccessed || mItem.isDeleteSuccessed) {
            mAdapter.removeItem(mItem);
        }
        mAdapter.notifyDataSetChanged();
    }

    private class HazardRowAdapter extends BaseAdapter {

        private ArrayList<HazardDanger> items;

        public HazardRowAdapter() {
            items = new ArrayList<>();
        }

        public void addItem(HazardDanger item) {
            items.add(item);
        }

        public void removeAt(int index) {
            items.remove(index);
        }

        public void removeItem(HazardDanger item) {
            items.remove(item);
        }

        public void clearItems() {
            items.clear();
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
                convertView = getLayoutInflater().inflate(R.layout.view_hazard_actionrow, parent, false);
                holder.nameTV = (TextView) convertView.findViewById(R.id.hazardRowNameTV);
                holder.countTV = (TextView) convertView.findViewById(R.id.hazardRowCountTV);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HazardDanger item = items.get(position);
            if (null != item) {
                holder.nameTV.setText(item.name);
                holder.countTV.setText(item.count + "(吨)");
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView nameTV;
            private TextView countTV;
        }
    }
}
