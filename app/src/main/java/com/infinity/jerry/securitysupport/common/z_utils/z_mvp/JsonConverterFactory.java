/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_mvp;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by root on 2/14/17.
 */

public class JsonConverterFactory extends Converter.Factory {

    private JSONObject jsonObject;

    public static JsonConverterFactory create() {
        return create(new JSONObject());
    }

    private static JsonConverterFactory create(JSONObject jsonObject) {
        return new JsonConverterFactory(jsonObject);
    }

    private JsonConverterFactory(JSONObject jsonObject) {
        if (jsonObject == null) throw new NullPointerException("json == null");
        this.jsonObject = jsonObject;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonResponseBodyConverter<>(jsonObject);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new JsonRequestBodyConverter<>(jsonObject);
    }
}
