package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;

import java.util.ArrayList;

/**
 * Created by edwardliu on 16/4/21.
 */
public class PdfFileAdapter extends BaseAdapter {

    private ArrayList<PdfFile> items;
    private ArrayList<String> keys;
    private LayoutInflater mInflater;
    private Context mContext;

    public PdfFileAdapter(Context context) {
        mContext = context;
        items = new ArrayList<>();
        keys = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void addItems(ArrayList<PdfFile> items) {
        this.items.addAll(items);
        for (PdfFile file : items) {
            keys.add(file.url);
        }
    }

    public int indexOf(String url) {
        return keys.indexOf(url);
    }

    public void updateDownloadState(int position) {
        items.get(position).isExist = true;
        items.get(position).isDownloading = false;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= items.size()) {
            return null;
        }
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
            convertView = mInflater.inflate(R.layout.view_pdf_show_item, parent, false);
            holder.nameTV = (TextView) convertView.findViewById(R.id.pdfNameTV);
            holder.downloadTV = (TextView) convertView.findViewById(R.id.pdfDownloadTV);
            holder.downloadPB = (ProgressBar) convertView.findViewById(R.id.pdfPB);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PdfFile item = (PdfFile) getItem(position);
        if (null != item) {
            if (item.isExist) {
                holder.downloadTV.setVisibility(View.GONE);
                holder.downloadPB.setVisibility(View.GONE);
            } else {
                if (item.isDownloading) {
                    holder.downloadTV.setVisibility(View.GONE);
                    holder.downloadPB.setVisibility(View.VISIBLE);
                } else {
                    holder.downloadTV.setVisibility(View.VISIBLE);
                    holder.downloadPB.setVisibility(View.GONE);
                }
            }
            holder.nameTV.setText(item.name);
            holder.downloadTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PDFDownloadService.class);
                    intent.putExtra(Constants.EXTRA_DOWNLOAD_PDF, item);
                    mContext.startService(intent);
                    item.isDownloading = true;
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        TextView nameTV;
        TextView downloadTV;
        ProgressBar downloadPB;
    }
}
