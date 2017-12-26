package com.infinity.jerry.securitysupport.common.otherstuff.seriousdanger;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.HazardDBController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.SimpleCursorLoader;


/**
 * Created by edwardliu on 16/1/7.
 */
public class HazardSearchActivity extends TitlebarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    private String mSqlWhere;

    private static final String KEY = "HazardSearchActivity";
    static SmallActivityCache<String> sStoryHashMap = new SmallActivityCache<String>();

    public static void launchActivityForResult(Activity context, String sql, int requestCode) {
        Long key = sStoryHashMap.put(sql);

        Intent intent = new Intent(context, HazardSearchActivity.class);
        intent.putExtra(HazardSearchActivity.KEY, key);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected String centerTitle() {
        return "搜索结果";
    }

    @Override
    protected boolean hasBackOption() {
        return true;
    }

    @Override
    protected View.OnClickListener rightClickListener() {
        return null;
    }

    @Override
    protected View onCreateView() {
        mSqlWhere = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            mSqlWhere = sStoryHashMap.get(extras.getLong(KEY));
        }
        if (null == mSqlWhere || mSqlWhere.trim().isEmpty()) {
            finish();
            return null;
        }
        View view = getLayoutInflater().inflate(R.layout.activity_chemicals_searchresult, null);
        final ListView lv = (ListView) view.findViewById(R.id.chemicalsSearchLV);

        showLoading();
        getLoaderManager().initLoader(0x01, null, this);
        mAdapter = new SimpleCursorAdapter(this, R.layout.view_string_actionrow,
                null, new String[]{"name"}, new int[]{R.id.actionRowNameTV}, 0);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mAdapter.getCursor();
                cursor.moveToPosition(position);
                int dangerId = cursor.getInt(0);
                int categoryId = cursor.getInt(1);
                String name = cursor.getString(2);
                int rankPosition = cursor.getInt(3);
                String threshold = cursor.getString(4);
                String beta = cursor.getString(5);

                Intent intent = new Intent();
                intent.putExtra("HazardCategoryId", categoryId);
                intent.putExtra("HazardName", name);
                intent.putExtra("HazardThreshold", threshold);
                intent.putExtra("HazardBeta", beta);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new HazardCursorLoader(this, mSqlWhere);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        hideLoading();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private static final class HazardCursorLoader extends SimpleCursorLoader {

        private String sqlWhere;

        public HazardCursorLoader(Context context, String sqlWhere) {
            super(context);
            this.sqlWhere = sqlWhere;
        }

        @Override
        public Cursor loadInBackground() {
            return HazardDBController.instance().db_getHazardCursorByName(sqlWhere);
        }
    }
}
