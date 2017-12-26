package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by edwardliu on 15/11/28.
 */
public class StringActionRowAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> items;

    public StringActionRowAdapter(Context context) {
        mContext = context;
        items = new ArrayList<>();
    }

    public void setItems(Collection<String> collections) {
        items.clear();
        items.addAll(collections);
    }

    public void addItem(String item) {
        items.add(item);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_string_actionrow, parent, false);
            holder.nameTV = (TextView) convertView.findViewById(R.id.actionRowNameTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = items.get(position);
        if (null != item) {
            holder.nameTV.setText(item);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView nameTV;
    }
}
