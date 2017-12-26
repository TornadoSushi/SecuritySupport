package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.database.Cursor;

import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.table.LAWSINFO_TABLE;
import com.infinity.jerry.securitysupport.common.otherstuff.law_rule.LawInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by edwardliu on 15/12/16.
 */
public class LawDBController extends DBController {
    private static LawDBController sInstance;
    private final LawInfoDBFactory mLawInfoDBFactory;

    private LawDBController() {
        super();
        mLawInfoDBFactory = new LawInfoDBFactory();
    }

    public static LawDBController instance() {
        if (null == sInstance) {
            sInstance = new LawDBController();
        }
        return sInstance;
    }

    /*
    LawInfo DB
     */
    private List<LawInfo> db_getLawInfos(String lawWhere, String order) {
        List<LawInfo> lawInfoList = getHelper().queryDB(mLawInfoDBFactory,
                LawInfoDBFactory.TRANSACTION_QUERY_ALL_LAWDETAILS, false,
                LAWSINFO_TABLE.TABLE_NAME, null, lawWhere, null, null,
                null, order, null);
        return lawInfoList;
    }

    public List<LawInfo> db_getQueriedLawInfos(String keyword) {
        String where = LAWSINFO_TABLE.CONTENT + " like '%" + keyword + "%'";
        return db_getLawInfos(where, null);
    }


    private class LawInfoDBFactory implements IDBResult<LawInfo> {

        public static final int TRANSACTION_QUERY_ALL_LAWDETAILS = 1;

        @Override
        public List<LawInfo> resultFromCursor(int transaction, Cursor cursor) {
            final List<LawInfo> itemList = new LinkedList<LawInfo>();
            if (transaction == TRANSACTION_QUERY_ALL_LAWDETAILS) {
                final int i_id = cursor.getColumnIndex(LAWSINFO_TABLE.INFO_ID);
                final int i_type = cursor
                        .getColumnIndex(LAWSINFO_TABLE.TYPE);
                final int i_region = cursor
                        .getColumnIndex(LAWSINFO_TABLE.REGION_ID);
                final int i_content = cursor
                        .getColumnIndex(LAWSINFO_TABLE.CONTENT);
                final int i_clause = cursor
                        .getColumnIndex(LAWSINFO_TABLE.CLAUSE);

                while (cursor.moveToNext()) {
                    final LawInfo item = new LawInfo();

                    item.infoId = cursor.getInt(i_id);
                    item.type = cursor.getInt(i_type);
                    item.regionId = cursor.getInt(i_region);
                    item.content = cursor.getString(i_content);
                    item.clause = cursor.getString(i_clause);
                    itemList.add(item);
                }
            }
            return itemList;
        }
    }
}
