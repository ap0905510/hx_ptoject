package com.yw.mvp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 作者：create by YW
 * 日期：2018.02.08 09:36
 * 描述：gson
 */
public class IGsonFactory extends Converter.Factory {

    public static IGsonFactory create() {
        return create(new GsonBuilder().setLenient().create());
    }

    public static IGsonFactory create(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson == null");
        return new IGsonFactory(gson);
    }

    private final Gson gson;

    private IGsonFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        System.out.println("#接收响应回调#");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new IResponseBodyConverter<>(gson, adapter); // 响应
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        System.out.println("#发起请求#");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new IRequestBodyConverter<>(gson, adapter); // 请求
    }
}
