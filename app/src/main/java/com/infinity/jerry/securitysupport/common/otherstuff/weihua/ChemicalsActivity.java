package com.infinity.jerry.securitysupport.common.otherstuff.weihua;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.SearchbarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.WindowUtils;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.ChemicalDataController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.SimpleCursorLoader;


/**
 * Created by edwardliu on 15/11/23.
 */
public class ChemicalsActivity extends SearchbarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;

    @Override
    protected String centerTitle() {
        return "危化品查询";
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
    protected View onCreateChildView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_chemicals, null);
        final ListView lv = (ListView) view.findViewById(R.id.chemicalsLV);
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
                ChemicalDetailActivity.launchActivity(ChemicalsActivity.this, itemId);
            }
        });
        return view;
    }

    @Override
    protected boolean beforeSearchStarted(String searchWord) {
        ChemicalSearchResultActivity.launchActivity(ChemicalsActivity.this, "(zhname like '%" + searchWord +
                "%' or enname like '%" + searchWord + "%')");
        return false;
    }

    @Override
    protected void onAsyncSearchStarted(final String searchWord) {
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {
        TextView tv = new TextView(this);
        tv.setText("特征");
        tv.setBackgroundResource(R.drawable.main_btn_background);
        int horizonPadding = WindowUtils.dip2px(this, 16);
        int verticalPadding = WindowUtils.dip2px(this, 8);
        tv.setPadding(horizonPadding, verticalPadding, horizonPadding, verticalPadding);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, WindowUtils.sp2px(this, 16));
        ll.addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChemicalsActivity.this, ChemicalFeatureActivity.class));
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ChemicalCursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private static final class ChemicalCursorLoader extends SimpleCursorLoader {

        public ChemicalCursorLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            return ChemicalDataController.getController().db_getChemicalCursor();
        }
    }
}
