/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_mvp;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by root on 2/10/17.
 */
public abstract class ZResultSubscriber<T> extends Subscriber<ZCommonEntity<T>> {

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e("subscriber", "custom subscriber onError " + e.toString());
        onErrorZ(e);
    }

    @Override
    public void onNext(ZCommonEntity<T> tZcommonEntity) {
        if (tZcommonEntity.getCode() == 0) {
            onSuccessZ(tZcommonEntity.getData());
        }else{
            onError(new Throwable("nonononono"));
        }
    }

    public abstract void onSuccessZ(T t);

    public abstract void onErrorZ(Throwable throwable);


}
