package com.infinity.jerry.securitysupport.common.otherstuff;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by edwardliu on 16/4/18.
 */
public class SimpleUrlConnection {

    private int mConnectTimeOut;
    private int mReadTimeOut;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000, DEFAULT_READ_TIMEOUT = 15000;

    public SimpleUrlConnection() {
        mConnectTimeOut = DEFAULT_CONNECT_TIMEOUT;
        mReadTimeOut = DEFAULT_READ_TIMEOUT;
    }

    public SimpleUrlConnection(int connectTimeout, int readTimeout) {
        mConnectTimeOut = connectTimeout;
        mReadTimeOut = readTimeout;
    }

    public void startUrl(String urlStr, ConnectionCallBack callback) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(mConnectTimeOut);
            urlConnection.setReadTimeout(mReadTimeOut);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.connect();
            if (null != callback) {
                callback.onGetStream(urlConnection.getResponseCode(), urlConnection);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static interface ConnectionCallBack {
        public void onGetStream(int statusCode, HttpURLConnection connection);
    }
}
