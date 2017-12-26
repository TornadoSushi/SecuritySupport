package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by edwardliu on 15/7/9.
 */
public class MobileWebApi {

    public static int ERROR = 0;
    public static int SUCCESS = 1;
    public static int CANCELLED = 2;

    public static enum ResponseType {
        JSONObject, JSONArray, JSONString
    }

    public static interface ErrorResponseListener {
        public void onError();
    }

    public static abstract class CancelRequestListener {
        private boolean mRequestCancelled = false;

        private void notifyRequestCancelled() {
            mRequestCancelled = true;
            onCancel();
        }

        public boolean wasRequestCancelled() {
            return mRequestCancelled;
        }

        public abstract void onCancel();

    }

    public static abstract class ResponseListener {
        private ErrorResponseListener mErrorResponseListener;
        private CancelRequestListener mCancelRequestListener;

        ResponseListener(ErrorResponseListener errorListener, CancelRequestListener cancelListener) {
            mErrorResponseListener = errorListener;
            mCancelRequestListener = cancelListener;
        }

        public void onError() {
            mErrorResponseListener.onError();
        }

        public CancelRequestListener getCancelRequestListener() {
            return mCancelRequestListener;
        }

        public boolean wasRequestCancelled() {
            if (mCancelRequestListener != null) {
                return mCancelRequestListener.wasRequestCancelled();
            }

            return false;
        }
    }

    public static class ServerResponseException extends Exception {
    }

    ;

    public static abstract class JSONArrayResponseListener extends ResponseListener {
        public JSONArrayResponseListener(Handler uiHandler) {
            super(new DefaultErrorListener(uiHandler), new DefaultCancelRequestListener(uiHandler));
        }

        public JSONArrayResponseListener(ErrorResponseListener errorListener, CancelRequestListener cancelListener) {
            super(errorListener, cancelListener);
        }

        public abstract void onResponse(JSONArray array, long receivedTime) throws ServerResponseException, JSONException;
    }

    public static abstract class JSONObjectResponseListener extends ResponseListener {
        public JSONObjectResponseListener(Handler uiHandler) {
            super(new DefaultErrorListener(uiHandler), new DefaultCancelRequestListener(uiHandler));
        }

        public JSONObjectResponseListener(ErrorResponseListener errorListener, CancelRequestListener cancelListener) {
            super(errorListener, cancelListener);
        }

        public abstract void onResponse(JSONObject object, long receivedTime) throws ServerResponseException, JSONException;
    }

    public static abstract class JSONStringResponseListener extends ResponseListener {
        public JSONStringResponseListener(Handler uiHandler) {
            super(new DefaultErrorListener(uiHandler), new DefaultCancelRequestListener(uiHandler));
        }

        public JSONStringResponseListener(ErrorResponseListener errorListener, CancelRequestListener cancelListener) {
            super(errorListener, cancelListener);
        }

        public abstract void onResponse(String object, long receivedTime) throws ServerResponseException, JSONException;
    }

    public static void sendErrorMessage(Handler uiHandler) {
        sendErrorMessage(uiHandler, null);
    }

    public static void sendErrorMessage(Handler uiHandler, Object userData) {
        Message message = Message.obtain();
        message.arg1 = MobileWebApi.ERROR;
        message.obj = userData;
        uiHandler.sendMessage(message);
    }

    public static void sendCancelMessage(Handler uiHandler) {
        Message message = Message.obtain();
        message.arg1 = MobileWebApi.CANCELLED;
        uiHandler.sendMessage(message);
    }

    public static void sendSuccessMessage(Handler uiHandler) {
        sendSuccessMessage(uiHandler, null);
    }

    public static void sendSuccessMessage(Handler uiHandler, Object userData) {
        Message message = Message.obtain();
        message.arg1 = MobileWebApi.SUCCESS;
        message.obj = userData;
        uiHandler.sendMessage(message);
    }

    public static void sendSuccessMessage(Handler uiHandler, Object userData, int count) {
        Message message = Message.obtain();
        message.arg1 = MobileWebApi.SUCCESS;
        message.arg2 = count;
        message.obj = userData;
        uiHandler.sendMessage(message);
    }

    public static class DefaultErrorListener implements ErrorResponseListener {
        Handler mUIHandler;

        public DefaultErrorListener(Handler uiHandler) {
            mUIHandler = uiHandler;
        }

        @Override
        public void onError() {
            sendErrorMessage(mUIHandler);
        }
    }

    public static class IgnoreErrorListener implements ErrorResponseListener {
        @Override
        public void onError() {
            // do nothing
        }
    }

    public static class DefaultCancelRequestListener extends CancelRequestListener {
        Handler mUIHandler;

        public DefaultCancelRequestListener(Handler uiHandler) {
            mUIHandler = uiHandler;
        }

        @Override
        public void onCancel() {
            sendCancelMessage(mUIHandler);
        }
    }

    private Context mContext;
    private Handler mUIHandler = null;

    public MobileWebApi(Context context, Handler uiHandler) {
        mContext = context;
        mUIHandler = uiHandler;
    }

    private boolean requestResponse(int method, final String url, Map<String, String> params, Map<String, String> headers,
                                    final ResponseType expectedType, final ResponseListener responseListener) {
        ConnectionWrapper.ConnectionCallback callback = new ConnectionWrapper.ConnectionCallback() {

            @Override
            public void onError(final ConnectionWrapper.ErrorType error) {
                if (responseListener.wasRequestCancelled()) {
                    // nothing to handle request was cancelled
                    return;
                }

                if (null != mUIHandler) {
                    mUIHandler.post(new Runnable() {
                        public void run() {
                            String errorMessage = "连接错误";

                            if (error == ConnectionWrapper.ErrorType.Network) {
                                errorMessage = "网络错误";
                            } else if (error == ConnectionWrapper.ErrorType.Server) {
                                errorMessage = "服务器错误";
                            }
                            Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                responseListener.onError();
            }

            @Override
            public void onResponse(String responseText) {
                if (responseListener.wasRequestCancelled()) {
                    return;
                }

                try {
                    switch (expectedType) {
                        case JSONObject:
                            JSONObject responseObject = new JSONObject(responseText);
                            JSONObjectResponseListener objectResponseListener = (JSONObjectResponseListener) responseListener;
                            objectResponseListener.onResponse(responseObject, System.currentTimeMillis());
                            break;

                        case JSONArray:
                            JSONArrayResponseListener arrayResponseListener = (JSONArrayResponseListener) responseListener;
                            JSONArray responseArray = new JSONArray(responseText);
                            arrayResponseListener.onResponse(responseArray, System.currentTimeMillis());
                            break;

                        case JSONString:
                            JSONStringResponseListener stringResponseListener = (JSONStringResponseListener) responseListener;
                            String responseString = responseText;
                            stringResponseListener.onResponse(responseString, System.currentTimeMillis());
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onError(ConnectionWrapper.ErrorType.Server);
                } catch (ServerResponseException e) {
                    e.printStackTrace();
                    onError(ConnectionWrapper.ErrorType.Server);
                }

            }
        };

        ConnectionWrapper connection = new ConnectionWrapper(mContext);
        boolean isStarted = connection.openUrl(method, url, params, headers, callback);
        return isStarted;
    }

    public boolean requestJSONObject(int method, String url, Map<String, String> parameters, Map<String, String> headers,
                                     JSONObjectResponseListener responseListener) {
        return requestResponse(method, url, parameters, headers, ResponseType.JSONObject, responseListener);
    }

    public boolean requestJSONArray(int method, String url, Map<String, String> parameters, Map<String, String> headers,
                                    JSONArrayResponseListener responseListener) {
        return requestResponse(method, url, parameters, headers, ResponseType.JSONArray, responseListener);
    }

    public boolean requestJSONString(int method, String url, Map<String, String> parameters, Map<String, String> headers,
                                     JSONStringResponseListener responseListener) {
        return requestResponse(method, url, parameters, headers, ResponseType.JSONString, responseListener);
    }
}
