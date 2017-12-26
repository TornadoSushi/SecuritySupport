package com.infinity.jerry.securitysupport.common.otherstuff.weihua;

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
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.ChemicalDataController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.SimpleCursorLoader;


/**
 * Created by edwardliu on 15/12/25.
 */
public class ChemicalSearchResultActivity extends TitlebarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    private String mSqlWhere;

    private static final String KEY = "ChemicalSearchResultActivity";
    static SmallActivityCache<String> sStoryHashMap = new SmallActivityCache<String>();

    public static void launchActivity(Context context, String sql) {
        Long key = sStoryHashMap.put(sql);

        Intent intent = new Intent(context, ChemicalSearchResultActivity.class);
        intent.putExtra(ChemicalSearchResultActivity.KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        return "查询结果";
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
                null, new String[]{"zhname"}, new int[]{R.id.actionRowNameTV}, 0);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mAdapter.getCursor();
                cursor.moveToPosition(position);
                int i_id = cursor.getColumnIndex("_id");
                int itemId = cursor.getInt(i_id);
                ChemicalDetailActivity.launchActivity(ChemicalSearchResultActivity.this, itemId);
            }
        });
        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ChemicalCursorLoader(this, mSqlWhere);
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

    private static final class ChemicalCursorLoader extends SimpleCursorLoader {

        private String sqlWhere;

        public ChemicalCursorLoader(Context context, String sqlWhere) {
            super(context);
            this.sqlWhere = sqlWhere;
        }

        @Override
        public Cursor loadInBackground() {
            return ChemicalDataController.getController().db_getChemicalCursorWithFilter(sqlWhere);
        }
    }
}
