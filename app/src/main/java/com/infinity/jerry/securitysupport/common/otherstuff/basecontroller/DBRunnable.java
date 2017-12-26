package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.database.sqlite.SQLiteDatabase;

public interface DBRunnable {

    /**
     * the database running method
     * 
     * @param database
     */
    public void onDBRunning(SQLiteDatabase database);
}