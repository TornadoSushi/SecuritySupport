package com.infinity.jerry.securitysupport.common.otherstuff.firedistance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.FireDistDataMgr;
import com.infinity.jerry.securitysupport.common.otherstuff.PropEditView;
import com.infinity.jerry.securitysupport.common.otherstuff.PropSelecterView;
import com.infinity.jerry.securitysupport.common.otherstuff.PropWidget;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.WindowUtils;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.FireDistanceDBController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IceLi on 2016-01-03.
 */
public class FireDistanceBuildingProperties extends TitlebarActivity {
    static SmallActivityCache<String> sStoryHashMap = new SmallActivityCache<String>();
    private List<PropWidget> widgetList  = new LinkedList<PropWidget>() ;
    private String mEnterType = FireDistEnum.MAIN_BUILDING_TYPE;
    private static String mPrevType = FireDistEnum.PREV_TYPE;

    public static void launchActivity(Context context, String enterType, String prevType) {
        long key = sStoryHashMap.put(enterType);
        Intent intent = new Intent(context, FireDistanceBuildingProperties.class);
        intent.putExtra(FireDistEnum.ENTER_TYPE, key);
        mPrevType = prevType;
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle()
    {
        return "建筑属性";
    }

    @Override
    protected boolean hasBackOption() {return true;}

    @Override
    protected View.OnClickListener leftClickListener() {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doBack();
            }
        };
    }

    @Override
    public void onBackPressed()
    {
        doBack();
    }

    @Override
    protected View.OnClickListener rightClickListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FireDistEnum.MAIN_BUILDING_TYPE == mEnterType) {
                    Log.d("IceLi","PropertiesActivity: entert by MainBuilding. clear MainBuildingID");
                    FireDistDataMgr.getInst().clearSelectedMainBuildingID();
                    FireDistDataMgr.getInst().clearSelectedNearbyBuildingID();
                    FireDistanceBuildingActivity.launchActivity(FireDistanceBuildingProperties.this,
                            FireDistEnum.MAIN_BUILDING_TYPE, FireDistEnum.FIRE_DIST_PROPS_ACTIVITY);
                }
                else
                {
                    FireDistDataMgr.getInst().clearSelectedNearbyBuildingID();
                    FireDistanceBuildingActivity.launchActivity(FireDistanceBuildingProperties.this,
                            FireDistEnum.NEARBY_BUILDING_TYPE, FireDistEnum.FIRE_DIST_PROPS_ACTIVITY);
                }
                finish();
            }
        };
    }

    @Override
    protected View onCreateView()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(FireDistEnum.ENTER_TYPE)) {
            mEnterType  = sStoryHashMap.get(extras.getLong(FireDistEnum.ENTER_TYPE));
        }

        View view = getLayoutInflater().inflate(R.layout.activity_firedistance_building_prop, null);
        LinearLayout mainLL = (LinearLayout)view.findViewById(R.id.firedistanceBuildingPropMainLL);

        LinearLayout nameLL = (LinearLayout)view.findViewById(R.id.firedistanceBuildingPropNameLL);
        TextView nameTV = (TextView)nameLL.findViewById(R.id.firedistanceBuildingPropNameTV);
        FireDistDataMgr FDDM = FireDistDataMgr.getInst();
        String name = FDDM.getMainBuildingItemByID(mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)?FDDM.getSelectMainBuilding():FDDM.getSelectNearbyBuilding()).name;
        nameTV.setText(name);

        final List<BuildingPropItem> listPropItems;
        if (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))
        {
            listPropItems = FireDistDataMgr.getInst().listMainBuildingsProp;
        }
        else
        {
            listPropItems = FireDistDataMgr.getInst().listNearbyBuildingsProp;
        }

        if (null != listPropItems) {
            for (int i = 0; i < listPropItems.size(); ++i) {
                BuildingPropItem item = listPropItems.get(i);
                switch (item.inputType) {
                    case 0:
                    case 1:
                    case 2:
                        final PropWidget selectView = new PropSelecterView(FireDistanceBuildingProperties.this, item.displayName, null, item.propId, mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE));
                        selectView.setCommentIconVisible(item.comment.isEmpty() ? false : true);
                        selectView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showPropDialog(selectView);
                            }
                        });
                        widgetList.add(selectView);
                        mainLL.addView(selectView);
                        break;
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        final PropWidget editView = new PropEditView(FireDistanceBuildingProperties.this, item.displayName, null, item.propId, mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE));
                        widgetList.add(editView);
                        mainLL.addView(editView);
                        break;
                    // TODO: Search function do not have any function yet.
                    /*
                    case 20:
                        PropWidget searchView = new PropSearchView(FireDistanceBuildingProperties.this, item.displayName, null, item.propId, mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE));
                        widgetList.add(searchView);
                        mainLL.addView(searchView);
                        break;
                    */
                    default:
                        break;
                }
            }
        }

        final float scale = this.getResources().getDisplayMetrics().density;
        int paddingPx = (int)(scale * 10 + 0.5);

        LinearLayout btnLL = new LinearLayout(FireDistanceBuildingProperties.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = paddingPx*2;
        lp.leftMargin = paddingPx;
        lp.rightMargin = paddingPx;
        btnLL.setLayoutParams(lp);
        btnLL.setGravity(Gravity.CENTER);
        btnLL.setPadding(paddingPx*4, 0, paddingPx*4, 0);
        btnLL.setBackgroundResource(R.drawable.main_btn_background);
        btnLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<widgetList.size(); ++i)
                {
                    BuildingPropItem item;
                    if (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))
                        item = FireDistDataMgr.getInst().getMainBuildingPropItemByID(widgetList.get(i).getPropID());
                    else
                        item = FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(widgetList.get(i).getPropID());

                    if (item.inputType == 10 || item.inputType == 11 || item.inputType == 12
                            || item.inputType == 13 || item.inputType == 14) // edit property, collect user input
                    {
                        setEditViewResult(widgetList.get(i), widgetList.get(i).getEditValue().toString());
                    }
                }
                finish();
            }
        });

        TextView okTV = new TextView(this);
        okTV.setText("确  定");
        okTV.setTextSize(16);
        okTV.setGravity(Gravity.CENTER);
        okTV.setTextColor(0xFFFFFFFF);
        okTV.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        btnLL.addView(okTV);
        mainLL.addView(btnLL);

        updateViewItems();
        return view;
    }

    protected void onAddRightTitleView(LinearLayout ll)
    {
        TextView tv = new TextView(this);
        tv.setText("重新选择");
        tv.setBackgroundResource(R.drawable.main_btn_background);
        int horizonPadding = WindowUtils.dip2px(this, 16);
        int verticalPadding = WindowUtils.dip2px(this, 8);
        tv.setPadding(horizonPadding, verticalPadding, horizonPadding, verticalPadding);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, WindowUtils.sp2px(this, 16));
        ll.addView(tv);
    }

    public void showPropDialog(final PropWidget widget)
    {
        final BuildingPropItem item = (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))?
                                    FireDistDataMgr.getInst().getMainBuildingPropItemByID(widget.getPropID())
                                   : FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(widget.getPropID());
        final List<BuildingPropOptionItem> listOptionDisplay = getBuildingPropOptionItems(item);

        final String[] displayNames = new String[listOptionDisplay.size()+1];
        displayNames[0] = "未知（空）";
        for (int i=0; i<listOptionDisplay.size(); ++i)
        {
            displayNames[i+1] = listOptionDisplay.get(i).displayName;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选项")
                .setItems(displayNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (0 == i) {
                            widget.setEditValue("");
                            item.userSelectOpton = null;
                        } else {
                            widget.setEditValue(displayNames[i]);
                            item.userSelectOpton = listOptionDisplay.get(i - 1);
                        }
                        if (FireDistDataMgr.getInst().checkDataOptionChange(mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))) {
                            updateViewItems();
                        }
                        dialogInterface.dismiss();
                    }
                });

        builder.create().show();
    }

    @NonNull
    private List<BuildingPropOptionItem> getBuildingPropOptionItems(BuildingPropItem item) {
        int parentID = item.parentId;
        int propID = item.propId;

        String whereCondiation = FIRE_DISTANCE_TABLE.PROP_ID+"="+propID;
        if (0 != parentID) { // get Items which value depends on the father.
            BuildingPropItem fatherItem;
            if (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))
                fatherItem = FireDistDataMgr.getInst().getMainBuildingPropItemByID(parentID);
            else
                fatherItem = FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(parentID);

            if (null != fatherItem.userSelectOpton) {
                whereCondiation += " and " + FIRE_DISTANCE_TABLE.FATHER_OPTION_ID + "=" + fatherItem.userSelectOpton.id;
            }
            else
            {
                return new LinkedList<BuildingPropOptionItem>();
            }
        }

        final List<BuildingPropOptionItem> listOptions;
        Log.d("IceLi", "getBuildingPropOptionItems. Where condication = "+whereCondiation);
        listOptions = FireDistanceDBController.getCtrl()
                .db_getOptions(whereCondiation, propID);
        if (null == listOptions || listOptions.isEmpty())
        {
            return new LinkedList<BuildingPropOptionItem>();
        }


        FireDistDataMgr.getInst().insertPropOptions2PropItem(propID, (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)), listOptions);
        return  listOptions;
    }

    public void updateViewItems()
    {
        for (int i=0; i<widgetList.size(); ++i)
        {
            PropWidget widget = widgetList.get(i);
            BuildingPropItem propItem;
            if (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))
                propItem = FireDistDataMgr.getInst().getMainBuildingPropItemByID(widget.getPropID());
            else
               propItem = FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(widget.getPropID());
            if (null == propItem.userSelectOpton && propItem.userInputValue.isEmpty())
            {
                widget.setEditValue("");
            }
            else if ( !propItem.userInputValue.isEmpty())
            {
                widget.setEditValue(propItem.userInputValue);
            }
            else if (null != propItem.userSelectOpton)
            {
                widget.setEditValue(propItem.userSelectOpton.displayName);
            }
        }
    }

    void setEditViewResult(final PropWidget widget, CharSequence txtUserInputs) {
        Log.d("IceLi", "user input "+txtUserInputs);
        final BuildingPropItem item = (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE))?
                FireDistDataMgr.getInst().getMainBuildingPropItemByID(widget.getPropID())
                : FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(widget.getPropID());
        final List<BuildingPropOptionItem> listMatchedOption = getBuildingPropOptionItems(item);
        FireDistDataMgr.getInst().insertPropOptions2PropItem(item.propId, (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)), listMatchedOption);
        if(0 == listMatchedOption.size())
        {
            if((item.inputType == 10 || item.inputType == 11 || item.inputType==12
                    || item.inputType == 13 || item.inputType == 14))
            {
                item.userInputValue = txtUserInputs.toString();
            }
        }
        for (int i=0; i<listMatchedOption.size(); ++i)
        {
            BuildingPropOptionItem optionItem = listMatchedOption.get(i);
            if (0 == item.parentId
                    && (item.inputType == 10 || item.inputType == 11 || item.inputType==12
                    || item.inputType == 13 || item.inputType == 14))
            {
                item.userInputValue = txtUserInputs.toString();
                if(isUserValueInRange(txtUserInputs.toString(), optionItem.displayName))
                {
                    Log.d("IceLi", txtUserInputs.toString()+" in range "+optionItem.displayName);
                    item.userSelectOpton = optionItem;
                    Log.d("IceLi", "Set Prop "+item.displayName+" value to "+item.userSelectOpton.calValue);
                    return;
                }
                else
                {
                    Log.d("IceLi", "FDBProperties:: set userSelectOption to NULL. 22");
                    item.userSelectOpton = null;
                }
            }
            else {
                BuildingPropItem parentItem = (mEnterType.equals(FireDistEnum.MAIN_BUILDING_TYPE)) ?
                        FireDistDataMgr.getInst().getMainBuildingPropItemByID(item.parentId)
                        : FireDistDataMgr.getInst().getNearbyBuildingPropItemByID(item.parentId);
                if (null == parentItem.userSelectOpton) {
                    Log.d("IceLi", "FDBProperties:: set userSelectOption to NULL");
                    item.userInputValue = "";
                    item.userSelectOpton = null;
                    break;
                } else if (parentItem.userSelectOpton.id != optionItem.fatherOptionID) {
                    continue;
                } else {
                    if (isUserValueInRange(txtUserInputs.toString(), optionItem.displayName)) {
                        Log.d("IceLi", txtUserInputs.toString() + " in range " + optionItem.displayName);
                        item.userSelectOpton = optionItem;
                        item.userInputValue = txtUserInputs.toString();
                        Log.d("IceLi", "Set Prop " + item.displayName + " value to " + item.userSelectOpton.calValue);
                        break;
                    } else {
                        Log.d("IceLi", "FDBProperties:: set userSelectOption to NULL. 22");
                        item.userInputValue = txtUserInputs.toString();
                        item.userSelectOpton = null;
                    }
                }
            }
        }
    }
    public  boolean isUserValueInRange(String userInputValue, String strValue)
    {
        if (userInputValue.isEmpty()) return false;

        boolean isInRange = false;
        List<Integer> symbolPosition = new ArrayList<Integer>();
        String[] strValues = strValue.split(",");
        for (int i=0; i<strValues.length; ++i)
        {
            switch (strValues[i])
            {
                case "<=":
                case "<":
                    symbolPosition.add(i);
                    break;
                default:
                    break;
            }
        }

        Log.d("IceLi", "Have symbol count = "+ symbolPosition.size());
        int iUserValue = Integer.parseInt(userInputValue);
        if (2 == symbolPosition.size()) // XX </<= userValue </<= YY
        {
            int compareLeft, compareRight;
            String first = strValues[symbolPosition.get(0)-1];
            compareLeft = Integer.parseInt(first);

            String second = strValues[symbolPosition.get(1)+1];
            compareRight = Integer.parseInt(second);

            String compareSymbol1 = strValues[symbolPosition.get(0)];
            String compareSymbol2 = strValues[symbolPosition.get(1)];
            if(compareWithSymbol(compareSymbol1, iUserValue, compareLeft, true)
                    &&compareWithSymbol(compareSymbol2, iUserValue, compareRight, false)) {
               isInRange = true;
            }
        }
        else if (1 == symbolPosition.size()) // XX </<= userValue or userValue>/>= YY
        {
            int index = strValue.indexOf(",,,");
            if (-1 != index)
            {
                if (index == 0) // userInputValue < XXX
                {
                   String first = strValues[symbolPosition.get(0)+1];
                    int compareNumber = Integer.parseInt(first);
                   if(compareWithSymbol(strValues[symbolPosition.get(0)], iUserValue, compareNumber, false))
                   {
                       isInRange = true;
                   }
                }
                else // userInputValue > XXX
                {
                    String first = strValues[symbolPosition.get(0)-1];
                    int compareNumber = Integer.parseInt(first);
                    if(compareWithSymbol(strValues[symbolPosition.get(0)], iUserValue, compareNumber, true))
                    {
                        isInRange = true;
                    }
                }
            }
        }
        Log.d("IceLi", "isUserInRange. userInput="+userInputValue+", strValue="+strValue+". Result="+isInRange);
        return isInRange;
    }

    private boolean compareWithSymbol(String compareSymbol, int iUserValue, int iCompareValue, boolean isCompareValueLeft) {
        if (0 == compareSymbol.compareTo("<"))
        {
            if (isCompareValueLeft && iCompareValue < iUserValue) {
                return true;
            }
            else if (!isCompareValueLeft && iUserValue < iCompareValue)
            {
                return  true;
            }
        }
        else if (0 == compareSymbol.compareTo("<="))
        {
            if(isCompareValueLeft && iCompareValue<=iUserValue)
            {
                return true;
            }
            else if (!isCompareValueLeft && iUserValue<= iCompareValue) {
                return true;
            }
        }
        return false;
    }

    private void doBack()
    {
        if (FireDistEnum.FIRE_DIST_BUILDING_ACTIVITY.equals(mPrevType))
        {
            FireDistanceBuildingActivity.launchActivity(FireDistanceBuildingProperties.this, mEnterType, FireDistEnum.FIRE_DIST_PROPS_ACTIVITY);
        }
        finish();
    }
}


