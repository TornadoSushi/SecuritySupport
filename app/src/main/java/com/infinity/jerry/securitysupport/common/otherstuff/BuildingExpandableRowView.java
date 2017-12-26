package com.infinity.jerry.securitysupport.common.otherstuff;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.FireDistanceDBController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.IDBResult;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingPropItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.FIRE_DISTANCE_TABLE;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.FireDistEnum;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.FireDistanceBuildingActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.FireDistanceBuildingProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IceLi on 2016-01-02.
 */
public class BuildingExpandableRowView extends ExpandableRowView {
    public final static String DISPLAY_NAME = "BUILDING_DISPLAY_NAME";
    public final static String ID = "BUILDING_ID";
    public final static String COMMENT_FLAG = "COMMENT_FLAG";
    public final static String ENTER_TYPE = "ENTER_TYPE";
    Context mContext;

    public BuildingExpandableRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected ExpanableRowBuilder builder() {
        return new BuildingRowViewBuilder();
    }

    private class BuildingRowViewBuilder implements ExpanableRowBuilder {
        private ArrayList<String> mBuildingNameList;
        private ArrayList<Integer> mIdList;
        private ArrayList<Integer> mCommentFlagList;
        private String mEnterType;

        @Override
        public int mainViewResourceId() {
            return R.layout.view_expanable_titleview;
        }

        @Override
        public void onTitleViewCreated(View view) {
            ;
        }

        @Override
        public int titleViewResourceId() {
            return R.id.expanableTitleTV;
        }

        @Override
        public int expandImageResourceId() {
            return R.id.expanableTitleIV;
        }

        @Override
        public void setChildCount(int count) {

        }

        @Override
        public View createExpandChildView(Intent data, final int index) {
            if (null == mBuildingNameList) {
                mBuildingNameList = data.getStringArrayListExtra(DISPLAY_NAME);
                mIdList = data.getIntegerArrayListExtra(ID);
                mCommentFlagList = data.getIntegerArrayListExtra(COMMENT_FLAG);
                mEnterType = data.getStringExtra(ENTER_TYPE);
            }

            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_firedistance_buildinginfo, null);
            ((TextView) view.findViewById(R.id.buildingNameTV)).setText(mBuildingNameList.get(index));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)) {
                        onMainBuildingTypeClick(index);
                    }
                    else
                    {
                        onNearbyBuildingTypeClick(index);
                    }
                }
            });

            ImageView commentIV = (ImageView) view.findViewById(R.id.buildingInfoIV);
            commentIV.setVisibility(mCommentFlagList.get(index).intValue() == 0 ? View.INVISIBLE : View.VISIBLE);
            commentIV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id =mIdList.get(index);
                    MainBuildingItem item = FireDistDataMgr.getInst().getMainBuildingItemByID(id);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                    builder.setTitle("注释")
                            .setMessage(item.buildingComments);

                    builder.create().show();
                }
            });
            return view;
        }

        @Override
        public void onChildViewsCreated() {
        }

        ;

        @Override
        public void recycle(){}

        public void onMainBuildingTypeClick(final int index)
        {
            FireDistanceDBController.getCtrl().db_getMainBuildingsProp(FIRE_DISTANCE_TABLE.STRUCT_ID+"="+mIdList.get(index),
                    new IDBResult.Callback<List<BuildingPropItem>>()
                    {
                        @Override
                        public void onResultCallback(List<BuildingPropItem> listMainBuildingsPrp)
                        {
                            if (null == listMainBuildingsPrp) // do not have prop, direct to main page.
                            {
                                FireDistDataMgr.getInst().selectMainBuilding(mIdList.get(index));
                            }
                            else // goto prop activity
                            {
                                FireDistDataMgr.getInst().selectMainBuilding(mIdList.get(index));
                                FireDistanceBuildingProperties.launchActivity(mContext, FireDistEnum.MAIN_BUILDING_TYPE, FireDistEnum.FIRE_DIST_BUILDING_ACTIVITY);
                            }

                            FireDistanceBuildingActivity.instance.finish();
                        }
                    });
        }

        public void onNearbyBuildingTypeClick(final int index)
        {
            FireDistanceDBController.getCtrl().db_getNearbyBuildingsProp(FIRE_DISTANCE_TABLE.STRUCT_ID + "=" + mIdList.get(index),
                    new IDBResult.Callback<List<BuildingPropItem>>() {
                        @Override
                        public void onResultCallback(List<BuildingPropItem> listNearbyBuildingsPrp) {
                            if (null == listNearbyBuildingsPrp) // do not have prop, direct to main page.
                            {
                                FireDistDataMgr.getInst().selectNearbyBuilding(mIdList.get(index));
                            } else // goto prop activity
                            {
                                FireDistDataMgr.getInst().selectNearbyBuilding(mIdList.get(index));
                                FireDistanceBuildingProperties.launchActivity(mContext, FireDistEnum.NEARBY_BUILDING_TYPE, FireDistEnum.FIRE_DIST_BUILDING_ACTIVITY);
                            }
                            FireDistanceBuildingActivity.instance.finish();
                        }
                    });
        }
    }
}