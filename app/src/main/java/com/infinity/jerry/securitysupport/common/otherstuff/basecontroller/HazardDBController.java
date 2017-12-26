package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.database.Cursor;

import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.table.HAZARD_DANGER_TABLE;
import com.infinity.jerry.securitysupport.common.otherstuff.seriousdanger.HazardDanger;

import java.util.LinkedList;
import java.util.List;

public class HazardDBController extends DBController {
    private static HazardDBController sInstance;
    private final HazardDBFactory mDBFactory;

    private HazardDBController() {
        super();
        mDBFactory = new HazardDBFactory();
    }

    public static HazardDBController instance() {
        if (null == sInstance) {
            sInstance = new HazardDBController();
        }
        return sInstance;
    }

    /*
    Category DB
     */
    private List<HazardDanger> db_getHazards(String where, String order) {
        List<HazardDanger> items = getHelper().queryDB(mDBFactory,
                mDBFactory.TRANSACTION_QUERY_ALL_HAZARDS, false,
                HAZARD_DANGER_TABLE.TABLE_NAME, null, where, null, null,
                null, order, null);
        return items;
    }

    public List<HazardDanger> db_getHazardsByCategoryId(int categoryId) {
        String where = HAZARD_DANGER_TABLE.CATEGORY_ID + " = '" + categoryId + "'";
        String order = HAZARD_DANGER_TABLE.RANK_POSITION + " ASC";
        List<HazardDanger> items = db_getHazards(where, order);
        if (items != null && !items.isEmpty()) {
            return items;
        }
        return null;
    }

    public Cursor db_getHazardCursorByName(String name) {
        return getHelper().queryItems("SELECT DISTINCT danger_id as _id, category_id, name, rank_position, threshold, beta FROM HAZARD_DANGER WHERE name like '%" + name + "%' ORDER BY rank_position ASC");
    }

    private class HazardDBFactory implements IDBResult<HazardDanger> {
        public static final int TRANSACTION_QUERY_ALL_HAZARDS = 1;

        @Override
        public List<HazardDanger> resultFromCursor(int transaction, Cursor cursor) {
            final List<HazardDanger> items = new LinkedList<HazardDanger>();
            if (transaction == TRANSACTION_QUERY_ALL_HAZARDS) {
                final int i_id = cursor.getColumnIndex(HAZARD_DANGER_TABLE.CATEGORY_ID);
                final int i_name = cursor
                        .getColumnIndex(HAZARD_DANGER_TABLE.NAME);
                final int i_rank = cursor
                        .getColumnIndex(HAZARD_DANGER_TABLE.RANK_POSITION);
                final int i_threshold = cursor
                        .getColumnIndex(HAZARD_DANGER_TABLE.THRESHOLD);
                final int i_beta = cursor
                        .getColumnIndex(HAZARD_DANGER_TABLE.BETA);

                while (cursor.moveToNext()) {
                    final HazardDanger item = new HazardDanger();

                    item.categoryId = cursor.getInt(i_id);
                    item.name = cursor.getString(i_name);
                    item.rankPosition = cursor.getInt(i_rank);
                    item.threhold = cursor.getString(i_threshold);
                    item.beta = cursor.getString(i_beta);
                    items.add(item);
                }
            }
            return items;
        }
    }
}
