package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Intent;
import android.view.View;

/**
 * Created by edwardliu on 15/12/29.
 */
public interface ExpanableRowBuilder {
    public int mainViewResourceId();

    public void onTitleViewCreated(View view);

    public int titleViewResourceId();

    public int expandImageResourceId();

    public void setChildCount(int count);

    public View createExpandChildView(Intent data, int index);

    public void onChildViewsCreated();

    public void recycle();
}
