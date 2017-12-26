package com.infinity.jerry.securitysupport.common.otherstuff.equipment;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.MobileWebApi;
import com.infinity.jerry.securitysupport.common.otherstuff.PDFDownloadStateManager;
import com.infinity.jerry.securitysupport.common.otherstuff.PdfFile;
import com.infinity.jerry.securitysupport.common.otherstuff.PdfFileAdapter;
import com.infinity.jerry.securitysupport.common.otherstuff.PdfModel;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by edwardliu on 15/12/13.
 */
public class EquipmentActivity extends TitlebarActivity implements PDFDownloadStateManager.Callback, View.OnClickListener {

    private ListView mListView;
    private View mFooterView;
    private PDFDownloadStateManager mWatcher;

    private PdfFileAdapter mAdapter;
    private int mPageIndex = 1;
    private boolean mHasMore = true;

    @Override
    protected String centerTitle() {
        return "装备使用";
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
        mListView = (ListView) getInflateView(R.layout.activity_common_list);
        mFooterView = getInflateView(R.layout.view_lv_footview);
        TextView moreTV = (TextView) mFooterView.findViewById(R.id.footLoadMoreTV);
        moreTV.setOnClickListener(this);
        mListView.addFooterView(mFooterView);

        mAdapter = new PdfFileAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PdfFile item = (PdfFile) mAdapter.getItem(position);
                if (null != item && item.isExist) {
                    launchPdfActivity(item.shortName);
                } else if (position < mAdapter.getCount()) {
                    Toast.makeText(EquipmentActivity.this, "无法查看未下载的文档，请点击右侧按钮开始下载", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mWatcher = new PDFDownloadStateManager(this);
        mWatcher.startWatch();
        mWatcher.setCallback(this);

        getEquipmentPdfs();
        return mListView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWatcher.stopWatch();
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {
    }

    private void getEquipmentPdfs() {
        if (!mHasMore) {
            return;
        }
        showLoading();
        PdfModel.getEquipmentPdfs(this, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == MobileWebApi.SUCCESS) {
                    ArrayList<PdfFile> items = (ArrayList<PdfFile>) msg.obj;
                    if (null != items && !items.isEmpty()) {
                        mAdapter.addItems(items);
                        mAdapter.notifyDataSetChanged();
                        ++mPageIndex;
                    } else {
                        mHasMore = false;
                        --mPageIndex;
                        mListView.removeFooterView(mFooterView);
                    }
                }
                hideLoading();
            }
        }, mPageIndex);
    }

    private void launchPdfActivity(String shortName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "SecurityLawPdf");
        File temp_file = new File(file.getAbsolutePath() + "/" + shortName);

        Uri data = Uri.fromFile(temp_file);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getMimeTypeFromExtension("pdf");

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(data, type);
        startActivity(intent);
    }

    @Override
    public void onDownloadFinish(String id) {
        int index = mAdapter.indexOf(id);
        if (index > -1) {
            mAdapter.updateDownloadState(index);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAllDownloadFinish() {

    }

    @Override
    public void onClick(View v) {
        getEquipmentPdfs();
    }
}
