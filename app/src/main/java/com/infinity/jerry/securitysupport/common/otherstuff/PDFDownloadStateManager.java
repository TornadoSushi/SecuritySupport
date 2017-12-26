package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


public class PDFDownloadStateManager extends BroadcastReceiver {

    static final String TAG = "PDFDownloadStateManager";
    private Context mContext;
    private Callback mCallback;

    public PDFDownloadStateManager(Context context) {
        mContext = context;
    }

    public void startWatch() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DEF.ACTION_DOWNLOAD_ALL_PDFS_FINISH);
        filter.addAction(DEF.ACTION_DOWNLOAD_PDF_FINISH);
        mContext.registerReceiver(this, filter);
    }

    public void stopWatch() {
        mContext.unregisterReceiver(this);
        mCallback = null;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (mCallback != null) {
            if (action.equals(DEF.ACTION_DOWNLOAD_ALL_PDFS_FINISH)) {
                mCallback.onAllDownloadFinish();
            } else if (action.equals(DEF.ACTION_DOWNLOAD_PDF_FINISH)) {
                String id = intent.getStringExtra(Constants.EXTRA_DOWNLOAD_FINISH_PDF_ID);
                mCallback.onDownloadFinish(id);
            }
        }
    }

    public static interface Callback {
        public void onDownloadFinish(String id);

        public void onAllDownloadFinish();
    }
}