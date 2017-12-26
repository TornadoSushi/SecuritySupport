package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.infinity.jerry.securitysupport.R;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack0;
import com.infinity.jerry.securitysupport.common.z_utils.z_callback.CallBack1;

/**
 * Created by jerry on 2017/11/15.
 */

public class ZDialogUtils {

    public static void showDialog(Context context, String msg, final CallBack0 callBack0) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(context.getString(R.string.ensure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack0.callBack();
                    }
                }).setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create().show();
    }

    public static void showDialog2(Context context, final String[] items, final String title, final CallBack1 callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.callBack(which);
                    }
                });
        builder.create().show();
    }
}
