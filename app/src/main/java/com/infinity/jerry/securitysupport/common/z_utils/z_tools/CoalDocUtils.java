package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.infinity.jerry.securitysupport.coal_security.constant.DocsConstant;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/23.
 */

public class CoalDocUtils {

    public static String getDocNameByType(int type) {
        String docName = "";
        switch (type) {
            case DocsConstant.SCENE_INSP:
                docName = "现场检查笔录";
                break;
            case DocsConstant.EVIDENCE_INSP:
                docName = "取证调查笔录";
                break;
            case DocsConstant.SCENE_DEAL:
                docName = "现场处理决定书";
                break;
            case DocsConstant.ORDER_CANCELWORK:
                docName = "撤出作业人员命令书";
                break;
            case DocsConstant.CLOSE_COAL:
                docName = "责令关闭矿井决定书";
                break;
            case DocsConstant.TEMP_CANCEL_LIC:
                docName = "暂扣或吊销煤炭生产许可证决定书";
                break;
            case DocsConstant.BETTER_ADVICE:
                docName = "加强和改善安全监督管理建议书";
                break;
        }
        return docName;
    }


    public static void entryPrinterShare(Context context, String fileName, String assetName,
                                         HashMap<String, String> params, CallBack1 callBack1) {
        String dir = FileUtils.getCacheDir(context);
        String newFileName = fileName + System.currentTimeMillis() + ".doc";
        InputStream abpath;
        try {
            abpath = context.getAssets().open(assetName);
            WordUtils wu = new WordUtils();
            wu.writeDoc(abpath, dir + "/" + newFileName, params);
        } catch (IOException e) {
            Log.e("error", e.toString());
            e.printStackTrace();
        }

        String data_type = "application/doc";
        File file = new File(dir + "/" + newFileName);
        if (file.exists()) {
            try {
                Uri data_uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.dynamixsoftware.printershare");
                intent.setType(data_type);
                intent.setData(data_uri);
                callBack1.callBack(intent);
            } catch (Exception e) {
                Toasty.error(context, "请先安装打印app" + e.toString()).show();
            }
        }
    }

    public static String[] getDocNameAndFileByType(int type) {
        String[] strings = new String[2];

        switch (type) {
            case DocsConstant.LOW_MAIN: //行政执法案卷（首页)

                strings[0] = "行政执法案卷";
                strings[1] = "XingZhengZhiFaAnJuan.doc";
                break;
            case DocsConstant.LOW_INNER://档案卷内目录
                strings[0] = "档案卷内目录";
                strings[1] ="XingZhengJuanNeiMuLu.doc";
                break;
            case DocsConstant.ESTAB_DECIDE://立案决定书
                strings[0] = "立案决定书";
                strings[1] ="LiAnJueDingShu.doc";
                break;
            case DocsConstant.LISTEN_RECORD://听证笔录
                strings[0] = "听证笔录";
                strings[1] ="XingZhengFuYiDiaoChaBiLu.doc";///!!!!!!!!没有？
                break;
            case DocsConstant.LOW_PUNISH_TELL://行政处罚告知书
                strings[0] = "行政处罚告知书";
                strings[1] ="XingZhengChuFaGaoZhiShu.doc";
                break;
            case DocsConstant.LOW_REDIS_INVEST://行政复议调查取证笔录
                strings[0] = "行政复议调查取证笔录";
                strings[1] ="XingZhengFuYiDiaoChaQuZhengBiLu.doc";
                break;
            case DocsConstant.LOW_REDIS_APPLY://行政复议申请笔录
                strings[0] = "行政复议申请笔录";
                strings[1] ="XingZhengFuYiShenQingShu.doc";
                break;
            case DocsConstant.LOW_REDIS_DECIDE://行政复议决定书
                strings[0] = "行政复议决定书";
                strings[1] = "XingZhengFuYiJueDingShu.doc";
                break;
            case DocsConstant.LOW_PUNISH_DECIDE://行政处罚决定书
                strings[0] = "行政处罚决定书";
                strings[1] ="XingZhengChuFaJueDingShu.doc";
                break;
            case DocsConstant.LOW_PUNISH_SEND://行政处罚（行政复议）送达收执
                strings[0] = "行政处罚（行政复议）送达收执";
                strings[1] ="XingZhengChuFaSongDaFuZhidoc.doc";
                break;
            case DocsConstant.FORCE_EXCUTE://强制执行申请书
                strings[0] = "强制执行申请书";
                strings[1] ="QiangZhiZhiXingShenQingShu.doc";
                break;
            case DocsConstant.FINISH_CASE://案件结案报告
                strings[0] = "案件结案报告";
                strings[1] ="AnJianJieAnBaoGao.doc";
                break;
            case DocsConstant.TRANS_TO://移送书
                strings[0] = "移送书";
                strings[1] ="YiSongShu.doc";
                break;
            case DocsConstant.TRANS_CREMIAL://涉嫌犯罪案件移送书
                strings[0] = "涉嫌犯罪案件移送书";
                strings[1] ="SheXianFanZuiYiSongShu.doc";
                break;
        }

        return strings;
    }

}
