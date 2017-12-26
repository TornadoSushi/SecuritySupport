package com.infinity.jerry.securitysupport.common.app;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.AccidentDataController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.ChemicalDataController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.DataController;
import com.infinity.jerry.securitysupport.common.otherstuff.basecontroller.FireDistanceDBController;
import com.infinity.jerry.securitysupport.common.z_utils.z_tools.ZShrPrefencs;
import com.tencent.bugly.Bugly;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;
import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import es.dmoral.toasty.Toasty;

/**
 * Created by jerry on 2017/11/13.
 */

public class CommonApplication extends LitePalApplication {

    public static boolean isMeijian = true;
    public static CommonApplication app = null;

    public static DbManager db;

    @Override
    public void onCreate() {
        super.onCreate();
        initEverything();
    }

    private void initEverything() {
        app = this;
        Bugly.init(getApplicationContext(), "14f2ee3a09", false);
        Connector.getDatabase();
        ZShrPrefencs.registApp(this);
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        ZShrPrefencs.registApp(this);
        Toasty.Config.getInstance()
                .setErrorColor(ContextCompat.getColor(this, R.color.tt_error)) // optional
                .setInfoColor(ContextCompat.getColor(this, R.color.tt_info)) // optional
                .setSuccessColor(ContextCompat.getColor(this, R.color.tt_success)) // optional
                .setWarningColor(ContextCompat.getColor(this, R.color.tt_warn)) // optional
                .setTextColor(ContextCompat.getColor(this, R.color.color_white)) // optional
                .tintIcon(true) // optional (apply textColor also to the icon)
                .setToastTypeface(Typeface.DEFAULT) // optional
                .setTextSize(14) // optional
                .apply(); // required
        DataController.initController(this);
        AccidentDataController.initController(this);
        FireDistanceDBController.initController(this);
        ChemicalDataController.initController(this);
        initDb();

    }

    protected void initDb() {
        //本地数据的初始化
        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("ajzf") //设置数据库名
                .setDbVersion(2) //设置数据库版本,每次启动应用时将会检查该版本号,发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreate(DbManager db, TableEntity<?> table) {
                        //balabala...
                    }
                })//设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //balabala...
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下
        db = x.getDb(daoConfig);

    }

}
