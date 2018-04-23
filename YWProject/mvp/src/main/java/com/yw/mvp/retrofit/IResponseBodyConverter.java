package com.yw.mvp.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 作者：create by YW
 * 日期：2018.02.08 09:39
 * 描述：
 */
public class IResponseBodyConverter<F> implements Converter<ResponseBody, F> {

    private final Gson gson;
    private final TypeAdapter<F> adapter;

    IResponseBodyConverter(Gson gson, TypeAdapter<F> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public F convert(ResponseBody value) throws IOException {
        String string = value.toString();
        System.out.println("响应#解密前#" + string);
        //string = AesEncryptionUtil.decrypt(string);
        System.out.println("响应#解密后#" + string);
        return adapter.fromJson(string);
    }
}
