package com.infinity.jerry.securitysupport.common.otherstuff.law_rule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.SearchListener;

/**
 */
public class SLSearchView extends RelativeLayout {

    private EditText mSearchET;
    private TextView mSearchTV;

    private SearchListener mListener;

    public SLSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_search_bar, this);

        mSearchET = (EditText) findViewById(R.id.searchBarET);
        mSearchTV = (TextView) findViewById(R.id.searchBarTV);

        mSearchET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    onSearchStarted();
                    return true;
                }
                return false;
            }
        });
        mSearchTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchStarted();
            }
        });
    }

    public void setSearchWord(String searchWord) {
        mSearchET.setText(searchWord);
    }

    private String searchWord() {
        return mSearchET.getText().toString();
    }

    private void onSearchStarted() {
        String searchWord = searchWord();
//        if (!searchWord.trim().isEmpty() && null != mListener) {
//            mListener.onSearchStarted(searchWord);
//        }
        if (null != mListener) {
            mListener.onSearchStarted(searchWord);
        }
    }

    public void setSearchListener(SearchListener listener) {
        mListener = listener;
    }
}
