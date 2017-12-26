package com.infinity.jerry.securitysupport.common.otherstuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by edwardliu on 15/11/20.
 */
public abstract class TitlebarActivity extends Activity {

    private ProgressDialog mDialog;

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMessage(msg);
        }
    };

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    protected abstract String centerTitle();

    protected abstract boolean hasBackOption();

    protected View.OnClickListener leftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    protected abstract View.OnClickListener rightClickListener();

    protected abstract View onCreateView();

    protected abstract void onAddRightTitleView(LinearLayout ll);

    protected void sendEmptyMessage(int what) {
        uiHandler.sendEmptyMessage(what);
    }

    protected void postRunnable(Runnable runnable) {
        uiHandler.post(runnable);
    }

    protected void handleMessage(Message msg) {
    }

    protected void submitWorker(Runnable runnable) {
        executorService.submit(runnable);
    }

    protected TextView mTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_titlebar);

        mTitleTV = (TextView) findViewById(R.id.titleBarCenterTV);
        LinearLayout leftLL = (LinearLayout) findViewById(R.id.titleBarLeftLL);
        LinearLayout titleRightLL = (LinearLayout) findViewById(R.id.titleBarRightLL);

        String title = centerTitle();
        leftLL.setVisibility(hasBackOption() ? View.VISIBLE : View.GONE);

        View.OnClickListener leftClickListener = leftClickListener();
        View.OnClickListener rightClickListener = rightClickListener();

        if (null != title) {
            mTitleTV.setText(title);
        }

        if (null != leftClickListener) {
            leftLL.setOnClickListener(leftClickListener);
        }
        if (null != rightClickListener) {
            titleRightLL.setOnClickListener(rightClickListener);
        }

        RelativeLayout container = (RelativeLayout) findViewById(R.id.titleBarViewContainerRL);
        View view = onCreateView();
        if (null != view) {
            container.addView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        onAddRightTitleView(titleRightLL);
    }

    protected View getInflateView(int id) {
        return getLayoutInflater().inflate(id, null);
    }

    protected void showLoading() {
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("加载中....");
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    protected void hideLoading() {
        if (null != mDialog) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
