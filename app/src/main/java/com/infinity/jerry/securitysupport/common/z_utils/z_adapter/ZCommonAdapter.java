/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by root on 2/10/17.
 */

public abstract class ZCommonAdapter<T> extends BaseAdapter {

    protected List<T> dataList;
    protected Context context;
    protected LayoutInflater inflater;
    protected final int resId;

    public ZCommonAdapter(Context context, List<T> dataList, int resId) {
        this.dataList = dataList;
        this.context = context;
        this.resId = resId;
        inflater = LayoutInflater.from(context);
    }

    public void appendListData(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int i) {
        if (dataList != null) {
            return dataList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ZViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();

    }

    public abstract void convert(ZViewHolder holder, T item, int position);

    private ZViewHolder getViewHolder(int position, View convertView,
                                      ViewGroup parent) {
        return ZViewHolder.getInstance(context, convertView, parent, resId,
                position);
    }
}
