package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.infinity.jerry.securitysupport.common.otherstuff.FireDistDataMgr;
import com.infinity.jerry.securitysupport.common.otherstuff.MainBuildingItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingCategoryItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingDistance;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingInstance;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingMap;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingPropItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingPropOptionItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.FIRE_DISTANCE_TABLE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-04.
 */
public class FireDistanceDBController {
    static final String TAG = "FireDistanceDBController";
    private static FireDistanceDBController sController;
    private DataHelper mDataHelper;
    private FireDistanceMainBuildingFactory mMainBuildingFactory;
    private FireDistanceMainBuildingCategoryFactory mMainBuildingCategoryFactory;
    private FireDistanceMainBuildingPropFactory mMainBuildinPropFactory;
    private FireDistanceOptionFactory mOptionFactory;
    private FireDistanceNearbyBuildingFactory mNearbyIDSFactory;
    private FireDistanceInstanceFactory mInstanceFactory;
    private FireDistanceDistanceFactory mDistanceFactry;
    private FireDistanceNotesFactory mNoteFactory;
    private FireDistanceMapFactory mMapFactory;
    private final ContentValues mCommonValues;

    private FireDistanceDBController(Context context) {
        mCommonValues = new ContentValues();
        mDataHelper = new DataHelper(context);
        mMainBuildingFactory = new FireDistanceMainBuildingFactory();
        mMainBuildingCategoryFactory = new FireDistanceMainBuildingCategoryFactory();
        mMainBuildinPropFactory = new FireDistanceMainBuildingPropFactory();
        mOptionFactory = new FireDistanceOptionFactory();
        mNearbyIDSFactory = new FireDistanceNearbyBuildingFactory();
        mInstanceFactory = new FireDistanceInstanceFactory();
        mDistanceFactry = new FireDistanceDistanceFactory();
        mNoteFactory = new FireDistanceNotesFactory();
        mMapFactory = new FireDistanceMapFactory();
    }

    public static void initController(Context context) {checkDatabaseAndCP(context);
        if (sController == null) {
            sController = new FireDistanceDBController(context);
        }
    }

    private static void checkDatabaseAndCP(Context context) {
        String newName = "fhjj.db";

        String DB_PATH = "/data/data/" + context.getPackageName()
                + "/databases/";
        File baseFile = new File(DB_PATH);
        baseFile.mkdirs();

        File newFile = new File(DB_PATH + newName);
        if (!newFile.exists()) {
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

    public static FireDistanceDBController getCtrl() {
        if (sController == null) {
            throw new RuntimeException(
                    "must after invoke 'initController' method");
        }
        return sController;
    }

    public void db_getMainBuildings(/*final IDBResult.Callback<List<MainBuildingItem>> callback*/) {
        // mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
        // @Override
        //  public void onDBRunning(SQLiteDatabase database) {
        List<MainBuildingItem> list = mDataHelper.queryDB(mMainBuildingFactory,
                mMainBuildingFactory.TRANSACTION_QUERY_ALL_MAIN_BUILDING,
                false,
                FIRE_DISTANCE_TABLE.STRUCTURE,
                null, null, null, null, null,
                FIRE_DISTANCE_TABLE.STRUCT_ID,
                null);
        FireDistDataMgr.getInst().listMainBuildings = list;
        // callback.onResultCallback(list);
        //}
        // });
    }

    public void db_getMainBuildingsCategry(/*final IDBResult.Callback<List<BuildingCategoryItem>> callback*/) {
        //mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
        // @Override
        //public void onDBRunning(SQLiteDatabase database) {
        List<BuildingCategoryItem> list = mDataHelper.queryDB(mMainBuildingCategoryFactory,
                FireDistanceMainBuildingCategoryFactory.TRANSACTION_QUERY_ALL_MAIN_CATEGORY,
                false,
                FIRE_DISTANCE_TABLE.STRUCT_CATEGORY,
                null, null, null, null, null,
                FIRE_DISTANCE_TABLE.DISPLAY_ORDER,
                null);
        FireDistDataMgr.listMainbuildingCate = list;
        // callback.onResultCallback(list);
        // }
        //});
    }

    public List<BuildingMap> db_getMap(String whereCondication) {
        List<BuildingMap> list = mDataHelper.queryDB(mMapFactory,
                FireDistanceMapFactory.TRANSACTION_QUERY_MAPPED_MAP,
                false,
                FIRE_DISTANCE_TABLE.STRUCTURE_MAP,
                null, whereCondication, null, null, null, null, null);
        return list;
    }

    public List<Integer> db_getNearbyBuildingIDS(final String whereCondition/*, final IDBResult.Callback<List<Integer>> callback*/) {
        /*
    }
        mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
            @Override
            public void onDBRunning(SQLiteDatabase database) {
*/
        List<Integer> list = mDataHelper.queryDB(mNearbyIDSFactory,
                FireDistanceNearbyBuildingFactory.TRANSACTION_QUERY_MAPPED_NEARBY_BUILDING,
                false,
                FIRE_DISTANCE_TABLE.NEIGHBOUR_MAPPING,
                null, whereCondition, null, null, null,
                null,
                null);
        FireDistDataMgr.listNearbyBuildingIDS = list;
        return list;
                /*
                callback.onResultCallback(list);
                }
        });
        */
    }

    public void db_getMainBuildingsProp(final String whereCondition, final IDBResult.Callback<List<BuildingPropItem>> callback) {
        mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
            @Override
            public void onDBRunning(SQLiteDatabase database) {
                List<BuildingPropItem> list = mDataHelper.queryDB(mMainBuildinPropFactory,
                        FireDistanceMainBuildingPropFactory.TRANSACTION_QUERY_ALL_MAIN_PROP,
                        false,
                        FIRE_DISTANCE_TABLE.STRUCT_PROP,
                        null, whereCondition, null, null, null,
                        FIRE_DISTANCE_TABLE.DISPLAY_ORDER,
                        null);
                FireDistDataMgr.listMainBuildingsProp = list;
                callback.onResultCallback(list);
            }
        });
    }

    public void db_getNearbyBuildingsProp(final String whereCondition, final IDBResult.Callback<List<BuildingPropItem>> callback) {
        mDataHelper.asyncRunDBRunnableWithTransaction(new DBRunnable() {
            @Override
            public void onDBRunning(SQLiteDatabase database) {
                List<BuildingPropItem> list = mDataHelper.queryDB(mMainBuildinPropFactory,
                        FireDistanceMainBuildingPropFactory.TRANSACTION_QUERY_ALL_MAIN_PROP,
                        false,
                        FIRE_DISTANCE_TABLE.STRUCT_PROP,
                        null, whereCondition, null, null, null,
                        FIRE_DISTANCE_TABLE.DISPLAY_ORDER,
                        null);
                FireDistDataMgr.listNearbyBuildingsProp = list;
                callback.onResultCallback(list);
            }
        });
    }

    public List<BuildingPropOptionItem> db_getOptions(final String whereCondition, int propID) {
        List<BuildingPropOptionItem> list = mDataHelper.queryDB(mOptionFactory,
                FireDistanceOptionFactory.TRANSACTION_QUERY_ALL_OPTION,
                false,
                FIRE_DISTANCE_TABLE.STRUCT_OPTION,
                null, whereCondition, null, null, null,
                FIRE_DISTANCE_TABLE.DISPLAY_ORDER, null);
        return list;
    }

    public List<BuildingInstance> db_getInstanceID(final String whereCondication, final String order, final String limit) {
        List<BuildingInstance> list = mDataHelper.queryDB(mInstanceFactory,
                FireDistanceInstanceFactory.TRANSACTION_QUERY_INSTANCE,
                false,
                FIRE_DISTANCE_TABLE.STRUCT_INSTANCE,
                null, whereCondication, null, null, null,
                order, limit);
        return list;
    }

    public List<BuildingDistance> db_getDistance(final String whereCondication) {
        List<BuildingDistance> list = mDataHelper.queryDB(mDistanceFactry,
                FireDistanceDistanceFactory.TRANSACTION_QUERY_FIRE_DISTANCE,
                false,
                FIRE_DISTANCE_TABLE.DISTANCE,
                null, whereCondication, null, null, null,
                null, null);
        return list;
    }

    public List<String> db_getNote(final String whereCondication) {
        List<String> list = mDataHelper.queryDB(mNoteFactory,
                FireDistanceNotesFactory.TRANSACTION_QUERY_GET_NOTE,
                false,
                FIRE_DISTANCE_TABLE.DISTANCE_NOTES,
                null, whereCondication, null, null, null,
                null, null);
        return list;
    }

    class DataHelper extends BaseDatabaseHelper {
        protected static final String DB_NAME = "fhjj.db";
        protected static final int DB_VERSION = 1;

        public DataHelper(Context context) {
            super(context, DB_NAME, DB_VERSION);
        }
    }

    class FireDistanceMainBuildingCategoryFactory implements IDBResult<BuildingCategoryItem> {
        public static final int TRANSACTION_QUERY_ALL_MAIN_CATEGORY = 1;

        @Override
        public List<BuildingCategoryItem> resultFromCursor(int transaction, Cursor cursor) {
            final List<BuildingCategoryItem> listItems = new LinkedList<BuildingCategoryItem>();
            if (transaction == TRANSACTION_QUERY_ALL_MAIN_CATEGORY) {
                final int i_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.CATE_ID);
                final int i_cateName = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.CATE_NAME);
                final int i_displayOrder = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.DISPLAY_ORDER);
                while (cursor.moveToNext()) {
                    BuildingCategoryItem item = new BuildingCategoryItem();
                    item.id = cursor.getInt(i_id);
                    item.cateName = cursor.getString(i_cateName);
                    item.displayOrder = cursor.getInt(i_displayOrder);
                    listItems.add(item);
                    Log.d("IceLi:", "FireDistanceMainBuildingCategoryFactory::id=" + item.id + ", cate_name=" + item.cateName +
                            ", order=" + item.displayOrder);
                }
            }
            return listItems;
        }
    }

    class FireDistanceMainBuildingPropFactory implements IDBResult<BuildingPropItem> {
        public static final int TRANSACTION_QUERY_ALL_MAIN_PROP = 1;

        @Override
        public List<BuildingPropItem> resultFromCursor(int transaction, Cursor cursor) {
            final List<BuildingPropItem> listItems = new LinkedList<BuildingPropItem>();
            if (transaction == TRANSACTION_QUERY_ALL_MAIN_PROP) {
                final int i_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.PROP_ID);
                final int i_inputType = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.INPUT_TYPE);
                final int i_displayName = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.DISPLAY_NAME);
                final int i_displayOrder = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.ORDER);
                final int i_comment = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.PROP_REMARK);
                final int i_mainBuildingId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_ID);
                final int i_parentId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.PARENT_ID);
                final int i_calId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.CAL_ID);
                while (cursor.moveToNext()) {
                    BuildingPropItem item = new BuildingPropItem();
                    item.propId = cursor.getInt(i_id);
                    item.inputType = cursor.getInt(i_inputType);
                    item.displayName = cursor.getString(i_displayName);
                    item.displayOrder = cursor.getInt(i_displayOrder);
                    item.comment = cursor.getString(i_comment);
                    item.mainBuildingId = cursor.getInt(i_mainBuildingId);
                    item.parentId = cursor.getInt(i_parentId);
                    item.calID = cursor.getInt(i_calId);
                    listItems.add(item);
                    Log.d("IceLi:", "FireDistanceMainBuildingPropFactory::prop id=" + item.propId + ", displayName=" + item.displayName + ", inputTye=" + item.inputType +
                            ", order=" + item.displayOrder + ", mainBuildingId=" + item.mainBuildingId + ", parentId=" + item.parentId + ", comment=" + item.comment);
                }
            }
            return listItems;
        }
    }

    class FireDistanceInstanceFactory implements IDBResult<BuildingInstance> {
        public static final int TRANSACTION_QUERY_INSTANCE = 1;

        @Override
        public List<BuildingInstance> resultFromCursor(int transaction, Cursor cursor) {
            final List<BuildingInstance> listItem = new LinkedList<BuildingInstance>();
            if (transaction == TRANSACTION_QUERY_INSTANCE) {
                final int i_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.TYPE_ID);
                final int i_buildingProp1 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "1");
                final int i_buildingProp2 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "2");
                final int i_buildingProp3 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "3");
                final int i_buildingProp4 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "4");
                final int i_buildingProp5 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "5");
                final int i_buildingProp6 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "6");
                final int i_buildingProp7 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "7");
                final int i_buildingProp8 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "8");
                final int i_buildingProp9 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "9");
                final int i_buildingProp10 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "10");

                while (cursor.moveToNext()) {
                    BuildingInstance bi = new BuildingInstance();
                    bi.typeID = cursor.getInt(i_id);
                    bi.buildingProp1 = cursor.getString(i_buildingProp1);
                    bi.buildingProp2 = cursor.getString(i_buildingProp2);
                    bi.buildingProp3 = cursor.getString(i_buildingProp3);
                    bi.buildingProp4 = cursor.getString(i_buildingProp4);
                    bi.buildingProp5 = cursor.getString(i_buildingProp5);
                    bi.buildingProp6 = cursor.getString(i_buildingProp6);
                    bi.buildingProp7 = cursor.getString(i_buildingProp7);
                    bi.buildingProp8 = cursor.getString(i_buildingProp8);
                    bi.buildingProp9 = cursor.getString(i_buildingProp9);
                    bi.buildingProp10 = cursor.getString(i_buildingProp10);

                    listItem.add(bi);
                    Log.d("IceLi", "FireDistanceDBController::typeID = " + bi.typeID);
                }
            }
            return listItem;
        }
    }

    class FireDistanceOptionFactory implements IDBResult<BuildingPropOptionItem> {
        public static final int TRANSACTION_QUERY_ALL_OPTION = 1;

        @Override
        public List<BuildingPropOptionItem> resultFromCursor(int transaction, Cursor cursor) {
            final List<BuildingPropOptionItem> listItem = new LinkedList<BuildingPropOptionItem>();
            if (transaction == TRANSACTION_QUERY_ALL_OPTION) {
                final int i_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.OPTION_ID);
                final int i_name = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.DISPLAY_NAME);
                final int i_order = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.DISPLAY_ORDER);
                final int i_cal = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.CAL_VALUE);
                final int i_propId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.PROP_ID);
                final int i_fatherId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.FATHER_OPTION_ID);

                while (cursor.moveToNext()) {
                    BuildingPropOptionItem item = new BuildingPropOptionItem();
                    item.id = cursor.getInt(i_id);
                    item.displayName = cursor.getString(i_name);
                    item.displayOrder = cursor.getInt(i_order);
                    item.calValue = cursor.getInt(i_cal);
                    item.propID = cursor.getInt(i_propId);
                    item.fatherOptionID = cursor.getInt(i_fatherId);
                    listItem.add(item);

                    Log.d("IceLi:", "FireDistanceDBController::id=" + item.id + ", name=" + item.displayName +
                            ", order=" + item.displayOrder + ", calValue=" + item.calValue + ", propID=" + item.propID);
                }
            }
            return listItem;
        }
    }

    class FireDistanceNotesFactory implements IDBResult<String> {
        public static final int TRANSACTION_QUERY_GET_NOTE = 1;

        @Override
        public List<String> resultFromCursor(int transaction, Cursor cursor) {
            final List<String> listItems = new LinkedList<String>();
            if (transaction == TRANSACTION_QUERY_GET_NOTE) {
                final int i_note = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.NOTE);
                final int i_gb = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.GB_CATE);

                while (cursor.moveToNext()) {
                    //BuildingNote item = new BuildingNote();
                    // item.gbCategory = cursor.getInt(i_gb);
                    String note = cursor.getString(i_note);
                    // item.note = note;
                    listItems.add(note);
                    Log.d("IceLi:", "FireDistanceDBController::Note = " + note == null ? "Nothing" : note);
                }
            }
            return listItems;
        }
    }

    class FireDistanceNearbyBuildingFactory implements IDBResult<Integer> {
        public static final int TRANSACTION_QUERY_MAPPED_NEARBY_BUILDING = 1;

        @Override
        public List<Integer> resultFromCursor(int transaction, Cursor cursor) {
            final List<Integer> listItems = new LinkedList<Integer>();
            if (transaction == TRANSACTION_QUERY_MAPPED_NEARBY_BUILDING) {
                final int i_nearbyBuildingId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.NEIGHBOR_STRUCT_ID);

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(i_nearbyBuildingId);
                    listItems.add(id);
                    Log.d("IceLi:", "FireDistanceDBController::Nearby IDS = " + id);
                }
            }
            return listItems;
        }
    }

    class FireDistanceMapFactory implements IDBResult<BuildingMap> {
        public static final int TRANSACTION_QUERY_MAPPED_MAP = 1;

        @Override
        public List<BuildingMap> resultFromCursor(int transaction, Cursor cursor) {
            final List<BuildingMap> listItems = new LinkedList<BuildingMap>();
            if (transaction == TRANSACTION_QUERY_MAPPED_MAP) {
                final int i_Id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.ID);
                final int i_primary_struct_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.PRIMARY_STRUCT_ID);
                final int i_map_struct_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.MAP_STRUCT_ID);
                final int i_secondary_struct_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.SECONDARY_STRUCT_ID);
                final int i_primary_prop_value = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.PRIMARY_PROP_VALUE);
                final int i_map_prop_value = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.MAP_PROP_VALUE);
                while (cursor.moveToNext()) {
                    BuildingMap mapItem = new BuildingMap();
                    mapItem.id = cursor.getInt(i_Id);
                    mapItem.primary_struct_id = cursor.getInt(i_primary_struct_id);
                    mapItem.map_sruct_id = cursor.getInt(i_map_struct_id);
                    mapItem.secondary_struct_id = cursor.getInt(i_secondary_struct_id);
                    mapItem.primary_prop_value = cursor.getString(i_primary_prop_value);
                    mapItem.map_prop_value = cursor.getString(i_map_prop_value);
                    listItems.add(mapItem);
                    Log.d("IceLi:", "FireDistanceDBController::getMap:id=" + mapItem.id + ", psi=" + mapItem.primary_struct_id
                            + "msi=" + mapItem.map_sruct_id + ", ssi=" + mapItem.secondary_struct_id + ", ppv=" + mapItem.primary_prop_value
                            + ", mpv=" + mapItem.map_prop_value);
                }
            }
            return listItems;
        }
    }

    class FireDistanceDistanceFactory implements IDBResult<BuildingDistance> {
        public static final int TRANSACTION_QUERY_FIRE_DISTANCE = 1;

        @Override
        public List<BuildingDistance> resultFromCursor(int transaction, Cursor cursor) {
            final List<BuildingDistance> listItems = new LinkedList<BuildingDistance>();
            if (transaction == TRANSACTION_QUERY_FIRE_DISTANCE) {
                final int i_caseId = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.CASE_ID);
                final int i_gbCate = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.GB_CATE);
                final int i_inst1 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_INSTANCE_1);
                final int i_inst2 = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_INSTANCE_2);
                final int i_fireRemark = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.FIRE_RESITRCT_REMARK);
                final int i_fireResitrct = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.FIRE_RESITRCT);

                while (cursor.moveToNext()) {
                    BuildingDistance distance = new BuildingDistance();
                    distance.caseID = cursor.getInt(i_caseId);
                    ;
                    distance.gbCatetory = cursor.getInt(i_gbCate);
                    distance.buildingInstance1 = cursor.getInt(i_inst1);
                    distance.buildingInstance2 = cursor.getInt(i_inst2);
                    distance.fireResitrctRemark = cursor.getString(i_fireRemark);
                    distance.fireResitrct = cursor.getString(i_fireResitrct);
                    listItems.add(distance);
                    Log.d("IceLi:", "FireDistanceDBController::FireDistance case id = " + distance.caseID + "," + distance.fireResitrctRemark + " " + distance.fireResitrct);
                }
            }
            return listItems;
        }
    }

    class FireDistanceMainBuildingFactory implements IDBResult<MainBuildingItem> {
        public static final int TRANSACTION_QUERY_ALL_MAIN_BUILDING = 1;

        @Override
        public List<MainBuildingItem> resultFromCursor(int transaction, Cursor cursor) {
            final List<MainBuildingItem> listItems = new LinkedList<MainBuildingItem>();
            if (transaction == TRANSACTION_QUERY_ALL_MAIN_BUILDING) {
                final int i_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.STRUCT_ID);
                final int i_name = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.NAME);
                final int i_order = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.ORDER);
                final int i_main_building = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.MAIN_STRUCT);
                final int i_comments = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.COMMENTS);
                final int i_cate_id = cursor.getColumnIndex(FIRE_DISTANCE_TABLE.CATE_ID);

                while (cursor.moveToNext()) {
                    MainBuildingItem item = new MainBuildingItem();
                    item.id = cursor.getInt(i_id);
                    item.name = cursor.getString(i_name);
                    item.displayOrder = cursor.getInt(i_order);
                    item.isMainBuilding = cursor.getInt(i_main_building);
                    item.buildingComments = cursor.getString(i_comments);
                    item.category_id = cursor.getInt(i_cate_id);

                    Log.d("IceLi:", "FireDistanceDBController::id=" + item.id + ", name=" + item.name +
                            ", order=" + item.displayOrder + ", mainbuilding=" + item.isMainBuilding +
                            ", comments=" + item.buildingComments + ", cate_id=" + item.category_id);
                    listItems.add(item);
                }
            }
            return listItems;
        }
    }
}





