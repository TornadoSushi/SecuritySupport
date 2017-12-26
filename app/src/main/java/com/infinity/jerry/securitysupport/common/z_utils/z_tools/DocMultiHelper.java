package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import java.util.Map;

/**
 * Created by jerry on 2017/10/10.
 */

public class DocMultiHelper {

    private static final String DOC_MAIN_TITLE = "重 庆 市 煤 矿 安 全 监 管";
    private static final String DOC_SUB_TITLE = "文  书  续  页";
    private static final String DOC_COUNT_2 = "  第  页  共  页";
    private static final String DOC_SIGN = "签名：_______________  日期：_______________\n";
    private static final String DOC_REMARK1 = "备注：本文书一式两联，一联存档，一联将被检查单位（或个人）;";
    private static final String BASE_FILED = "${content}";
    private static final int BASE_SPLIT_COUNT = 780;
    public static final String SOMETHING_SHIT = "_______________________________________________________________________________________ ";

    public static Map<String, String> setPageInfo2(Map<String, String> param) {
        if (param == null) {
            throw new NullPointerException("no param go away");
        }
        param.put("${page2Main}", DOC_MAIN_TITLE);
        param.put("${page2Sub}", DOC_SUB_TITLE);
        param.put("${page2Sign}", DOC_SIGN);
        param.put("${page2Remark}", DOC_REMARK1);

        return param;
    }

    private static Map<String, String> setCurrentPageInfo(Map<String, String> param, int type, int totalPages) {
        if (param == null) {
            throw new NullPointerException("no param go away");
        }
        String filed = "${page" + (type + 2) + "Count}";
        String under = "${page" + (type + 2) + "Under}";
        String main = "${page" + (type + 2) + "Main}";
        String sub = "${page" + (type + 2) + "Sub}";
        String sign = "${page" + (type + 2) + "Sign}";
        String remark = "${page" + (type + 2) + "Remark}";
        param.put(filed, "第 " + (type + 2) + " 页 " + "共 " + totalPages + " 页");
        param.put(under, SOMETHING_SHIT);
        param.put(main, DOC_MAIN_TITLE);
        param.put(sub, DOC_SUB_TITLE);
        param.put(sign, DOC_SIGN);
        param.put(remark, DOC_REMARK1);

        return param;
    }

    public static Map<String, String> setSliptContent(Map<String, String> param, String content, int totalCount) {
        if (param == null) {
            throw new NullPointerException("no param go away");
        }
        return setSliptContent(param, content, totalCount, BASE_FILED);
    }

    public static Map<String, String> setSliptContent(Map<String, String> param, String content, int totalCount, String filed) {
        if (param == null) {
            throw new NullPointerException("no param go away");
        }
        StringBuilder contentBuilder = new StringBuilder(content);
        int length = content.length();
        if (length <= totalCount) {
            contentBuilder.append("\t\t\t\t");
            param.put(filed, contentBuilder.toString() + "（下无续页）");
            param.put("${linex}", SOMETHING_SHIT);
            param.put("${page1Count}", "第 1 页 " + "共 1 页");
        } else {//开始分页
            int pages = (length - totalCount) / BASE_SPLIT_COUNT + 1;//分页数
            param.put("${page1Count}", "第 1 页 " + "共 " + (pages + 1) + " 页");

            param.put(filed, content.substring(0, totalCount - 1) + "（下接续页第2页）");

            param.put("${linex}", SOMETHING_SHIT);
            for (int i = 0; i < pages; i++) {
                String pageParame = "${page" + (i + 2) + "Other}";
                if (i == pages - 1) {
                    String lastString = content.substring(totalCount - 1
                            + BASE_SPLIT_COUNT * i);
                    StringBuilder lastBuilder = new StringBuilder(lastString);
                    lastBuilder.append("（下无续页）(本行以下为空白) ");
                    param.put(pageParame, lastBuilder.toString());
                } else {
                    param.put(pageParame, content.substring(totalCount - 1
                            + BASE_SPLIT_COUNT * i, (BASE_SPLIT_COUNT * (i + 1)) + totalCount - 1) + "（下接续页第 " + (i + 2) + " 页）");
                }
                setCurrentPageInfo(param, i, pages + 1);
            }
        }
        return param;
    }

    public static void setPageEmptyInfo(Map<String, String> params) {
        if (params == null) {
            throw new NullPointerException("no param go away");
        }
        for (int i = 2; i < 15; i++) {
            String main = "${page" + i + "Main}";
            String sub = "${page" + i + "Sub}";
            String count = "${page" + i + "Count}";
            String under = "${page" + i + "Under}";
            String other = "${page" + i + "Other}";
            String sign = "${page" + i + "Sign}";
            String remark = "${page" + i + "Remark}";
            params.put(main, "");
            params.put(sub, "");
            params.put(count, "");
            params.put(under, "");
            params.put(other, "");
            params.put(sign, "");
            params.put(remark, "");
        }

    }

}
