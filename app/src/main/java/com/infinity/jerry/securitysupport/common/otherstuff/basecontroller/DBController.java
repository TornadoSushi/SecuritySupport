package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.content.ContentValues;

/**
 * Created by edwardliu on 15/12/16.
 */
public class DBController {
    final ContentValues mCommonValues;
    private DataController mController;

    DBController() {
        mController = DataController.getController();
        mCommonValues = new ContentValues();
    }

    public DataController.DataHelper getHelper() {
        return mController.getHelper();
    }
}
