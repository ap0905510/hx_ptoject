package com.yw.mvp.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：create by YW
 * 日期：2018.01.04 15:14
 * 描述：
 */
public class RetrofitFactory {

    private static final String TAG = "YW";
    public static final String BASE_URL = "http://api.ihuxin.net/";
    private static final long TIMEOUT = 30;

    private static RetrofitFactory ourInstance;

    private RetrofitService mRetrofitService;

    public static RetrofitFactory newInstance() {
        if (ourInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (ourInstance == null) {
                    ourInstance = new RetrofitFactory();
                }
            }
        }
        return ourInstance;
    }

    private RetrofitFactory() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(HEADER) //header
                .addInterceptor(new LoggingInterceptor()) //logging
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofitService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(GsonConverterFactory.create(gson)) //Gson转换器
                .addConverterFactory(IGsonFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加Retrofit到RxJava的转换器
                .client(httpClient)
                .build()
                .create(RetrofitService.class);

    }

    public RetrofitService getRetrofitService() {
        return mRetrofitService;
    }

    Interceptor HEADER = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("token", "123456");
        return chain.proceed(builder.build());
    };

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            Log.e(TAG, String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理

            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.e("YW", String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers(),
                    response.protocol()));
            return response;
        }
    }

    Gson gson = new GsonBuilder().setLenient().create();
}
