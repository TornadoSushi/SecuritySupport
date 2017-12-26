/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_mvp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infinity.jerry.securitysupport.common.z_utils.constant.ConstantPool;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 2/10/17.
 */

public class ZServiceFactory {
    private Gson zGsonDataFormat;
    private static Retrofit retrofit;

    private ZServiceFactory() {
        zGsonDataFormat = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    private static class SingleInstance {
        private static final ZServiceFactory INSTANCE = new ZServiceFactory();
    }

    public static ZServiceFactory getInstance() {
        return SingleInstance.INSTANCE;
    }

    /**
     * Now it's a method to create a service(interface) with T
     */
    public <T> T createService(Class<T> serviceClass) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantPool.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(zGsonDataFormat))
                    .client(getOkHttpClient())
                    .build();

        }
        return retrofit.create(serviceClass);
    }

    private final static long DEFAULT_TIMEOUT = 15;

    // to custom a client for retrofit
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        builder.addInterceptor(new ZCommonIntercepter());
        return builder.build();
    }

}
