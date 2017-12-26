package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

//    public static String getCacheDir(Context context) {
//        String APP_DIR_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wqkjprint";
//        File file = new File(APP_DIR_NAME);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        return APP_DIR_NAME;
//    }

    public static String getCacheDir(Context context) {

        String APP_DIR_NAME = "/sdcard/security";
        File file = new File(APP_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return APP_DIR_NAME;
    }


    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public static File getAssetFileToCacheDir(Context context, String fileName) {
        String filePath = getCacheDir(context) + "/" + fileName;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("====>fileutils", e.toString());
        }
        return null;
    }

    public static String createTemplate(Context context) {
        String APP_DIR_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wqkjprint/template";
        File file = new File(APP_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return APP_DIR_NAME;
    }

    public static boolean checkTemplate(Context context, String fileName) {
        String dir = createTemplate(context);
        String filePath = dir + "/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static void copyTemplate(Context context) {
        String dir = createTemplate(context);
        AssetManager am = context.getAssets();
        try {
            String[] path = am.list("template");
            if (null != path && path.length > 0) {
                for (int i = 0; i < path.length; i++) {
                    String fileName = path[i];
                    String filePath = dir + "/" + fileName;
                    InputStream is = am.open("template/" + fileName);
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] temp = new byte[1024];
                    int k = 0;
                    while ((k = is.read(temp)) > 0) {
                        fos.write(temp, 0, k);
                    }
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            Log.e("====>copyTemplate", e.toString());
        }
    }
}
