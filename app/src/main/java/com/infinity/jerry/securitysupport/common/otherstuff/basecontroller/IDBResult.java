package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.database.Cursor;

import java.util.List;

public interface IDBResult<T> {

    /**
     * create the instance from cursor result
     *
     * @param transaction the transaction id, you can use this to indentify the
     *                    different query requirement
     * @param cursor      the database query return
     * @return
     */
    public List<T> resultFromCursor(final int transaction, final Cursor cursor);

    /**
     * the result callback interface
     *
     * @param <T>
     */
    public interface Callback<T> {

        /**
         * result callback, this method run in the ui, so can modify the ui
         *
         * @param t
         */
        public void onResultCallback(T t);
    }
}