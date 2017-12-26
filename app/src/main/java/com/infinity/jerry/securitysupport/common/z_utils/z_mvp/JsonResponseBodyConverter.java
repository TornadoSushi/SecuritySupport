/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.securitysupport.common.z_utils.z_mvp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by root on 2/14/17.
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private JSONObject jsonObject;

    JsonResponseBodyConverter(JSONObject jsonObject) {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedReader br = new BufferedReader(value.charStream());
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        //buffer.toString()
        Log.d("123", "接收数据:" + buffer.toString());
        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (T) jsonObject;
    }
}
