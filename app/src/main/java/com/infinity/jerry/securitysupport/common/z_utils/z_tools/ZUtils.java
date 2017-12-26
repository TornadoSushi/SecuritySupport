package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.coal_security.constant.CoalConstant;
import com.infinity.jerry.securitysupport.common.app.CommonApplication;
import com.infinity.jerry.securitysupport.common.entity.CaseHelper;
import com.infinity.jerry.securitysupport.common.entity.CoalCheckItem;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/11/15.
 */

public class ZUtils {

    private static List<CoalCheckItem> itemList;

    public static String getAreaByType(int type) {
        String area = "";
        switch (type) {
            case CoalConstant.AREA_EAST:
                area = "渝东监察分局";
                break;
            case CoalConstant.AREA_MIDDLE:
                area = "渝中监察分局";
                break;
            case CoalConstant.AREA_SOUTH:
                area = "渝南监察分局";
                break;
            case CoalConstant.AREA_QIANJIANG:
                area = "黔江办事处";
                break;
        }
        return area;
    }


    public static String getStrFromPlanType(int planType) {
        String str = "";
        switch (planType) {
            case 0:
                str = CommonApplication.app.getApplicationContext().getString(R.string.full_safty_check);
                break;
            case 1:
                str = CommonApplication.app.getApplicationContext().getString(R.string.full_health_check);
                break;
            case 2:
                str = CommonApplication.app.getApplicationContext().getString(R.string.temp_safty_check);
                break;
            case 3:
                str = CommonApplication.app.getApplicationContext().getString(R.string.temp_health_check);
                break;
        }
        return str;
    }

    public static String getExcutePerson() {
        String[] strings = ZShrPrefencs.getInstance().getNameAndPwd();
        if (strings.length == 3) {
            return strings[2];
        }
        return "Jerry";
    }

    public static String getSuperItem(int id) {
        if (itemList == null) {
            itemList = DataSupport.where("parent IS NULL").find(CoalCheckItem.class);
        }

        for (CoalCheckItem item : itemList) {
            if (item.getId() == id) {
                return item.getCheckinItem();
            }
        }
        return "井矿安全检查";
    }

    public static String getLowCaseByStep(int step) {
        String temp = "";
        switch (step) {
            case 0:
                temp = "立案审批";
                break;
            case 1:
                temp = "调查取证";
                break;
            case 2:
                temp = "案件报批";
                break;
            case 3:
                temp = "事先告知";
                break;
            case 4:
                temp = "陈述申辩及听证";
                break;
            case 5:
                temp = "复审确认";
                break;
            case 6:
                temp = "处罚执行";
                break;
            case 7:
                temp = "结案备案";
                break;
        }
        return temp;
    }

    public static List<CaseHelper> getCaseStepList() {
        List<CaseHelper> caseList = new ArrayList<>();

        CaseHelper item = new CaseHelper();
        item.setCaseStep(0);
        item.setStepName("立案审批");
        item.setPass(false);

        CaseHelper item2 = new CaseHelper();
        item2.setCaseStep(0);
        item2.setStepName("调查取证");
        item2.setPass(false);

        CaseHelper item3 = new CaseHelper();
        item3.setCaseStep(0);
        item3.setStepName("案件报批");
        item3.setPass(false);

        CaseHelper item4 = new CaseHelper();
        item4.setCaseStep(0);
        item4.setStepName("事先告知");
        item4.setPass(false);

        CaseHelper item5 = new CaseHelper();
        item5.setCaseStep(0);
        item5.setStepName("陈述申辩及听证");
        item5.setPass(false);

        CaseHelper item6 = new CaseHelper();
        item6.setCaseStep(0);
        item6.setStepName("复审确认");
        item6.setPass(false);

        CaseHelper item7 = new CaseHelper();
        item7.setCaseStep(0);
        item7.setStepName("处罚执行");
        item7.setPass(false);

        CaseHelper item8 = new CaseHelper();
        item8.setCaseStep(0);
        item8.setStepName("结案备案");
        item8.setPass(false);

        caseList.add(item);
        caseList.add(item2);
        caseList.add(item3);
        caseList.add(item4);
        caseList.add(item5);
        caseList.add(item6);
        caseList.add(item7);
        caseList.add(item8);

        return caseList;
    }
}
