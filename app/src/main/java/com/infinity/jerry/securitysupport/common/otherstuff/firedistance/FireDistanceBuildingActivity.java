package com.infinity.jerry.securitysupport.common.otherstuff.firedistance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.BuildingExpandableRowView;
import com.infinity.jerry.securitysupport.common.otherstuff.ExpandableRowView;
import com.infinity.jerry.securitysupport.common.otherstuff.FireDistDataMgr;
import com.infinity.jerry.securitysupport.common.otherstuff.MainBuildingItem;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.FireDistanceDBController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IceLi on 2016-01-02.
 */
public class FireDistanceBuildingActivity extends TitlebarActivity {
    static SmallActivityCache<String> sStoryHashMap = new SmallActivityCache<String>();

    public static FireDistanceBuildingActivity instance;
    private String mEnterType = FireDistEnum.MAIN_BUILDING_TYPE;

    private LinearLayout mFireDistanceMainBuildingLL;

    public static void launchActivity(Context context, String enterType, String prevType) {

        sStoryHashMap.clear();
        long key = sStoryHashMap.put(enterType);
        Intent intent = new Intent(context, FireDistanceBuildingActivity.class);
        intent.putExtra(FireDistEnum.ENTER_TYPE, key);

        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(FireDistEnum.ENTER_TYPE)) {
            mEnterType  = sStoryHashMap.get(extras.getLong(FireDistEnum.ENTER_TYPE));
        }

        if (mEnterType!=null && mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)) {
            return "主要建筑";
        }
        return "相邻建筑";
    }

    @Override
    protected boolean hasBackOption() {
        return true;
    }

    @Override
    protected View.OnClickListener leftClickListener() {
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //FireDistanceActivity.launchActivity(FireDistanceBuildingActivity.this);
               finish();
           }
       };
    }

    @Override
    protected View.OnClickListener rightClickListener() {
        return null;
    }

    @Override
    protected View onCreateView() {
        instance = this;
        Bundle extras = getIntent().getExtras();

        View view = LayoutInflater.from(this).inflate(R.layout.activity_firedistacne_building, null);
        mFireDistanceMainBuildingLL = (LinearLayout)view.findViewById(R.id.firedistanceMainBuildingLL);

        showLoading();
        if (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)) {
            if (FireDistDataMgr.getInst().isMainBuildingCateReady()
                    && FireDistDataMgr.getInst().isMainBuildingReady()) {
                Log.d("IceLi", "both cate and building is ready.");
                onMainBuildingsUpdate();
            }
            else
            {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                            FireDistanceDBController.getCtrl().db_getMainBuildingsCategry();
                            FireDistanceDBController.getCtrl().db_getMainBuildings();
                            onMainBuildingsUpdate();
                        }
                });
            }
        }
        else // Enter by nearby button
        {
            // main building list must have already fetched.
            onNearyBuildingsUpdate();
        }
        return view;
    }

    private void setViewResource(final ExpandableRowView rowView,
                                 final String title,
                                 final ArrayList<String> displayList,
                                 final ArrayList<Integer> idList,
                                 final ArrayList<Integer> commentFlagList)
    {
        postRunnable(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(BuildingExpandableRowView.DISPLAY_NAME, displayList);
                intent.putIntegerArrayListExtra(BuildingExpandableRowView.ID, idList);
                intent.putIntegerArrayListExtra(BuildingExpandableRowView.COMMENT_FLAG, commentFlagList);
                intent.putExtra(BuildingExpandableRowView.ENTER_TYPE, mEnterType);
                rowView.initResources(title,intent, displayList.size());
            }
        });
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    private void onMainBuildingsUpdate()
    {
        if (!FireDistDataMgr.getInst().isMainBuildingCateReady()
                || !FireDistDataMgr.getInst().isMainBuildingReady())
        {
            Log.d("IceLi", "onMainBuildingsUpdate. Data is not ready.");
            return;
        }
        Log.d("IceLi", "onMainBuildingsUpdate. Data Load Complete..");

        int listCateSize = FireDistDataMgr.listMainbuildingCate.size();
        int listMainBuildingSize = FireDistDataMgr.listMainBuildings.size();
        for (int i = 0; i<listCateSize; ++i)
        {
            BuildingCategoryItem cateItem = FireDistDataMgr.listMainbuildingCate.get(i);
            final ArrayList<String> displayNameList = new ArrayList<String>();
            final ArrayList<Integer> idList = new ArrayList<Integer>();
            final ArrayList<Integer> commentFlagList = new ArrayList<Integer>();
            for (int k = 0; k < listMainBuildingSize; ++k)
            {
                MainBuildingItem buildingItem = FireDistDataMgr.listMainBuildings.get(k);
                if (buildingItem.category_id == cateItem.id && 1 == buildingItem.isMainBuilding)
                {
                    displayNameList.add(buildingItem.name);
                    idList.add(buildingItem.id);
                    commentFlagList.add(buildingItem.buildingComments.length() == 0 ? 0:1);
                }
            }
            BuildingExpandableRowView rvView = new BuildingExpandableRowView(this, null);
            setViewResource(rvView, cateItem.cateName, displayNameList, idList, commentFlagList);
            mFireDistanceMainBuildingLL.addView(rvView);


            View divisionLine = new View(this);
            divisionLine.setBackgroundResource(R.drawable.divider_line);
            mFireDistanceMainBuildingLL.addView(divisionLine);
            hideLoading();
        }
    }

    private void onNearyBuildingsUpdate()
    {
        if (null == FireDistDataMgr.listNearbyBuildingIDS) {
            hideLoading();
            return;
        }

        //  Construct the nearby building list
        List<MainBuildingItem> listNearbyBuildingItem = new LinkedList<MainBuildingItem>();
        for (int i=0; i< FireDistDataMgr.listNearbyBuildingIDS.size(); ++i)
        {
            Integer nearbyID = FireDistDataMgr.listNearbyBuildingIDS.get(i);
            for (int m=0; m< FireDistDataMgr.listMainBuildings.size(); ++m)
            {
                MainBuildingItem item = FireDistDataMgr.listMainBuildings.get(m);
                if (item.id == nearbyID)
                {
                    listNearbyBuildingItem.add(item);
                    continue;
                }
            }
        }

        FireDistDataMgr.listNearbyBuildings = listNearbyBuildingItem;

        int listCateSize = FireDistDataMgr.listMainbuildingCate.size();
        int listMainBuildingSize = listNearbyBuildingItem.size();
        for (int i = 0; i<listCateSize; ++i)
        {
            BuildingCategoryItem cateItem = FireDistDataMgr.listMainbuildingCate.get(i);
            final ArrayList<String> displayNameList = new ArrayList<String>();
            final ArrayList<Integer> idList = new ArrayList<Integer>();
            final ArrayList<Integer> commentFlagList = new ArrayList<Integer>();
            for (int k = 0; k < listMainBuildingSize; ++k)
            {
                MainBuildingItem buildingItem = listNearbyBuildingItem.get(k);
                if (buildingItem.category_id == cateItem.id)
                {
                    displayNameList.add(buildingItem.name);
                    idList.add(buildingItem.id);
                    commentFlagList.add(buildingItem.buildingComments.length() == 0 ? 0:1);
                }
            }
            if (0 == displayNameList.size()) continue;
            BuildingExpandableRowView rvView = new BuildingExpandableRowView(this, null);
            setViewResource(rvView, cateItem.cateName, displayNameList, idList, commentFlagList);
            mFireDistanceMainBuildingLL.addView(rvView);

            View divisionLine = new View(this);
            divisionLine.setBackgroundResource(R.drawable.divider_line);
            mFireDistanceMainBuildingLL.addView(divisionLine);
        }
        hideLoading();
    }
}
