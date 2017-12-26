package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataController {

    static final String TAG = "DataController";
    private static DataController sController;
    private DataHelper mDataHelper;

    private DataController(Context context) {
        mDataHelper = new DataHelper(context);
    }

    /**
     * this method must invoke before {@link #getController()}
     *
     * @param context
     */
    public static void initController(Context context) {
        checkDatabaseAndCP(context);
        if (sController == null) {
            sController = new DataController(context);
        }
    }

    private static void checkDatabaseAndCP(Context context) {
        String newName = "security.db";

        File baseFile = context.getDatabasePath(newName);
        baseFile = new File(baseFile.getAbsolutePath().replace(newName, ""));
        baseFile.mkdirs();
        File newFile = context.getDatabasePath(newName);
        if (!newFile.exists()) {
            Log.i(TAG, "copy local database to app location");
            try {
                BufferedInputStream bis = new BufferedInputStream(context
                        .getAssets().open(newName));
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(newFile), 8 * 1024);
                byte[] buffer = new byte[4 * 1024];
                int length;
                while ((length = bis.read(buffer)) > 0) {
                    bos.write(buffer, 0, length);
                }
                bos.flush();
                bos.close();
                bis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public DataHelper getHelper() {
        return mDataHelper;
    }

    /**
     * get controller instance, this method invoke need after
     * {@link #initController(Context)}
     *
     * @return
     * @throws RuntimeException
     */
    public static DataController getController() {
        if (sController == null) {
            throw new RuntimeException(
                    "must after invoke 'initController' method");
        }
        return sController;
    }

    public static void releaseController() {
        if (sController != null) {
            sController.mDataHelper.close();
        }
        sController = null;
    }

    public class DataHelper extends BaseDatabaseHelper {
        protected static final String DB_NAME = "security.db";
        protected static final int DB_VERSION = 1;

        public DataHelper(Context context) {
            super(context, DB_NAME, DB_VERSION);
        }

        public Cursor queryItems(String sql) {
            Cursor cursor = null;
            try {
                // lock the database
                synchronized (mLocker) {
                    SQLiteDatabase database = getWritableDatabase();
                    cursor = database.rawQuery(sql, null);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "queryDB error:", e);
            }
            return cursor;
        }
    }
}