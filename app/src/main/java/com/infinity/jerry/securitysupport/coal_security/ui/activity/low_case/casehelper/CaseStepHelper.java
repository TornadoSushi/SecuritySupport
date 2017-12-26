package com.infinity.jerry.securitysupport.coal_security.ui.activity.low_case.casehelper;

import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017/9/29.
 */

public class CaseStepHelper {
    public static List<CaseStepHelper> dataList = null;

    public static List<CaseStepHelper> getListData() {

        if (dataList == null) {
            dataList = new ArrayList<>();
            CaseStepHelper entity1 = new CaseStepHelper();
            entity1.setContent("立案审批");
            //m行政执法案卷（首页） m档案卷内目录 m立案决定书  o移送书 o涉嫌犯罪案件移送书
            List<DocsSurface> docList11 = new ArrayList<>();
            DocsSurface sub11 = new DocsSurface();
            sub11.setDocName("行政执法案卷（首页)");
            sub11.setFinish(false);
            sub11.setHasTo(true);
            sub11.setDocType(DocsConstant.LOW_MAIN);
            docList11.add(sub11);

            DocsSurface sub12 = new DocsSurface();
            sub12.setDocName("档案卷内目录");
            sub12.setFinish(false);
            sub12.setHasTo(true);
            sub12.setDocType(DocsConstant.LOW_INNER);
            docList11.add(sub12);

            DocsSurface sub13 = new DocsSurface();
            sub13.setDocName("立案决定书");
            sub13.setDocType(DocsConstant.ESTAB_DECIDE);
            sub13.setFinish(false);
            sub13.setHasTo(true);
            docList11.add(sub13);

            DocsSurface sub14 = new DocsSurface();
            sub14.setDocName("移送书");
            sub14.setDocType(DocsConstant.TRANS_TO);
            sub14.setFinish(false);
            sub14.setHasTo(false);
            docList11.add(sub14);

            DocsSurface sub15 = new DocsSurface();
            sub15.setDocName("涉嫌犯罪案件移送书");
            sub15.setFinish(false);
            sub15.setDocType(DocsConstant.TRANS_CREMIAL);
            sub15.setHasTo(true);
            docList11.add(sub15);

            entity1.setDocsSurfaces(docList11);
            entity1.setChoose(false);
            dataList.add(entity1);
            //m调查取证笔录 o复查意见书 o撤出作业人员命令书 o责令关闭矿井决定书
            //o暂扣或者吊销煤炭生产许可证决定书 o加强和改善安全监督管理建议书
            //o移送书 涉嫌犯罪案件移送书
            CaseStepHelper entity2 = new CaseStepHelper();
            entity2.setContent("调查取证");
            List<DocsSurface> docList21 = new ArrayList<>();
            DocsSurface sub21 = new DocsSurface();
            sub21.setDocName("调查取证笔录");
            sub21.setDocType(DocsConstant.EVIDENCE_INSP);
            sub21.setFinish(false);
            sub21.setHasTo(true);
            docList21.add(sub21);

            DocsSurface sub22 = new DocsSurface();
            sub22.setDocName("复查意见书");
            sub22.setDocType(DocsConstant.REVIEW_ADVICE);
            sub22.setFinish(false);
            sub22.setHasTo(false);
            docList21.add(sub22);

            DocsSurface sub23 = new DocsSurface();
            sub23.setDocName("撤出作业人员命令书");
            sub23.setDocType(DocsConstant.ORDER_CANCELWORK);
            sub23.setFinish(false);
            sub23.setHasTo(false);
            docList21.add(sub23);

            DocsSurface sub24 = new DocsSurface();
            sub24.setDocName("责令关闭矿井决定书");
            sub24.setFinish(false);
            sub24.setDocType(DocsConstant.CLOSE_COAL);
            sub24.setHasTo(false);
            docList21.add(sub24);

            DocsSurface sub25 = new DocsSurface();
            sub25.setDocName("暂扣或者吊销煤炭生产许可证决定书");
            sub25.setFinish(false);
            sub25.setDocType(DocsConstant.TEMP_CANCEL_LIC);
            sub25.setHasTo(false);
            docList21.add(sub25);

            DocsSurface sub26 = new DocsSurface();
            sub26.setDocName("加强和改善安全监督管理建议书");
            sub26.setFinish(false);
            sub26.setDocType(DocsConstant.BETTER_ADVICE);
            sub26.setHasTo(false);
            docList21.add(sub26);

            DocsSurface sub27 = new DocsSurface();
            sub27.setDocName("移送书");
            sub27.setFinish(false);
            sub27.setDocType(DocsConstant.TRANS_TO);
            sub27.setHasTo(false);
            docList21.add(sub27);

            DocsSurface sub28 = new DocsSurface();
            sub28.setDocName("涉嫌犯罪案件移送书");
            sub28.setFinish(false);
            sub28.setDocType(DocsConstant.TRANS_CREMIAL);
            sub28.setHasTo(false);
            docList21.add(sub28);

            entity2.setDocsSurfaces(docList21);
            entity2.setChoose(false);
            dataList.add(entity2);

            //o移送书 o涉嫌犯罪案件移送书
            CaseStepHelper entity3 = new CaseStepHelper();
            entity3.setContent("案件报批");
            List<DocsSurface> docList31 = new ArrayList<>();
            DocsSurface sub31 = new DocsSurface();
            sub31.setDocName("移送书");
            sub31.setDocType(DocsConstant.TRANS_TO);
            sub31.setFinish(false);
            sub31.setHasTo(false);
            docList31.add(sub31);

            DocsSurface sub32 = new DocsSurface();
            sub32.setDocName("涉嫌犯罪案件移送书");
            sub32.setFinish(false);
            sub32.setDocType(DocsConstant.TRANS_CREMIAL);
            sub32.setHasTo(false);
            docList31.add(sub32);

            entity3.setDocsSurfaces(docList31);
            entity3.setChoose(false);
            dataList.add(entity3);

            //0行政处罚告知书 o移送书 o涉嫌犯罪案件移送书
            CaseStepHelper entity4 = new CaseStepHelper();
            entity4.setContent("事先告知");
            List<DocsSurface> docList41 = new ArrayList<>();
            DocsSurface sub41 = new DocsSurface();
            sub41.setDocName("行政处罚告知书");
            sub41.setDocType(DocsConstant.LOW_PUNISH_TELL);
            sub41.setFinish(false);
            sub41.setHasTo(true);
            docList41.add(sub41);

            DocsSurface sub42 = new DocsSurface();
            sub42.setDocName("移送书");
            sub42.setFinish(false);
            sub42.setDocType(DocsConstant.TRANS_TO);
            sub42.setHasTo(false);
            docList41.add(sub42);

            DocsSurface sub43 = new DocsSurface();
            sub43.setDocName("涉嫌犯罪案件移送书");
            sub43.setFinish(false);
            sub43.setDocType(DocsConstant.TRANS_CREMIAL);
            sub43.setHasTo(false);
            docList41.add(sub43);

            entity4.setDocsSurfaces(docList41);
            entity4.setChoose(false);
            dataList.add(entity4);

            //o行政复议调查取证笔录 m听证笔录 o移送书 o涉嫌犯罪案件移送书
            CaseStepHelper entity5 = new CaseStepHelper();
            entity5.setContent("陈述申辩");
            List<DocsSurface> docList51 = new ArrayList<>();
            DocsSurface sub51 = new DocsSurface();
            sub51.setDocName("行政复议调查取证笔录");
            sub51.setDocType(DocsConstant.LOW_REDIS_INVEST);
            sub51.setFinish(false);
            sub51.setHasTo(false);
            docList51.add(sub51);

            DocsSurface sub52 = new DocsSurface();
            sub52.setDocName("听证笔录");
            sub52.setDocType(DocsConstant.LISTEN_RECORD);
            sub52.setFinish(false);
            sub52.setHasTo(true);
            docList51.add(sub52);

            DocsSurface sub53 = new DocsSurface();
            sub53.setDocName("移送书");
            sub53.setFinish(false);
            sub53.setDocType(DocsConstant.TRANS_TO);
            sub53.setHasTo(false);
            docList51.add(sub53);

            DocsSurface sub54 = new DocsSurface();
            sub54.setDocName("涉嫌犯罪案件移送书");
            sub54.setDocType(DocsConstant.TRANS_CREMIAL);
            sub54.setFinish(false);
            sub54.setHasTo(false);
            docList51.add(sub54);

            entity5.setDocsSurfaces(docList51);
            entity5.setChoose(false);
            dataList.add(entity5);

            //复审确认 o行政复议调查取证笔录
            // m行政复议申请笔录 m行政复议决定书
            // o移送书 o涉嫌犯罪案件移送书
            CaseStepHelper entity6 = new CaseStepHelper();
            entity6.setContent("陈述申辩");
            List<DocsSurface> docList61 = new ArrayList<>();
            DocsSurface sub61 = new DocsSurface();
            sub61.setDocName("行政复议调查取证笔录");
            sub61.setFinish(false);
            sub61.setDocType(DocsConstant.LOW_REDIS_INVEST);
            sub61.setHasTo(false);
            docList61.add(sub61);

            DocsSurface sub62 = new DocsSurface();
            sub62.setDocName("行政复议申请笔录");
            sub62.setFinish(false);
            sub62.setDocType(DocsConstant.LOW_REDIS_APPLY);
            sub62.setHasTo(true);
            docList61.add(sub62);

            DocsSurface sub63 = new DocsSurface();
            sub63.setDocName("行政复议决定书");
            sub63.setFinish(false);
            sub63.setDocType(DocsConstant.LOW_REDIS_DECIDE);
            sub63.setHasTo(true);
            docList61.add(sub63);

            DocsSurface sub64 = new DocsSurface();
            sub64.setDocName("移送书");
            sub64.setFinish(false);
            sub64.setHasTo(false);
            sub64.setDocType(DocsConstant.TRANS_TO);
            docList61.add(sub64);

            DocsSurface sub65 = new DocsSurface();
            sub65.setDocName("涉嫌犯罪案件移送书");
            sub65.setFinish(false);
            sub65.setDocType(DocsConstant.TRANS_CREMIAL);
            sub65.setHasTo(false);
            docList61.add(sub65);

            entity6.setDocsSurfaces(docList61);
            entity6.setChoose(false);
            dataList.add(entity6);

            //处罚执行 m行政处罚决定书 m行政处罚（行政复议）送达收执 o移送书 o强制执行申请书 o涉嫌犯罪案件移送书
            CaseStepHelper entity7 = new CaseStepHelper();
            entity7.setContent("处罚执行");
            List<DocsSurface> docList71 = new ArrayList<>();
            DocsSurface sub71 = new DocsSurface();
            sub71.setDocName("行政处罚决定书");
            sub71.setDocType(DocsConstant.LOW_PUNISH_DECIDE);
            sub71.setFinish(false);
            sub71.setHasTo(true);
            docList71.add(sub71);

            DocsSurface sub72 = new DocsSurface();
            sub72.setDocName("行政处罚（行政复议）送达收执");
            sub72.setFinish(false);
            sub72.setHasTo(true);
            sub72.setDocType(DocsConstant.LOW_PUNISH_SEND);
            docList71.add(sub72);

            DocsSurface sub73 = new DocsSurface();
            sub73.setDocName("移送书");
            sub73.setDocType(DocsConstant.TRANS_TO);
            sub73.setFinish(false);
            sub73.setHasTo(false);
            docList71.add(sub73);

            DocsSurface sub74 = new DocsSurface();
            sub74.setDocName("强制执行申请书");
            sub74.setFinish(false);
            sub74.setDocType(DocsConstant.FORCE_EXCUTE);
            sub74.setHasTo(false);
            docList71.add(sub74);

            DocsSurface sub75 = new DocsSurface();
            sub75.setDocName("涉嫌犯罪案件移送书");
            sub75.setFinish(false);
            sub75.setDocType(DocsConstant.TRANS_CREMIAL);
            sub75.setHasTo(false);
            docList71.add(sub75);

            entity7.setDocsSurfaces(docList71);
            entity7.setChoose(false);
            dataList.add(entity7);

            //结案备案 m行政执法案卷（首页）m档案卷内目录 m案件结案报告 o移送书  o涉嫌犯罪案件移送书
            CaseStepHelper entity8 = new CaseStepHelper();
            entity8.setContent("结案备案");
            List<DocsSurface> docList81 = new ArrayList<>();
            DocsSurface sub81 = new DocsSurface();
            sub81.setDocName("行政执法案卷（首页）");
            sub81.setDocType(DocsConstant.LOW_MAIN);
            sub81.setFinish(false);
            sub81.setHasTo(true);
            docList81.add(sub81);

            DocsSurface sub82 = new DocsSurface();
            sub82.setDocName("档案卷内目录");
            sub82.setDocType(DocsConstant.LOW_INNER);
            sub82.setFinish(false);
            sub82.setHasTo(true);
            docList81.add(sub82);

            DocsSurface sub83 = new DocsSurface();
            sub83.setDocName("案件结案报告");
            sub83.setFinish(false);
            sub83.setDocType(DocsConstant.FINISH_CASE);
            sub83.setHasTo(true);
            docList81.add(sub83);

            DocsSurface sub84 = new DocsSurface();
            sub84.setDocName("移送书");
            sub84.setFinish(false);
            sub84.setDocType(DocsConstant.TRANS_TO);
            sub84.setHasTo(false);
            docList81.add(sub84);

            DocsSurface sub85 = new DocsSurface();
            sub85.setDocName("涉嫌犯罪案件移送书");
            sub85.setFinish(false);
            sub85.setDocType(DocsConstant.TRANS_CREMIAL);
            sub85.setHasTo(false);
            docList81.add(sub85);

            entity8.setDocsSurfaces(docList81);
            entity8.setChoose(false);
            dataList.add(entity8);
        }
        return dataList;

    }

    private int id = -256;
    private int caseId;
    private String content;
    private List<DocsSurface> docsSurfaces;
    private boolean isFinish;
    private boolean isChoose;

    @Override
    public String toString() {
        return "CaseStepHelper{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", content='" + content + '\'' +
                ", docsSurfaces=" + docsSurfaces +
                ", isFinish=" + isFinish +
                ", isChoose=" + isChoose +
                '}';
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<DocsSurface> getDocsSurfaces() {
        return docsSurfaces;
    }

    public void setDocsSurfaces(List<DocsSurface> docsSurfaces) {
        this.docsSurfaces = docsSurfaces;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public static class DocsSurface {
        private int id = -256;
        private String docName;
        private boolean isFinish;
        private boolean isHasTo;
        private int docType;
        private boolean hasThisDoc;

        @Override
        public String toString() {
            return "DocsSurface{" +
                    "id=" + id +
                    ", docName='" + docName + '\'' +
                    ", isFinish=" + isFinish +
                    ", isHasTo=" + isHasTo +
                    ", docType=" + docType +
                    ", hasThisDoc=" + hasThisDoc +
                    '}';
        }

        public boolean isHasThisDoc() {

            return hasThisDoc;
        }

        public void setHasThisDoc(boolean hasThisDoc) {
            this.hasThisDoc = hasThisDoc;
        }

        public int getDocType() {
            return docType;
        }

        public void setDocType(int docType) {
            this.docType = docType;
        }

        public boolean isHasTo() {
            return isHasTo;
        }

        public void setHasTo(boolean hasTo) {
            isHasTo = hasTo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public boolean isFinish() {
            return isFinish;
        }

        public void setFinish(boolean finish) {
            isFinish = finish;
        }
    }
}
