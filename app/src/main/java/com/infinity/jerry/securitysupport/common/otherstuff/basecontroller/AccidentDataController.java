package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.infinity.jerry.securitysupport.common.otherstuff.accident.AccidentCategory;
import com.infinity.jerry.securitysupport.common.otherstuff.accident.AccidentDetail;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.table.ACCIDENT_TABLE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class AccidentDataController {
    static final String TAG = "AccidentDataController";
    private static AccidentDataController sController;
    private DataHelper mDataHelper;
    private AccidentCategoryFactory mFactory;
    private AccidentDetailDBFactory mDetailFactory;

    private AccidentDataController(Context context) {
        mFactory = new AccidentCategoryFactory();
        mDetailFactory = new AccidentDetailDBFactory();
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
            sController = new AccidentDataController(context);
        }
    }

    private static void checkDatabaseAndCP(Context context) {
        String newName = "accident.db";

        String DB_PATH = "/data/data/" + context.getPackageName()
                + "/databases/";
        File baseFile = new File(DB_PATH);
        baseFile.mkdirs();

        File newFile = new File(DB_PATH + newName);
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

    /**
     * get controller instance, this method invoke need after
     * {@link #initController(Context)}
     *
     * @return
     * @throws RuntimeException
     */
    public static AccidentDataController getController() {
        if (sController == null) {
            throw new RuntimeException(
                    "must after invoke 'initController' method");
        }
        return sController;
    }

    private List<AccidentCategory> db_getCategories(String categoryWhere, String order) {
        List<AccidentCategory> itemList = mDataHelper.queryDB(mFactory,
                AccidentCategoryFactory.TRANSACTION_QUERY_ALL_CATEGORIES, false,
                ACCIDENT_TABLE.TABLE_NAME, null, categoryWhere, null, null,
                null, order, null);
        return itemList;
    }

    public List<AccidentCategory> db_getAllCategory() {
        return db_getCategories(null, null);
    }

    private List<AccidentDetail> db_getAccidents(String categoryWhere, String order) {
        List<AccidentDetail> itemList = mDataHelper.queryDB(mDetailFactory,
                1, false,
                ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.TABLE_NAME, null, categoryWhere, null, null,
                null, order, null);
        return itemList;
    }

    public List<AccidentDetail> db_getAccidentsWithId(int id) {
        String where = ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.PARENT_ID + "='" + id + "'";
        return db_getAccidents(where, null);
    }

    public static void releaseController() {
        if (sController != null) {
            sController.mDataHelper.close();
        }
        sController = null;
    }

    public class DataHelper extends BaseDatabaseHelper {
        protected static final String DB_NAME = "accident.db";
        protected static final int DB_VERSION = 1;

        public DataHelper(Context context) {
            super(context, DB_NAME, DB_VERSION);
        }
    }

    private class AccidentCategoryFactory implements IDBResult<AccidentCategory> {
        public static final int TRANSACTION_QUERY_ALL_CATEGORIES = 1;

        @Override
        public List<AccidentCategory> resultFromCursor(int transaction, Cursor cursor) {
            final List<AccidentCategory> itemList = new ArrayList<AccidentCategory>();
            if (transaction == TRANSACTION_QUERY_ALL_CATEGORIES) {
                final int i_id = cursor.getColumnIndex(ACCIDENT_TABLE.ID);
                final int i_name = cursor.getColumnIndex(ACCIDENT_TABLE.NAME);

                while (cursor.moveToNext()) {
                    final AccidentCategory item = new AccidentCategory();
                    item.id = cursor.getInt(i_id);
                    item.name = cursor.getString(i_name);
                    itemList.add(item);
                }
                return itemList;
            }
            return itemList;
        }
    }

    private class AccidentDetailDBFactory implements IDBResult<AccidentDetail> {
        @Override
        public List<AccidentDetail> resultFromCursor(int transaction, Cursor cursor) {
            final List<AccidentDetail> itemList = new ArrayList<AccidentDetail>();
            final int i_id = cursor.getColumnIndex(ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.ID);
            final int i_pid = cursor.getColumnIndex(ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.PARENT_ID);
            final int i_name = cursor.getColumnIndex(ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.NAME);
            final int i_knowledge = cursor.getColumnIndex(ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.KNOWLEDGE);
            final int i_save = cursor.getColumnIndex(ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.SAVE);
            final int i_advice = cursor.getColumnIndex(ACCIDENT_TABLE.ACCIDENT_LIST_TABLE.ADVICE);
            while (cursor.moveToNext()) {
                final AccidentDetail item = new AccidentDetail();
                item.id = cursor.getInt(i_id);
                item.parentId = cursor.getInt(i_pid);
                item.name = cursor.getString(i_name);
                item.knowledge = cursor.getString(i_knowledge);
                item.save = cursor.getString(i_save);
                item.advice = cursor.getString(i_advice);
                itemList.add(item);
            }
            return itemList;
        }
    }
}
