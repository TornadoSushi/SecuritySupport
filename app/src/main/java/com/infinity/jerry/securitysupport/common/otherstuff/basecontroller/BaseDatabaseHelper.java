package com.infinity.jerry.securitysupport.common.otherstuff.basecontroller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link SQLiteOpenHelper} a simple implements.</br> you can extends this, and
 * impelment {@link #initDatabase(SQLiteDatabase)} and
 * {@link #updateDatabase(SQLiteDatabase, int, int)} methods. all sql sentences
 * in this two method will using transaction, the detail check
 * {@link SQLiteDatabase#beginTransaction()}.</br>also supply
 * {@link #initDatabaseFromFile(SQLiteDatabase, InputStream)} method to read sql
 * sentences to init database.
 *
 * @version 1.0
 */
public class BaseDatabaseHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = "BaseDatabaseHelper";

    static final int DEFAULT_BUFFER = 1024 * 4;

    /**
     * default database version
     */
    public static final int INIT_DATABASE_VERSION = 1;

    private String databaseName;

    final Object mLocker = new Object();

    Handler mHandler;

    ExecutorService mExecutor;

    /**
     * using {@link #INIT_DATABASE_VERSION} as the default data version to init.
     *
     * @param context
     * @param dname
     */
    public BaseDatabaseHelper(Context context, String dname) {
        this(context, dname, INIT_DATABASE_VERSION);
    }

    /**
     * init a database with the name and version.
     *
     * @param context
     * @param dname   database name
     * @param version database version code
     */
    public BaseDatabaseHelper(Context context, String dname, int version) {
        super(context, dname, null, version);
        databaseName = dname;

        mHandler = new Handler(context.getMainLooper());
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "database:" + databaseName + " init...");
        db.beginTransaction();
        try {
            initDatabase(db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(LOG_TAG, "database:" + databaseName + " init error", e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * init database.
     *
     * @param db
     */
    protected void initDatabase(SQLiteDatabase db) {

    }

    /**
     * update database.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    protected void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "database:" + databaseName + " update...");
        db.beginTransaction();
        try {
            updateDatabase(db, oldVersion, newVersion);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(LOG_TAG, "database:" + databaseName + " update error", e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * using the stream to init database data. <br/>
     * the stream must like the every line has insert table sql.
     *
     * @param db
     * @param ins
     */
    public static void initDatabaseFromFile(SQLiteDatabase db, InputStream ins) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(ins), DEFAULT_BUFFER);
            String line = null;
            db.beginTransaction();
            while ((line = br.readLine()) != null) {
                db.execSQL(line);
            }
            db.setTransactionSuccessful();
        } catch (IOException e) {
            Log.e(LOG_TAG, "read database init file error");
        } finally {
            db.endTransaction();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "buffer reader close error");
                }
            }
        }
    }

    /**
     * query the database and return result
     *
     * @param factory       result factory, see {@link IDBResult}
     * @param transaction   this query tranaction
     * @param sql
     * @param selectionArgs
     * @return
     */
    public <E> List<E> rawQuery(IDBResult<E> factory, int transaction, String sql, String[] selectionArgs) {
        Cursor cursor = null;
        List<E> result = null;
        try {
            // lock the database
            synchronized (mLocker) {
                SQLiteDatabase database = getWritableDatabase();
                cursor = database.rawQuery(sql, selectionArgs);
                if (cursor != null && cursor.getCount() > 0) {
                    result = factory.resultFromCursor(transaction, cursor);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "rawQuery error:", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * query the database and return result
     *
     * @param factory       result factory, see {@link IDBResult}
     * @param transaction   this query tranaction
     * @param distinct
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    public <E> List<E> queryDB(IDBResult<E> factory, int transaction, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                               String orderBy, String limit) {
        Cursor cursor = null;
        List<E> result = null;
        try {
            // lock the database
            synchronized (mLocker) {
                SQLiteDatabase database = getWritableDatabase();
                cursor = database.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                if (cursor != null && cursor.getCount() > 0) {
                    result = factory.resultFromCursor(transaction, cursor);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "queryDB error:", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * like {@link #queryDB} , but this method is async
     *
     * @param factory
     * @param callback      when query finish will callback this interface
     * @param transaction
     * @param distinct
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     */
    public <E> void asyncQueryDB(final IDBResult<E> factory, final IDBResult.Callback<List<E>> callback, final int transaction, final boolean distinct, final String table, final String[] columns,
                                 final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<E> result = queryDB(factory, transaction, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultCallback(result);
                    }
                });
            }
        });
    }

    /**
     * database running
     *
     * @param runner
     */
    public void runDBRunnable(DBRunnable runner) {
        synchronized (mLocker) {
            final SQLiteDatabase database = getWritableDatabase();
            try {
                runner.onDBRunning(database);
            } catch (Exception e) {
                Log.e(LOG_TAG, "ruDBRunnable error:", e);
            }
        }
    }

    /**
     * async {@link #runDBRunnable(DBRunnable)}
     *
     * @param runner
     */
    public void asyncRunDBRunnable(final DBRunnable runner) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                runDBRunnable(runner);
            }
        });
    }

    /**
     * like {@link #runDBRunnable(DBRunnable)}, but all sql will run in the
     * transaction, if have one error, the transaction dont commit to the
     * database
     *
     * @param runner
     */
    public void runDBRunnableWithTransaction(DBRunnable runner) {
        synchronized (mLocker) {
            final SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            try {
                runner.onDBRunning(database);
                database.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e(LOG_TAG, "runDBRunnableWithTransaction error:", e);
            } finally {
                database.endTransaction();
            }
        }
    }

    /**
     * async {@link #runDBRunnableWithTransaction(DBRunnable)}
     *
     * @param runner
     */
    public void asyncRunDBRunnableWithTransaction(final DBRunnable runner) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                runDBRunnableWithTransaction(runner);
            }
        });
    }

    /**
     * override the close method, when the database is used, the close method
     * need wait
     */
    @Override
    public synchronized void close() {
        synchronized (mLocker) {
            super.close();
        }
    }

    public void release() {
        mExecutor.shutdown();
        close();
    }

}
