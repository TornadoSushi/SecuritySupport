package com.infinity.jerry.securitysupport.common.otherstuff;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by edwardliu on 16/4/18.
 */
public class PDFDownloadService extends Service {

    private PDFDownloadThread mPdfDownloader;
    private static final String TAG = "PDFDownloadService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        mPdfDownloader = new PDFDownloadThread();
        mPdfDownloader.startDownload();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        if (intent != null) {
            PdfFile file = intent.getParcelableExtra(Constants.EXTRA_DOWNLOAD_PDF);
            if (null != file) {
                Log.i(TAG, String.format("%s pdf will added to the download queue", file.shortName));
                mPdfDownloader.addPdfToDownloadQueue(file);
            } else {
                List<PdfFile> pdfs = intent.getParcelableArrayListExtra(Constants.EXTRA_DOWNLOAD_PDFS);
                if (pdfs != null) {
                    Log.i(TAG, String.format("%d pdfs will added to the download queue", pdfs.size()));
                    mPdfDownloader.addPdfsToDownloadQueue(pdfs);
                }
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPdfDownloader.stopDownload();
    }

    private boolean isFileExist(String path) {
        return new File(path).exists();
    }

    class PDFDownloadThread extends Thread implements SimpleUrlConnection.ConnectionCallBack {

        private boolean isRunning;
        private static final int BUFFER_SIZE = 16 * 1024;
        private static final int READ_BUFFER_SIZE = 8 * 1024;
        byte[] buffers = new byte[READ_BUFFER_SIZE];

        private PdfFile mFile;
        private String mPath;
        private SimpleUrlConnection mConnection;

        LinkedList<PdfFile> mDownloadQueue = new LinkedList<PdfFile>();

        public PDFDownloadThread() {
            mConnection = new SimpleUrlConnection();
        }

        public void startDownload() {
            if (!isRunning) {
                isRunning = true;
                start();
            }
        }

        public void stopDownload() {
            isRunning = false;
            makeThreadNotify();
        }

        @Override
        public void run() {
            setName("pdf_downloading");
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            while (isRunning) {
                synchronized (mDownloadQueue) {
                    if (mDownloadQueue.isEmpty()) {
                        Intent i = new Intent(DEF.ACTION_DOWNLOAD_ALL_PDFS_FINISH);
                        sendBroadcast(i);
                        try {
                            mDownloadQueue.wait();
                        } catch (InterruptedException e) {
                            Log.w(TAG, "interrupt download save thread");
                        }
                    }
                }
                if (!mDownloadQueue.isEmpty()) {
                    mFile = mDownloadQueue.getFirst();
                    mPath = getFolderPath() + "/" + mFile.shortName;
                    if (isFileExist(mPath)) {
                        notifyDownloadFinish();
                    } else {
                        mConnection.startUrl(mFile.url, this);
                    }
                }
            }
        }

        @Override
        public void onGetStream(int statusCode, HttpURLConnection connection) {
            if (statusCode == 200) {
                File folder = new File(getFolderPath());
                boolean isSuccess = folder.mkdirs();

                String tempPath = mPath + "-temp";
                // every time write the stream need delete the temp file first
                File tempFile = new File(tempPath);
                tempFile.delete();

                Log.i(TAG, "save to temp path:" + tempPath);
                try {
                    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempPath), BUFFER_SIZE);
                    int size = 0;
                    while ((size = bis.read(buffers, 0, READ_BUFFER_SIZE)) != -1) {
                        bos.write(buffers, 0, size);
                    }
                    bos.flush();

                    bos.close();
                    bis.close();

                    File finalFile = new File(mPath);
                    tempFile.renameTo(finalFile);
                    Log.i(TAG, "rename to final path:" + mPath);

                    notifyDownloadFinish();
                } catch (IOException e) {
                    e.printStackTrace();
                    // error will make current to the last
                    moveCurrentCallToTheLast();
                }
            }
        }

        private String getFolderPath() {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "SecurityLawPdf");
            return file.getAbsolutePath();
        }

        private void notifyDownloadFinish() {
            // here notify current download finish
            Intent i = new Intent(DEF.ACTION_DOWNLOAD_PDF_FINISH);
            i.putExtra(Constants.EXTRA_DOWNLOAD_FINISH_PDF_ID, mFile.url);
            sendBroadcast(i);
            removeCurrentCall();
        }

        private void makeThreadNotify() {
            Log.i(TAG, "notify thread");
            synchronized (mDownloadQueue) {
                mDownloadQueue.notify();
            }
        }

        private void removeCurrentCall() {
            synchronized (mDownloadQueue) {
                Log.i(TAG, "remove pdf from download queue");
                mDownloadQueue.removeFirst();
                mFile = null;
                mPath = null;
            }
        }

        public void addPdfsToDownloadQueue(List<PdfFile> files) {
            if (isRunning && files != null) {
                synchronized (mDownloadQueue) {
                    for (PdfFile file : files) {
                        if (!mDownloadQueue.contains(file)) {
                            mDownloadQueue.add(file);
                        }
                    }
                    mDownloadQueue.notify();
                }
            }
        }

        public void addPdfToDownloadQueue(PdfFile file) {
            if (isRunning && file != null) {
                synchronized (mDownloadQueue) {
                    if (!mDownloadQueue.contains(file)) {
                        mDownloadQueue.add(file);
                    }
                    mDownloadQueue.notify();
                }
            }
        }

        private void moveCurrentCallToTheLast() {
            synchronized (mDownloadQueue) {
                PdfFile file = mDownloadQueue.removeFirst();
                mDownloadQueue.addLast(file);
                mPath = null;
                mFile = null;
            }
        }
    }
}
