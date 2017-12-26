package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.table;

/**
 * Created by edwardliu on 16/2/23.
 */
public class ACCIDENT_TABLE {
    public static final String TABLE_NAME = "category";
    public static final String ID = "_id";
    public static final String NAME = "name";

    public static class ACCIDENT_LIST_TABLE {
        public static final String TABLE_NAME = "accidentList";
        public static final String ID = "_id";
        public static final String PARENT_ID = "parent_id";
        public static final String NAME = "name";
        public static final String KNOWLEDGE = "knowledge";
        public static final String SAVE = "save";
        public static final String ADVICE = "advice";
    }
}
