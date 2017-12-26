package com.infinity.jerry.securitysupport.common.otherstuff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.law_rule.SLSearchView;

public abstract class SearchbarActivity extends TitlebarActivity {

    private SLSearchView mSearchView;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_searchbar, null);
        mSearchView = (SLSearchView) view.findViewById(R.id.searchBarSV);
        mSearchView.setSearchListener(new SearchListener() {
            @Override
            public void onSearchStarted(final String keyword) {
                startSearch(keyword);
            }
        });
        RelativeLayout container = (RelativeLayout) view.findViewById(R.id.searchContainerRL);
        View childView = onCreateChildView();
        if (null != childView) {
            container.addView(childView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        return view;
    }

    protected void startSearch(final String keyword) {
        if (beforeSearchStarted(keyword)) {
            mSearchView.setSearchWord(keyword);
            showLoading();
            submitWorker(new Runnable() {
                @Override
                public void run() {
                    onAsyncSearchStarted(keyword);
                    postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                        }
                    });
                }
            });
        }
    }

    protected void clearSearch() {
        mSearchView.setSearchWord("");
    }

    protected abstract View onCreateChildView();

    /**
     * Function did something before search started, it needs to return true if we want the search worked.
     *
     * @param searchWord
     * @return
     */
    protected abstract boolean beforeSearchStarted(String searchWord);

    protected abstract void onAsyncSearchStarted(String searchWord);
}
