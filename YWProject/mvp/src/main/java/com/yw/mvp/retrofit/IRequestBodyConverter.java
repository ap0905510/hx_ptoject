package com.yw.mvp.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 作者：create by YW
 * 日期：2018.02.08 09:25
 * 描述：
 */

public class IRequestBodyConverter<F> implements Converter<F, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType
            .parse("application/json; charset=UTF-8");
    static final Charset UTF_8 = Charset.forName("UTF-8");

    final Gson gson;
    final TypeAdapter<F> adapter;

    IRequestBodyConverter(Gson gson, TypeAdapter<F> adapter) {
        this.gson = gson;
        this.adapter = adapter;
        System.out.println("#IRequestBodyConverter初始化#");
    }

    @Override
    public RequestBody convert(F value) throws IOException {
        String json = value.toString();
        System.out.println("#加密前#" + json);
        //json = AesEncryptionUtil.encrypt(json);
        System.out.println("#加密后#" + json);
        return RequestBody.create(MEDIA_TYPE, json);
    }
}
