package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by edwardliu on 15/7/9.
 */
public class ConnectionWrapper {

    private RequestQueue mRequestQueue;
    private ConnectivityManager mConnectivityManager;

    ConnectionWrapper(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static enum ErrorType {
        Network,
        Server,
        Timeout
    }

    public interface ConnectionCallback {
        void onResponse(String content);

        void onError(ErrorType error);
    }

    public boolean openUrl(int method, String url, final Map<String, String> params,
                           final Map<String, String> headers, final ConnectionCallback callback) {
        if (mConnectivityManager != null) {
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isAvailable()) {
                // network not available call error handler
                callback.onError(ErrorType.Network);
                return false;
            }
        }
        UTFStringRequest request = null;
        if (method == 0) {
            if (null != params) {
                url = url + this.query(params);
            }
            request = new UTFStringRequest(method, url, params, headers, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callback.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        callback.onError(ErrorType.Timeout);
                    } else {
                        callback.onError(ErrorType.Server);
                    }
                }
            });
        } else if (method == 1) {
            request = new UTFStringRequest(method, url, params, headers, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callback.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        callback.onError(ErrorType.Timeout);
                    } else {
                        callback.onError(ErrorType.Server);
                    }
                }
            });
        }
        mRequestQueue.add(request);
        return true;
    }

    private String query(Map<String, String> parameters) {
        String query = "";
        Iterator iterator = parameters.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            try {
                if (query.length() > 0) {
                    query = query + "&";
                }
                query = query + key + "=" + URLEncoder.encode(parameters.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return query;
    }
}
