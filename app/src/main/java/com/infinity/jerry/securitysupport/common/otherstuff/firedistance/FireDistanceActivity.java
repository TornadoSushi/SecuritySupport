package com.infinity.jerry.securitysupport.common.otherstuff.firedistance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.FireDistDataMgr;
import com.infinity.jerry.securitysupport.common.otherstuff.MainBuildingItem;
import com.infinity.jerry.securitysupport.common.otherstuff.SmallActivityCache;
import com.infinity.jerry.securitysupport.common.otherstuff.TitlebarActivity;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.FireDistanceDBController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by edwardliu on 15/11/26.
 */
public class FireDistanceActivity extends TitlebarActivity {
    public static final String ITEM_ID = "ITEM_ID";
    public static final String FROM_WHERE = "FROME_WHERE";
    public static final String HAVE_PROPERTY = "HAVE_PROPERTY";
    public static final String GB_1 = "GB50016-2006 建筑设计防火规范:";
    public static final String GB_2 = "GB50160-2008 石油化工企业设计防火规范:";
    public static final String SEARCH_NOTHING = "查无规范";

    static SmallActivityCache<Integer> sStoryHashMap = new SmallActivityCache<Integer>();

    private LinearLayout mMainBuildingLL;
    private TextView mMainBuildingTV;
    private LinearLayout mNearbyBuildingLL;
    private TextView mNearbyBuildingTV;
    private LinearLayout mBuildingCalcRetLL;
    private LinearLayout mbuildingCalcRetLL;
    private LinearLayout mOilBuildingCalcLL;
    private TextView mBuildingCalcRetCommentsTV;
    private TextView mOilBuildingCalcRetCommentsTV;
    private Button mCalcBtn;
    private Button mBuildingCalcRetCommentBtn;
    private BuildingNote mNote;
    private Button mOilBuildingCalcRetCommentBtn;
    private TextView mRemarkTV;
    private int mLastSelectedMainBuilding = FireDistDataMgr.INVALID_ID;
    private int mLastSelectedNearbyBuilding = FireDistDataMgr.INVALID_ID;
    private HashMap<String, Double> mMainInput;
    private HashMap<String, Double> mNearbyInput;
    private String mStrInstances[] = null;

    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, FireDistanceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected String centerTitle() {
        return "防火间距";
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
                FireDistDataMgr.getInst().clearAll();
                finish();
            }
        };
    }

    @Override
    protected View.OnClickListener rightClickListener() {
        return null;
    }

    @Override
    public void onBackPressed()
    {
        FireDistDataMgr.getInst().clearAll();
        finish();
    }

    @Override
    protected View onCreateView() {
        View view = getLayoutInflater().inflate(R.layout.activity_firedistance, null);

        mMainBuildingLL = (LinearLayout) view.findViewById(R.id.mainBuildingLL);
        mMainBuildingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMainBuildingSelected()) {
                    FireDistanceBuildingProperties.launchActivity(FireDistanceActivity.this,
                            FireDistEnum.MAIN_BUILDING_TYPE, FireDistEnum.FIRE_DIST_ACTIVITY);
                } else {
                    FireDistanceBuildingActivity.launchActivity(FireDistanceActivity.this,
                            FireDistEnum.MAIN_BUILDING_TYPE, FireDistEnum.FIRE_DIST_ACTIVITY);
                }
            }
        });
        mMainBuildingTV = (TextView) mMainBuildingLL.findViewById(R.id.mainBuildingTV);

        mNearbyBuildingLL = (LinearLayout) view.findViewById(R.id.nearbyBuildingLL);
        mNearbyBuildingTV = (TextView) mNearbyBuildingLL.findViewById(R.id.nearbyBuildingTV);

        mCalcBtn = (Button) view.findViewById(R.id.calcFireDistanceBTN);
        mCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onCalcFireDistance();
                        onGetComment();
                    }
                }.run();

            }
        });

        mBuildingCalcRetLL = (LinearLayout) view.findViewById(R.id.buildingCalcLL);
        mbuildingCalcRetLL = (LinearLayout) mBuildingCalcRetLL.findViewById(R.id.buildingCalcRetLL);
        mBuildingCalcRetCommentsTV = (TextView) mbuildingCalcRetLL.findViewById(R.id.buildingCalcRetCommentsTV);


        mOilBuildingCalcLL = (LinearLayout) mBuildingCalcRetLL.findViewById(R.id.oilBuildingCalcLL);
        mOilBuildingCalcRetCommentsTV = (TextView) mOilBuildingCalcLL.findViewById(R.id.oilBuildingCalcRetCommentsTV);
        RelativeLayout oilBuildingCalcRetCommentRL = (RelativeLayout) mOilBuildingCalcLL.findViewById(R.id.oilBuildingCalcRetCommentRL);
        mOilBuildingCalcRetCommentBtn = (Button)oilBuildingCalcRetCommentRL.findViewById(R.id.oilBuildingCalcRetCommentBtn);
        mOilBuildingCalcRetCommentBtn.setVisibility(View.INVISIBLE);

        mBuildingCalcRetLL.setVisibility(View.INVISIBLE);
        RelativeLayout buildingCalcRetRL = (RelativeLayout) mBuildingCalcRetLL.findViewById(R.id.buildingCalcRetCommentRL);
        mBuildingCalcRetCommentBtn = (Button)buildingCalcRetRL.findViewById(R.id.buildingCalcRetCommentBtn);

        mRemarkTV = (TextView) mBuildingCalcRetLL.findViewById(R.id.txtRemark);
        mRemarkTV.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    protected void onAddRightTitleView(LinearLayout ll) {

    }

    @Override
    protected void onStart()
    {
        Log.d("IceLi", "FireDistance: onStart");
        super.onStart();
        final int mainBuildingSelectID = FireDistDataMgr.getInst().getSelectMainBuilding();
        final int nearbyBuildingSelectID = FireDistDataMgr.getInst().getSelectNearbyBuilding();

        if (isMainBuildingSelected()) {
            MainBuildingItem item;
            item = FireDistDataMgr.getInst().getMainBuildingItemByID(mainBuildingSelectID);
            StringBuffer mainBuildingDisplay = new StringBuffer();
            mainBuildingDisplay.append(item.name);
            if (!getDisplayType(true).isEmpty())
            {
                mainBuildingDisplay.append(" (")
                        .append(getDisplayType(true))
                        .append(")");
            }
            mMainBuildingTV.setText(mainBuildingDisplay.toString());

            List<Integer> listNearbyBuildingsIDS =
            FireDistanceDBController.getCtrl().db_getNearbyBuildingIDS(
                    FIRE_DISTANCE_TABLE.MAIN_STRUCT_ID + "=" + mainBuildingSelectID);

            if (null == listNearbyBuildingsIDS) // can calc without nearby
            {
                mNearbyBuildingLL.setOnClickListener(null);
                mNearbyBuildingTV.setText("");
                mNearbyBuildingTV.setHint("请直接计算");
            }
            else if (!isNearbyBuildingSelected()) {
                mNearbyBuildingTV.setText("");
                mNearbyBuildingTV.setHint("请选择");
                mNearbyBuildingLL.setOnClickListener(new NearbyBuildingClickListener(this));
            } else if (isNearbyBuildingSelected()) {
                StringBuffer strNearbyBuildingDisplay = new StringBuffer();
                strNearbyBuildingDisplay.append(FireDistDataMgr.getInst().getNearbyBuildingItemByID(nearbyBuildingSelectID).name);
                if (!getDisplayType(false).isEmpty())
                {
                    strNearbyBuildingDisplay.append(" (")
                            .append(getDisplayType(false))
                            .append(")");
                }
                mNearbyBuildingTV.setText(strNearbyBuildingDisplay.toString());
                mNearbyBuildingLL.setOnClickListener(new NearbyBuildingClickListener(this));
            }
            else
            {
                mNearbyBuildingTV.setHint("请选择主要建筑");
                mNearbyBuildingLL.setOnClickListener(null);
            }
            if (mainBuildingSelectID != mLastSelectedMainBuilding
                    || nearbyBuildingSelectID != mLastSelectedNearbyBuilding)
            {
                hideCalcresult();
            }
        }
        else
        {
            mMainBuildingTV.setText("");
            mMainBuildingTV.setHint("请选择");
            mNearbyBuildingTV.setText("");
            mNearbyBuildingTV.setHint("请选择主要建筑");
            mNearbyBuildingLL.setOnClickListener(null);
            hideCalcresult();
        }
        mLastSelectedMainBuilding = mainBuildingSelectID;
        mLastSelectedNearbyBuilding = nearbyBuildingSelectID;
    }

    private void setPlusAndMinus(List<BuildingDistance> distList1, List<BuildingDistance> distList2, List<BuildingDistance> distList3)
    {
        Iterator<BuildingDistance> iter = distList3.iterator();
        while(iter.hasNext())
        {
            BuildingDistance dist = iter.next();
            if(FireDistDataMgr.getInst().getFireResitrct(dist.fireResitrct, mMainInput, mNearbyInput) > 0.0d)
            {
                distList1.add(dist);
            }
            else
            {
                distList2.add(dist);
            }
        }
    }

    private BuildingDistance getCalcResult(List<BuildingDistance> distList1, List<BuildingDistance> distList2)
    {
        BuildingDistance dist1 = null;
        BuildingDistance dist2 = null;
        if(distList1.size() > 0)
        {
            dist1 = (BuildingDistance) Collections.max(distList1, new DistanceComparator(mMainInput, mNearbyInput));
            Log.d("IceLi", "BuildingDistance::getCalcResult. dist1="+dist1.caseID);
        }

        if(distList2.size() > 0)
        {
            dist2 = (BuildingDistance) Collections.max(distList2, new DistanceComparator(mMainInput, mNearbyInput));
        }
        if(null != dist2) {
            double d = Math.abs(FireDistDataMgr.getInst().getFireResitrct(dist2.fireResitrct, mMainInput, mNearbyInput));
            if (null != dist1)
            {
                if(d > FireDistDataMgr.getInst().getFireResitrct(dist1.fireResitrct, mMainInput, mNearbyInput))
                {
                    return dist2;
                }
                return dist1;
            }
            return dist2;
        }
        return dist1;
    }

    private boolean onCalcFireDistance() {
        Log.d("IceLi", "onCalcFireDistance");
        int mainBuildingSelectID = FireDistDataMgr.getInst().getSelectMainBuilding();
        int nearbyBuildingSelectID = FireDistDataMgr.getInst().getSelectNearbyBuilding();

        if (mainBuildingSelectID == FireDistDataMgr.INVALID_ID
                && nearbyBuildingSelectID == FireDistDataMgr.INVALID_ID)
        {
            Log.d("IceLi", "onCalcFireDistance. No building is chosed, show Toast ");
            showToast();
            return false;
        }

        mMainInput = FireDistDataMgr.getInst().getPropUserInputValues(true);
        mNearbyInput = FireDistDataMgr.getInst().getPropUserInputValues(false);
        if(canDirectCalcWithoutNearby() || !isNearbyBuildingSelected()) {
            Log.d("IceLi", "9999");
            mStrInstances = new String[]{getStructInstanceId(true, mainBuildingSelectID, 9999),
                                        getStructInstanceId(false, 9999, mainBuildingSelectID)};
        }
        else
        {
            mStrInstances = new String[]{getStructInstanceId(true, mainBuildingSelectID, nearbyBuildingSelectID),
                                      getStructInstanceId(false, nearbyBuildingSelectID, mainBuildingSelectID)};
        }

        // doCalc
        List<BuildingDistance> candicateList = getDistances(mStrInstances);

        ArrayList<BuildingDistance> distCate1 = new ArrayList<>();
        ArrayList<BuildingDistance> distCate2 = new ArrayList<>();

        if(candicateList != null && !candicateList.isEmpty())
        {
            Log.d("IceLi", "onCalcFireDistance Handle distance.");
            Iterator<BuildingDistance> distIter = candicateList.iterator();
            while (distIter.hasNext())
            {
                BuildingDistance distItem = distIter.next();
                if(1 == distItem.gbCatetory)
                    distCate1.add(distItem);
                else
                    distCate2.add(distItem);
            }
        }
        ArrayList<BuildingDistance> distResult = new ArrayList<BuildingDistance>();
        ArrayList<BuildingDistance> distCate1Ret1 = new ArrayList<>();
        ArrayList<BuildingDistance> distCate1Ret2 = new ArrayList<>();
        ArrayList<BuildingDistance> distCate2Ret1 = new ArrayList<>();
        ArrayList<BuildingDistance> distCate2Ret2 = new ArrayList<>();
        setPlusAndMinus(distCate1Ret1, distCate1Ret2, distCate1);
        setPlusAndMinus(distCate2Ret1, distCate2Ret2, distCate2);
        BuildingDistance cate1Ret = getCalcResult(distCate1Ret1, distCate1Ret2);
        BuildingDistance cate2Ret = getCalcResult(distCate2Ret1, distCate2Ret2);
        if(null != cate1Ret) {
            distResult.add(cate1Ret);
            Log.d("IceLi", "onCalc, ret1 id = "+cate1Ret.caseID+", remark"+cate1Ret.fireResitrctRemark+", distance ="+cate1Ret.fireResitrct);
        }
        if(null != cate2Ret) {
            distResult.add(cate2Ret);
            Log.d("IceLi", "onCalc, ret2 id = "+cate2Ret.caseID+", remark"+cate2Ret.fireResitrctRemark+", distance ="+cate2Ret.fireResitrct);
        }
        showCalcResult(distResult);
        return false;
    }

    private void onGetComment() {
        final List<List<String>> listNotes = getBuildingSpecComment();
        mOilBuildingCalcRetCommentBtn.setVisibility(View.INVISIBLE);
        mBuildingCalcRetCommentBtn.setVisibility(View.INVISIBLE);
        if (null != listNotes)
        {
            if (!listNotes.get(0).isEmpty()) {
                mBuildingCalcRetCommentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!listNotes.get(0).isEmpty()) {
                            String[] strNotes = new String[listNotes.get(0).size()];
                            for(int i=0; i<listNotes.get(0).size();++i)
                            {
                                String str = listNotes.get(0).get(i);
                                strNotes[i] = str;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(FireDistanceActivity.this);
                            builder.setTitle("注释")
                                    .setItems(strNotes, null)
                                    .create().show();
                        }
                    }
                });
                mBuildingCalcRetCommentBtn.setVisibility(View.VISIBLE);
            }

            if (!listNotes.get(1).isEmpty())
            {
                mOilBuildingCalcRetCommentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!listNotes.get(1).isEmpty()) {
                            String[] strNotes = new String[listNotes.get(1).size()];
                            for(int i=0; i<listNotes.get(1).size();++i)
                            {
                                String str = listNotes.get(1).get(i);
                                strNotes[i] = str;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(FireDistanceActivity.this);
                            builder.setTitle("注释")
                                    .setItems(strNotes, null)
                                    .create().show();
                        }
                    }
                });
                mOilBuildingCalcRetCommentBtn.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            mBuildingCalcRetCommentBtn.setOnClickListener(null);
            mBuildingCalcRetCommentBtn.setVisibility(View.INVISIBLE);
            mOilBuildingCalcRetCommentBtn.setOnClickListener(null);
            mOilBuildingCalcRetCommentBtn.setVisibility(View.INVISIBLE);
        }
    }

    private List<BuildingDistance> getDistances(String[] aryDist)
    {
        ArrayList<BuildingDistance> distListRet =  new ArrayList<BuildingDistance>();
        String strWhere = "struct_instance_1 in "+aryDist[0]+" and struct_instance_2 in"+aryDist[1];
        Log.d("IceLi", "onCalcFireDistance. 1. where ="+strWhere);
        List<BuildingDistance> listDist1 = FireDistanceDBController.getCtrl().db_getDistance(strWhere);
        if(null != listDist1 && !listDist1.isEmpty())
        {
            distListRet.addAll(listDist1);
        }

        strWhere = "struct_instance_1 in "+aryDist[1]+" and struct_instance_2 in"+aryDist[0];
        Log.d("IceLi", "onCalcFireDistance. 2. where ="+strWhere);
        List<BuildingDistance> listDist2 = FireDistanceDBController.getCtrl().db_getDistance(strWhere);
        if(null != listDist2 && !listDist2.isEmpty())
        {
            distListRet.addAll(listDist2);
        }
        return distListRet;
    }

    private String getStructInstanceId(boolean isMainbuilding, int mainBuildingID, int nearbyBuildingID)
    {
        String str1 = setPropOptionCal(isMainbuilding);
        Log.d("IceLi", "getStructInstanceId 1. setPropOptionCal return "+str1);

        List<Map<Integer, List<BuildingPropItem>>> listMap = getMap(mainBuildingID, nearbyBuildingID, str1);

        List<BuildingPropItem> propList = FireDistDataMgr.getPropList(isMainbuilding);
        ArrayList aryList = getInstanceArrayList(mainBuildingID, propList);
        Iterator<Map<Integer, List<BuildingPropItem>>> mapIter = listMap.iterator();
        while(mapIter.hasNext())
        {
            Map<Integer, List<BuildingPropItem>> mapItem = mapIter.next();
            Iterator<Integer> keySetIter = mapItem.keySet().iterator();
            while(keySetIter.hasNext())
            {
                Integer iKey = keySetIter.next();
                List<BuildingPropItem> innerListProps = mapItem.get(iKey);
                if(null == innerListProps)
                {
                    innerListProps = new ArrayList<BuildingPropItem>();
                    innerListProps.addAll(propList);
                }
                else
                {

                    for (int i = 0; i < propList.size(); ++i) {
                        boolean isAdd = true;
                        BuildingPropItem pItem = propList.get(i);
                        for(int j=0; j<innerListProps.size(); ++j) {
                            if(pItem.calID == innerListProps.get(j).calID)
                            {
                                isAdd = false;
                                break;
                            }
                        }
                        if(isAdd) {
                            innerListProps.add(pItem);
                        }
                    }
                }

                ArrayList newInstList = getInstanceArrayList(iKey, innerListProps);
                if(null != newInstList)
                {
                    for(int i=0; i<newInstList.size(); ++i)
                    {
                        if(!aryList.contains(newInstList.get(i)))
                        {
                            aryList.add(newInstList.get(i));
                        }
                    }
                }
            }

        }

        String strRet = aryList.toString().replace("[","(").replace("]",")");
        Log.d("IceLi", "getStructInstanceId:: retValue=" + strRet);
        return strRet;
    }

    @NonNull
    private ArrayList getInstanceArrayList(int buildingID, List<BuildingPropItem> propList) {
        List<BuildingInstance> buildingInsts = getPossibleInst(buildingID, propList, true);
        ArrayList aryList = new ArrayList();
        if(null != buildingInsts && !buildingInsts.isEmpty())
        {
            for (int i=0; i<buildingInsts.size(); ++i)
            {
                BuildingInstance instItem = buildingInsts.get(i);
                aryList.add(instItem.typeID);
            }
        }
        return aryList;
    }

    private List<Map<Integer, List<BuildingPropItem>>> getMap(int primaryID, int secondaryID, String strParam)
    {
        List<Map<Integer, List<BuildingPropItem>>> listRet = new ArrayList<Map<Integer, List<BuildingPropItem>>>();
        String mapWhere = " primary_struct_id ="+primaryID+" and (secondary_struct_id="+secondaryID+" or secondary_struct_id=-1)";
        Log.d("IceLi", "Map where = "+mapWhere);
        List<BuildingMap> mapList = FireDistanceDBController.getCtrl().db_getMap(mapWhere);
        if(null == mapList || mapList.isEmpty() )
        {
            return listRet;
        }

        Iterator<BuildingMap> iter = mapList.iterator();
        while(iter.hasNext())
        {
            List<BuildingPropItem> listAdded = new ArrayList<BuildingPropItem>();
            BuildingMap mapItem = iter.next();
            if(null != mapItem.primary_prop_value
                    && null != strParam)
            {
                if(null != mapItem.map_prop_value)
                {
                    if(strParam.contains(mapItem.map_prop_value)) {
                        Log.d("IceLi", "getMap, put key:" + mapItem.map_sruct_id + ", value:" + mapItem.map_prop_value + "to map");
                        BuildingPropItem propItem = new BuildingPropItem();
                        String[] strKeyValues = mapItem.map_prop_value.split("]");
                        for (int i = 0; i < strKeyValues.length; ++i) {
                            String strKeyValue = strKeyValues[i]; // [1,2]
                            BuildingPropOptionItem optionItem = new BuildingPropOptionItem();
                            propItem.calID = Integer.parseInt(String.valueOf(strKeyValue.charAt(strKeyValue.indexOf("[") + 1))); // [1,2
                            optionItem.calValue = Integer.parseInt(String.valueOf(strKeyValue.charAt(strKeyValue.indexOf("[") + 3)));
                            propItem.userSelectOpton = optionItem;
                        }
                        listAdded.add(propItem);
                    }
                }
            }
            else
            {
                BuildingPropItem propItem = new BuildingPropItem();
                if(null != mapItem.map_prop_value) {
                    String[] strKeyValues = mapItem.map_prop_value.split("]");
                    for (int i = 0; i < strKeyValues.length; ++i) {
                        String strKeyValue = strKeyValues[i]; // [1,2]
                        BuildingPropOptionItem optionItem = new BuildingPropOptionItem();
                        propItem.calID = Integer.parseInt(String.valueOf(strKeyValue.charAt(strKeyValue.indexOf("[")+1))); // [1,2
                        optionItem.calValue = Integer.parseInt(String.valueOf(strKeyValue.charAt(strKeyValue.indexOf("[") + 3)));
                        propItem.userSelectOpton = optionItem;
                    }
                }
                listAdded.add(propItem);
            }
            Map<Integer, List<BuildingPropItem>> mapReplace = new HashMap<Integer, List<BuildingPropItem>>();
            mapReplace.put(mapItem.map_sruct_id, listAdded);
            listRet.add(mapReplace);
        }
        return listRet;
    }

    private List<Integer> getCalcIDS(boolean isMainBuilding)
    {
        ArrayList<Integer> aryIDS = new ArrayList<Integer>();
        List<BuildingPropItem> propList = FireDistDataMgr.getPropList(isMainBuilding);
        for (int i=0; i<propList.size(); ++i)
        {
            BuildingPropItem propItem = propList.get(i);
            aryIDS.add(propItem.calID);
        }
        return aryIDS;
    }

    private List<BuildingInstance> getPossibleInst(int mainBuildingSelectID,
                                                   List<BuildingPropItem> listMainBuildingPropItems,
                                                   boolean isNeedReg)
    {
        StringBuffer whereCondication = new StringBuffer();
        StringBuffer orderConcication = new StringBuffer();
        whereCondication.append(FIRE_DISTANCE_TABLE.STRUCT_ID + "=" + mainBuildingSelectID + " ");
        if (null == listMainBuildingPropItems)
        {
            whereCondication
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "1 = \",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "2 = \",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "3 =\",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "4 = \",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "5 = \",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "6 =\",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "7 =\",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "8 = \",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "9 =\",-1,\"")
                    .append(" and " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + "10 =\",-1,\"");
        }
        else {
            for (int i = 0; i < listMainBuildingPropItems.size(); ++i) {
                BuildingPropItem propItem = listMainBuildingPropItems.get(i);
                BuildingPropOptionItem selectOptionItem = propItem.userSelectOpton;
                if (0 == propItem.calID) continue;
                whereCondication.append(" and (" + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE + propItem.calID + " like ");
                if (null == selectOptionItem) {
                    whereCondication.append(" '%,-1,%'" + getSuffixWhereStringForEachInput(propItem.calID));
                } else {
                    if (isNeedReg) {
                        whereCondication.append(" '%," + selectOptionItem.calValue + ",%'" + getSuffixWhereStringForEachInput(propItem.calID));
                    } else {
                        whereCondication.append(" '," + selectOptionItem.calValue + ",' ");
                    }
                }
            }
        }

        Log.d("IceLi", "getPossibleInst::Calculate whrecondicaton = " + whereCondication);

        return FireDistanceDBController.getCtrl().db_getInstanceID(whereCondication.toString(),
                orderConcication.toString(),
                "");
    }

    private String getSuffixWhereStringForEachInput(int callID)
    {
        return " or " + FIRE_DISTANCE_TABLE.STRUCT_PROP_TITLE +callID +" like ',-1,')";
    }

    private String setPropOptionCal(boolean isMainBuilding)
    {
        List<BuildingPropItem> propList= FireDistDataMgr.getPropList(isMainBuilding);
        StringBuffer strBuf = new StringBuffer();
        if(null == propList) return null;

        for (int i=0; i<propList.size(); ++i)
        {
            BuildingPropItem item = propList.get(i);
            if (null != item.userSelectOpton)
            {
                strBuf.append("["+item.calID+","+item.userSelectOpton.calValue+"],");
            }
        }
        Log.d("IceLi", "setPropOptionCal:: map value is:"+strBuf.toString());
        return strBuf.toString();
    }



    boolean isMainBuildingSelected()
    {
        if (FireDistDataMgr.getInst().getSelectMainBuilding()!= FireDistDataMgr.INVALID_ID) {
            return true;
        }
        return false;
    }

    boolean canDirectCalcWithoutNearby()
    {
        if (isMainBuildingSelected() && !FireDistDataMgr.haveNearbyBuildingIDS()) {
            return true;
        }
        return false;
    }

    boolean isNearbyBuildingSelected()
    {
        if (!canDirectCalcWithoutNearby()
                && FireDistDataMgr.getInst().getSelectNearbyBuilding() != FireDistDataMgr.INVALID_ID)
        {
            return true;
        }
        return false;
    }

    void hideCalcresult()
    {
        mBuildingCalcRetLL.setVisibility(View.INVISIBLE);
    }

    void showCalcResult(List<BuildingDistance> listDistance)
    {
        if (null == listDistance || listDistance.isEmpty())
        {
            mBuildingCalcRetLL.setVisibility(View.VISIBLE);
            mBuildingCalcRetCommentsTV.setText("查无规范");
            mOilBuildingCalcRetCommentsTV.setText("查无规范");
            mRemarkTV.setVisibility(View.VISIBLE);
            return;
        }
        mBuildingCalcRetCommentsTV.setText("查无规范");
        mOilBuildingCalcRetCommentsTV.setText("查无规范");
        Iterator<BuildingDistance> iterDistance = listDistance.iterator();
        while(iterDistance.hasNext()) {
            BuildingDistance distance = iterDistance.next();
            double fireResitrct = FireDistDataMgr.getInst().getFireResitrct(distance.fireResitrct, mMainInput, mNearbyInput);
            fireResitrct = Math.abs(fireResitrct);
            if(fireResitrct == 10000.999D)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("请输入较大储罐的直径（D）")
                        .create().show();

                return;
            }
            else if(fireResitrct == 11000.999D)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("请输入较长长度（L）")
                        .create().show();
                return;
            }
            else if(fireResitrct == 12000.999D)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("请输入高度（H）")
                        .create().show();
                return;
            }

            String strDistance = distance.fireResitrctRemark;
            if (fireResitrct != 0.0d && fireResitrct != 99999.990000000005D) {
                DecimalFormat formater = new DecimalFormat("#.00");
                if (fireResitrct < 1.0D) {
                    strDistance += ("0" + formater.format(fireResitrct) + "m");
                } else {
                    strDistance += (formater.format(fireResitrct) + "m");
                }
            }
            if (1 == distance.gbCatetory) {
                mBuildingCalcRetCommentsTV.setText(strDistance);
            } else {
                mOilBuildingCalcRetCommentsTV.setText(strDistance);
            }
            mRemarkTV.setVisibility(View.INVISIBLE);
            mBuildingCalcRetLL.setVisibility(View.VISIBLE);
        }
    }

    List<List<String>> getBuildingSpecComment()
    {
        if(null == mStrInstances) return null;

        List<List<String>> noteList = new ArrayList<List<String>>();
        String str1 = " gb_category=1 and(struct_instance_1 in " + mStrInstances[0] + " or struct_instance_1 is null) and (struct_instance_2 in " + mStrInstances[1] + " or struct_instance_2 is null)";
        String str2 = " gb_category=1 and(struct_instance_1 in " + mStrInstances[1] + " or struct_instance_1 is null) and (struct_instance_2 in " + mStrInstances[0] + " or struct_instance_2 is null)";
        String str3 = " gb_category=2 and(struct_instance_1 in " + mStrInstances[0] + " or struct_instance_1 is null) and (struct_instance_2 in " + mStrInstances[1] + " or struct_instance_2 is null)";
        String str4 = " gb_category=2 and(struct_instance_1 in " + mStrInstances[1] + " or struct_instance_1 is null) and (struct_instance_2 in " + mStrInstances[0] + " or struct_instance_2 is null)";

        List<String> cate1List1 = FireDistanceDBController.getCtrl().db_getNote(str1);
        List<String> cate1List2 = FireDistanceDBController.getCtrl().db_getNote(str2);

        List<String> cate1Merge = mergeValues(cate1List1, cate1List2);
        if(null != cate1Merge)
        {
            noteList.add(cate1Merge);
        }
        else
        {
            noteList.add(new ArrayList<String>());
        }

        List<String> cate2List1 = FireDistanceDBController.getCtrl().db_getNote(str3);
        List<String> cate2List2 = FireDistanceDBController.getCtrl().db_getNote(str4);

        List<String> cate2Merge = mergeValues(cate2List1, cate2List2);
        if(null != cate2Merge)
        {
            noteList.add(cate2Merge);
        }
        else
        {
            noteList.add(new ArrayList<String>());
        }

        return noteList;
    }

    private List<String> mergeValues(List<String> cateList1, List<String> cateList2) {
        List<String> listMerge= new ArrayList<String>();
        if (null != cateList1 && !cateList1.isEmpty()) {
            for (int i = 0; i < cateList1.size(); ++i) {
                String comment = cateList1.get(i);
                if (!listMerge.contains(comment)) {
                    listMerge.add(comment);
                }
            }
        }

        if (null != cateList2 && !cateList2.isEmpty()) {
            for (int i = 0; i < cateList2.size(); ++i) {
                String comment = cateList2.get(i);
                if (!listMerge.contains(comment)) {
                    listMerge.add(comment);
                }
            }
        }
        return listMerge;
    }

    String getDisplayType(boolean isMainBuilding)
    {
        List<BuildingPropItem> listProps;
        if (isMainBuilding)
        {
            if(null == FireDistDataMgr.listMainBuildingsProp
                    || FireDistDataMgr.listMainBuildingsProp.isEmpty())
            {
                return "";
            }
            listProps = FireDistDataMgr.listMainBuildingsProp;
        }
        else
        {
            if(null == FireDistDataMgr.listNearbyBuildingsProp
                    || FireDistDataMgr.listNearbyBuildingsProp.isEmpty())
            {
                return "";
            }
            listProps = FireDistDataMgr.listNearbyBuildingsProp;
        }

        for (int i = 0; i<listProps.size(); ++i)
        {
            BuildingPropItem propItem = listProps.get(i);
            if (1 == propItem.calID
                    && null != propItem.userSelectOpton)
            {
                return propItem.userSelectOpton.displayName;
            }
        }
        return "";
    }

    void showToast()
    {
        Toast.makeText(this, "请选择主要建筑", Toast.LENGTH_SHORT).show();
    }


    class NearbyBuildingClickListener implements View.OnClickListener
    {
        private Context mContext;
        public NearbyBuildingClickListener(Context context)
        {
            mContext = context;
        }
        @Override
        public void onClick(View var1)
        {
            if (FireDistDataMgr.getInst().getSelectNearbyBuilding() != FireDistDataMgr.INVALID_ID)
            {
                FireDistanceBuildingProperties.launchActivity(mContext,
                        FireDistEnum.NEARBY_BUILDING_TYPE, FireDistEnum.FIRE_DIST_ACTIVITY);
            }
            else {
                FireDistanceBuildingActivity.launchActivity(mContext,
                        FireDistEnum.NEARBY_BUILDING_TYPE, FireDistEnum.FIRE_DIST_ACTIVITY);
            }
        }
    }

    private class DistanceComparator implements Comparator<BuildingDistance>
    {
        HashMap<String, Double> mainUserInput;
        HashMap<String, Double> nearbyUserbyInput;

        public DistanceComparator(HashMap<String, Double> mainInput, HashMap<String, Double> nearbyInput)
        {
            mainUserInput = mainInput;
            nearbyUserbyInput = nearbyInput;
        }

        @Override
        public int compare(BuildingDistance dist1, BuildingDistance dist2)
        {
            Log.d("IceLi:", "dist1.fireResitrct="+dist1.fireResitrct+", dist2.fireResitrct"+dist2.fireResitrct);
            if(FireDistDataMgr.getInst().getFireResitrct(dist1.fireResitrct, mainUserInput, nearbyUserbyInput)
                    > FireDistDataMgr.getInst().getFireResitrct(dist2.fireResitrct, mainUserInput, nearbyUserbyInput))
                return 1;
            return -1;
        }
    }
}
