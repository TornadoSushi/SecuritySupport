package com.infinity.jerry.securitysupport.common.otherstuff.firedistance;
import java.util.List;

/**
 * Created by IceLi on 2016-01-06.
 */
public class BuildingPropItem {
    public int propId;
    public int inputType;
    public String displayName;
    public int displayOrder;
    public String comment;
    public int mainBuildingId;
    public int parentId;
    public int calID;
    public BuildingPropOptionItem userSelectOpton = null;
    public String userInputValue = "";
    public List<BuildingPropOptionItem> optionItemList;
}
