package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.infinity.jerry.securitysupport.common.otherstuff.Chemical;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.table.CHEMICAL_TABLE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ChemicalDataController {
    static final String TAG = "ChemicalDataController";
    private static ChemicalDataController sController;
    private DataHelper mDataHelper;
    private ChemicalFactory mFactory;
    private ChemicalNameFactory mNameFactory;
    private ChemicalFeatureDBFactory mFeatureFactory;
    private final ContentValues mCommonValues;

    private ChemicalDataController(Context context) {
        mCommonValues = new ContentValues();
        mFactory = new ChemicalFactory();
        mNameFactory = new ChemicalNameFactory();
        mFeatureFactory = new ChemicalFeatureDBFactory();
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
            sController = new ChemicalDataController(context);
        }
    }

    private static void checkDatabaseAndCP(Context context) {
        String newName = "weihua.db";

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
    public static ChemicalDataController getController() {
        if (sController == null) {
            throw new RuntimeException(
                    "must after invoke 'initController' method");
        }
        return sController;
    }

    private List<Chemical> db_getChemicals(String categoryWhere, String order) {
        List<Chemical> itemList = mDataHelper.queryDB(mFactory,
                ChemicalFactory.TRANSACTION_QUERY_ALL_CHEMICALS, false,
                CHEMICAL_TABLE.TABLE_NAME, null, categoryWhere, null, null,
                null, order, null);
        return itemList;
    }

    public void db_getSearchedChemicals(final String where,
                                        final IDBResult.Callback<List<Chemical>> callback) {
        mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
            @Override
            public void onDBRunning(SQLiteDatabase database) {
                callback.onResultCallback(db_getChemicals(where, CHEMICAL_TABLE.ID));
            }
        });
    }

    public List<Chemical> db_getSearchedChemicals(final String where) {
        return db_getChemicals(where, CHEMICAL_TABLE.ID);
    }

    public void db_getOrderedChemicals(final IDBResult.Callback<List<Chemical>> callback) {
        mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
            @Override
            public void onDBRunning(SQLiteDatabase database) {
                callback.onResultCallback(db_getChemicals(null, CHEMICAL_TABLE.ID));
            }
        });
    }

    public Cursor db_getChemicalCursor() {
        return mDataHelper.queryAllChemicals();
    }

    public Cursor db_getChemicalCursorWithFilter(String sqlWhere) {
        return mDataHelper.queryChemicalsWithFilter(sqlWhere);
    }

    public void db_getOrderedChemicalNames(final IDBResult.Callback<List<String>> callback) {
        mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
            @Override
            public void onDBRunning(SQLiteDatabase database) {
                List<String> names = mDataHelper.queryDB(mNameFactory,
                        ChemicalNameFactory.TRANSACTION_QUERY_ALL_CHEMICAL_NAMES, false,
                        CHEMICAL_TABLE.TABLE_NAME, null, null, null, null,
                        null, CHEMICAL_TABLE.ID, null);
                callback.onResultCallback(names);
            }
        });
    }

    public List<String> db_getFeatures(String tableName) {
        mFeatureFactory.setFeatureName(tableName + "_NAME");
        return mDataHelper.queryDB(mFeatureFactory, 0, false,
                tableName, null, null, null, null,
                null, tableName + "_ID", null);
    }

    public static void releaseController() {
        if (sController != null) {
            sController.mDataHelper.close();
        }
        sController = null;
    }

    public class DataHelper extends BaseDatabaseHelper {
        protected static final String DB_NAME = "weihua.db";
        protected static final int DB_VERSION = 1;

        public DataHelper(Context context) {
            super(context, DB_NAME, DB_VERSION);
        }

        public Cursor queryAllChemicals() {
            Cursor cursor = null;
            try {
                // lock the database
                synchronized (mLocker) {
                    SQLiteDatabase database = getWritableDatabase();
                    cursor = database.rawQuery("SELECT DISTINCT id as _id, zhname FROM HUAXUEPIN ORDER BY _id", null);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "queryDB error:", e);
            }
            return cursor;
        }

        public Cursor queryChemicalsWithFilter(String sqlWhere) {
            Cursor cursor = null;
            try {
                // lock the database
                synchronized (mLocker) {
                    SQLiteDatabase database = getWritableDatabase();
                    cursor = database.rawQuery("SELECT DISTINCT id as _id, zhname FROM HUAXUEPIN WHERE " + sqlWhere + " ORDER BY _id", null);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "queryDB error:", e);
            }
            return cursor;
        }
    }

    private class ChemicalNameFactory implements IDBResult<String> {
        public static final int TRANSACTION_QUERY_ALL_CHEMICAL_NAMES = 1;

        @Override
        public List<String> resultFromCursor(int transaction, Cursor cursor) {
            final List<String> names = new LinkedList<String>();
            final int i_zhname = cursor.getColumnIndex(CHEMICAL_TABLE.ZHNAME);
            while (cursor.moveToNext()) {
                String name = cursor.getString(i_zhname);
                names.add(name);
            }
            return names;
        }
    }

    private class ChemicalFactory implements IDBResult<Chemical> {
        public static final int TRANSACTION_QUERY_ALL_CHEMICALS = 1;

        @Override
        public List<Chemical> resultFromCursor(int transaction, Cursor cursor) {
            final List<Chemical> itemList = new LinkedList<Chemical>();
            if (transaction == TRANSACTION_QUERY_ALL_CHEMICALS) {
                final int i_zhname = cursor.getColumnIndex(CHEMICAL_TABLE.ZHNAME);
                final int i_enname = cursor.getColumnIndex(CHEMICAL_TABLE.ENNAME);
                final int i_cas = cursor.getColumnIndex(CHEMICAL_TABLE.CAS);
                final int i_fzs = cursor.getColumnIndex(CHEMICAL_TABLE.FZS);
                final int i_mdair = cursor.getColumnIndex(CHEMICAL_TABLE.MDAIR);
                final int i_clarity = cursor.getColumnIndex(CHEMICAL_TABLE.CLARITY);
                final int i_ljwd = cursor.getColumnIndex(CHEMICAL_TABLE.LJWD);
                final int i_bzsx = cursor.getColumnIndex(CHEMICAL_TABLE.BZSX);
                final int i_ph = cursor.getColumnIndex(CHEMICAL_TABLE.PH);
                final int i_odor = cursor.getColumnIndex(CHEMICAL_TABLE.ODOR);
                final int i_zrwd = cursor.getColumnIndex(CHEMICAL_TABLE.ZRWD);
                final int i_ljyl = cursor.getColumnIndex(CHEMICAL_TABLE.LJYL);
                final int i_fd = cursor.getColumnIndex(CHEMICAL_TABLE.FD);
                final int i_zqywd = cursor.getColumnIndex(CHEMICAL_TABLE.ZQYWD);
                final int i_bizhong = cursor.getColumnIndex(CHEMICAL_TABLE.BIZHONG);
                final int i_sd = cursor.getColumnIndex(CHEMICAL_TABLE.SD);
                final int i_mdwater = cursor.getColumnIndex(CHEMICAL_TABLE.MDWATER);
                final int i_rd = cursor.getColumnIndex(CHEMICAL_TABLE.RD);
                final int i_wgxz = cursor.getColumnIndex(CHEMICAL_TABLE.WGXZ);
                final int i_zqmd = cursor.getColumnIndex(CHEMICAL_TABLE.ZQMD);
                final int i_bzxx = cursor.getColumnIndex(CHEMICAL_TABLE.BZXX);
                final int i_taste = cursor.getColumnIndex(CHEMICAL_TABLE.TASTE);
                final int i_color = cursor.getColumnIndex(CHEMICAL_TABLE.COLOR);
                final int i_state = cursor.getColumnIndex(CHEMICAL_TABLE.STATE);
                final int i_rsr = cursor.getColumnIndex(CHEMICAL_TABLE.RSR);
                final int i_jkwh = cursor.getColumnIndex(CHEMICAL_TABLE.JKWH);
                final int i_wwxlb = cursor.getColumnIndex(CHEMICAL_TABLE.WWXLB);
                final int i_sr = cursor.getColumnIndex(CHEMICAL_TABLE.SR);
                final int i_pfjc = cursor.getColumnIndex(CHEMICAL_TABLE.PFJC);
                final int i_yjjc = cursor.getColumnIndex(CHEMICAL_TABLE.YJJC);
                final int i_xr = cursor.getColumnIndex(CHEMICAL_TABLE.XR);
                final int i_wxtx = cursor.getColumnIndex(CHEMICAL_TABLE.WXTX);
                final int i_mhff = cursor.getColumnIndex(CHEMICAL_TABLE.MHFF);
                final int i_xlcl = cursor.getColumnIndex(CHEMICAL_TABLE.XLCL);
                final int i_cyzysx = cursor.getColumnIndex(CHEMICAL_TABLE.CYZYSX);
                final int i_qt = cursor.getColumnIndex(CHEMICAL_TABLE.QT);
                final int i_yjfh = cursor.getColumnIndex(CHEMICAL_TABLE.YJFH);
                final int i_stfh = cursor.getColumnIndex(CHEMICAL_TABLE.STFH);
                final int i_sfh = cursor.getColumnIndex(CHEMICAL_TABLE.SFH);
                final int i_jcxzz = cursor.getColumnIndex(CHEMICAL_TABLE.JCXZZ);
                final int i_gckz = cursor.getColumnIndex(CHEMICAL_TABLE.GCKZ);
                final int i_hxxtfh = cursor.getColumnIndex(CHEMICAL_TABLE.HXXTFH);
                final int i_wdx = cursor.getColumnIndex(CHEMICAL_TABLE.WDX);
                final int i_rtecs = cursor.getColumnIndex(CHEMICAL_TABLE.RTECS);
                final int i_dux = cursor.getColumnIndex(CHEMICAL_TABLE.DUX);
                final int i_bzlb = cursor.getColumnIndex(CHEMICAL_TABLE.BZLB);
                final int i_un = cursor.getColumnIndex(CHEMICAL_TABLE.UN);
                final int i_iso = cursor.getColumnIndex(CHEMICAL_TABLE.ISO);
                final int i_wxhwbzbz = cursor.getColumnIndex(CHEMICAL_TABLE.WXHWBZBZ);
                final int i_wxhwbh = cursor.getColumnIndex(CHEMICAL_TABLE.WXHWBH);
                final int i_id = cursor.getColumnIndex(CHEMICAL_TABLE.ID);

                while (cursor.moveToNext()) {
                    final Chemical item = new Chemical();
                    item.id = cursor.getInt(i_id);
                    item.cName = cursor.getString(i_zhname);
                    item.eName = cursor.getString(i_enname);
                    item.casno = cursor.getString(i_cas);
                    item.fzs = cursor.getString(i_fzs);
                    item.mdair = cursor.getString(i_mdair);
                    item.clarity = cursor.getString(i_clarity);
                    item.ljwd = cursor.getString(i_ljwd);
                    item.bzsx = cursor.getString(i_bzsx);
                    item.ph = cursor.getString(i_ph);
                    item.odor = cursor.getString(i_odor);
                    item.zrwd = cursor.getString(i_zrwd);
                    item.ljyl = cursor.getString(i_ljyl);
                    item.fd = cursor.getString(i_fd);
                    item.zqywd = cursor.getString(i_zqywd);
                    item.bizhong = cursor.getString(i_bizhong);
                    item.sd = cursor.getString(i_sd);
                    item.mdwater = cursor.getString(i_mdwater);
                    item.rd = cursor.getString(i_rd);
                    item.wgxz = cursor.getString(i_wgxz);
                    item.zqmd = cursor.getString(i_zqmd);
                    item.bzxx = cursor.getString(i_bzxx);
                    item.taste = cursor.getString(i_taste);
                    item.color = cursor.getString(i_color);
                    item.state = cursor.getString(i_state);
                    item.rsr = cursor.getString(i_rsr);
                    item.jkwh = cursor.getString(i_jkwh);
                    item.wwxlb = cursor.getString(i_wwxlb);
                    item.sr = cursor.getString(i_sr);
                    item.pfjc = cursor.getString(i_pfjc);
                    item.yjjc = cursor.getString(i_yjjc);
                    item.xr = cursor.getString(i_xr);
                    item.wxtx = cursor.getString(i_wxtx);
                    item.mhff = cursor.getString(i_mhff);
                    item.xlcl = cursor.getString(i_xlcl);
                    item.cyzysx = cursor.getString(i_cyzysx);
                    item.qt = cursor.getString(i_qt);
                    item.yjfh = cursor.getString(i_yjfh);
                    item.stfh = cursor.getString(i_stfh);
                    item.sfh = cursor.getString(i_sfh);
                    item.jcxzz = cursor.getString(i_jcxzz);
                    item.gckz = cursor.getString(i_gckz);
                    item.hxxtfh = cursor.getString(i_hxxtfh);
                    item.wdx = cursor.getString(i_wdx);
                    item.rtecs = cursor.getString(i_rtecs);
                    item.dux = cursor.getString(i_dux);
                    item.bzlb = cursor.getString(i_bzlb);
                    item.un = cursor.getString(i_un);
                    item.iso = cursor.getString(i_iso);
                    item.wxhwbzbz = cursor.getString(i_wxhwbzbz);
                    item.wxhwbh = cursor.getString(i_wxhwbh);

                    itemList.add(item);
                }
                return itemList;
            }
            return itemList;
        }
    }

    private class ChemicalFeatureDBFactory implements IDBResult<String> {

        private String featureName;

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        @Override
        public List<String> resultFromCursor(int transaction, Cursor cursor) {
            final List<String> features = new LinkedList<String>();
            final int i_name = cursor
                    .getColumnIndex(featureName);
            while (cursor.moveToNext()) {
                String feature = cursor.getString(i_name);
                features.add(feature);
            }
            return features;
        }
    }
}
