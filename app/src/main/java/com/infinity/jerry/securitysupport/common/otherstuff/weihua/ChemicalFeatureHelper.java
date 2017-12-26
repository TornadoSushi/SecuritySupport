package com.infinity.jerry.securitysupport.common.otherstuff.weihua;


import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.ChemicalDataController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by edwardliu on 15/12/25.
 */
public class ChemicalFeatureHelper {
    static final String LH[] = {"状态", "颜色", "气味", "味道", "比重",
            "比重空气", "PH值", "透明度"};
    static final String JK[] = {"胃/泌尿系统", "神经系统", "眼睛", "心血管", "皮肤",
            "呼吸系统", "鼻子", "嘴/喉咙", "耳朵"};

    private static final String LH_TABLE[] = {"STATE", "COLOR", "ODOR", "TASTE", "BIZHONG",
            "ZQMD", "PH", "CLARITY"};
    private static final String JK_TABLE[] = {"GASTRO_URINARY", "NEUROLOGICAL", "EYE", "CARDIOVASCULAR", "SKIN",
            " RESPIRATORY", "NOSE", "MOUTH_THROAT", "EAR"};

    public static List<String> getFeatures(boolean isLH, int index) {
        List<String> features = null;
        ChemicalDataController controller = ChemicalDataController.getController();
        if (isLH) {
            features = controller.db_getFeatures(LH_TABLE[index]);
        } else {
            features = controller.db_getFeatures(JK_TABLE[index]);
        }
        return features;
    }

    private static String queryWhere(HashMap<Integer, String> params, String[] table) {
        StringBuilder where = new StringBuilder();
        int i = 0;
        for (Integer key : params.keySet()) {
            String columName = table[key].toLowerCase();
            String value = params.get(key);
            if (value.contains("/")) {
                String values[] = value.split("/");
                int j = 0;
                for (String val : values) {
                    if (j == 0) {
                        where.append(" (");
                    } else {
                        where.append("%' or ");
                    }
                    where.append(columName);
                    where.append(" like '%");
                    where.append(val);
                    if (j == values.length - 1) {
                        where.append("%')");
                    }
                    ++j;
                }
            } else {
                where.append(columName);
                where.append(" like '%");
                where.append(value);
                where.append("%'");
            }
            ++i;
            if (i < params.size()) {
                where.append(" and ");
            }
        }
        return where.toString();
    }

    public static String generateChemicalQuerySql(HashMap<Integer, String> lh, HashMap<Integer, String> jk) {
        StringBuilder where = new StringBuilder();
        if (lh.isEmpty() && jk.isEmpty()) {
            return null;
        }
        if (!lh.isEmpty()) {
            where.append(queryWhere(lh, LH_TABLE));
        }
        if (!jk.isEmpty()) {
            if (!lh.isEmpty()) {
                where.append(" and ");
            }
            where.append(queryWhere(jk, JK_TABLE));
        }
        return where.toString();
    }
}
