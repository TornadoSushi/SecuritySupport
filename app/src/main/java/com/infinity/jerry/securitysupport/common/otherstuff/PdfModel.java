package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by edwardliu on 16/4/21.
 */
public class PdfModel {
    private static final String GET_STANDARDS_PDF_URL = "http://139.196.253.18:8899/api/spec/list?page_size=10&page=";
    private static final String GET_EQUIPMENTS_PDF_URL = "http://139.196.253.18:8899/api/gear/list?page_size=10&page=";
    private static final String CHECK_VERSION_URL = "http://139.196.253.18:8899/api/version/check";

    public static void fetchVersion(final Context context, final Handler handler) {
        MobileWebApi api = new MobileWebApi(context, handler);
        api.requestJSONObject(0, CHECK_VERSION_URL, null, null, new MobileWebApi.JSONObjectResponseListener(handler) {
            @Override
            public void onResponse(JSONObject object, long receivedTime) throws MobileWebApi.ServerResponseException, JSONException {
                int success = object.optInt("success");
                if (success == 1) {
                    VersionItem item = new VersionItem();
                    item.versionCode = object.optInt("code");
                    item.versionName = object.optString("versionName");
                    item.downloadUrl = object.optString("releaseUrl");
                    MobileWebApi.sendSuccessMessage(handler, item);
                } else {
                    MobileWebApi.sendErrorMessage(handler);
                }
            }
        });
    }

    public static void getStandardPdfs(final Context context, final Handler handler, final int page) {
        MobileWebApi api = new MobileWebApi(context, handler);
        String url = GET_STANDARDS_PDF_URL + page;
        api.requestJSONObject(0, url, null, null, new MobileWebApi.JSONObjectResponseListener(handler) {
            @Override
            public void onResponse(JSONObject object, long receivedTime) throws MobileWebApi.ServerResponseException, JSONException {
                int success = object.optInt("success");
                if (success == 1) {
                    JSONArray array = object.optJSONArray("list");
                    ArrayList<PdfFile> items = null;
                    if (null != array && array.length() > 0) {
                        items = new ArrayList<PdfFile>();
                        for (int i = 0; i != array.length(); ++i) {
                            JSONObject uObj = array.optJSONObject(i);
                            PdfFile item = new PdfFile();
                            item.name = uObj.optString("name");
                            item.url = uObj.optString("file_url");
                            String[] names = item.url.split("/");
                            item.shortName = names[names.length - 1];
                            File file = new File(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOCUMENTS), String.format("SecurityLawPdf/%s", item.shortName));
                            item.isExist = file.exists();
                            items.add(item);
                        }
                    }
                    MobileWebApi.sendSuccessMessage(handler, items);
                } else {
                    MobileWebApi.sendErrorMessage(handler);
                }
            }
        });
    }

    public static void getEquipmentPdfs(final Context context, final Handler handler, final int page) {
        MobileWebApi api = new MobileWebApi(context, handler);
        String url = GET_EQUIPMENTS_PDF_URL + page;
        api.requestJSONObject(0, url, null, null, new MobileWebApi.JSONObjectResponseListener(handler) {
            @Override
            public void onResponse(JSONObject object, long receivedTime) throws MobileWebApi.ServerResponseException, JSONException {
                int success = object.optInt("success");
                if (success == 1) {
                    JSONArray array = object.optJSONArray("list");
                    ArrayList<PdfFile> items = null;
                    if (null != array && array.length() > 0) {
                        items = new ArrayList<PdfFile>();
                        for (int i = 0; i != array.length(); ++i) {
                            JSONObject uObj = array.optJSONObject(i);
                            PdfFile item = new PdfFile();
                            item.name = uObj.optString("name");
                            item.url = uObj.optString("file_url");
                            String[] names = item.url.split("/");
                            item.shortName = names[names.length - 1];
                            File file = new File(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOCUMENTS), String.format("SecurityLawPdf/%s", item.shortName));
                            item.isExist = file.exists();
                            items.add(item);
                        }
                    }
                    MobileWebApi.sendSuccessMessage(handler, items);
                } else {
                    MobileWebApi.sendErrorMessage(handler);
                }
            }
        });
    }
}
