package com.infinity.jerry.securitysupport.common.otherstuff;

import android.util.Log;

import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingCategoryItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingPropItem;
import com.infinity.jerry.securitysupport.common.otherstuff.firedistance.BuildingPropOptionItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016-01-06.
 */
public class FireDistDataMgr {
    public  static final int INVALID_ID = -1;
    private static FireDistDataMgr mSelf = null;

    public static List<BuildingCategoryItem> listMainbuildingCate = null;

    public static List<MainBuildingItem> listMainBuildings = null;
    public static List<BuildingPropItem> listMainBuildingsProp = null;

    public static List<Integer> listNearbyBuildingIDS = null;
    public static List<MainBuildingItem> listNearbyBuildings = null;
    public static List<BuildingPropItem> listNearbyBuildingsProp = null;

    private int mSelectMainBuildingID = INVALID_ID;
    private int mSelectNearbyBuildingID = INVALID_ID;

    public static FireDistDataMgr getInst()
    {
        if (null == mSelf)
        {
            mSelf = new FireDistDataMgr();
        }
        return mSelf;
    }

    public static boolean mainBuldingHaveProperties()
    {
        return listMainBuildingsProp == null? false:true;
    }

    public static boolean haveNearbyBuildingIDS()
    {
        if(null == listNearbyBuildingIDS || listNearbyBuildingIDS.isEmpty()) return false;
        return true;
    }

    public Boolean isMainBuildingReady()
    {
        if (null == listMainBuildings || listMainBuildings.isEmpty())
        {
            return false;
        }
        return true;
    }

    public Boolean isMainBuildingCateReady()
    {
        if (null == listMainbuildingCate || listMainbuildingCate.isEmpty())
        {
            return false;
        }
        return true;
    }

    public MainBuildingItem getMainBuildingItemByID(int mainBuildingID)
    {
        if (!isMainBuildingReady()) return new MainBuildingItem();

        for (int i=0; i<listMainBuildings.size(); ++i)
        {
            MainBuildingItem item = listMainBuildings.get(i);
            if (mainBuildingID == item.id)
            {
                return item;
            }
        }
        return new MainBuildingItem();
    }

    public MainBuildingItem getNearbyBuildingItemByID(int nearbyBuildingID)
    {
        for (int i=0; i<listNearbyBuildings.size(); ++i)
        {
            MainBuildingItem item = listNearbyBuildings.get(i);
            if (nearbyBuildingID == item.id)
            {
                return item;
            }
        }
        return new MainBuildingItem();
    }

    public void selectMainBuilding(int id)
    {
        Log.d("IceLi", "Select mainbuilding id = "+id);
        mSelectMainBuildingID = id;
    }

    public int getSelectMainBuilding()
    {
        return mSelectMainBuildingID;
    }

    public void selectNearbyBuilding(int id)
    {
        mSelectNearbyBuildingID = id;
    }

    public int getSelectNearbyBuilding() { return mSelectNearbyBuildingID;}

    public void clearSelectData()
    {
        mSelectMainBuildingID = INVALID_ID;
    }

    public BuildingPropItem getMainBuildingPropItemByID(int propID)
    {
        return getBuildingPropItemByID(listMainBuildingsProp, propID);
    }

    public BuildingPropItem getNearbyBuildingPropItemByID(int propID)
    {
        return getBuildingPropItemByID(listNearbyBuildingsProp, propID);
    }

    private BuildingPropItem getBuildingPropItemByID(List<BuildingPropItem> listItems, int propID)
    {
        if (null == listItems) return new BuildingPropItem();

        for (int i = 0; i<listItems.size(); ++i)
        {
            BuildingPropItem item = listItems.get(i);
            if (item.propId == propID)
            {
                return item;
            }
        }
        return new BuildingPropItem();
    }

    public void insertPropOptions2PropItem(int propID, Boolean isMainbuilding, List<BuildingPropOptionItem> listPropOption)
    {
        List<BuildingPropItem> buildingPropItems;
        if (isMainbuilding)
        {
            buildingPropItems =  listMainBuildingsProp;
        }
        else
        {
            buildingPropItems = listNearbyBuildingsProp;
        }
        for (int i=0; i<buildingPropItems.size(); ++i)
        {
            BuildingPropItem propItem = buildingPropItems.get(i);
            if (propItem.propId == propID)
            {
                propItem.optionItemList = listPropOption;
                break;
            }
        }
    }
    public boolean checkDataOptionChange(boolean isMainbuilding)
    {
        boolean isChanged = false;
        List<BuildingPropItem> buildingPropItems = null;
        if (isMainbuilding) buildingPropItems = listMainBuildingsProp;
        else buildingPropItems = listNearbyBuildingsProp;
        for (int i=0; i<buildingPropItems.size(); ++i) {
            BuildingPropItem checkItem = buildingPropItems.get(i);
            if (0 == checkItem.parentId) continue; // Do not have any rely
            if (null == checkItem.userSelectOpton) continue;

            // The data structure should be
            // PropXXX
            // -- select one option

            // PropYYY
            // -- select one option
            // -- the select option relate to OptionZZZZ/OptionMMMM which locate on PropXXX
            BuildingPropItem fatherPropItem;
            if (isMainbuilding) fatherPropItem = getMainBuildingPropItemByID(checkItem.parentId);
            else fatherPropItem = getNearbyBuildingPropItemByID(checkItem.parentId);
            if (null != fatherPropItem.userSelectOpton && fatherPropItem.userSelectOpton.id != checkItem.userSelectOpton.fatherOptionID) {
                checkItem.userSelectOpton = null;
                isChanged = true;
            } else if (null == fatherPropItem.userSelectOpton && 0 != checkItem.userSelectOpton.fatherOptionID) {
                checkItem.userSelectOpton = null;
                isChanged = true;
            }
        }
            return isChanged;
    }

    public static List<BuildingPropItem> getPropList(boolean isMainBuilding)
    {
        if(isMainBuilding)
            return listMainBuildingsProp;
        else
            return listNearbyBuildingsProp;
    }

    public static List<MainBuildingItem> getBuildingItemList(boolean isMainBuilding)
    {
        if(isMainBuilding)
            return listMainBuildings;
        else
            return listNearbyBuildings;
    }

    public void clearAll()
    {
        clearSelectedMainBuildingID();
        clearSelectedNearbyBuildingID();
        listMainbuildingCate = null;
        listNearbyBuildingIDS = null;
        listMainBuildings = null;
        listNearbyBuildings = null;
    }

    public HashMap<String, Double> getPropUserInputValues(boolean isMainBuilding)
    {
        HashMap<String, Double> userInputHash = new HashMap<String, Double>();
        List<BuildingPropItem> propList = FireDistDataMgr.getPropList(isMainBuilding);

        if(null == propList) return userInputHash;

        Iterator<BuildingPropItem> iter = propList.iterator();
        while(iter.hasNext())
        {
            BuildingPropItem propItem = iter.next();
            switch(propItem.inputType)
            {
                case 10:
                    if(!propItem.userInputValue.isEmpty()) {
                        userInputHash.put("D", Double.parseDouble(propItem.userInputValue));
                    }
                    else {
                        userInputHash.put("D", 0.0d);
                    }
                    Log.d("IceLi", "Case10. put  "+propItem.displayName+" value:"+propItem.userInputValue+" to hashMap");
                    break;
                case 11:
                    if(!propItem.userInputValue.isEmpty()) {
                        userInputHash.put("L", Double.parseDouble(propItem.userInputValue));
                    }
                    else {
                        userInputHash.put("L", 0.0d);
                    }
                    Log.d("IceLi", "Case11. put  "+propItem.displayName+" value:"+propItem.userInputValue+" to hashMap");
                    break;
                case 12:
                    if(!propItem.userInputValue.isEmpty()) {
                        userInputHash.put("H", Double.parseDouble(propItem.userInputValue));
                    }
                    else {
                        userInputHash.put("H", 0.0d);
                    }
                    Log.d("IceLi", "Case12. put  "+propItem.displayName+" value:"+propItem.userInputValue+" to hashMap");
                    break;
                case 14:
                    if(!propItem.userInputValue.isEmpty()) {
                        userInputHash.put("X", Double.parseDouble(propItem.userInputValue));
                    }
                    else {
                        userInputHash.put("X", 0.0d);
                    }
                    Log.d("IceLi", "Case13. put  "+propItem.displayName+" value:"+propItem.userInputValue+" to hashMap");
                    break;
            }
        }
        return  userInputHash;
    }

    public double getFireResitrct(String fireResitrct, HashMap<String, Double> mainUserInput, HashMap<String, Double> nearbyUserbyInput)
    {
        Log.d("IceLi", "getFireResitrct. value = "+fireResitrct);
        double d;
        if(fireResitrct.endsWith("D"))
        {
            Log.d("IceLi", "Compare: meet string end with D. ");
            if(!mainUserInput.containsKey("D"))
            {
                d = nearbyUserbyInput.get("D").doubleValue();
                if(0.0D == d)
                    return Double.valueOf(10000.999D);
            }
            else {
                if(!nearbyUserbyInput.containsKey("D"))
                {
                    d = mainUserInput.get("D");
                }
                else
                {
                    if (mainUserInput.get("D").doubleValue() > nearbyUserbyInput.get("D").doubleValue()) {
                        d = mainUserInput.get("D").doubleValue();
                    } else {
                        d = nearbyUserbyInput.get("D").doubleValue();
                    }
                }
                if (fireResitrct.substring(0, fireResitrct.indexOf("D")).trim().isEmpty()) {
                    Log.d("IceLi", "getFireResitrct return"+d);
                    return d;
                }
            }
            return d * Double.valueOf(fireResitrct.substring(0, fireResitrct.indexOf("D"))).doubleValue();
        }
        else if (fireResitrct.endsWith("L"))
        {
            Log.d("IceLi", "Compare: meet string end with L");
            if(!mainUserInput.containsKey("L"))
            {
                d = nearbyUserbyInput.get("L").doubleValue();
                if(0.0D == d)
                    return Double.valueOf(11000.999D);
            }
            else {
                if(!nearbyUserbyInput.containsKey("L"))
                {
                    d = mainUserInput.get("L");
                }
                else
                {
                    if (mainUserInput.get("L").doubleValue() > nearbyUserbyInput.get("L").doubleValue()) {
                        d = mainUserInput.get("L").doubleValue();
                    } else {
                        d = nearbyUserbyInput.get("L").doubleValue();
                    }
                }
                if (fireResitrct.substring(0, fireResitrct.indexOf("L")).trim().isEmpty()) {
                    return d;
                }
            }
            return d * Double.valueOf(fireResitrct.substring(0, fireResitrct.indexOf("L"))).doubleValue();
        }
        else if (fireResitrct.endsWith("H"))
        {
            Log.d("IceLi", "Compare: meet string end with H");
            if(!mainUserInput.containsKey("H"))
            {
                d = nearbyUserbyInput.get("H").doubleValue();
                if(0.0D == d)
                    return Double.valueOf(12000.999D);
            }
            else {
                if(!nearbyUserbyInput.containsKey("H"))
                {
                    d = mainUserInput.get("H");
                }
                else
                {
                    if (mainUserInput.get("H").doubleValue() > nearbyUserbyInput.get("H").doubleValue()) {
                        d = mainUserInput.get("H").doubleValue();
                    } else {
                        d = nearbyUserbyInput.get("H").doubleValue();
                    }
                }
                if (fireResitrct.substring(0, fireResitrct.indexOf("H")).trim().isEmpty()) {
                    return d;
                }
            }
            return d * Double.valueOf(fireResitrct.substring(0, fireResitrct.indexOf("H"))).doubleValue();
        }
        else if (fireResitrct.endsWith("X"))
        {
            Log.d("IceLi", "Compare: meet string end with X");
            if(!mainUserInput.containsKey("X"))
            {
                d = nearbyUserbyInput.get("X").doubleValue();
                if(0.0D == d)
                    return Double.valueOf(12000.999D);
            }
            else {
                if(!nearbyUserbyInput.containsKey("X"))
                {
                    d = mainUserInput.get("X");
                }
                else
                {
                    if (mainUserInput.get("X").doubleValue() > nearbyUserbyInput.get("X").doubleValue()) {
                        d = mainUserInput.get("X").doubleValue();
                    } else {
                        d = nearbyUserbyInput.get("X").doubleValue();
                    }
                }
                if (fireResitrct.substring(0, fireResitrct.indexOf("X")).trim().isEmpty()) {
                    return d;
                }
            }
            return d * Double.valueOf(fireResitrct.substring(0, fireResitrct.indexOf("X"))).doubleValue();
        }
        else
        {
            Log.d("IceLi", "Compare: directlly parse");
            return Double.parseDouble(fireResitrct);
        }
    }


    public void clearSelectedMainBuildingID()
    {
        mSelectMainBuildingID = INVALID_ID;
    }

    public void clearSelectedNearbyBuildingID()
    {
        mSelectNearbyBuildingID = INVALID_ID;
    }
}
