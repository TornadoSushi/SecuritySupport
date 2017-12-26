package com.infinity.jerry.securitysupport.common.otherstuff;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by edwardliu on 15/7/9.
 */
public class UTFStringRequest extends StringRequest {

    private Map<String, String> mParams;
    private Map<String, String> mHeaders;

    public UTFStringRequest(int method, String url, final Map<String, String> params,
                            final Map<String, String> headers, Response.Listener<String> responseListener,
                            Response.ErrorListener errorListener) {
        super(method, url, responseListener, errorListener);
        mParams = params;
        mHeaders = headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (null == mParams) {
            return super.getParams();
        }
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (null == mHeaders) {
            return super.getHeaders();
        }

        mHeaders.put("Charset", "UTF-8");
        mHeaders.put("Content-Type", "application/json");
        mHeaders.put("Accept", "application/json");
        return mHeaders;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // TODO Auto-generated method stub
        String str = null;
        try {
            str = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
    }
}
