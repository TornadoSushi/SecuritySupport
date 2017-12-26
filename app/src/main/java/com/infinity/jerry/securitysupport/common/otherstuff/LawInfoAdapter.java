package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.law_rule.LawInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwardliu on 15/11/28.
 */
public class LawInfoAdapter extends BaseAdapter {

    private String mKeyword;
    private Context mContext;
    private List<LawInfo> items;

    public LawInfoAdapter(Context context, String keyword) {
        mContext = context;
        mKeyword = keyword;
        items = new ArrayList<>();
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    public void setItems(List<LawInfo> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_twoline_actionrow, parent, false);
            holder.primaryTV = (TextView) convertView.findViewById(R.id.rowPrimaryTV);
            holder.secondaryTV = (TextView) convertView.findViewById(R.id.rowSecondaryTV);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LawInfo item = items.get(position);
        if (null != item) {
            int index = item.contentShow.indexOf(mKeyword);
            SpannableStringBuilder style = new SpannableStringBuilder(item.contentShow);
            style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor
                    (R.color.color_2)), index, index + mKeyword.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.primaryTV.setText(style);
            holder.secondaryTV.setText(item.clause);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView primaryTV;
        TextView secondaryTV;
    }
}
